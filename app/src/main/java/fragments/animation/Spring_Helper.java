package fragments.animation;

import android.view.View;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.tumblr.backboard.performer.Performer;

import fragments.Interface.TaskProcess;

/**
 * Created by mt.karimi on 03/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class Spring_Helper {
    private static final SpringConfig SPRING_CONFIG = SpringConfig.fromOrigamiTensionAndFriction(40, 7);

    public static void spring_Views(View View_To_Show, float Current, float End, final TaskProcess mTaskProcess) {
        final SpringSystem mSpringSystem = SpringSystem.create();
        final Spring mSpring = mSpringSystem.createSpring();
//        mSpring.setSpringConfig(SPRING_CONFIG);
        mSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                if (mTaskProcess != null) {
                    mTaskProcess.onProgress(spring.getCurrentValue());
                }
            }

            @Override
            public void onSpringAtRest(Spring spring) {
                if (mTaskProcess != null) {
                    mTaskProcess.onDone();
                    mSpring.removeAllListeners();
                }
            }

            @Override
            public void onSpringActivate(Spring spring) {
            }

            @Override
            public void onSpringEndStateChange(Spring spring) {
            }
        });
        Performer SCALE_X = new Performer(View_To_Show, View.SCALE_X);
        Performer SCALE_Y = new Performer(View_To_Show, View.SCALE_Y);
        mSpring.addListener(SCALE_X);
        mSpring.addListener(SCALE_Y);
        mSpring.setCurrentValue(Current);
        mSpring.setEndValue(End);
        mSpring.setVelocity(0);
    }

    public float transition(float progress, float startValue, float endValue) {
        return (float) SpringUtil.mapValueFromRangeToRange(progress, 0, 1, startValue, endValue);
    }
//    @Override
//    public void onClick(View v) {
//        mPhotoIsZoomedOut = !mPhotoIsZoomedOut;
//        photoIsZoomedOut(mPhotoIsZoomedOut);
//    }
//    private boolean mPhotoIsZoomedOut;   private final Spring photoIsZoomedOutSpring;
//    public void photoIsZoomedOut(boolean on) {
//        photoIsZoomedOutSpring.setEndValue(on ? 1 : 0);
//    }
//    https://github.com/Korilakkuma/CanvasView
//    https://android-arsenal.com/details/1/4242
//    https://android-arsenal.com/details/1/3592
}
