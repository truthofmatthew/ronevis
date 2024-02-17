package fragments.MTViews;
/**
 * Created by mt.karimi on 9/26/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by jacob-wj on 2015/4/20.
 */
public class DragTopLayout extends FrameLayout {
    public static final int AUTO_FLING_MIN = 1000;
    private ViewDragHelper mViewDragHelper;
    private int mDragRange;
    private int mDragOffset;
    private View mContentView;
    private int mContentViewHeight;
    private boolean isDragable = false;
    private int mLayoutWidth;
    private boolean shouldIntercept = true;

    public DragTopLayout(Context context) {
        this(context, null);
    }

    public DragTopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("TAg", "onLayout");
        mDragRange = (int) (getHeight() * 0.33f);
        resetContentViewHeight();
        mContentView.layout(left, mDragOffset, mLayoutWidth, getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLayoutWidth = w;
    }

    private void resetContentViewHeight() {
        mContentViewHeight = getHeight();
        LayoutParams params = (LayoutParams) mContentView.getLayoutParams();
        params.height = getHeight() - mDragOffset;
        mContentView.setLayoutParams(params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//         mContentView = findViewById(R.id.relative_content_view);
        mViewDragHelper = ViewDragHelper.create(this, 1f, new ViewDragCallBack());
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        super.computeScroll();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mViewDragHelper.cancel();
                break;
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev) && shouldIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class ViewDragCallBack extends ViewDragHelper.Callback {
        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top <= 0) {
                top = 0;
            }
            if (top >= mDragRange) {
                top = mDragRange;
            }
            mDragOffset = top;
            requestLayout();
            return top;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            requestLayout();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if ((mDragOffset == 0)
                    || (mDragOffset == mDragRange)) {
                return;
            }
            int range = mDragRange;
            boolean settleToOpen = false;
            if (yvel > AUTO_FLING_MIN) {
                settleToOpen = true;
            } else if (yvel < -AUTO_FLING_MIN) {
                settleToOpen = false;
            } else if (mDragOffset > range / 2) {
                settleToOpen = true;
            } else if (mDragOffset < range / 2) {
                settleToOpen = false;
            }
            mDragOffset = settleToOpen ? mDragRange : 0;
            mViewDragHelper.settleCapturedViewAt(0, mDragOffset);
            Log.e("TAg", "onViewReleased");
            requestLayout();
        }
    }
}