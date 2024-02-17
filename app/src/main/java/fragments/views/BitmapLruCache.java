package fragments.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class BitmapLruCache {
    private static final int DISK_CACHE_FLUSH_DELAY_SECS = 5;
    private File mTempDir;
    private Resources mResources;
    private BitmapMemoryLruCache mMemoryCache;
    private RecyclePolicy mRecyclePolicy;
    private DiskLruCache mDiskCache;
    private HashMap<String, ReentrantLock> mDiskCacheEditLocks;
    private ScheduledThreadPoolExecutor mDiskCacheFlusherExecutor;
    private DiskCacheFlushRunnable mDiskCacheFlusherRunnable;
    private ScheduledFuture<?> mDiskCacheFuture;

    private BitmapLruCache(Context context) {
        if (null != context) {
            context = context.getApplicationContext();
            mTempDir = context.getCacheDir();
            mResources = context.getResources();
        }
    }

    private static void checkNotOnMainThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException(
                    "This method should not be called from the main/UI thread.");
        }
    }

    private static String transformUrlForDiskCacheKey(String url) {
        return Md5.encode(url);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void addInBitmapOption(BitmapFactory.Options opts, Bitmap inBitmap) {
        opts.inBitmap = inBitmap;
    }

    public boolean contains(String url) {
        return containsInMemoryCache(url) || containsInDiskCache(url);
    }

    private boolean containsInDiskCache(String url) {
        if (null != mDiskCache) {
            checkNotOnMainThread();
            try {
                return null != mDiskCache.get(transformUrlForDiskCacheKey(url));
            } catch (IOException ignored) {
            }
        }
        return false;
    }

    private boolean containsInMemoryCache(String url) {
        return null != mMemoryCache && null != mMemoryCache.get(url);
    }

    public CacheableBitmapDrawable get(String url) {
        return get(url, null);
    }

    private CacheableBitmapDrawable get(String url, BitmapFactory.Options decodeOpts) {
        CacheableBitmapDrawable result;
        result = getFromMemoryCache(url);
        if (null == result) {
            result = getFromDiskCache(url, decodeOpts);
        }
        return result;
    }

    private CacheableBitmapDrawable getFromDiskCache(final String url,
                                                     final BitmapFactory.Options decodeOpts) {
        CacheableBitmapDrawable result = null;
        if (null != mDiskCache) {
            checkNotOnMainThread();
            try {
                final String key = transformUrlForDiskCacheKey(url);
                result = decodeBitmap(new SnapshotInputStreamProvider(key), url, decodeOpts);
                if (null != result) {
                    if (null != mMemoryCache) {
                        mMemoryCache.put(result);
                    }
                } else {
                    mDiskCache.remove(key);
                    scheduleDiskCacheFlush();
                }
            } catch (IOException ignored) {
            }
        }
        return result;
    }

    private CacheableBitmapDrawable getFromMemoryCache(final String url) {
        CacheableBitmapDrawable result = null;
        if (null != mMemoryCache) {
            synchronized (mMemoryCache) {
                result = mMemoryCache.get(url);
                if (null != result && !result.isBitmapValid()) {
                    mMemoryCache.remove(url);
                    result = null;
                }
            }
        }
        return result;
    }

    public boolean isDiskCacheEnabled() {
        return null != mDiskCache;
    }

    public boolean isMemoryCacheEnabled() {
        return null != mMemoryCache;
    }

    public CacheableBitmapDrawable put(final String url, final Bitmap bitmap) {
        return put(url, bitmap, Bitmap.CompressFormat.PNG, 100);
    }

    private CacheableBitmapDrawable put(final String url, final Bitmap bitmap,
                                        Bitmap.CompressFormat compressFormat, int compressQuality) {
        CacheableBitmapDrawable d = new CacheableBitmapDrawable(url, mResources, bitmap,
                mRecyclePolicy, CacheableBitmapDrawable.SOURCE_UNKNOWN);
        if (null != mMemoryCache) {
            mMemoryCache.put(d);
        }
        if (null != mDiskCache) {
            checkNotOnMainThread();
            final String key = transformUrlForDiskCacheKey(url);
            final ReentrantLock lock = getLockForDiskCacheEdit(key);
            lock.lock();
            OutputStream os = null;
            try {
                DiskLruCache.Editor editor = mDiskCache.edit(key);
                os = editor.newOutputStream(0);
                bitmap.compress(compressFormat, compressQuality, os);
                os.flush();
                editor.commit();
            } catch (IOException e) {
                Log.e(Constants.LOG_TAG, "Error while writing to disk cache", e);
            } finally {
                IoUtils.closeStream(os);
                lock.unlock();
                scheduleDiskCacheFlush();
            }
        }
        return d;
    }

    public CacheableBitmapDrawable put(final String url, final InputStream inputStream) {
        return put(url, inputStream, null);
    }

    private CacheableBitmapDrawable put(final String url, final InputStream inputStream,
                                        final BitmapFactory.Options decodeOpts) {
        checkNotOnMainThread();
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile("bitmapcache_", null, mTempDir);
            IoUtils.copy(inputStream, tmpFile);
        } catch (IOException e) {
            Log.e(Constants.LOG_TAG, "Error writing to saving stream to temp file: " + url, e);
        }
        CacheableBitmapDrawable d = null;
        if (null != tmpFile) {
            d = decodeBitmap(new FileInputStreamProvider(tmpFile), url, decodeOpts);
            if (d != null) {
                if (null != mMemoryCache) {
                    d.setCached(true);
                    mMemoryCache.put(d.getUrl(), d);
                }
                if (null != mDiskCache) {
                    final String key = transformUrlForDiskCacheKey(url);
                    final ReentrantLock lock = getLockForDiskCacheEdit(url);
                    lock.lock();
                    try {
                        DiskLruCache.Editor editor = mDiskCache.edit(key);
                        IoUtils.copy(tmpFile, editor.newOutputStream(0));
                        editor.commit();
                    } catch (IOException e) {
                        Log.e(Constants.LOG_TAG, "Error writing to disk cache. URL: " + url, e);
                    } finally {
                        lock.unlock();
                        scheduleDiskCacheFlush();
                    }
                }
            }
            tmpFile.delete();
        }
        return d;
    }

    public void remove(String url) {
        if (null != mMemoryCache) {
            mMemoryCache.remove(url);
        }
        if (null != mDiskCache) {
            checkNotOnMainThread();
            try {
                mDiskCache.remove(transformUrlForDiskCacheKey(url));
                scheduleDiskCacheFlush();
            } catch (IOException ignored) {
            }
        }
    }

    public void trimMemory() {
        if (null != mMemoryCache) {
            mMemoryCache.trimMemory();
        }
    }

    private synchronized void setDiskCache(DiskLruCache diskCache) {
        mDiskCache = diskCache;
        if (null != diskCache) {
            mDiskCacheEditLocks = new HashMap<>();
            mDiskCacheFlusherExecutor = new ScheduledThreadPoolExecutor(1);
            mDiskCacheFlusherRunnable = new DiskCacheFlushRunnable(diskCache);
        }
    }

    private void setMemoryCache(BitmapMemoryLruCache memoryCache) {
        mMemoryCache = memoryCache;
        mRecyclePolicy = memoryCache.getRecyclePolicy();
    }

    private ReentrantLock getLockForDiskCacheEdit(String url) {
        synchronized (mDiskCacheEditLocks) {
            ReentrantLock lock = mDiskCacheEditLocks.get(url);
            if (null == lock) {
                lock = new ReentrantLock();
                mDiskCacheEditLocks.put(url, lock);
            }
            return lock;
        }
    }

    private void scheduleDiskCacheFlush() {
        if (null != mDiskCacheFuture) {
            mDiskCacheFuture.cancel(false);
        }
        mDiskCacheFuture = mDiskCacheFlusherExecutor
                .schedule(mDiskCacheFlusherRunnable, DISK_CACHE_FLUSH_DELAY_SECS,
                        TimeUnit.SECONDS);
    }

    private CacheableBitmapDrawable decodeBitmap(InputStreamProvider ip, String url,
                                                 BitmapFactory.Options opts) {
        Bitmap bm = null;
        InputStream is = null;
        int source = CacheableBitmapDrawable.SOURCE_NEW;
        try {
            if (mRecyclePolicy.canInBitmap()) {
                if (opts == null) {
                    opts = new BitmapFactory.Options();
                }
                if (opts.inSampleSize <= 1) {
                    opts.inSampleSize = 1;
                    if (addInBitmapOptions(ip, opts)) {
                        source = CacheableBitmapDrawable.SOURCE_INBITMAP;
                    }
                }
            }
            is = ip.getInputStream();
            bm = BitmapFactory.decodeStream(is, null, opts);
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Unable to decode stream", e);
        } finally {
            IoUtils.closeStream(is);
        }
        if (bm != null) {
            return new CacheableBitmapDrawable(url, mResources, bm, mRecyclePolicy, source);
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private boolean addInBitmapOptions(InputStreamProvider ip, BitmapFactory.Options opts) {
        final InputStream is = ip.getInputStream();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, opts);
        IoUtils.closeStream(is);
        opts.inJustDecodeBounds = false;
        opts.inMutable = true;
        Bitmap reusableBm = mMemoryCache.getBitmapFromRemoved(opts.outWidth, opts.outHeight);
        if (reusableBm != null) {
            if (Constants.DEBUG) {
                Log.i(Constants.LOG_TAG, "Using inBitmap");
            }
            addInBitmapOption(opts, reusableBm);
            return true;
        }
        return false;
    }

    public enum RecyclePolicy {
        DISABLED,
        PRE_HONEYCOMB_ONLY,
        ALWAYS;

        boolean canInBitmap() {
            switch (this) {
                case PRE_HONEYCOMB_ONLY:
                case DISABLED:
                    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
            }
            return false;
        }

        boolean canRecycle() {
            switch (this) {
                case DISABLED:
                    return false;
                case PRE_HONEYCOMB_ONLY:
                    return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;
                case ALWAYS:
                    return true;
            }
            return false;
        }
    }

    interface InputStreamProvider {
        InputStream getInputStream();
    }

    public final static class Builder {
        static final int MEGABYTE = 1024 * 1024;
        static final float DEFAULT_MEMORY_CACHE_HEAP_RATIO = 1f / 8f;
        static final float MAX_MEMORY_CACHE_HEAP_RATIO = 0.75f;
        static final int DEFAULT_DISK_CACHE_MAX_SIZE_MB = 10;
        static final int DEFAULT_MEM_CACHE_MAX_SIZE_MB = 3;
        static final RecyclePolicy DEFAULT_RECYCLE_POLICY = RecyclePolicy.PRE_HONEYCOMB_ONLY;
        static final float DEFAULT_MEMORY_CACHE_HEAP_PERCENTAGE = DEFAULT_MEMORY_CACHE_HEAP_RATIO
                * 100;
        static final float MAX_MEMORY_CACHE_HEAP_PERCENTAGE = MAX_MEMORY_CACHE_HEAP_RATIO * 100;
        private final Context mContext;
        private boolean mDiskCacheEnabled;
        private File mDiskCacheLocation;
        private long mDiskCacheMaxSize;
        private boolean mMemoryCacheEnabled;
        private int mMemoryCacheMaxSize;
        private RecyclePolicy mRecyclePolicy;

        public Builder() {
            this(null);
        }

        public Builder(Context context) {
            mContext = context;
            mDiskCacheMaxSize = DEFAULT_DISK_CACHE_MAX_SIZE_MB * MEGABYTE;
            mMemoryCacheEnabled = true;
            mMemoryCacheMaxSize = DEFAULT_MEM_CACHE_MAX_SIZE_MB * MEGABYTE;
            mRecyclePolicy = DEFAULT_RECYCLE_POLICY;
        }

        private static long getHeapSize() {
            return Runtime.getRuntime().maxMemory();
        }

        public BitmapLruCache build() {
            final BitmapLruCache cache = new BitmapLruCache(mContext);
            if (isValidOptionsForMemoryCache()) {
                if (Constants.DEBUG) {
                    Log.d("BitmapLruCache.Builder", "Creating Memory Cache");
                }
                cache.setMemoryCache(new BitmapMemoryLruCache(mMemoryCacheMaxSize, mRecyclePolicy));
            }
            if (isValidOptionsForDiskCache()) {
                new AsyncTask<Void, Void, DiskLruCache>() {
                    @Override
                    protected DiskLruCache doInBackground(Void... params) {
                        try {
                            return DiskLruCache.open(mDiskCacheLocation, 0, 1, mDiskCacheMaxSize);
                        } catch (IOException ignored) {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(DiskLruCache result) {
                        cache.setDiskCache(result);
                    }
                }.execute();
            }
            return cache;
        }

        public Builder setDiskCacheEnabled(boolean enabled) {
            mDiskCacheEnabled = enabled;
            return this;
        }

        public Builder setDiskCacheLocation(File location) {
            mDiskCacheLocation = location;
            return this;
        }

        public Builder setDiskCacheMaxSize(long maxSize) {
            mDiskCacheMaxSize = maxSize;
            return this;
        }

        public Builder setMemoryCacheEnabled(boolean enabled) {
            mMemoryCacheEnabled = enabled;
            return this;
        }

        public Builder setMemoryCacheMaxSize(int size) {
            mMemoryCacheMaxSize = size;
            return this;
        }

        public Builder setMemoryCacheMaxSizeUsingHeapSize() {
            return setMemoryCacheMaxSizeUsingHeapSize(DEFAULT_MEMORY_CACHE_HEAP_RATIO);
        }

        public Builder setMemoryCacheMaxSizeUsingHeapSize(float percentageOfHeap) {
            int size = Math
                    .round(getHeapSize() * Math.min(percentageOfHeap, MAX_MEMORY_CACHE_HEAP_RATIO));
            return setMemoryCacheMaxSize(size);
        }

        public Builder setRecyclePolicy(RecyclePolicy recyclePolicy) {
            if (null == recyclePolicy) {
                throw new IllegalArgumentException("The recycle policy can not be null");
            }
            mRecyclePolicy = recyclePolicy;
            return this;
        }

        private boolean isValidOptionsForDiskCache() {
            boolean valid = mDiskCacheEnabled;
            if (valid) {
                if (null == mDiskCacheLocation) {
                    Log.i(Constants.LOG_TAG,
                            "Disk Cache has been enabled, but no location given. Please call setDiskCacheLocation(...)");
                    valid = false;
                } else if (!mDiskCacheLocation.canWrite()) {
                    Log.i(Constants.LOG_TAG,
                            "Disk Cache Location is not write-able, disabling disk caching.");
                    valid = false;
                }
            }
            return valid;
        }

        private boolean isValidOptionsForMemoryCache() {
            return mMemoryCacheEnabled && mMemoryCacheMaxSize > 0;
        }
    }

    static final class DiskCacheFlushRunnable implements Runnable {
        private final DiskLruCache mDiskCache;

        public DiskCacheFlushRunnable(DiskLruCache cache) {
            mDiskCache = cache;
        }

        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            if (Constants.DEBUG) {
                Log.d(Constants.LOG_TAG, "Flushing Disk Cache");
            }
            try {
                mDiskCache.flush();
            } catch (IOException ignored) {
            }
        }
    }

    static class FileInputStreamProvider implements InputStreamProvider {
        final File mFile;

        FileInputStreamProvider(File file) {
            mFile = file;
        }

        @Override
        public InputStream getInputStream() {
            try {
                return new FileInputStream(mFile);
            } catch (FileNotFoundException e) {
                Log.e(Constants.LOG_TAG, "Could not decode file: " + mFile.getAbsolutePath(), e);
            }
            return null;
        }
    }

    final class SnapshotInputStreamProvider implements InputStreamProvider {
        final String mKey;

        SnapshotInputStreamProvider(String key) {
            mKey = key;
        }

        @Override
        public InputStream getInputStream() {
            try {
                DiskLruCache.Snapshot snapshot = mDiskCache.get(mKey);
                if (snapshot != null) {
                    return snapshot.getInputStream(0);
                }
            } catch (IOException e) {
                Log.e(Constants.LOG_TAG, "Could open disk cache for url: " + mKey, e);
            }
            return null;
        }
    }
}