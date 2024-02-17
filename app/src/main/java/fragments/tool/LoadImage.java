package fragments.tool;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 6/25/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class LoadImage extends AsyncTask<Object, Void, Drawable> {
    private final WeakReference<ImageView> imageViewReference;
    TransitionDrawable transition;
    Drawable[] layers = new Drawable[2];
    Drawable drawable;
    private String tag;
    private String path;
    private boolean isBacks;
    private boolean running = true;

    public LoadImage(ImageView imv, String path, boolean isBacks, String tag) {
        this.tag = tag;
        this.path = path;
        this.isBacks = isBacks;
        imageViewReference = new WeakReference<>(imv);
        ImageView imageView = imageViewReference.get();
    }

    @Override
    protected void onCancelled() {
        running = false;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Drawable doInBackground(Object... params) {
        if (running) {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inSampleSize = isBacks ? 3 : 2;
            o.inDither = true;
            return drawable = new BitmapDrawable(BitmapFactory.decodeFile(path, o));
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Drawable result) {
        ImageView imageView = imageViewReference.get();
        if (imageView != null) {
            if (imageView.getTag().toString().equals(tag)) {
                layers[0] = Util.getDrawable(R.drawable.empty_ronevis);
                layers[1] = result;
                transition = new TransitionDrawable(layers);
                transition.setCrossFadeEnabled(true);
                transition.startTransition(230);
                imageView.setImageDrawable(transition);
            } else {
                Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.empty_ronevis);
                imageView.setImageDrawable(placeholder);
            }
        }
    }
}