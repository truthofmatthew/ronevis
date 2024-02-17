package fragments.MTViews;
/**
 * Created by mt.karimi on 30/09/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import mt.karimi.ronevis.R;

/**
 * ScSlidingPanel
 * This class create and manage a sliding panel and the movement will be decided automatically
 * by the inner alignment of the components inside his parent container.
 * <p>
 * v1.0.0
 */
public class ScSlidingPanel
        extends RelativeLayout
        implements Animator.AnimatorListener {
    /**
     * Private attributes
     */
    private int mLayout = -1;
    private boolean mStartOpen = true;
    private boolean mToggleOnTouch = true;
    private boolean mAnimateAlpha = false;
    private boolean mAnimateTranslation = true;
    private int mDuration = 500;
    private int mHandleSize = 0;
    private int mOffset = 0;
    private boolean mHideOnClose = true;
    /**
     * Private variables
     */
    private boolean mIsOpen = true;
    private boolean mIsFirstTime = true;
    private boolean mDrag = false;
    private float mStartX = 0;
    private float mStartY = 0;
    private boolean mTopAlignment = false;
    private boolean mLeftAlignment = false;
    private boolean mBottomAlignment = false;
    private boolean mRightAlignment = false;
    private OnChangeListener mOnChangeListener = null;
    private OnHandleDragListener mOnHandleDragListener = null;

    /**
     * Constructors
     */
    public ScSlidingPanel(Context context) {
        super(context);
        this.init(context, null, 0);
    }

    public ScSlidingPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, 0);
    }

    public ScSlidingPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Privates methods
     */
    // Inflate layout
    private void inflateLayout(Context context, int resId) {
        // Inflate layout inside new view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resId, this, false);
        // Add view to group
        this.addView(view);
    }

    // Init the component
    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Get the attributes list
        final TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.ScComponents, defStyle, 0);
        // Read all attributes from xml and assign the value to linked variables
        this.mLayout = attrArray.getResourceId(R.styleable.ScComponents_scc_layout, -1);
        this.mStartOpen = attrArray.getBoolean(R.styleable.ScComponents_scc_start_open, true);
        this.mToggleOnTouch = attrArray.getBoolean(R.styleable.ScComponents_scc_toggle_ontouch, true);
        this.mAnimateAlpha = attrArray.getBoolean(R.styleable.ScComponents_scc_animate_alpha, false);
        this.mAnimateTranslation = attrArray.getBoolean(R.styleable.ScComponents_scc_animate_translation, true);
        this.mDuration = attrArray.getInt(R.styleable.ScComponents_scc_duration, 500);
        this.mHandleSize = attrArray.getDimensionPixelSize(R.styleable.ScComponents_scc_handle_size, 0);
        this.mOffset = attrArray.getDimensionPixelSize(R.styleable.ScComponents_scc_offset, 0);
        this.mHideOnClose = attrArray.getBoolean(R.styleable.ScComponents_scc_hide_onclose, true);
        // Recycle
        attrArray.recycle();
        // Check properties
        if (this.mDuration < 0) this.mDuration = 0;
        if (this.mHandleSize < 0) this.mHandleSize = 0;
        // Inflate layout resource if have
        if (this.mLayout != -1) this.inflateLayout(context, this.mLayout);
        // The animation listener
        this.animate().setListener(this);
    }

    // Find the layout alignments
    private void findLayoutAlignments() {
        // Only in running mode
        if (!isInEditMode()) {
            // Get layout
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
            int[] rules = lp.getRules();
            // Simplify using the holder global variable
            this.mTopAlignment = rules[RelativeLayout.ALIGN_PARENT_TOP] == RelativeLayout.TRUE;
            this.mLeftAlignment = rules[RelativeLayout.ALIGN_PARENT_LEFT] == RelativeLayout.TRUE;
            this.mBottomAlignment = rules[RelativeLayout.ALIGN_PARENT_BOTTOM] == RelativeLayout.TRUE;
            this.mRightAlignment = rules[RelativeLayout.ALIGN_PARENT_RIGHT] == RelativeLayout.TRUE;
        }
    }

    // Animate the panel by the current status
    private void doAnimate(int duration, boolean withEvent) {
        // Find alignments
        this.findLayoutAlignments();
        // Check if panel is visible in the open phase
        if (this.mIsOpen && this.getVisibility() != View.VISIBLE)
            this.setVisibility(View.VISIBLE);
        // Get the animator
        ViewPropertyAnimator animator = this.animate();
        // If need to raise the event execute the animation
        if (withEvent) {
            // Set duration of animation. Always at the end of animation raise the event.
            // The duration is passed as params because need to make an open/close without animation.
            // For example when the panel start opened or closed.
            animator.setDuration(duration);
        }
        // Start alpha animation if needed
        if (this.mAnimateAlpha) {
            // Calc alpha value
            float toAlpha = this.mIsOpen ? 1f : 0f;
            // Apply the alpha
            if (withEvent) {
                // raise event at the end of animation
                animator.alpha(toAlpha);
            } else {
                // Apply the new alpha direct to this object and not raise event
                this.setAlpha(toAlpha);
            }
        }
        // Start translate animation if needed
        if (this.mAnimateTranslation) {
            // Measure the view
            this.measure(0, 0);
            // Calc the horizontal and vertical values
            int toX = (this.mIsOpen ? 0 : this.getMeasuredWidth()) * (this.mLeftAlignment ? -1 : 1);
            int toY = (this.mIsOpen ? 0 : this.getMeasuredHeight()) * (this.mTopAlignment ? -1 : 1);
            // Add the handle size to the calc position.
            // The size can be sum or dec depend from the alignment of the component respect
            // the container.
            if (!this.mIsOpen) {
                toX += (this.mLeftAlignment ? 1 : -1) * this.mHandleSize;
                toY += (this.mTopAlignment ? 1 : -1) * this.mHandleSize;
            }
            // Fix the values with offset.
            // In this case the offset can be positive or negative by the user setting so
            // don't need to do distinction between the component alignments.
            toX -= this.mOffset;
            toY -= this.mOffset;
            // Apply the new horizontal coordinates
            if (this.mLeftAlignment || this.mRightAlignment) {
                // Check if need to call the linked events.
                if (withEvent) {
                    // Apply using the animator and raise event at the end of animation
                    animator.translationX(toX);
                } else {
                    // Apply directly to this object and not raise event.
                    this.setTranslationX(toX);
                }
            }
            // Apply the new vertical coordinates
            if (this.mTopAlignment || this.mBottomAlignment) {
                // Check if need to call the linked events.
                if (withEvent) {
                    // Apply using the animator and raise event at the end of animation
                    animator.translationY(toY);
                } else {
                    // Apply directly to this object and not raise event.
                    this.setTranslationY(toY);
                }
            }
        }
        // If do any animation check is must hide the panel
        if (!this.mIsOpen && !withEvent && this.mHideOnClose &&
                this.getVisibility() == View.VISIBLE) {
            // Hide the panel
            this.setVisibility(View.GONE);
        }
    }

    // Toggle the component visibility
    private void toggleVisibility(int duration, boolean withEvent) {
        // Toggle status
        this.mIsOpen = !this.mIsOpen;
        // Animate the panel
        this.doAnimate(duration, withEvent);
    }

    // Check if touch the handle
    private boolean touchOnHandle(float x, float y) {
        // Check for have
        if (this.mHandleSize == 0) return false;
        // Get the dimensions
        int width = this.getWidth();
        int height = this.getHeight();
        // Return
        return this.mAnimateTranslation && (
                (this.mTopAlignment && (y >= height - this.mHandleSize && y < height)) ||
                        (this.mBottomAlignment && (y >= 0 && y <= this.mHandleSize)) ||
                        (this.mLeftAlignment && (x >= width - this.mHandleSize && x <= width)) ||
                        (this.mRightAlignment && (x >= 0 && x <= this.mHandleSize))
        );
    }

    // Reposition the view by delta
    private void reposition(float deltaX, float deltaY) {
        // Check if really moving
        if (deltaX != 0 || deltaY != 0) {
            // Calc new translation
            float transX = this.getTranslationX() - deltaX;
            float transY = this.getTranslationY() - deltaY;
            // Find the limits for translation
            float limitLeft = this.mLeftAlignment ? -this.getWidth() + this.mHandleSize : 0;
            float limitRight = this.mRightAlignment ? this.getWidth() - this.mHandleSize : 0;
            float limitTop = this.mTopAlignment ? -this.getHeight() + this.mHandleSize : 0;
            float limitBottom = this.mBottomAlignment ? this.getHeight() - this.mHandleSize : 0;
            // Apply the limits
            if (transX < limitLeft) transX = limitLeft;
            if (transX > limitRight) transX = limitRight;
            if (transY < limitTop) transY = limitTop;
            if (transY > limitBottom) transY = limitBottom;
            // Stop animation if not finish
            Animation animation = this.getAnimation();
            if (animation != null && !animation.hasEnded()) {
                animation.cancel();
            }
            // Translate
            if (this.mLeftAlignment || this.mRightAlignment) this.setTranslationX(transX - mOffset);
            if (this.mTopAlignment || this.mBottomAlignment) this.setTranslationY(transY - mOffset);
        }
    }

    // Check for pending position
    private void checkForPendingPosition() {
        // Get the current translations
        float transX = Math.abs(this.getTranslationX());
        float transY = Math.abs(this.getTranslationY());
        // Check the x position
        if ((this.mLeftAlignment || this.mRightAlignment) && transX != 0) {
            // Find half height measure
            float handle = this.getWidth() / 2;
            // Select the action
            if ((this.mLeftAlignment && transX > handle) || (this.mRightAlignment && transX < handle)) {
                this.close();
            } else {
                this.open();
            }
        }
        // Check the y position
        if ((this.mTopAlignment || this.mBottomAlignment) && transY != 0) {
            // Find half height measure
            float handle = this.getHeight() / 2;
            // Select the action
            if ((this.mTopAlignment && transY > handle) || (this.mBottomAlignment && transY < handle)) {
                this.close();
            } else {
                this.open();
            }
        }
    }

    /**
     * Events
     */
    // When is attached to parent
    @Override
    protected void onAttachedToWindow() {
        // Super
        super.onAttachedToWindow();
        // not in edit mode
        if (!isInEditMode() && mIsFirstTime) {
            // Set the trigger to false
            this.mIsFirstTime = false;
            // If start not open
            if (!this.mStartOpen) {
                // Set (virtually) the panel like invisible
                this.mIsOpen = false;
            }
            // Fix the start position
            this.doAnimate(0, false);
        }
    }

    // Check the touch
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Select action
        switch (event.getAction()) {
            // Press
            case MotionEvent.ACTION_DOWN:
                // Recalculate alignments
                this.findLayoutAlignments();
                // Check if must start to drag
                this.mDrag = this.touchOnHandle(event.getX(), event.getY());
                // If drag
                if (this.mDrag) {
                    // Save the pointer position
                    this.mStartX = event.getX();
                    this.mStartY = event.getY();
                    // Listener
                    if (this.mOnHandleDragListener != null) {
                        this.mOnHandleDragListener.onStart(this);
                    }
                }
                break;
            // Release
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Long click
                boolean isScrolled =
                        ((this.mLeftAlignment || this.mRightAlignment) && this.mStartX != event.getX()) ||
                                ((this.mTopAlignment || this.mBottomAlignment) && this.mStartY != event.getY());
                // Finish drag
                if (this.mDrag && isScrolled) {
                    // Fix the last position
                    this.checkForPendingPosition();
                    // Listener
                    if (this.mOnHandleDragListener != null) {
                        this.mOnHandleDragListener.onEnd(this);
                    }
                }
                //Trigger
                this.mDrag = false;
                // Emulate onclick
                if (this.mToggleOnTouch && !isScrolled) {
                    // Toggle visibility
                    this.toggleVisibility(this.mDuration, true);
                }
                break;
            // Move
            case MotionEvent.ACTION_MOVE:
                // If dragging
                if (this.mDrag) {
                    // Calc deltas
                    float deltaX = this.mStartX - event.getX();
                    float deltaY = this.mStartY - event.getY();
                    // Reposition the view
                    this.reposition(deltaX, deltaY);
                }
                break;
        }
        // Block event propagation
        return true;
    }

    // Change size
    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        // Super
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        // Only if closed
        if (!this.mIsOpen) {
            // Fix position
            this.doAnimate(0, false);
        }
    }

    /**
     * Instance state
     */
    // Save
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        Bundle state = new Bundle();
        state.putParcelable("PARENT", superState);
        state.putInt("mLayout", this.mLayout);
        state.putBoolean("mStartOpen", this.mStartOpen);
        state.putBoolean("mToggleOnTouch", this.mToggleOnTouch);
        state.putBoolean("mAnimateAlpha", this.mAnimateAlpha);
        state.putBoolean("mAnimateTranslation", this.mAnimateTranslation);
        state.putInt("mDuration", this.mDuration);
        state.putInt("mHandleSize", this.mHandleSize);
        state.putInt("mOffset", this.mOffset);
        return state;
    }

    // Restore
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle savedState = (Bundle) state;
        Parcelable superState = savedState.getParcelable("PARENT");
        super.onRestoreInstanceState(superState);
        this.mLayout = savedState.getInt("mLayout");
        this.mStartOpen = savedState.getBoolean("mStartOpen");
        this.mToggleOnTouch = savedState.getBoolean("mToggleOnTouch");
        this.mAnimateAlpha = savedState.getBoolean("mAnimateAlpha");
        this.mAnimateTranslation = savedState.getBoolean("mAnimateTranslation");
        this.mDuration = savedState.getInt("mDuration");
        this.mHandleSize = savedState.getInt("mHandleSize");
        this.mOffset = savedState.getInt("mOffset");
    }

    /**
     * Override
     */
    @Override
    public void onAnimationStart(Animator animation) {
        // Do nothing
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        // Call super
        super.onAnimationEnd();
        // Recalculate all
        this.requestLayout();
        // Check the status
        if (this.mIsOpen) {
            // Call the open event
            if (this.mOnChangeListener != null)
                this.mOnChangeListener.onOpened(this);
        } else {
            // Hide the panel if necessary
            if (this.mHideOnClose && this.mHandleSize <= 0)
                this.setVisibility(GONE);
            // Call the close event
            if (this.mOnChangeListener != null)
                this.mOnChangeListener.onClosed(this);
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        // Do nothing
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        // Do nothing
    }

    /**
     * Properties
     */
    // Source
    @SuppressWarnings("unused")
    public int getLayout() {
        return this.mLayout;
    }

    @SuppressWarnings("unused")
    public void setLayout(int resId) {
        // Only if different
        if (this.mLayout != resId) {
            // Remove old layout
            this.removeAllViews();
            // Hold then new value
            this.mLayout = resId;
            // Inflate the new layout
            this.inflateLayout(this.getContext(), this.mLayout);
            // Fix the position
            this.doAnimate(0, false);
        }
    }

    // Start open
    @SuppressWarnings("unused")
    public boolean getStartOpen() {
        return this.mStartOpen;
    }

    @SuppressWarnings("unused")
    public void setStartOpen(boolean value) {
        this.mStartOpen = value;
    }

    // Toggle visibility on touch
    @SuppressWarnings("unused")
    public boolean getToggleOnTouch() {
        return this.mToggleOnTouch;
    }

    @SuppressWarnings("unused")
    public void setToggleOnTouch(boolean value) {
        this.mToggleOnTouch = value;
    }

    // Animate alpha on transition
    @SuppressWarnings("unused")
    public boolean getAnimateAlpha() {
        return this.mAnimateAlpha;
    }

    @SuppressWarnings("unused")
    public void setAnimateAlpha(boolean value) {
        this.mAnimateAlpha = value;
    }

    // Animate translation on transition
    @SuppressWarnings("unused")
    public boolean getAnimateTranslation() {
        return this.mAnimateTranslation;
    }

    @SuppressWarnings("unused")
    public void setAnimateTranslation(boolean value) {
        this.mAnimateTranslation = value;
    }

    // Animation duration
    @SuppressWarnings("unused")
    public int getDuration() {
        return this.mDuration;
    }

    @SuppressWarnings("unused")
    public void setDuration(int value) {
        this.mDuration = value;
    }

    // Handle size
    @SuppressWarnings("unused")
    public int getHandleSize() {
        return this.mHandleSize;
    }

    @SuppressWarnings("unused")
    public void setHandleSize(int value) {
        // Set the new value
        this.mHandleSize = value;
        // Fix the position
        this.doAnimate(0, false);
    }

    /**
     * Public methods
     */
    // Close panel
    @SuppressWarnings("unused")
    public void close(boolean smooth) {
        // Set (virtually) the panel like visible
        this.mIsOpen = false;
        // Animate
        this.doAnimate(smooth ? this.mDuration : 0, true);
    }

    @SuppressWarnings("unused")
    public void close() {
        // Close with smooth animation
        this.close(true);
    }

    // Open panel
    @SuppressWarnings("unused")
    public void open(boolean smooth) {
        // Set (virtually) the panel like invisible
        this.mIsOpen = true;
        // Animate
        this.doAnimate(smooth ? this.mDuration : 0, true);
    }

    @SuppressWarnings("unused")
    public void open() {
        // open with smooth animation
        this.open(true);
    }

    // Toggle status
    @SuppressWarnings("unused")
    public void toggle(boolean smooth) {
        // Call the internal function toggle
        this.toggleVisibility(smooth ? this.mDuration : 0, true);
    }

    @SuppressWarnings("unused")
    public void toggle() {
        this.toggle(true);
    }

    // Return if is open
    @SuppressWarnings("unused")
    public boolean isOpen() {
        return this.mIsOpen;
    }

    /**
     * Public listener
     */
    // Set the listener
    @SuppressWarnings("unused")
    public void setOnChangeListener(OnChangeListener listener) {
        this.mOnChangeListener = listener;
    }

    // Set the listener
    @SuppressWarnings("unused")
    public void setOnHandleDragListener(OnHandleDragListener listener) {
        this.mOnHandleDragListener = listener;
    }

    // Define the on change listener
    public interface OnChangeListener {
        void onClosed(ScSlidingPanel source);

        void onOpened(ScSlidingPanel source);
    }

    // Define the on drag listener
    public interface OnHandleDragListener {
        void onStart(ScSlidingPanel source);

        void onEnd(ScSlidingPanel source);
    }
}