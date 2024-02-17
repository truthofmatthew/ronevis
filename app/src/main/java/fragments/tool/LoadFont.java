package fragments.tool;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 6/25/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class LoadFont extends AsyncTask<Object, Void, Drawable> {
    TransitionDrawable transition;
    Drawable[] layers = new Drawable[2];
    Drawable drawable;
    private ImageView imv;
    private String path;
    private boolean isBacks;

    public LoadFont(ImageView imv, String path, boolean isBacks) {
        this.imv = imv;
        this.path = path;
        this.isBacks = isBacks;
    }

    @Override
    protected void onPreExecute() {
        layers[0] = Util.getDrawable(R.drawable.empty_ronevis);
        imv.setImageDrawable(Util.getDrawable(R.drawable.empty_ronevis));
    }

    @Override
    protected Drawable doInBackground(Object... params) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = isBacks ? 3 : 2;
        o.inDither = true;
        //        return BitmapFactory.decodeFile(path, o);
        return drawable = new BitmapDrawable(BitmapFactory.decodeFile(path, o));
    }

    @Override
    protected void onPostExecute(Drawable result) {
        layers[1] = result;
        transition = new TransitionDrawable(layers);
        transition.startTransition(230);
        imv.setImageDrawable(transition);
    }
}