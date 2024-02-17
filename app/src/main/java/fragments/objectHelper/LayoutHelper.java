package fragments.objectHelper;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

public class LayoutHelper {
    private static int widthLayout;
    private static int heightLayout;

    public static void LayoutParamSetter(ViewGroup changeLayout, ViewGroup.LayoutParams parentLayoutParams, Boolean wrap) {
//        changeLayout.requestLayout();
        if (wrap) {
            parentLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            parentLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            parentLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            parentLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
//        parentLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        parentLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        changeLayout.setLayoutParams(parentLayoutParams);
        changeLayout.invalidate();
        changeLayout.requestLayout();
    }

    public static void LayoutParamSetter(ViewGroup changeLayout, ViewGroup.LayoutParams parentLayoutParams, int width, int height) {
        changeLayout.requestLayout();
        parentLayoutParams.width = width;
        parentLayoutParams.height = height;
        changeLayout.setLayoutParams(parentLayoutParams);
        changeLayout.invalidate();
        changeLayout.requestLayout();
    }

    public static Rect GetLayoutMargine(final View ViewToMargine) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) ViewToMargine.getLayoutParams();
        return new Rect(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin);
    }
}
