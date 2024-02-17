package fragments.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class CacheableIV extends ImageView {
    private final static Matrix savedMatrix = new Matrix();
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private static final PointF start = new PointF();
    private static final PointF mid = new PointF();
    public static Drawable drawable;
    private static int _xDelta;
    private static int _yDelta;
    private static int mode = NONE;
    private static float oldDist = 1f;
    private Matrix matrix = new Matrix();
    private int actW;
    private int actH;

    public CacheableIV(Context context) {
        super(context);
    }

    public CacheableIV(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CacheableIV(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private static void onDrawableSet(Drawable drawable) {
        if (drawable instanceof CacheableBitmapDrawable) {
            ((CacheableBitmapDrawable) drawable).setBeingUsed(true);
        }
    }

    private static void onDrawableUnset(final Drawable drawable) {
        if (drawable instanceof CacheableBitmapDrawable) {
            ((CacheableBitmapDrawable) drawable).setBeingUsed(false);
        }
    }

    private static float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private static void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        final Drawable previousDrawable = getDrawable();
        super.setImageDrawable(drawable);
        if (drawable != previousDrawable) {
            onDrawableSet(drawable);
            onDrawableUnset(previousDrawable);
        }
    }

    @Override
    public void setImageResource(int resId) {
        final Drawable previousDrawable = getDrawable();
        super.setImageResource(resId);
        onDrawableUnset(previousDrawable);
    }

    @Override
    public void setImageURI(Uri uri) {
        final Drawable previousDrawable = getDrawable();
        super.setImageURI(uri);
        onDrawableUnset(previousDrawable);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float[] f = new float[9];
        getImageMatrix().getValues(f);
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];
        final Drawable d = getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();
        actW = Math.round(origW * scaleX);
        actH = Math.round(origH * scaleY);
        setMeasuredDimension(actW, actH);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        float width = r - l;
        float height = b - t;
        matrix = getImageMatrix();
        float scaleFactor, scaleFactorWidth, scaleFactorHeight;
        scaleFactorWidth = width / (float) getDrawable().getIntrinsicWidth();
        scaleFactorHeight = height / (float) getDrawable().getIntrinsicHeight();
        if (scaleFactorHeight > scaleFactorWidth) {
            scaleFactor = scaleFactorHeight;
        } else {
            scaleFactor = scaleFactorWidth;
        }
        matrix.setScale(scaleFactor, scaleFactor, 0, 0);
        setImageMatrix(matrix);
        return super.setFrame(l, t, r, b);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
    }
}