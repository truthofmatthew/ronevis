package fragments.objectHelper;

import android.os.Build;
import androidx.fragment.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

import activities.MainActivity;
import fragments.FireHelper;
import fragments.MTViews.ReboundHelpers.ReboundContainerView;
import fragments.tool.Util;
import mt.karimi.ronevis.R;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;
//import com.google.android.gms.analytics.Logger;

public class Manimator {
    public static void Alhpa(final View view, final float progress, final int Duration) {
        animate(view).alpha(progress).setDuration(Duration);
    }

    public static void AlhpaGone(final View view, final int Duration) {
        AlphaAnimation fadeInAnimation = new AlphaAnimation(1.0f, 0.0f);
        fadeInAnimation.setDuration(Duration);
        fadeInAnimation.setFillAfter(true);
        view.startAnimation(fadeInAnimation);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public static void AlhpaVis(final View view, final int Duration) {
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeInAnimation.setDuration(Duration);
        fadeInAnimation.setFillAfter(true);
        view.startAnimation(fadeInAnimation);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public static void RotateX(final View view, final float progress, final int Duration) {
        animate(view).rotationX(progress).setDuration(Duration);
    }

    public static void RotateY(final View view, final float progress, final int Duration) {
        animate(view).rotationY(progress).setDuration(Duration);
    }

    public static void RotateZ(final View view, final float progress, final int Duration) {
        animate(view).rotation(progress).setDuration(Duration);
    }

    public static void SlideInUp(final ReboundContainerView target) {
        MainActivity.mainInstance().mAnimating = true;
        ViewTreeObserver vtos = target.getViewTreeObserver();
        vtos.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (target.getMeasuredHeight() > 0) {
                    target.setTranslationY(target.getMeasuredHeight());
                    target.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            target.reveal(true, new ReboundContainerView.Callback() {
                                @Override
                                public void onProgress(double progress) {
//                        float scale = (float) SpringUtil.mapValueFromRangeToRange(progress, 0, 1, 0.8, 1);
//                        mRootContainer.setScaleX(scale);
//                        mRootContainer.setScaleY(scale);
//                        mRootContainer.setAlpha((float) progress);
                                }

                                @Override
                                public void onEnd(View v) {
                                    MainActivity.mainInstance().mAnimating = false;
                                }
                            });
                        }
                    }, 100);
//                    target.setStashPixel(0);
//                    target.setRevealPixel(target.getMeasuredHeight());
//                    target.setTranslationY(target.getMeasuredHeight());
//                    target.setTranslateDirection(ReboundRevealRelativeLayout.TRANSLATE_DIRECTION_VERTICAL);
//                    target.setOpen(false);
//                    if(ViewCompat.isLaidOut(target)) {
//                        YoYo.with(Techniques.SlideInUp)
//                                .duration(230)
//                                .playOn(target);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        target.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        target.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
//                    }
                }
            }
        });
    }

    public static void InformAnimation(View view) {
        YoYo.with(Techniques.Shake)
                .duration(Util.getInt(R.integer.AnimDurationLongest))
                .playOn(view);
    }

    public static void SlideOutDownRemove(final ViewGroup Removeview, final View view, final int Duration, final FragmentManager fragmentManager) {
        try {
            if (view != null) {
                YoYo.with(Techniques.SlideOutDown)
                        .duration(Duration)
                        .withListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                if (MainActivity.mainInstance().RTFrame.isEmpty()) {
                                    MainActivity.mainInstance().FragContainerFrame = null;
                                }
                                Removeview.removeView(view);
                                fragmentManager.popBackStack();
                            }
                        })
                        .playOn(view);
            }
        } catch (Exception ignored) {

            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);

        }
    }
}
