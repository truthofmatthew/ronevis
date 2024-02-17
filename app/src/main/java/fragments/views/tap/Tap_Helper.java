package fragments.views.tap;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 02/11/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class Tap_Helper {
    public static void showViewPrompt(Activity activity, View view) {
        final MaterialTapTargetPrompt.Builder tapTargetPromptBuilder = new MaterialTapTargetPrompt.Builder(activity)
                .setPrimaryText(R.string.daaMe)
                .setSecondaryText(R.string.daaEmail)
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .setMaxTextWidth(R.dimen.tap_target_menu_max_width)
                .setAutoDismiss(false)
                .setAutoFinish(false);
        tapTargetPromptBuilder.setTarget(view);
        tapTargetPromptBuilder.setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener() {
            @Override
            public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                //Do something such as storing a value so that this prompt is never shown again
            }

            @Override
            public void onHidePromptComplete() {
            }
        });
        tapTargetPromptBuilder.show();
    }
//    public void showOverflowPrompt(View view)
//    {
//        final MaterialTapTargetPrompt.Builder tapTargetPromptBuilder = new MaterialTapTargetPrompt.Builder(this)
//                .setPrimaryText(R.string.overflow_prompt_title)
//                .setSecondaryText(R.string.overflow_prompt_description)
//                .setAnimationInterpolator(new FastOutSlowInInterpolator())
//                .setMaxTextWidth(R.dimen.tap_target_menu_max_width)
//                .setIcon(R.drawable.ic_more_vert);
//        final Toolbar tb = (Toolbar) this.findViewById(R.id.toolbar);
//        tapTargetPromptBuilder.setTarget(tb.getChildAt(2));
//
//        tapTargetPromptBuilder.setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener()
//        {
//            @Override
//            public void onHidePrompt(MotionEvent event, boolean tappedTarget)
//            {
//                //Do something such as storing a value so that this prompt is never shown again
//            }
//
//            @Override
//            public void onHidePromptComplete()
//            {
//
//            }
//        });
//        tapTargetPromptBuilder.show();
//    }
//
//
//    public void showSideNavigationPrompt(View view)
//    {
//        final MaterialTapTargetPrompt.Builder tapTargetPromptBuilder = new MaterialTapTargetPrompt.Builder(this)
//                .setPrimaryText(R.string.menu_prompt_title)
//                .setSecondaryText(R.string.menu_prompt_description)
//                .setAnimationInterpolator(new FastOutSlowInInterpolator())
//                .setMaxTextWidth(R.dimen.tap_target_menu_max_width)
//                .setIcon(R.drawable.ic_menu);
//        final Toolbar tb = (Toolbar) this.findViewById(R.id.toolbar);
//        tapTargetPromptBuilder.setTarget(tb.getChildAt(1));
//
//        tapTargetPromptBuilder.setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener()
//        {
//            @Override
//            public void onHidePrompt(MotionEvent event, boolean tappedTarget)
//            {
//                //Do something such as storing a value so that this prompt is never shown again
//            }
//
//            @Override
//            public void onHidePromptComplete()
//            {
//
//            }
//        });
//        tapTargetPromptBuilder.show();
//    }
//    MaterialTapTargetPrompt mFabPrompt;
//    public void showNoAutoDismiss(View view)
//    {
//        if (mFabPrompt != null)
//        {
//            return;
//        }
//        mFabPrompt = new MaterialTapTargetPrompt.Builder(MainActivity.this)
//                .setTarget(findViewById(R.id.fab))
//                .setPrimaryText("No Auto Dismiss")
//                .setSecondaryText("This prompt will only be removed after tapping the envelop")
//                .setAnimationInterpolator(new FastOutSlowInInterpolator())
//                .setAutoDismiss(false)
//                .setAutoFinish(false)
//                .setCaptureTouchEventOutsidePrompt(true)
//                .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener()
//                {
//                    @Override
//                    public void onHidePrompt(MotionEvent event, boolean tappedTarget)
//                    {
//                        if (tappedTarget)
//                        {
//                            mFabPrompt.finish();
//                            mFabPrompt = null;
//                        }
//                    }
//
//                    @Override
//                    public void onHidePromptComplete()
//                    {
//
//                    }
//                })
//                .show();
//    }
//
}
