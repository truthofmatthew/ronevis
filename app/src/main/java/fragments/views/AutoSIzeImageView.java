package fragments.views;
/**
 * Created by mt.karimi on 4/23/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AutoSIzeImageView extends ImageView {
    //    private BGResizeListener orl = null;
    private int width = 0;
    private int height = 0;
    private Float mCropYCenterOffsetPct = 1f;
    private Float mCropXCenterOffsetPct = 1f;

    public AutoSIzeImageView(Context context) {
        super(context);
    }
//    public void SetOnResizeListener(BGResizeListener orlExt) {
//        orl = orlExt;
//    }
//
//    @Override
//    public void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
//        super.onSizeChanged(xNew, yNew, xOld, yOld);
//        if (orl != null) {
//            orl.OnResize(getId(), xNew, yNew, xOld, yOld);
//        }
//    }

    public AutoSIzeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSIzeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getDrawable() != null) {
            int OrgX = getDrawable().getIntrinsicHeight();
            int OrgY = getDrawable().getIntrinsicWidth();
            if (OrgX < OrgY) {
                width = MeasureSpec.getSize(widthMeasureSpec);
                height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
            }
            if (OrgX > OrgY) {
                height = MeasureSpec.getSize(heightMeasureSpec);
                width = height * getDrawable().getIntrinsicWidth() / getDrawable().getIntrinsicHeight();
            }
            if (OrgX == OrgY) {
                width = MeasureSpec.getSize(widthMeasureSpec);
                height = (int) Math.ceil((float) width * (float) getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth());
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
//        Matrix matrix = getImageMatrix();
//        float scaleFactor = getWidth()/(float)getDrawable().getIntrinsicWidth();
//        float scaleFactorH = getHeight()/(float)getDrawable().getIntrinsicHeight();
//        matrix.setScale(scaleFactor/2, scaleFactorH/2, 0, 0);
//        setImageMatrix(matrix);
        Drawable d = this.getDrawable();
        if (d != null) {
            int dwidth = d.getIntrinsicWidth();
            int dheight = d.getIntrinsicHeight();
            Matrix m = new Matrix();
            int vwidth = getWidth() - this.getPaddingLeft() - this.getPaddingRight();
            int vheight = getHeight() - this.getPaddingTop() - this.getPaddingBottom();
            float scale;
            float dx = 0, dy = 0;
            if (dwidth * vheight > vwidth * dheight) {
                float cropXCenterOffsetPct = mCropXCenterOffsetPct != null ?
                        mCropXCenterOffsetPct.floatValue() : 0.5f;
                scale = (float) vheight / (float) dheight;
                dx = (vwidth - dwidth * scale) * cropXCenterOffsetPct;
            } else {
                float cropYCenterOffsetPct = mCropYCenterOffsetPct != null ?
                        mCropYCenterOffsetPct.floatValue() : 0.5f;
                scale = (float) vwidth / (float) dwidth;
                dy = (vheight - dheight * scale) * cropYCenterOffsetPct;
            }
            m.setScale(scale, scale);
            m.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
            this.setImageMatrix(m);
        }
        return super.setFrame(l, t, r, b);
    }
//    @Override
//    protected boolean setFrame(int frameLeft, int frameTop, int frameRight, int frameBottom) {
//        float frameWidth = frameRight - frameLeft;
//        float frameHeight = frameBottom - frameTop;
//
//        float originalImageWidth = (float)getDrawable().getIntrinsicWidth();
//        float originalImageHeight = (float)getDrawable().getIntrinsicHeight();
//
//        float usedScaleFactor = 1;
//
//        if((frameWidth > originalImageWidth) || (frameHeight > originalImageHeight)) {
//            // If frame is bigger than image
//            // => Crop it, keep aspect ratio and position it at the bottom and center horizontally
//
//            float fitHorizontallyScaleFactor = frameWidth/originalImageWidth;
//            float fitVerticallyScaleFactor = frameHeight/originalImageHeight;
//
//            usedScaleFactor = Math.min(fitHorizontallyScaleFactor, fitVerticallyScaleFactor);
//        }
//
//        float newImageWidth = originalImageWidth * usedScaleFactor;
//        float newImageHeight = originalImageHeight * usedScaleFactor;
//
//        Matrix matrix = getImageMatrix();
//        matrix.setScale(usedScaleFactor, usedScaleFactor, 0, 0); // Replaces the old matrix completly
////comment matrix.postTranslate if you want crop from TOP
//        matrix.postTranslate((frameWidth - newImageWidth)/2 , (frameHeight - newImageHeight)/2);
//        setImageMatrix(matrix);
//        return super.setFrame(frameLeft, frameTop, frameRight, frameBottom);
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        try {
//            setImageDrawable(null);
//            ((BitmapDrawable) getDrawable()).getBitmap().recycle();
//        } catch (Exception ignored) {
//        }
//    }
}