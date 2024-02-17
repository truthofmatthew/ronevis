package fragments.views;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;

/**
 * Created by mt.karimi on 8/9/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class HorizontalOverScrollView extends HorizontalScrollView {
    private static final int WIDTH_DEVIDER_OVERSCROLL_DISTANCE = 3;
    boolean attachedOnce = false;
    private TimeInterpolator mInterpolator;
    private int mMaxOverscrollDistance;
    private int mAnimTime;
    private long mStartTime;
    private OnLayoutListener mListener;

    public HorizontalOverScrollView(final Context context) {
        super(context);
        init();
    }

    public HorizontalOverScrollView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalOverScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
        mMaxOverscrollDistance = widthPixels / WIDTH_DEVIDER_OVERSCROLL_DISTANCE;
        mAnimTime = getContext().getResources().getInteger(android.R.integer.config_longAnimTime);
        mInterpolator = new DecelerateInterpolator();
        this.setSmoothScrollingEnabled(true);
//        fullScrollOnLayout();
    }
//    private OnLayoutListener mListener;
//    ///...
//    private interface OnLayoutListener {
//        void onLayout();
//    }
//
//    public void fullScrollOnLayout() {
//        mListener = new OnLayoutListener() {
//            @Override
//            public void onLayout() {
//                fullScroll(HorizontalScrollView.FOCUS_RIGHT);
//                mListener = null;
//            }
//        };
//    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        int overScrollDistance = mMaxOverscrollDistance;
        if (isTouchEvent) {
            mStartTime = AnimationUtils.currentAnimationTimeMillis();
        } else {
            final long elapsedTime = AnimationUtils.currentAnimationTimeMillis() - mStartTime;
            float interpolation = mInterpolator.getInterpolation((float) elapsedTime / mAnimTime);
            interpolation = interpolation > 1 ? 1 : interpolation;
            overScrollDistance -= overScrollDistance * interpolation;
            overScrollDistance = overScrollDistance < 0 ? 0 : overScrollDistance;
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, overScrollDistance, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        if(mListener != null)
//            mListener.onLayout();
        if (mListener != null)
            mListener.onLayout();
//        String ss ;
//
//        if (this.getTag() != null){
//              ss = this.getTag().toString();
//            if (ss.equals("TextHOSVtag")){
//
//                String idStr = getResources().getResourceName(this.getId());
//                if (!attachedOnce) {
//                Logger.d(ss+"\n"+idStr+"\n"+this.getId());
//                    fullScroll(HorizontalScrollView.FOCUS_RIGHT);
//                }
//            }
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        attachedOnce = true;
    }

    public void fullScrollOnLayout(final int direction) {
        mListener = new OnLayoutListener() {
            @Override
            public void onLayout() {
                fullScroll(direction);
                mListener = null;
            }
        };
    }

    private interface OnLayoutListener {
        void onLayout();
    }
    //    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//
//
////                if (!attachedOnce) {
////                    attachedOnce = true;
////                    fullScroll(HorizontalScrollView.FOCUS_RIGHT);
////                    Logger.d("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
////                }
//
//    }
}