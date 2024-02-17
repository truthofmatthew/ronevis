package activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

import fragments.tool.ConnectionDetector;
import fragments.tool.CropConfig;
import fragments.tool.Util;
import fragments.tool.WeakHandler;
import fragments.tool.preferences.Pref;
import fragments.views.mtAlertDialog;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.BuildConfig;
import mt.karimi.ronevis.R;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import users.AppLocationService;
import users.PushUtil;

@RuntimePermissions
public abstract class BaseActivity extends AppCompatActivity {
    private static BaseActivity BaseInstance;
    private static Context BaseContext;

    static {
        System.loadLibrary("webp");
    }

    public WeakHandler mHandler;
    PushUtil pushUtil;
    AppLocationService appLocationService;
    private FirebaseAnalytics mFirebaseAnalytics;

    public static BaseActivity baseInstance() {
        return BaseInstance;
    }

    @NeedsPermission({android.Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public static void RequestStorage() {
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathFolder), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisExportPathFolder), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisTemplatePathFolder), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathTemp), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathBackGrounds), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathBackGroundsImg), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathBackGroundsTmb), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathStickers), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathTexture), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathFonts), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsFa), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsEn), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsUser), false);
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
                .icon(Util.getDrawableIcon(R.string.Icon_check_circle, R.color.colorGreen))
                .cancelable(false)
                .when(R.string.label_ok, new mtAlertDialog.Positive() {
                    @Override
                    public void clicked(DialogInterface dialog) {
                        request.proceed();
                        dialog.dismiss();
                    }
                })
                .when(R.string.label_cancel, new mtAlertDialog.Negative() {
                    @Override
                    public void clicked(DialogInterface dialog) {
                        request.cancel();
                        dialog.dismiss();
                    }
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
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                pushUtil = new PushUtil().getInstance();
                BaseActivityPermission.RequestAll(BaseActivity.this);
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            ConnectionDetector cd = new ConnectionDetector(ApplicationLoader.appInstance());
                            if (cd.isConnectingToInternet()) {
                                Util.checkCurrentAppVersion(BaseActivity.baseInstance());
//                        if (Util.checkPlayServices(BaseActivity.baseInstance())) {
//                            try {
//                                appLocationService = new AppLocationService(
//                                        BaseActivity.this);
//                                Location nwLocation = appLocationService
//                                        .getLocation(LocationManager.NETWORK_PROVIDER);
//                                if (nwLocation != null) {
//                                    double latitude = nwLocation.getLatitude();
//                                    double longitude = nwLocation.getLongitude();
//                                    Geocoder geocoder = new Geocoder(BaseActivity.this, Locale.getDefault());
//                                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                                    String cityName = addresses.get(0).getAddressLine(0);
//                                    String stateName = addresses.get(0).getAddressLine(1);
//                                    String countryName = addresses.get(0).getAddressLine(2);
//
//                                    Logger.d("cityName"  + cityName + "\n stateName " + stateName+ "\n countryName " + countryName);
//
//                                }
//                                pushUtil.registerGCM("Iran");
//                            } catch (IOException ignored) {
//                                AcraLSender acraLSender = new AcraLSender();
//                                acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "BaseActivity_166");
//                            }
//                        }
                            } else {
                                int verLast = Integer.valueOf(Pref.get("LastAppVersion", "0"));
                                if (verLast > ApplicationLoader.appInstance().appVersionCode) {
                                    mtAlertDialog.on(BaseActivity.baseInstance())
                                            .with()
                                            .title(R.string.duTitle)
                                            .message(String.format(Util.Persian(R.string.duMessage), BuildConfig.marketUriApp, ApplicationLoader.appInstance().appVersionCode, verLast))
                                            .icon(Util.getDrawableIcon(R.string.Icon_download, R.color.colorAccent))
                                            .cancelable(false)
                                            .when(R.string.duPositiveButton, new mtAlertDialog.Positive() {
                                                @Override
                                                public void clicked(DialogInterface dialog) {
                                                    try {
                                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                                        intent.setData(Uri.parse(BuildConfig.marketUriApp));
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        intent.setPackage(BuildConfig.marketPackage);
                                                        startActivity(intent);
                                                    } catch (ActivityNotFoundException ignored) {
                                                        FirebaseCrash.report(ignored);
                                                    }
                                                    finish();
                                                    dialog.dismiss();
                                                }
                                            })
                                            .when(R.string.duNegativeButton, new mtAlertDialog.Negative() {
                                                @Override
                                                public void clicked(DialogInterface dialog) {
                                                    BaseActivity.baseInstance().finish();
                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                }
                            }
                        } catch (Exception ignored) {
                            FirebaseCrash.report(ignored);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new WeakHandler();
        BaseInstance = this;
        ApplicationLoader.setCurrentActivity(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        Icepick.restoreInstanceState(this, savedInstanceState);
    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Icepick.saveInstanceState(this, outState);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActivityPermission.onRequestPermissionsResult(this, requestCode, grantResults);
        switch (requestCode) {
            case CropConfig.REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                CropConfig.pickFromGallery(BaseActivity.baseInstance());
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }
}