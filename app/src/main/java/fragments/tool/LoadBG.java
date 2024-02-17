package fragments.tool;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * Created by mt.karimi on 6/25/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class LoadBG extends AsyncTask<Object, Void, Drawable> {
    private final WeakReference<ViewGroup> imageViewReference;
    private String path;

    public LoadBG(ViewGroup imv, String path) {
        this.path = path;
        imageViewReference = new WeakReference<>(imv);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Drawable doInBackground(Object... params) {
        return new BitmapDrawable(BitmapFactory.decodeFile(path));
    }

    @Override
    protected void onPostExecute(Drawable result) {
        ViewGroup imageView = imageViewReference.get();
        if (imageView != null) {
            imageView.setBackgroundDrawable(result);
        }
    }
}