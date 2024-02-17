package fragments.lisetener;

import java.util.Locale;

import activities.MainActivity;

/**
 * Created by mt.karimi on 4/25/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class SizeChangeListenerUtil {
    private static float MIN_SCALE = 0.5f;
    private static float MAX_SCALE = 1.2f;
    private static int xNew;
    private static int yNew;
    private static int xOld;
    private static int yOld;
    public static final LayoutResizeListener FrlResized = new LayoutResizeListener() {
        @Override
        public void OnResize(int id, int xNew, int yNew, int xOld, int yOld) {
            SizeChangeListenerUtil.xNew = xNew;
            SizeChangeListenerUtil.yNew = yNew;
            SizeChangeListenerUtil.xOld = xOld;
            SizeChangeListenerUtil.yOld = yOld;
            if (MainActivity.mainInstance().mTitleSub != null) {
                MainActivity.mainInstance().mTitleSub.setText(String.format(Locale.ENGLISH, "W: %dpx | H: %dpx ", SizeChangeListenerUtil.xNew, SizeChangeListenerUtil.yNew));
            }
//            MainActivity.mainInstance().backGroundProperties.setBackGroundSize(new Point(SizeChangeListenerUtil.xNew, SizeChangeListenerUtil.yNew));
        }
    };

    public static int getxNew() {
        return xNew;
    }

    public static int getyNew() {
        return yNew;
    }

    public static int getxOld() {
        return xOld;
    }

    public static int getyOld() {
        return yOld;
    }
}
