package fragments.animation;
/**
 * Created by mt.karimi on 4/20/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;

public class TypingDotsDrawable extends Drawable {
    private static final float density = 1;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float[] scales = new float[3];
    private final float[] startTimes = new float[]{0, 150, 300};
    private final float[] elapsedTimes = new float[]{0, 0, 0};
    private final DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private boolean isChat = false;
    private long lastUpdateTime = 0;
    private boolean started = false;

    public TypingDotsDrawable() {
        super();
        paint.setColor(0x55ffffff);
    }

    private static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    public void setIsChat(boolean value) {
        isChat = value;
    }

    private void update() {
        long newTime = System.currentTimeMillis();
        long dt = newTime - lastUpdateTime;
        lastUpdateTime = newTime;
        if (dt > 50) {
            dt = 50;
        }
        for (int a = 0; a < 3; a++) {
            elapsedTimes[a] += dt;
            float timeSinceStart = elapsedTimes[a] - startTimes[a];
            if (timeSinceStart > 0) {
                if (timeSinceStart <= 320) {
                    float diff = decelerateInterpolator.getInterpolation(timeSinceStart / 320.0f);
                    scales[a] = 1.33f + diff;
                } else if (timeSinceStart <= 640) {
                    float diff = decelerateInterpolator.getInterpolation((timeSinceStart - 320.0f) / 320.0f);
                    scales[a] = 1.33f + (1 - diff);
                } else if (timeSinceStart >= 800) {
                    elapsedTimes[a] = 0;
                    startTimes[a] = 0;
                    scales[a] = 1.33f;
                } else {
                    scales[a] = 1.33f;
                }
            } else {
                scales[a] = 1.33f;
            }
        }
        invalidateSelf();
    }

    public void start() {
        lastUpdateTime = System.currentTimeMillis();
        started = true;
        invalidateSelf();
    }

    public void stop() {
        for (int a = 0; a < 3; a++) {
            elapsedTimes[a] = 0;
            scales[a] = 1.33f;
        }
        startTimes[0] = 0;
        startTimes[1] = 150;
        startTimes[2] = 300;
        started = false;
    }

    @Override
    public void draw(Canvas canvas) {
        int y;
        if (isChat) {
            y = dp(6);
        } else {
            y = dp(7);
        }
        canvas.drawCircle(dp(3), y, scales[0] * density, paint);
        canvas.drawCircle(dp(9), y, scales[1] * density, paint);
        canvas.drawCircle(dp(15), y, scales[2] * density, paint);
//        canvas.drawPath(createStarBySyze(dp(3) + scales[0] * density,5), paint);
//        canvas.drawPath(createStarBySyze(dp(9) + scales[1] * density,5), paint);
//        canvas.drawPath(createStarBySyze(dp(15) + scales[2] * density,5), paint);
        if (started) {
            update();
        }
    }

    private Path createStarBySyze(float width, int steps) {
        float halfWidth = width / 2.0F;
        float radius = halfWidth / 2.0F;
        float degreesPerStep = (float) Math.toRadians(360.0F / (float) steps);
        float halfDegreesPerStep = degreesPerStep / 2.0F;
        Path ret = new Path();
        ret.setFillType(Path.FillType.EVEN_ODD);
        float max = (float) (2.0F * Math.PI);
        ret.moveTo(width, halfWidth);
        for (double step = 0; step < max; step += degreesPerStep) {
            ret.lineTo((float) (halfWidth + halfWidth * Math.cos(step)), (float) (halfWidth + halfWidth * Math.sin(step)));
            ret.lineTo((float) (halfWidth + radius * Math.cos(step + halfDegreesPerStep)), (float) (halfWidth + radius * Math.sin(step + halfDegreesPerStep)));
        }
        ret.close();
        return ret;
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public int getIntrinsicWidth() {
        return dp(18);
    }

    @Override
    public int getIntrinsicHeight() {
        return dp(10);
    }
}