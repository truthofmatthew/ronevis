package fragments.MTViews.ReboundHelpers;
/**
 * Created by mt.karimi on 03/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

/**
 * A FrameLayout that can be animated vertically or horizontally using Facebook's Rebound library
 * Created by vishnu on 8/5/14.
 *
 * @see FrameLayout
 */
public class ReboundRevealFrameLayout extends FrameLayout {
    public static final int TRANSLATE_DIRECTION_VERTICAL = 0;
    public static final int TRANSLATE_DIRECTION_HORIZONTAL = 1;
    private static final SpringConfig SPRING_CONFIG = SpringConfig.fromOrigamiTensionAndFriction(6, 6);
    private int mRevealPixel;
    private int mStashPixel;
    private Spring mSpring;
    private boolean mOpen;
    private int mTranslateDirection;
    private RevealListener mRevealListener;

    public ReboundRevealFrameLayout(Context context) {
        this(context, null);
        Init();
    }

    public ReboundRevealFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Init();
    }

    public ReboundRevealFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Init();
    }

    public void Init() {
        SpringSystem springSystem = SpringSystem.create();
        mSpring = springSystem.createSpring();
        mSpring.setSpringConfig(SPRING_CONFIG);
        LinkSpringListener linkSpringListener = new LinkSpringListener();
        mSpring.setCurrentValue(0)
                .setEndValue(1)
                .addListener(linkSpringListener);
    }

    private void togglePosition(boolean open) {
        mSpring.setEndValue(open
                ? 0
                : 1);
    }

    public boolean isOpen() {
        return mOpen;
    }

    /**
     * Set whether the view visible or not
     *
     * @param open true if visible
     */
    public void setOpen(boolean open) {
        mOpen = open;
        togglePosition(open);
    }

    /**
     * Sets the direction in which to reveal and stash the view
     *
     * @param translateDirection {@link  } describing the direction to animate
     */
    public void setTranslateDirection(int translateDirection) {
        mTranslateDirection = translateDirection;
    }

    public int getRevealPixel() {
        return mRevealPixel;
    }

    /**
     * Sets the pixel to reveal the view to
     *
     * @param revealPixel Integer value to set the view to when revealing
     */
    public void setRevealPixel(int revealPixel) {
        mRevealPixel = revealPixel;
    }

    public int getStashPixel() {
        return mStashPixel;
    }

    /**
     * Sets the pixel to stash the view to
     *
     * @param stashPixel Integer value to set the view to when stashing
     */
    public void setStashPixel(int stashPixel) {
        mStashPixel = stashPixel;
    }

    /**
     * Set a listener callback for when visibility animations are complete
     *
     * @param listener {@link ReboundRevealFrameLayout.RevealListener}  listener for when animations complete
     */
    public void setRevealListener(RevealListener listener) {
        mRevealListener = listener;
    }

    /**
     * Interface to implement if you want to subscribe to visibility changes
     */
    public interface RevealListener {
        void onVisibilityChange(boolean visible);
    }

    private class LinkSpringListener implements SpringListener {
        @Override
        public void onSpringUpdate(Spring spring) {
            float val = (float) spring.getCurrentValue();
            float maxTranslate = mStashPixel;
            float minTranslate = mRevealPixel;
            float range = maxTranslate - minTranslate;
            float translate = (val * range) + minTranslate;
            switch (mTranslateDirection) {
                case TRANSLATE_DIRECTION_HORIZONTAL:
                    setTranslationX(translate);
                    break;
                case TRANSLATE_DIRECTION_VERTICAL:
                    setTranslationY(translate);
                    break;
            }
        }

        @Override
        public void onSpringAtRest(Spring spring) {
            if (mRevealListener != null) {
                mRevealListener.onVisibilityChange(spring.getCurrentValue() == 0.0);
            }
        }

        @Override
        public void onSpringActivate(Spring spring) {
        }

        @Override
        public void onSpringEndStateChange(Spring spring) {
        }
    }
}