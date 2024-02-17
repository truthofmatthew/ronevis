package fragments.views.horizontalwheelview;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

class Utils {
    static int convertToPx(int dp, Resources resources) {
        DisplayMetrics dm = resources.getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }
}
