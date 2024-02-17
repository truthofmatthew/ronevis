package fragments.views;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import androidx.collection.LruCache;

final class BitmapMemoryLruCache extends LruCache<String, CacheableBitmapDrawable> {
    private final Set<SoftReference<CacheableBitmapDrawable>> mRemovedEntries;
    private final BitmapLruCache.RecyclePolicy mRecyclePolicy;

    BitmapMemoryLruCache(int maxSize, BitmapLruCache.RecyclePolicy policy) {
        super(maxSize);
        mRecyclePolicy = policy;
        mRemovedEntries = policy.canInBitmap()
                ? Collections.synchronizedSet(new HashSet<SoftReference<CacheableBitmapDrawable>>())
                : null;
    }

    CacheableBitmapDrawable put(CacheableBitmapDrawable value) {
        if (null != value) {
            value.setCached(true);
            return put(value.getUrl(), value);
        }
        return null;
    }

    BitmapLruCache.RecyclePolicy getRecyclePolicy() {
        return mRecyclePolicy;
    }

    @Override
    protected int sizeOf(String key, CacheableBitmapDrawable value) {
        return value.getMemorySize();
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, CacheableBitmapDrawable oldValue,
                                CacheableBitmapDrawable newValue) {
        oldValue.setCached(false);
        if (mRemovedEntries != null && oldValue.isBitmapValid() && oldValue.isBitmapMutable()) {
            synchronized (mRemovedEntries) {
                mRemovedEntries.add(new SoftReference<>(oldValue));
            }
        }
    }

    Bitmap getBitmapFromRemoved(final int width, final int height) {
        if (mRemovedEntries == null) {
            return null;
        }
        Bitmap result = null;
        synchronized (mRemovedEntries) {
            final Iterator<SoftReference<CacheableBitmapDrawable>> it = mRemovedEntries.iterator();
            while (it.hasNext()) {
                CacheableBitmapDrawable value = it.next().get();
                if (value != null && value.isBitmapValid() && value.isBitmapMutable()) {
                    if (value.getIntrinsicWidth() == width
                            && value.getIntrinsicHeight() == height) {
                        it.remove();
                        result = value.getBitmap();
                        break;
                    }
                } else {
                    it.remove();
                }
            }
        }
        return result;
    }

    void trimMemory() {
        final Set<Entry<String, CacheableBitmapDrawable>> values = snapshot().entrySet();
        for (Entry<String, CacheableBitmapDrawable> entry : values) {
            CacheableBitmapDrawable value = entry.getValue();
            if (null == value || !value.isBeingDisplayed()) {
                remove(entry.getKey());
            }
        }
    }
}