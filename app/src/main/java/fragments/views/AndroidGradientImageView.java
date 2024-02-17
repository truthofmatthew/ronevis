package fragments.views;
/**
 * Created by mt.karimi on 8/31/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 2016. 6. 13..
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class AndroidGradientImageView extends ImageView {
    int[] colors = null;
    float[] offsets = null;
    Shader gradient = null;
    Matrix rotateMatrix = null;
    Paint gradientPaint = null;
    private float startX = 0f;
    private float startY = 0f;
    private float widthRatio = 1.0f;
    private float heightRatio = 1.0f;
    private float rotate = 0.0f;
    private int startColor = Color.parseColor("#00000000");
    private int endColor = Color.parseColor("#FF000000");
    private int middleColor = -1;
    private float gradientAlpha = 1.0f;
    private float startOffset = 0.0f;
    private float endOffset = 1.0f;
    private float middleOffset = 0.5f;

    public AndroidGradientImageView(Context context) {
        this(context, null);
    }

    public AndroidGradientImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AndroidGradientImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AndroidGradientImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs == null)
            return;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AndroidGradientImageViewAttrs);
        startX = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_x, startX);
        startY = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_y, startY);
        widthRatio = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_width, widthRatio);
        heightRatio = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_height, heightRatio);
        rotate = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_rotate, rotate);
        startColor = array.getColor(R.styleable.AndroidGradientImageViewAttrs_giv_startColor, startColor);
        endColor = array.getColor(R.styleable.AndroidGradientImageViewAttrs_giv_endColor, endColor);
        middleColor = array.getColor(R.styleable.AndroidGradientImageViewAttrs_giv_middleColor, middleColor);
        startOffset = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_startOffset, startOffset);
        endOffset = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_endOffset, endOffset);
        middleOffset = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_middleOffset, middleOffset);
        gradientAlpha = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_alpha, gradientAlpha);
        array.recycle();
        if (middleColor == -1) {
            colors = new int[]{startColor, endColor};
            offsets = new float[]{startOffset, endOffset};
        } else {
            colors = new int[]{startColor, middleColor, endColor};
            offsets = new float[]{startOffset, middleOffset, endOffset};
        }
        gradientPaint = new Paint();
        rotateMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = startX * getWidth();
        float top = startY * getHeight();
        float right = left + widthRatio * getWidth();
        float bottom = top + heightRatio * getHeight();
        if (gradient == null) {
//            gradient = new LinearGradient(
//                    left, top,
//                    right, bottom,
//                    colors,
//                    offsets,
//                    Shader.TileMode.CLAMP);
            gradient = new LinearGradient(
                    0, top,
                    0, bottom,
                    colors,
                    offsets,
                    Shader.TileMode.CLAMP);
        }
        rotateMatrix.setRotate(rotate, getWidth() / 2, getHeight() / 2);
        gradient.setLocalMatrix(rotateMatrix);
        gradientPaint.setShader(gradient);
        gradientPaint.setAlpha((int) (gradientAlpha * 255));
        canvas.drawRect(left, top, right, bottom, gradientPaint);
    }

    /**
     * Provide get/set methods for Property Animation
     */
    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
        gradient = null;
        postInvalidate();
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
        gradient = null;
        postInvalidate();
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
        gradient = null;
        postInvalidate();
    }

    public float getWidthRatio() {
        return widthRatio;
    }

    public void setWidthRatio(float widthRatio) {
        this.widthRatio = widthRatio;
        gradient = null;
        postInvalidate();
    }

    public float getHeightRatio() {
        return heightRatio;
    }

    public void setHeightRatio(float heightRatio) {
        this.heightRatio = heightRatio;
        gradient = null;
        postInvalidate();
    }

    public float getGradientAlpha() {
        return gradientAlpha;
    }

    public void setGradientAlpha(float gradientAlpha) {
        this.gradientAlpha = gradientAlpha;
        postInvalidate();
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
        gradient = null;
        postInvalidate();
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
        gradient = null;
        postInvalidate();
    }

    public int getMiddleColor() {
        return middleColor;
    }

    public void setMiddleColor(int middleColor) {
        this.middleColor = middleColor;
        gradient = null;
        postInvalidate();
    }

    public float getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(float startOffset) {
        this.startOffset = startOffset;
        offsets = new float[]{startOffset, middleOffset, endOffset};
        gradient = null;
        postInvalidate();
    }

    public float getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(float endOffset) {
        this.endOffset = endOffset;
        gradient = null;
        postInvalidate();
    }

    public float getMiddleOffset() {
        return middleOffset;
    }

    public void setMiddleOffset(float middleOffset) {
        this.middleOffset = middleOffset;
        offsets = new float[]{startOffset, middleOffset, endOffset};
        gradient = null;
        postInvalidate();
    }

    public void setStartMiddleOffset(float startOffset, float middleOffset) {
        this.middleOffset = middleOffset;
        this.startOffset = startOffset;
        offsets = new float[]{startOffset, middleOffset, endOffset};
        gradient = null;
        postInvalidate();
    }
//    private Bitmap blurBitmap;
//
//
//    public Bitmap getBlurBitmap() {
//        return blurBitmap;
//    }
//
//    public void setBlurBitmap(Bitmap blurBitmap) {
//        this.blurBitmap = blurBitmap;
//    }
//    public void setBlurAnimate() {
//        Bitmap bm;
//        if(this.getDrawable() instanceof BitmapDrawable)
//            bm = ((BitmapDrawable) this.getDrawable()).getBitmap();
//        else {
//            bm = Ion.with(this).getBitmap();
//        }
//
//        TransitionDrawable transition;
//        Drawable[] layers = new Drawable[2];
//        layers[0] = new BitmapDrawable(this.getResources(),bm);
//        layers[1] = new BitmapDrawable(this.getResources(),NativeStackBlur.process(bm,20));
//        transition = new TransitionDrawable(layers);
//        transition.setCrossFadeEnabled(true);
//        transition.startTransition(500);
//
//        this.setImageDrawable(transition);
//    }
//
//
//    public void setBlur() {
//        Bitmap bm;
//        if(this.getDrawable() instanceof BitmapDrawable)
//            bm = ((BitmapDrawable) this.getDrawable()).getBitmap();
//        else {
//            bm = Ion.with(this).getBitmap();
//        }
//
////        TransitionDrawable transition;
////        Drawable[] layers = new Drawable[2];
////        layers[0] = new BitmapDrawable(this.getResources(),bm);
////        layers[1] = new BitmapDrawable(this.getResources(),NativeStackBlur.process(bm,10));
////        transition = new TransitionDrawable(layers);
////        transition.setCrossFadeEnabled(true);
////        transition.startTransition(130);
//
//        this.setImageDrawable(new BitmapDrawable(this.getResources(),NativeStackBlur.process(bm,20)));
//
//
//    }
}