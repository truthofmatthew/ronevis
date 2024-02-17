package fragments.views;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class CacheableBitmapDrawable extends BitmapDrawable {
    public static final int SOURCE_UNKNOWN = -1;
    public static final int SOURCE_NEW = 0;
    public static final int SOURCE_INBITMAP = 1;
    private static final String LOG_TAG = "CacheableBitmapDrawable";
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private final String mUrl;
    private final BitmapLruCache.RecyclePolicy mRecyclePolicy;
    private final int mMemorySize;
    private final int mSource;
    private int mDisplayingCount;
    private boolean mHasBeenDisplayed;
    private int mCacheCount;
    private Runnable mCheckStateRunnable;
    private Throwable mStackTraceWhenRecycled;

    CacheableBitmapDrawable(String url, Resources resources, Bitmap bitmap,
                            BitmapLruCache.RecyclePolicy recyclePolicy, int source) {
        super(resources, bitmap);
        mMemorySize = null != bitmap ? (bitmap.getRowBytes() * bitmap.getHeight()) : 0;
        mUrl = url;
        mRecyclePolicy = recyclePolicy;
        mDisplayingCount = 0;
        mCacheCount = 0;
        mSource = source;
    }

    @Override
    public void draw(Canvas canvas) {
        try {
            super.draw(canvas);
        } catch (RuntimeException re) {
            if (null != mStackTraceWhenRecycled) mStackTraceWhenRecycled.printStackTrace();
            throw re;
        }
    }

    int getMemorySize() {
        return mMemorySize;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getSource() {
        return mSource;
    }

    public synchronized boolean isBitmapValid() {
        Bitmap bitmap = getBitmap();
        return null != bitmap && !bitmap.isRecycled();
    }

    public synchronized boolean isBitmapMutable() {
        Bitmap bitmap = getBitmap();
        return null != bitmap && bitmap.isMutable();
    }

    public synchronized boolean isBeingDisplayed() {
        return mDisplayingCount > 0;
    }

    public synchronized boolean isReferencedByCache() {
        return mCacheCount > 0;
    }

    public synchronized void setBeingUsed(boolean beingUsed) {
        if (beingUsed) {
            mDisplayingCount++;
            mHasBeenDisplayed = true;
        } else {
            mDisplayingCount--;
        }
        checkState();
    }

    synchronized void setCached(boolean added) {
        if (added) {
            mCacheCount++;
        } else {
            mCacheCount--;
        }
        checkState();
    }

    private void cancelCheckStateCallback() {
        if (null != mCheckStateRunnable) {
            if (Constants.DEBUG) {
                Log.d(LOG_TAG, "Cancelling checkState() callback for: " + mUrl);
            }
            sHandler.removeCallbacks(mCheckStateRunnable);
            mCheckStateRunnable = null;
        }
    }

    private void checkState() {
        checkState(false);
    }

    private synchronized void checkState(final boolean ignoreBeenDisplayed) {
        if (Constants.DEBUG) {
            Log.d(LOG_TAG, String.format(
                    "checkState(). Been Displayed: %b, Displaying: %d, Caching: %d, URL: %s",
                    mHasBeenDisplayed, mDisplayingCount, mCacheCount, mUrl));
        }
        if (!mRecyclePolicy.canRecycle()) {
            return;
        }
        cancelCheckStateCallback();
        if (mCacheCount <= 0 && mDisplayingCount <= 0 && isBitmapValid()) {
            if (mHasBeenDisplayed || ignoreBeenDisplayed) {
                if (Constants.DEBUG) {
                    Log.d(LOG_TAG, "Recycling bitmap with url: " + mUrl);
                }
                mStackTraceWhenRecycled = new Throwable("Recycled Bitmap Method Stack");
                getBitmap().recycle();
            } else {
                if (Constants.DEBUG) {
                    Log.d(LOG_TAG,
                            "Unused Bitmap which hasn't been displayed, delaying recycle(): "
                                    + mUrl);
                }
                mCheckStateRunnable = new CheckStateRunnable(this);
                sHandler.postDelayed(mCheckStateRunnable,
                        Constants.UNUSED_DRAWABLE_RECYCLE_DELAY_MS);
            }
        }
    }

    private static final class CheckStateRunnable
            extends WeakReferenceRunnable<CacheableBitmapDrawable> {
        public CheckStateRunnable(CacheableBitmapDrawable object) {
            super(object);
        }

        @Override
        public void run(CacheableBitmapDrawable object) {
            object.checkState(true);
        }
    }
}