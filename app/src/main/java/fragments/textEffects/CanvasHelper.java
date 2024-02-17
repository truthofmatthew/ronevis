package fragments.textEffects;

import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by mt.karimi on 07/11/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class CanvasHelper {
    static public Path RoundedRect(float left, float top, float right, float bottom, float rx, float ry, boolean conformToOriginalPost) {
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));
        path.moveTo(right, top + ry);
        path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        path.rLineTo(-widthMinusCorners, 0);
        path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        path.rLineTo(0, heightMinusCorners);
        if (conformToOriginalPost) {
            path.rLineTo(0, ry);
            path.rLineTo(width, 0);
            path.rLineTo(0, -ry);
        } else {
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
            path.rLineTo(widthMinusCorners, 0);
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        }
        path.rLineTo(0, -heightMinusCorners);
        path.close();//Given close, last lineto can be removed.
        return path;
    }
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    static public Path RoundedRect(float left, float top, float right, float bottom, float rx, float ry, boolean conformToOriginalPost) {
//        Path path = new Path();
//        if (rx < 0) rx = 0;
//        if (ry < 0) ry = 0;
//        float width = right - left;
//        float height = bottom - top;
//        if (rx > width/2) rx = width/2;
//        if (ry > height/2) ry = height/2;
//        float widthMinusCorners = (width - (2 * rx));
//        float heightMinusCorners = (height - (2 * ry));
//
//        path.moveTo(right, top + ry);
//        path.arcTo(right - 2*rx, top, right, top + 2*ry, 0, -90, false); //top-right-corner
//        path.rLineTo(-widthMinusCorners, 0);
//        path.arcTo(left, top, left + 2*rx, top + 2*ry, 270, -90, false);//top-left corner.
//        path.rLineTo(0, heightMinusCorners);
//        if (conformToOriginalPost) {
//            path.rLineTo(0, ry);
//            path.rLineTo(width, 0);
//            path.rLineTo(0, -ry);
//        }
//        else {
//            path.arcTo(left, bottom - 2 * ry, left + 2 * rx, bottom, 180, -90, false); //bottom-left corner
//            path.rLineTo(widthMinusCorners, 0);
//            path.arcTo(right - 2 * rx, bottom - 2 * ry, right, bottom, 90, -90, false); //bottom-right corner
//        }
//
//        path.rLineTo(0, -heightMinusCorners);
//
//        path.close();//Given close, last lineto can be removed.
//        return path;
//    }

    public static Path composeRoundedRectPath(RectF rect, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        Path path = new Path();
        topLeftRadius = topLeftRadius < 0 ? 0 : topLeftRadius;
        topRightRadius = topRightRadius < 0 ? 0 : topRightRadius;
        bottomLeftRadius = bottomLeftRadius < 0 ? 0 : bottomLeftRadius;
        bottomRightRadius = bottomRightRadius < 0 ? 0 : bottomRightRadius;
        path.moveTo(rect.left + topLeftRadius / 2, rect.top);
        path.lineTo(rect.right - topRightRadius / 2, rect.top);
        path.quadTo(rect.right, rect.top, rect.right, rect.top + topRightRadius / 2);
        path.lineTo(rect.right, rect.bottom - bottomRightRadius / 2);
        path.quadTo(rect.right, rect.bottom, rect.right - bottomRightRadius / 2, rect.bottom);
        path.lineTo(rect.left + bottomLeftRadius / 2, rect.bottom);
        path.quadTo(rect.left, rect.bottom, rect.left, rect.bottom - bottomLeftRadius / 2);
        path.lineTo(rect.left, rect.top + topLeftRadius / 2);
        path.quadTo(rect.left, rect.top, rect.left + topLeftRadius / 2, rect.top);
        path.close();
        return path;
    }
}
