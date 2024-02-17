package fragments.views;

/**
 * Created by MT on 14/05/2017.
 */


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class EyeDropper {

    private static final String TAG = "EyeDropper";
    private static final Matrix INVERT_MATRIX = new Matrix();
    private static final int NO_COLOR = Color.TRANSPARENT;

    private View targetView;
    private ColorSelectionListener colorListener;
    private int xTouch;
    private int yTouch;

    public EyeDropper(@NonNull View view, @NonNull ColorSelectionListener listener) {
        colorListener = listener;
        setTargetView(view);
        setTouchListener();
    }

    private void setTargetView(@NonNull final View targetView) {
        this.targetView = targetView;
        if (shouldDrawingCacheBeEnabled(targetView)) {
            targetView.setDrawingCacheEnabled(true);
            targetView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        }
    }

    private boolean shouldDrawingCacheBeEnabled(@NonNull View targetView) {
        return !(targetView instanceof ImageView) && !targetView.isDrawingCacheEnabled();
    }

    private void setTouchListener() {
        targetView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                xTouch = (int) event.getX();
                yTouch = (int) event.getY();
                final int colorSelected = getColorAtPoint(xTouch, yTouch);
                Log.d(TAG, "Color = " + colorSelected);
                notifyColorSelected(colorSelected);
                return true;
            }
        });
    }

    private int getColorAtPoint(int x, int y) {
        if (targetView instanceof ImageView) {
            return handleIfImageView(x, y);
        } else {
            final Bitmap drawingCache = targetView.getDrawingCache();
            return getPixelAtPoint(drawingCache, x, y);
        }
    }

    private int handleIfImageView(int x, int y) {
        final ImageView targetImageView = (ImageView) this.targetView;
        final Drawable drawable = targetImageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            targetImageView.getImageMatrix().invert(INVERT_MATRIX);
            final float[] mappedPoints = new float[]{x, y};
            INVERT_MATRIX.mapPoints(mappedPoints);

            final Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return getPixelAtPoint(bitmap, (int) mappedPoints[0], (int) mappedPoints[1]);
        }
        return NO_COLOR;
    }

    private int getPixelAtPoint(Bitmap bitmap, int x, int y) {
        if (isValidPoint(x, y, bitmap)) {
            return bitmap.getPixel(x, y);
        }
        return NO_COLOR;
    }

    private boolean isValidPoint(int x, int y, Bitmap bitmap) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        return isValidCoordinate(x, width) && isValidCoordinate(y, height);
    }

    private boolean isValidCoordinate(int coordinate, int size) {
        return coordinate > 0 && coordinate < size;
    }

    private void notifyColorSelected(int color) {
        if (colorListener != null) {
            colorListener.onColorSelected(color);
        }
    }

    public interface ColorSelectionListener {
        void onColorSelected(@ColorInt int color);
    }
}
