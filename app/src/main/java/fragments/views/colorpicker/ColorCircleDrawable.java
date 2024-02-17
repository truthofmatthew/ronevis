package fragments.views.colorpicker;
/**
 * Created by mt.karimi on 9/11/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class ColorCircleDrawable extends Drawable {
    private final Paint mPaint;
    private int mRadius = 0;

    public ColorCircleDrawable(final int color) {
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(color);
    }

    @Override
    public void draw(final Canvas canvas) {
        final Rect bounds = getBounds();
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), mRadius, mPaint);
    }

    @Override
    protected void onBoundsChange(final Rect bounds) {
        super.onBoundsChange(bounds);
        mRadius = Math.min(bounds.width(), bounds.height()) / 2;
    }

    @Override
    public void setAlpha(final int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(final ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}