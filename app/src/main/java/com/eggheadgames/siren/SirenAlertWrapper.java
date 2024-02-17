package com.eggheadgames.siren;

import android.app.Activity;
import android.content.DialogInterface;

import java.lang.ref.WeakReference;

import activities.MainActivity;
import fragments.tool.Util;
import fragments.views.mtAlertDialog;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.BuildConfig;
import mt.karimi.ronevis.R;

public class SirenAlertWrapper {
    private final WeakReference<Activity> mActivityRef;
    private final ISirenListener mSirenListener;
    private final String mMinAppVersion;
    private final SirenHelper mSirenHelper;

    public SirenAlertWrapper(Activity activity, ISirenListener sirenListener, SirenAlertType sirenAlertType,
                             String minAppVersion, SirenSupportedLocales locale, SirenHelper sirenHelper) {
        this.mSirenListener = sirenListener;
        this.mMinAppVersion = minAppVersion;
        this.mSirenHelper = sirenHelper;
        this.mActivityRef = new WeakReference<>(activity);
    }

    public void show() {
        if (mActivityRef.get() != null) {
            setupDialog();
        } else {
            if (mSirenListener != null) {
                mSirenListener.onError(new NullPointerException("activity reference is null"));
            }
        }
    }

    private void setupDialog() {
        mtAlertDialog.on(MainActivity.mainInstance())
                .with()
                .title(R.string.duTitle)
                .message(String.format(Util.Persian(R.string.duMessage), BuildConfig.marketName, ApplicationLoader.appInstance().appVersionCode, mMinAppVersion))
                .icon(Util.getDrawableIcon(R.string.Icon_download, R.color.colorAccent))
                .cancelable(false)
                .when(R.string.duPositiveButton, new mtAlertDialog.Positive() {
                    @Override
                    public void clicked(DialogInterface dialog) {
                        mSirenHelper.openGooglePlay(mActivityRef.get());
                        MainActivity.mainInstance().finish();
                        dialog.dismiss();
                    }
                })
                .when(R.string.duNegativeButton, new mtAlertDialog.Negative() {
                    @Override
                    public void clicked(DialogInterface dialog) {
                        MainActivity.mainInstance().finish();
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
