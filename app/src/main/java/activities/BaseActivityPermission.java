package activities;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

import java.lang.ref.WeakReference;

import androidx.core.app.ActivityCompat;
import fragments.tool.Util;
import mt.karimi.ronevis.R;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.PermissionUtils;

public abstract class BaseActivityPermission {
    // Only Android 9 and older need a permission (to copy saved images into Pictures); newer versions use MediaStore.
    public static final String[] PERMISSIONS_ALL = Build.VERSION.SDK_INT <= Build.VERSION_CODES.P
            ? new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
            : new String[0];

    private BaseActivityPermission() {
    }

    public static boolean RequestAll(Activity target) {
        if (PermissionUtils.hasSelfPermissions(target, PERMISSIONS_ALL)) {
            BaseActivity.RequestAll();
            return true;
        } else {
            if (PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSIONS_ALL)) {
                BaseActivity.RequestAllRatinale(new RequestPermissionUser(target, Util.getInt(R.integer.REQUEST_ALL)));
            } else {
                ActivityCompat.requestPermissions(target, PERMISSIONS_ALL, Util.getInt(R.integer.REQUEST_ALL));
            }
            return false;
        }
    }

    public static void onRequestPermissionsResult(Activity target, int requestCode, int[] grantResults) {
        if (requestCode == Util.getInt(R.integer.REQUEST_ALL)) {
            if (Build.VERSION.SDK_INT < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSIONS_ALL)) {
                BaseActivity.RequestAllDenied();
                return;
            }
            if (PermissionUtils.verifyPermissions(grantResults)) {
                BaseActivity.RequestAll();
            } else {
                if (!PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSIONS_ALL)) {
                    BaseActivity.RequestAllNever();
                } else {
                    BaseActivity.RequestAllDenied();
                }
            }
        }
    }

    private static final class RequestPermissionUser implements PermissionRequest {
        final String[] PERMISSIONS_ASKED;
        private final WeakReference<Activity> weakTarget;
        int REQUEST_CODE = 0;

        private RequestPermissionUser(Activity target, int REQUEST) {
            weakTarget = new WeakReference<>(target);
            PERMISSIONS_ASKED = PERMISSIONS_ALL;
            REQUEST_CODE = REQUEST;
        }

        @Override
        public void proceed() {
            Activity target = weakTarget.get();
            if (target == null) return;
            ActivityCompat.requestPermissions(target, PERMISSIONS_ASKED, REQUEST_CODE);
        }

        @Override
        public void cancel() {
            Activity target = weakTarget.get();
            if (target == null) return;
            if (REQUEST_CODE == Util.getInt(R.integer.REQUEST_ALL)) {
                BaseActivity.RequestAllDenied();
            }
        }
    }
}