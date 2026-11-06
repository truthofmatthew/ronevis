package fragments.imageHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;

import fragments.FireHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// Loads store images over HTTPS. Replaces Ion, whose TLS code crashes the app on current Android.
public final class RemoteImage {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final LruCache<String, Bitmap> CACHE = new LruCache<String, Bitmap>(16 * 1024 * 1024) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    private RemoteImage() {
    }

    public static void load(final ImageView view, final String url, int placeholder) {
        view.setTag(url); // recycled list rows: only the latest request may set the image
        Bitmap cached = url == null ? null : CACHE.get(url);
        if (cached != null) {
            view.setImageBitmap(cached);
            return;
        }
        view.setImageResource(placeholder);
        if (url == null || url.isEmpty()) return;
        CLIENT.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                FireHelper.report(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (!response.isSuccessful() || response.body() == null) return;
                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bitmap == null) return;
                    CACHE.put(url, bitmap);
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            if (url.equals(view.getTag())) view.setImageBitmap(bitmap);
                        }
                    });
                } finally {
                    response.close();
                }
            }
        });
    }
}
