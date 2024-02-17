package fragments.views;
/**
 * Created by mt.karimi on 9/24/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * FrameLayout that allows dragging its inner views around
 */
public class AllowsForDragFrameLayout extends FrameLayout {
    TranslationViewDragHelper mViewDragHelper;
    TranslationViewDragHelper.Callback mCallback = new TranslationViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //Any children can be captured
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d("MTKAR left", " " + left);
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //allow full movement along vertical axis
            Log.d("MTKAR top", " " + top);
            return top;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            Log.d("MTKAR setX", " " + left);
            Log.d("MTKAR setY", " " + top);
            Log.d("MTKAR setY", " " + changedView.getClass().getName());
//            changedView.setX(left);
//            changedView.setY(top);
        }
    };

    public AllowsForDragFrameLayout(Context context) {
        super(context);
        init();
    }

    public AllowsForDragFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AllowsForDragFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public AllowsForDragFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mViewDragHelper = TranslationViewDragHelper.create(this, 1.0f, mCallback);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}