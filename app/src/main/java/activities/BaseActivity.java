package activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;
import fragments.tool.ConnectionDetector;
import fragments.tool.CropConfig;
import fragments.tool.Util;
import fragments.tool.WeakHandler;
import fragments.tool.preferences.Pref;
import fragments.views.mtAlertDialog;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;
import permissions.dispatcher.*;

@RuntimePermissions
public abstract class BaseActivity extends AppCompatActivity {
    private static BaseActivity BaseInstance;
    private static Context BaseContext;

    static {
        System.loadLibrary("webp");
    }

    public WeakHandler mHandler;

    public static BaseActivity baseInstance() {
        return BaseInstance;
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public static void RequestStorage() {
        ApplicationLoader.appInstance().storage.createDirectory("ronevis", false);
    }

    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION})
    public static void RequestAll() {
        RequestStorage();
    }

    @OnShowRationale({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION})
    public static void RequestAllRatinale(PermissionRequest request) {
        RequestShowDialog(request);
    }

    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION})
    public static void RequestAllDenied() {
        Toast.makeText(ApplicationLoader.getCurrentActivity(), R.string.permission_deny_all, Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION})
    public static void RequestAllNever() {
        Toast.makeText(ApplicationLoader.getCurrentActivity(), R.string.permission_never, Toast.LENGTH_LONG).show();
    }

    private static void RequestShowDialog(final PermissionRequest request) {
        mtAlertDialog.on(ApplicationLoader.getCurrentActivity())
                .with()
                .title(R.string.permission_title)
                .message(R.string.permission_all)
                .cancelable(false)
                .when(R.string.label_ok, dialog -> {
                    request.proceed();
                    dialog.dismiss();
                })
                .when(R.string.label_cancel, dialog -> {
                    request.cancel();
                    dialog.dismiss();
                })
                .show();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? super.onCreateView(parent, name, context, attrs) : super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.post(() -> {
            BaseActivityPermission.RequestAll(BaseActivity.this);
            runOnUiThread(() -> {
                try {
                    ConnectionDetector cd = new ConnectionDetector(ApplicationLoader.appInstance());
                    if (!cd.isConnectingToInternet()) {
                        int verLast = Integer.parseInt(Pref.get("LastAppVersion", "0"));
                        if (verLast > ApplicationLoader.appInstance().appVersionCode) {
                            mtAlertDialog.on(BaseActivity.baseInstance())
                                    .with()
                                    .title(R.string.duTitle)
                                    .message(R.string.duMessage)
                                    .cancelable(false)
                                    .when(R.string.duPositiveButton, dialog -> {
                                        try {
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse("https://example.com")); // جایگزین کن
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } catch (Exception ignored) {}
                                        finish();
                                        dialog.dismiss();
                                    })
                                    .when(R.string.duNegativeButton, dialog -> {
                                        BaseActivity.baseInstance().finish();
                                        dialog.dismiss();
                                    })
                                    .show();
                        }
                    }
                } catch (Exception ignored) {}
            });
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new WeakHandler();
        BaseInstance = this;
        ApplicationLoader.setCurrentActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActivityPermission.onRequestPermissionsResult(this, requestCode, grantResults);
        if (requestCode == CropConfig.REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            CropConfig.pickFromGallery(BaseActivity.baseInstance());
        }
    }
}
