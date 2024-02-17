package fragments.animation;

import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by mt.karimi on 5/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class InformAnimation extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1, 0, 1),
//                ObjectAnimator.ofFloat(target,"scaleX",1,0.9f,0.9f,1.4f,1.4f,1.4f,1.4f,1.4f,1.4f,1),
//                ObjectAnimator.ofFloat(target,"scaleY",1,0.9f,0.9f,1.4f,1.4f,1.4f,1.4f,1.4f,1.4f,1),
                ObjectAnimator.ofFloat(target, "scaleY", 1, 1.3f, 1),
                ObjectAnimator.ofFloat(target, "scaleX", 1, 1.3f, 1),
                ObjectAnimator.ofFloat(target, "rotation", 0, 5, 0)
        );
    }
}
