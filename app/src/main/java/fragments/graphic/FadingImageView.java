package fragments.graphic;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import fragments.tool.Util;

/**
 * Created by mt.karimi on 11/25/13.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class FadingImageView extends ImageView {
    private final Paint mPaint = new Paint();
    private Shader mShader;
    private int mBackgroundColor;
    private boolean mFadeEnabled = true;
    private FadeSide mFadeSide = FadeSide.START;

    public FadingImageView(Context context) {
        super(context);
    }

    public FadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FadingImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setBackgroundColor(int color, FadeSide orientation) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        mBackgroundColor = Color.argb(255, r, g, b);
        mFadeSide = orientation;
        invalidate();
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setImageBitmap(Bitmap bitmap) {
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        if (Util.getSDK(16)) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mFadeSide == FadeSide.START) {
            mShader = new LinearGradient(0, h, w / 3, h, mBackgroundColor, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        } else if (mFadeSide == FadeSide.END) {
            mShader = new LinearGradient(2 * w / 3, h, w, h, Color.TRANSPARENT, mBackgroundColor, Shader.TileMode.CLAMP);
        } else if (mFadeSide == FadeSide.TOP) {
            mShader = new LinearGradient(w, 0, w, h / 3, mBackgroundColor, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        } else {
            mShader = new LinearGradient(w, 2 * h / 3, w, h, Color.TRANSPARENT, mBackgroundColor, Shader.TileMode.CLAMP);
        }
    }

    public void setFadeEnabled(boolean enabled) {
        mFadeEnabled = enabled;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShader != null && mFadeEnabled) {
            mPaint.setShader(mShader);
            canvas.drawPaint(mPaint);
        }
    }

    public enum FadeSide {
        START, END, TOP, Gravity, BOTTOM
    }
}