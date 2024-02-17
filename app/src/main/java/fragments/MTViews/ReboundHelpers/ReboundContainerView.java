/*
 * This file provided by Facebook is for non-commercial testing and evaluation purposes only.
 * Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fragments.MTViews.ReboundHelpers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

public class ReboundContainerView extends FrameLayout implements SpringListener {
    boolean removeMe = false;
    private SpringSystem mSpringSystem;
    private Spring mTransitionSpring;
    private Callback mCallback;

    public ReboundContainerView(Context context) {
        this(context, null);
    }

    public ReboundContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundContainerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mSpringSystem = SpringSystem.create();
        mTransitionSpring = mSpringSystem.createSpring();
        setClickable(true);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTransitionSpring.setCurrentValue(1).setAtRest();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
//    setBackgroundColor(Color.WHITE);
    }

    public void clearCallback() {
        mCallback = null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTransitionSpring.addListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTransitionSpring.removeListener(this);
    }

    public void reveal(boolean animated, Callback callback) {
        if (animated) {
            mTransitionSpring.setEndValue(0);
        } else {
            mTransitionSpring.setCurrentValue(0).setAtRest();
        }
        mCallback = callback;
    }

    public void hide(boolean animated, Callback callback) {
        if (animated) {
            mTransitionSpring.setEndValue(1);
        } else {
            mTransitionSpring.setCurrentValue(1).setAtRest();
        }
        mCallback = callback;
    }

    public void remove(boolean remove, Callback callback) {
        removeMe = remove;
        mTransitionSpring.setEndValue(1);
        mCallback = callback;
    }

    @Override
    public void onSpringUpdate(Spring spring) {
        double val = spring.getCurrentValue();
        float ylat = (float) SpringUtil.mapValueFromRangeToRange(val, 0, 1, 0, getHeight());
        setTranslationY(ylat);
        if (mCallback != null) {
            mCallback.onProgress(spring.getCurrentValue());
        }
    }

    @Override
    public void onSpringAtRest(Spring spring) {
        if (mCallback != null) {
            mCallback.onEnd(this);
        }
        if (removeMe) {
            clearCallback();
        }
    }

    @Override
    public void onSpringActivate(Spring spring) {
    }

    @Override
    public void onSpringEndStateChange(Spring spring) {
    }

    public interface Callback {
        void onProgress(double progress);

        void onEnd(View v);
    }
}
