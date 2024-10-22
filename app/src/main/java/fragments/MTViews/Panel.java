package fragments.MTViews;
/**
 * Created by mt.karimi on 30/09/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import mt.karimi.ronevis.R;

public class Panel extends LinearLayout {
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    private static final String TAG = "Panel";
    private final int mPosition;
    private final int mDuration;
    private final boolean mLinearFlying;
    private final Drawable mOpenedHandle;
    private final Drawable mClosedHandle;
    private final GestureDetector mGestureDetector;
    private final int mOrientation;
    private final PanelOnGestureListener mGestureListener;
    private boolean mIsShrinking;
    private View mHandle;
    private View mContent;
    private float mTrackX;
    private float mTrackY;
    private float mVelocity;
    private OnPanelListener panelListener;
    private State mState;
    private final AnimationListener animationListener = new AnimationListener() {
        public void onAnimationEnd(Animation animation) {
            mState = State.READY;
            if (mIsShrinking) {
                mContent.setVisibility(GONE);
            }
            postProcess();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
            mState = State.ANIMATING;
        }
    };
    private Interpolator mInterpolator;
    private int mContentHeight;
    private int mContentWidth;
    Runnable startAnimation = new Runnable() {
        public void run() {
            // this is why we post this Runnable couple of lines above:
            // now its save to use mContent.getHeight() && mContent.getWidth()
            TranslateAnimation animation;
            int fromXDelta = 0, toXDelta = 0, fromYDelta = 0, toYDelta = 0;
            if (mState == State.FLYING) {
                mIsShrinking = (mPosition == TOP || mPosition == LEFT) ^ (mVelocity > 0);
            }
            int calculatedDuration;
            if (mOrientation == VERTICAL) {
                int height = mContentHeight;
                if (!mIsShrinking) {
                    fromYDelta = mPosition == TOP ? -height : height;
                } else {
                    toYDelta = mPosition == TOP ? -height : height;
                }
                if (mState == State.TRACKING) {
                    if (Math.abs(mTrackY - fromYDelta) < Math.abs(mTrackY - toYDelta)) {
                        mIsShrinking = !mIsShrinking;
                        toYDelta = fromYDelta;
                    }
                    fromYDelta = (int) mTrackY;
                } else if (mState == State.FLYING) {
                    fromYDelta = (int) mTrackY;
                }
                // for FLYING events we calculate animation duration based on
                // flying velocity
                // also for very high velocity make sure duration >= 20 ms
                if (mState == State.FLYING && mLinearFlying) {
                    calculatedDuration = (int) (1000 * Math.abs((toYDelta - fromYDelta) / mVelocity));
                    calculatedDuration = Math.max(calculatedDuration, 20);
                } else {
                    calculatedDuration = mDuration * Math.abs(toYDelta - fromYDelta) / mContentHeight;
                }
            } else {
                int width = mContentWidth;
                if (!mIsShrinking) {
                    fromXDelta = mPosition == LEFT ? -width : width;
                } else {
                    toXDelta = mPosition == LEFT ? -width : width;
                }
                if (mState == State.TRACKING) {
                    if (Math.abs(mTrackX - fromXDelta) < Math.abs(mTrackX - toXDelta)) {
                        mIsShrinking = !mIsShrinking;
                        toXDelta = fromXDelta;
                    }
                    fromXDelta = (int) mTrackX;
                } else if (mState == State.FLYING) {
                    fromXDelta = (int) mTrackX;
                }
                // for FLYING events we calculate animation duration based on
                // flying velocity
                // also for very high velocity make sure duration >= 20 ms
                if (mState == State.FLYING && mLinearFlying) {
                    calculatedDuration = (int) (1000 * Math.abs((toXDelta - fromXDelta) / mVelocity));
                    calculatedDuration = Math.max(calculatedDuration, 20);
                } else {
                    calculatedDuration = mDuration * Math.abs(toXDelta - fromXDelta) / mContentWidth;
                }
            }
            mTrackX = mTrackY = 0;
            if (calculatedDuration == 0) {
                mState = State.READY;
                if (mIsShrinking) {
                    mContent.setVisibility(GONE);
                }
                postProcess();
                return;
            }
            animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
            animation.setDuration(calculatedDuration);
            animation.setAnimationListener(animationListener);
            if (mState == State.FLYING && mLinearFlying) {
                animation.setInterpolator(new LinearInterpolator());
            } else if (mInterpolator != null) {
                animation.setInterpolator(mInterpolator);
            }
            startAnimation(animation);
        }
    };
    OnTouchListener touchListener = new OnTouchListener() {
        int initX;
        int initY;
        boolean setInitialPosition;

        public boolean onTouch(View v, MotionEvent event) {
            // Log.d(TAG, "state: " + mState + " x: " + event.getX() + " y: " +
            // event.getY());
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                initX = 0;
                initY = 0;
                if (mContent.getVisibility() == GONE) {
                    // since we may not know content dimensions we use factors
                    // here
                    if (mOrientation == VERTICAL) {
                        initY = mPosition == TOP ? -1 : 1;
                    } else {
                        initX = mPosition == LEFT ? -1 : 1;
                    }
                }
                setInitialPosition = true;
            } else {
                if (setInitialPosition) {
                    // now we know content dimensions, so we multiply factors...
                    initX *= mContentWidth;
                    initY *= mContentHeight;
                    // ... and set initial panel's position
                    mGestureListener.setScroll(initX, initY);
                    setInitialPosition = false;
                    // for offsetLocation we have to invert values
                    initX = -initX;
                    initY = -initY;
                }
                // offset every ACTION_MOVE & ACTION_UP event
                event.offsetLocation(initX, initY);
            }
            if (!mGestureDetector.onTouchEvent(event)) {
                if (action == MotionEvent.ACTION_UP) {
                    // tup up after scrolling
                    post(startAnimation);
                }
            }
            return false;
        }
    };

    public Panel(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Panel);
        mDuration = 1000; // duration
        // defaults
        // to
        // 750
        // ms
        mPosition = a.getInteger(R.styleable.Panel_position, BOTTOM); // position
        // defaults
        // to
        // BOTTOM
        mLinearFlying = a.getBoolean(R.styleable.Panel_linearFlying, false); // linearFlying
        // defaults
        // to
        // false
        mOpenedHandle = a.getDrawable(R.styleable.Panel_openedHandle);
        mClosedHandle = a.getDrawable(R.styleable.Panel_closedHandle);
        a.recycle();
        mOrientation = VERTICAL;
        setOrientation(VERTICAL);
        mState = State.READY;
        mGestureListener = new PanelOnGestureListener();
        mGestureDetector = new GestureDetector(mGestureListener);
        mGestureDetector.setIsLongpressEnabled(false);
    }

    /**
     * Sets the listener that receives a notification when the panel becomes
     * open/close.
     *
     * @param onPanelListener The listener to be notified when the panel is opened/closed.
     */
    public void setOnPanelListener(OnPanelListener onPanelListener) {
        panelListener = onPanelListener;
    }

    /**
     * Gets Panel's mHandle
     *
     * @return Panel's mHandle
     */
    public View getHandle() {
        return mHandle;
    }

    /**
     * Gets Panel's mContent
     *
     * @return Panel's mContent
     */
    public View getContent() {
        return mContent;
    }

    /**
     * Sets the acceleration curve for panel's animation.
     *
     * @param i The interpolator which defines the acceleration curve
     */
    public void setInterpolator(Interpolator i) {
        mInterpolator = i;
    }

    /**
     * Set the opened state of Panel.
     *
     * @param open    True if Panel is to be opened, false if Panel is to be closed.
     * @param animate True if use animation, false otherwise.
     */
    public void setOpen(boolean open, boolean animate) {
        if (isOpen() ^ open) {
            mIsShrinking = !open;
            if (animate) {
                mState = State.ABOUT_TO_ANIMATE;
                if (!mIsShrinking) {
                    // this could make flicker so we test mState in
                    // dispatchDraw()
                    // to see if is equal to ABOUT_TO_ANIMATE
                    mContent.setVisibility(VISIBLE);
                }
                post(startAnimation);
            } else {
                mContent.setVisibility(open ? VISIBLE : GONE);
                postProcess();
            }
        }
    }

    /**
     * Returns the opened status for Panel.
     *
     * @return True if Panel is opened, false otherwise.
     */
    public boolean isOpen() {
        return mContent.getVisibility() == VISIBLE;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHandle = findViewById(R.id.panelHandle);
        if (mHandle == null) {
            throw new RuntimeException("Your Panel must have a View whose id attribute is 'R.id.panelHandle'");
        }
        mHandle.setOnTouchListener(touchListener);
        mContent = findViewById(R.id.panelContent);
        if (mContent == null) {
            throw new RuntimeException("Your Panel must have a View whose id attribute is 'R.id.panelContent'");
        }
        // reposition children
        removeView(mHandle);
        removeView(mContent);
        if (mPosition == TOP || mPosition == LEFT) {
            addView(mContent);
            addView(mHandle);
        } else {
            addView(mHandle);
            addView(mContent);
        }
        if (mClosedHandle != null) {
            mHandle.setBackgroundDrawable(mClosedHandle);
        }
        mContent.setVisibility(GONE);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mContentWidth = mContent.getWidth();
        mContentHeight = mContent.getHeight();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // String name = getResources().getResourceEntryName(getId());
        // Log.d(TAG, name + " ispatchDraw " + mState);
        // this is why 'mState' was added:
        // avoid flicker before animation start
        if (mState == State.ABOUT_TO_ANIMATE && !mIsShrinking) {
            int delta = mContentHeight;
            if (mPosition == LEFT || mPosition == TOP) {
                delta = -delta;
            }
            canvas.translate(0, delta);
        }
        if (mState == State.TRACKING || mState == State.FLYING) {
            canvas.translate(mTrackX, mTrackY);
        }
        super.dispatchDraw(canvas);
    }

    private float ensureRange(float v, int min, int max) {
        v = Math.max(v, min);
        v = Math.min(v, max);
        return v;
    }

    private void postProcess() {
        if (mIsShrinking && mClosedHandle != null) {
            mHandle.setBackgroundDrawable(mClosedHandle);
        } else if (!mIsShrinking && mOpenedHandle != null) {
            mHandle.setBackgroundDrawable(mOpenedHandle);
        }
        // invoke listener if any
        if (panelListener != null) {
            if (mIsShrinking) {
                panelListener.onPanelClosed(Panel.this);
            } else {
                panelListener.onPanelOpened(Panel.this);
            }
        }
    }
    private enum State {
        ABOUT_TO_ANIMATE, ANIMATING, READY, TRACKING, FLYING,
    }

    /**
     * Callback invoked when the panel is opened/closed.
     */
    public static interface OnPanelListener {
        /**
         * Invoked when the panel becomes fully closed.
         */
        public void onPanelClosed(Panel panel);

        /**
         * Invoked when the panel becomes fully opened.
         */
        public void onPanelOpened(Panel panel);
    }

    class PanelOnGestureListener implements OnGestureListener {
        float scrollY;
        float scrollX;

        public void setScroll(int initScrollX, int initScrollY) {
            scrollX = initScrollX;
            scrollY = initScrollY;
        }

        public boolean onDown(MotionEvent e) {
            scrollX = scrollY = 0;
            if (mState != State.READY) {
                // we are animating or just about to animate
                return false;
            }
            mState = State.ABOUT_TO_ANIMATE;
            mIsShrinking = mContent.getVisibility() == VISIBLE;
            if (!mIsShrinking) {
                // this could make flicker so we test mState in dispatchDraw()
                // to see if is equal to ABOUT_TO_ANIMATE
                mContent.setVisibility(VISIBLE);
            }
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mState = State.FLYING;
            mVelocity = velocityY;
            post(startAnimation);
            return true;
        }

        public void onLongPress(MotionEvent e) {
            // not used
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mState = State.TRACKING;
            float tmpY = 0, tmpX = 0;
            scrollY -= distanceY;
            if (mPosition == TOP) {
                tmpY = ensureRange(scrollY, -mContentHeight, 0);
            } else {
                tmpY = ensureRange(scrollY, 0, mContentHeight);
            }
            if (tmpX != mTrackX || tmpY != mTrackY) {
                mTrackX = tmpX;
                mTrackY = tmpY;
                invalidate();
            }
            return true;
        }

        public void onShowPress(MotionEvent e) {
            // not used
        }

        public boolean onSingleTapUp(MotionEvent e) {
            // simple tap: click
            post(startAnimation);
            return true;
        }
    }
}
