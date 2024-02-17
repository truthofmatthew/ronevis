package fragments.tool;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.util.List;

import activities.MainActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import fragments.MTViews.LayoutHelper;
import fragments.MTViews.ReboundHelpers.ReboundContainerView;
import fragments.objectHelper.Manimator;

public class FragmentController {
    private final Activity activity;
    private final FragmentActivity fragmentActivity;
    private Fragment fragment;
    private ViewGroup viewGroup;
    private View view;
    private Boolean checkView = false;
    private String backStackName;
    private int messageid = 0;
    private Bundle bundle;
    private int fragSize = LayoutHelper.WRAP_CONTENT;

    private FragmentController(Activity activity, FragmentActivity fragmentActivity) {
        this.activity = activity;
        this.fragmentActivity = fragmentActivity;
    }

    public static FragmentController on(Activity activity, FragmentActivity fragmentActivity) {
        return new FragmentController(activity, fragmentActivity);
    }

    public int getFragSize() {
        return fragSize;
    }

    public FragmentController setFragSize(int mfragSize) {
        fragSize = mfragSize > -2 ? mfragSize : LayoutHelper.WRAP_CONTENT;
        return this;
    }

    public FragmentController fragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public FragmentController viewGroup(RelativeLayout viewGroup) {
        this.viewGroup = viewGroup;
        return this;
    }

    public FragmentController viewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        return this;
    }

    public FragmentController viewChecker(View view) {
        this.view = view;
        return this;
    }

    public FragmentController Message(int messageid) {
        this.messageid = messageid;
        return this;
    }

    public FragmentController Bundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    public FragmentController BackStackName(String backStackName) {
        this.backStackName = backStackName;
        return this;
    }

    public FragmentController CheckView(Boolean checkView) {
        this.checkView = checkView;
        return this;
    }

    private void createFragment() {
        List<Fragment> fragentList = fragmentActivity.getSupportFragmentManager().getFragments();
        Fragment top;
        for (int i = fragentList.size() - 1; i >= 0; i--) {
            top = fragentList.get(i);
            if (top != null) {
                if (backStackName.equals(top.getTag())) {
//                    boolean fragmentPopped = fragmentActivity.getSupportFragmentManager().popBackStackImmediate(backStackName, i);
//                    Logger.d(" " + fragmentPopped);
                    return;
                }
//                Logger.d(" " + top.getTag());
//                Logger.d(" " + top.getClass().getName());
            }
        }
        try {
            MainActivity.mainInstance().FragContainerFrame = new ReboundContainerView(activity);
            MainActivity.mainInstance().FragContainerFrame.setId(Util.randInt());
            MainActivity.mainInstance().FragContainerFrame.setTag(backStackName);
            RelativeLayout.LayoutParams fragmentParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getFragSize());
            fragmentParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            MainActivity.mainInstance().FragContainerFrame.setLayoutParams(fragmentParams);
            MainActivity.mainInstance().RTFrame.add(MainActivity.mainInstance().FragContainerFrame);
//            viewGroup.addView(MainActivity.mainInstance().FragContainerFrame, LayoutHelper.createRelative(LayoutHelper.MATCH_PARENT, getFragSize(),RelativeLayout.ALIGN_PARENT_BOTTOM));
            viewGroup.addView(MainActivity.mainInstance().FragContainerFrame, fragmentParams);
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            fragmentTransaction.add(MainActivity.mainInstance().FragContainerFrame.getId(), fragment, backStackName);
            fragmentTransaction.addToBackStack(backStackName);
            fragmentTransaction.commitAllowingStateLoss();
            MainActivity.mainInstance().FragContainerFrame.setClickable(false);
            MainActivity.mainInstance().FragContainerFrame.setEnabled(true);
            MainActivity.mainInstance().FragContainerFrame.setFocusableInTouchMode(false);
            MainActivity.mainInstance().FragContainerFrame.setFocusable(false);
            Manimator.SlideInUp(MainActivity.mainInstance().FragContainerFrame);
        } catch (Exception ignored) {
            FirebaseCrash.report(ignored);
        }
    }

    public FragmentController show() {
        if (checkView) {
            if (view == null) {
                if (messageid > 0) {
                    Toast.makeText(activity, Util.Persian(messageid), Toast.LENGTH_SHORT).show();
                }
            } else if (activity != null) {
                createFragment();
            }
        } else {
            if (activity != null) {
                createFragment();
            }
        }
        return this;
    }
}
