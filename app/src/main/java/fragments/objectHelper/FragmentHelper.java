package fragments.objectHelper;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.FireHelper;
import fragments.tool.Util;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/21/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class FragmentHelper {
    public static Fragment getActiveFragmentByTag(AppCompatActivity activity) {
        if (activity.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            return null;
        }
        String tag = activity.getSupportFragmentManager().getBackStackEntryAt(activity.getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return activity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    public static Fragment getActiveFragmentByTag(String tag) {
        if (MainActivity.mainInstance().getSupportFragmentManager().getBackStackEntryCount() > 0) {
            return null;
        }
        return MainActivity.mainInstance().getSupportFragmentManager().findFragmentByTag(tag);
    }

    public static Fragment getActiveFragment() {
        if (MainActivity.mainInstance().FragContainerFrame != null) {
            return MainActivity.mainInstance().getSupportFragmentManager().findFragmentById(MainActivity.mainInstance().FragContainerFrame.getId());
        }
        return new Fragment();
    }

    public static void removeAllFragments() {
        if (MainActivity.mainInstance().getSupportFragmentManager().getBackStackEntryCount() > 0) {
            while (!MainActivity.mainInstance().RTFrame.isEmpty()) {
                MainActivity.mainInstance().FragContainerFrame = MainActivity.mainInstance().RTFrame.get(MainActivity.mainInstance().RTFrame.size() - 1);
                MainActivity.mainInstance().RTFrame.remove(MainActivity.mainInstance().RTFrame.size() - 1);
                if (MainActivity.mainInstance().FragContainerFrame != null) {
                    Manimator.SlideOutDownRemove(MainActivity.mainInstance().mainLayout, MainActivity.mainInstance().FragContainerFrame, Util.getInt(R.integer.AnimDuration), MainActivity.mainInstance().getSupportFragmentManager());
                }
            }
        }
    }

    public static String getActiveFragmentName() {
        try {
            if (!MainActivity.mainInstance().RTFrame.isEmpty()) {
                MainActivity.mainInstance().FragContainerFrame2 = MainActivity.mainInstance().RTFrame.get(MainActivity.mainInstance().RTFrame.size() - 1);
                Fragment checkMe = MainActivity.mainInstance().getSupportFragmentManager().findFragmentById(MainActivity.mainInstance().FragContainerFrame2.getId());
                if (checkMe instanceof BaseFragment) {
                    BaseFragment sourceObjectType = (BaseFragment) MainActivity.mainInstance().getSupportFragmentManager().findFragmentById(MainActivity.mainInstance().FragContainerFrame2.getId());
                    return sourceObjectType.myNameIs();
                }
            }
        } catch (Exception ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
        return "Nothing";
    }
}