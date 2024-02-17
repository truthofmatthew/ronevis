package fragments.views;
/**
 * Created by mt.karimi on 9/22/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.IntEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

public class CollapsableLinearLayout extends LinearLayout {
    private ValueAnimator animator;
    private Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private boolean animating;
    private int expandedHeight;
    private long duration = 600;
    public CollapsableLinearLayout(Context context) {
        super(context);
        onInit();
    }

    public CollapsableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public CollapsableLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onInit();
    }

    protected void onInit() {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // consume all touch events when animating
        if (animating) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if ((w != oldw || h != oldh) && !animating && h != 0) {
            expandedHeight = h;
        }
    }

    public long getAnimationDuration() {
        return duration;
    }

    public void setAnimationDuration(long duration) {
        this.duration = duration;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void expand() {
        if (getHeight() != expandedHeight) {
            animateHeight(0, expandedHeight);
        }
    }

    public void collapse() {
        if (getHeight() != 0) {
            animateHeight(expandedHeight, 0);
        }
    }

    private void animateHeight(int startHeight, int endHeight) {
        long previousAnimationPlayTime = -1;
        if (animator != null && animator.isRunning()) {
            previousAnimationPlayTime = animator.getCurrentPlayTime();
            animator.cancel();
        }
        animator = ValueAnimator.ofObject(new HeightEvaluator(), startHeight, endHeight);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                animating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
            }
        });
        animator.start();
        if (previousAnimationPlayTime != -1) {
            animator.setCurrentPlayTime(duration - previousAnimationPlayTime);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // We don't want children to move during animation.
        // Also, don't layout children if our height is zero.
        if (!animating && t != b) {
            super.onLayout(changed, l, t, r, b);
        }
    }

    private class HeightEvaluator extends IntEvaluator {
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int height = super.evaluate(fraction, startValue, endValue);
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = height;
            setLayoutParams(params);
            return height;
        }
    }
}