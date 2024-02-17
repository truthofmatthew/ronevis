package fragments.MTViews;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.WeakHashMap;

import androidx.customview.widget.ViewDragHelper;
import mt.karimi.ronevis.R;

public class DragHelper extends LinearLayout {
    private static final float SENSITIVITY = 1.0f;
    private static final float DEFAULT_DRAG_LIMIT = 0.5f;
    private static final int INVALID_POINTER = -1;
    LinearLayout container;
    private int activePointerId = INVALID_POINTER;
    private float verticalDragRange;
    private float horizontalDragRange;
    private float dragLimit;
    private TypedArray attributes;
    private ViewDragHelper viewDragHelper;
    private View mDragView;
    private WeakHashMap<Integer, View> views;
    private View container_view;
    private int dragRange;
    private int mSlideRange;

    public DragHelper(Context context) {
        super(context);
    }

    public DragHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttributes(attrs);
    }

    public DragHelper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeAttributes(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            mapGUI();
            attributes.recycle();
            configDragViewHelper();
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        setVerticalDragRange(height);
        setHorizontalDragRange(width);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return false;
        }
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                viewDragHelper.cancel();
                return false;
            case MotionEvent.ACTION_DOWN:
                int index = MotionEventCompat.getActionIndex(ev);
                activePointerId = MotionEventCompat.getPointerId(ev, index);
                if (activePointerId == INVALID_POINTER) {
                    return false;
                }
            default:
                return viewDragHelper.shouldInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        if ((actionMasked & MotionEventCompat.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            activePointerId = MotionEventCompat.getPointerId(ev, actionMasked);
        }
        if (activePointerId == INVALID_POINTER || views == null) {
            return false;
        }
        viewDragHelper.processTouchEvent(ev);
        boolean isClickAtView = false;
        for (View view : views.values()) {
            if (!isClickAtView) {
                isClickAtView = isViewHit(view, (int) ev.getX(), (int) ev.getY());
            }
        }
        return isClickAtView;
    }

    @Override
    public void computeScroll() {
        if (!isInEditMode() && viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private boolean smoothSlideTo(View view, int x, int y) {
        if (viewDragHelper != null && viewDragHelper.smoothSlideViewTo(view, x, y)) {
            ViewCompat.postInvalidateOnAnimation(views.get((Integer) view.getTag()));
            return true;
        }
        return false;
    }

    public float getVerticalDragRange() {
        return verticalDragRange;
    }

    public void setVerticalDragRange(float verticalDragRange) {
        this.verticalDragRange = verticalDragRange;
    }

    public float getHorizontalDragRange() {
        return horizontalDragRange;
    }

    public void setHorizontalDragRange(float horizontalDragRange) {
        this.horizontalDragRange = horizontalDragRange;
    }

    public float getDragLimit() {
        return dragLimit;
    }

    public void setDragLimit(float dragLimit) {
        if (dragLimit > 0.0f && dragLimit < 1.0f) {
            this.dragLimit = dragLimit;
        } else {
            throw new IllegalStateException("dragLimit needs to be between 0.0f and 1.0f");
        }
    }

    public View getDragView(int position) {
        return views != null ? views.get(position) : null;
    }

    private void initializeAttributes(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.draghelper_layout);
        this.dragLimit = attributes.getFloat(R.styleable.draghelper_layout_drag_limit,
                DEFAULT_DRAG_LIMIT);
        this.attributes = attributes;
    }

    private void mapGUI() {
        int count = getChildCount();
        if (views == null) {
            views = new WeakHashMap<>(count);
        }
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                View view = getChildAt(i);
                view.setTag(i);
                views.put(i, view);
            }
        } else {
            throw new IllegalStateException("DragHelper must contains one or more direct child");
        }
    }

    private void configDragViewHelper() {
        container = (LinearLayout) findViewById(R.id.container);
        mDragView = findViewById(R.id.drag_Handle);
        container_view = findViewById(R.id.container_view);
        viewDragHelper = ViewDragHelper.create(this, SENSITIVITY,
                new ViewDragHelperCallback(this));
    }

    private boolean isViewHit(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0]
                && screenX < viewLocation[0] + view.getWidth()
                && screenY >= viewLocation[1]
                && screenY < viewLocation[1] + view.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        dragRange = container.getMeasuredHeight();
    }

    public class ViewDragHelperCallback extends ViewDragHelper.Callback {
        private DragHelper dragHelper;

        public ViewDragHelperCallback(DragHelper dragHelper) {
            this.dragHelper = dragHelper;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) container_view.getLayoutParams();
//
////      if ((dragHelper.getHeight() - top) > AndroidUtilities.dp(72)) {
            params.setMargins(0, top + mDragView.getHeight(), 0, 0);
////      }
//
            container_view.setLayoutParams(params);
//        container.layout(0, top  , getWidth(), 0);
//        container.layout(0, top + 100, getWidth(), top + 100 + 500);
            container.layout(0, top + mDragView.getHeight(), getWidth(), top + mDragView.getHeight() + dragRange);
            invalidate();
        }
//    @Override
//    public boolean tryCaptureView(View child, int pointerId) {
//        return child == mDragView;
//        return true;
//    }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Object tag = child.getTag();
            if (tag instanceof Integer && dragHelper != null) {
                View view = dragHelper.getDragView((Integer) tag);
                if (view != null) {
                    return child.equals(mDragView);
                }
            }
            return false;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Object tag = child.getTag();
            if (tag instanceof Integer && dragHelper != null) {
                View view = dragHelper.getDragView((Integer) tag);
                return Math.min(Math.max(left, dragHelper.getPaddingLeft()),
                        dragHelper.getWidth() - (view != null ? view.getWidth() : 0));
            } else {
                return 0;
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Object tag = child.getTag();
            View view = dragHelper.getDragView((Integer) tag);
//      if (top < dragHelper.getHeight()){
//
//          return Math.max(top, dragHelper.getPaddingTop());
//      }
//      else{
//        return Math.max(top, container.getPaddingTop());
//      }
//
            return top;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragHelper != null ? (int) dragHelper.getHorizontalDragRange() : 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return dragHelper != null ? (int) dragHelper.getVerticalDragRange() : 0;
        }
    }
}
