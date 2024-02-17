package fragments.views.dragLayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import androidx.core.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

import java.util.WeakHashMap;

import fragments.MTViews.AndroidUtilities;
import fragments.views.MTIcon;
import mt.karimi.ronevis.R;

public class DragLayout extends LinearLayout {
    private static final int INVALID_POINTER = -1;
    private static final SpringConfig ORIGAMI_SPRING_CONFIG = SpringConfig.fromOrigamiTensionAndFriction(40, 7);
    public Spring mSpring;
    double mRevealed;
    private ViewDragHelper viewDragHelper;
    private ViewGroup container, Drag_Main_View;
    private MTIcon dragger_icon;
    private ViewGroup dragger_layout;
    private double dragRange;
    private WeakHashMap<Integer, View> views;
    private int activePointerId = INVALID_POINTER;
    private int minHeight;
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        public View getDragView(int position) {
            return views != null ? views.get(position) : null;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (child.getId() == R.id.dragger_layout) {
                return true;
            }
            return false;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (dy == 0) return;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dragger_layout.getLayoutParams();
            params.setMargins(0, top, 0, 0);
//            MainActivity.mainInstance().wholeCanvas.setTranslationY(top);
            dragger_layout.setLayoutParams(params);
            double f1d3 = dragger_layout.getTop() / mRevealed;
            mSpring.setCurrentValue(1 - f1d3);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int topBound = 0;
            int bottomBound = getHeight() - minHeight;
            final int newHeight = Math.min(Math.max(topBound, top), bottomBound);
            return newHeight;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return (int) dragRange;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }
    };
    private int maxHeight;
    private boolean autoFix;

    public DragLayout(Context context) {
        super(context);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void mapGUI() {
        int count = Drag_Main_View.getChildCount();
        if (views == null) {
            views = new WeakHashMap<>(count);
        }
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                View view = Drag_Main_View.getChildAt(i);
                views.put(i, view);
            }
        } else {
            throw new IllegalStateException("DragHelper must contains one or more direct child");
        }
    }

    public boolean isAutoFix() {
        return autoFix;
    }

    public void setAutoFix(boolean autoFix) {
        this.autoFix = autoFix;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = AndroidUtilities.dp(minHeight);
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = AndroidUtilities.dp(maxHeight);
    }

    public void init() {
        Drag_Main_View = this;
        container = (ViewGroup) findViewById(R.id.container);
        dragger_layout = (ViewGroup) findViewById(R.id.dragger_layout);
        dragger_icon = (MTIcon) findViewById(R.id.dragger_icon);
        mapGUI();
        viewDragHelper = ViewDragHelper.create(Drag_Main_View, callback);
        SpringListener revealerSpringListener = new RevealerSpringListener();
        mSpring = SpringSystem
                .create()
                .createSpring()
                .setCurrentValue(1)
                .setEndValue(1)
                .setSpringConfig(ORIGAMI_SPRING_CONFIG)
                .addListener(revealerSpringListener);
        dragger_icon.setOnClickListener(new OnNubTouchListener());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (autoFix) {
            container.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = container.getMeasuredHeight() + AndroidUtilities.dp(32);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            minHeight = h - AndroidUtilities.dp(48);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        dragRange = Drag_Main_View.getMeasuredHeight();
        mRevealed = dragRange - minHeight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
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

    private void togglePosition() {
        double currentValue = mSpring.getCurrentValue();
        if (currentValue == 0.0) {
            mSpring.setEndValue(1.0);
        } else if (currentValue == 1.0) {
            mSpring.setEndValue(0.0);
        } else if (0.5 < currentValue) {
            mSpring.setEndValue(1.0);
        } else if (0.5 > currentValue) {
            mSpring.setEndValue(0.0);
        }
    }

    private class OnNubTouchListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.dragger_icon:
            togglePosition();
//                    break;
//            }
        }
    }

    private class RevealerSpringListener implements SpringListener {
        @Override
        public void onSpringUpdate(Spring spring) {
            double value = mSpring.getCurrentValue();
            double selectedPhotoTranslateY = SpringUtil.mapValueFromRangeToRange(value, 0, 1, mRevealed, 0);
            double ceilingValue = Math.ceil(selectedPhotoTranslateY);
            int intValue3 = (int) ceilingValue;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dragger_layout.getLayoutParams();
            params.setMargins(0, intValue3, 0, 0);
//            MainActivity.mainInstance().wholeCanvas.setTranslationY(intValue3);
            dragger_layout.setLayoutParams(params);
        }

        @Override
        public void onSpringAtRest(Spring spring) {
        }

        @Override
        public void onSpringActivate(Spring spring) {
        }

        @Override
        public void onSpringEndStateChange(Spring spring) {
        }
    }
}