package fragments.lisetener;

import android.graphics.Point;
import android.view.View;

import activities.MainActivity;

/**
 * Created by mt.karimi on 4/25/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class PositionChangeListenerUtil {
    public static final LayoutPositionListener imageViewPosition = new LayoutPositionListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            super.onLayoutChange(v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom);
            if (MainActivity.mainInstance().ImageViewMap.get(v.getId()) != null) {
                MainActivity.mainInstance().ImageViewMap.get(v.getId()).setImageViewPosition(new Point(left, top));
            }
        }
    };
}
