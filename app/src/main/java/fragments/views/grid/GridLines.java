package fragments.views.grid;
/**
 * Created by mt.karimi on 10/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com fragments.views.grid.GridLines
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * GridLines is a view which directly overlays the preview and draws
 * evenly spaced grid lines.
 */
public class GridLines extends View
        /*implements PreviewStatusListener.PreviewAreaChangedListener */ {
    //    private Rect mDrawBounds;
    Paint mPaint = new Paint();

    public GridLines(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mPaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.grid_line_width));
//        mPaint.setColor(getResources().getColor(R.color.grid_line));
        mPaint.setStrokeWidth(5f);
        mPaint.setColor(Color.RED);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (mDrawBounds != null) {
//        canvas.getClipBounds(mDrawBounds);
        float thirdWidth = getWidth() / 3;
        float thirdHeight = getHeight() / 3;
        for (int i = 1; i < 3; i++) {
            // Draw the vertical lines.
            final float x = thirdWidth * i;
            mPaint.setColor(Color.RED);
            canvas.drawLine(x, 0, x, getHeight(), mPaint);
            // Draw the horizontal lines.
            final float y = thirdHeight * i;
            canvas.drawLine(0, y, getWidth(), y, mPaint);
            mPaint.setColor(Color.BLUE);
            canvas.drawCircle(x, y, 50, mPaint);
            mPaint.setColor(Color.GREEN);
            canvas.drawCircle(x, y, 50, mPaint);
            mPaint.setColor(Color.YELLOW);
            final float xx = thirdWidth * i;
            canvas.drawCircle(xx / 2, y, 50, mPaint);
            mPaint.setColor(Color.WHITE);
            final float xxx = thirdWidth * (2 + i);
            canvas.drawCircle(xxx, y, 50, mPaint);
            mPaint.setColor(Color.BLACK);
            final float xxxx = thirdWidth * (2 - i);
            canvas.drawCircle(xxxx / 2, y, 50, mPaint);
            mPaint.setColor(Color.CYAN);
            final float xxxxx = thirdWidth * (1 + i);
            canvas.drawCircle(xxxxx, y, 50, mPaint);
        }
//        }
    }

    private boolean isViewContains(View view, int rx, int ry) {
        int[] l = new int[2];
        view.getLocationOnScreen(l);
        int x = l[0];
        int y = l[1];
        int w = view.getWidth();
        int h = view.getHeight();
        if (rx < x || rx > x + w || ry < y || ry > y + h) {
            return false;
        }
        return true;
    }

    public boolean containsView(View dropZone, View draggedView) {
        // Create the Rect for the view where items will be dropped
        int[] pointA = new int[2];
        dropZone.getLocationOnScreen(pointA);
        Rect rectA = new Rect(pointA[0], pointA[1], pointA[0] + dropZone.getWidth(), pointA[1] + dropZone.getHeight());
        // Create the Rect for the view been dragged
        int[] pointB = new int[2];
        draggedView.getLocationOnScreen(pointB);
        Rect rectB = new Rect(pointB[0], pointB[1], pointB[0] + draggedView.getWidth(), pointB[1] + draggedView.getHeight());
        // Check if the dropzone currently contains the dragged view
        return rectA.contains(rectB);
    }
//    @Override
//    public void onPreviewAreaChanged(final RectF previewArea) {
//        setDrawBounds(previewArea);
//    }
//    private void setDrawBounds(final RectF previewArea) {
//        mDrawBounds = new RectF(previewArea);
//        invalidate();
//    }
}