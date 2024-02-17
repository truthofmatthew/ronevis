package com.eggheadgames.siren;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.firebase.crash.FirebaseCrash;

import org.acra.sender.AcraLSender;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import androidx.annotation.VisibleForTesting;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.BuildConfig;
import mt.karimi.ronevis.R;

public class SirenHelper {
    private static final SirenHelper instance = new SirenHelper();

    @SuppressWarnings("WeakerAccess")
    @VisibleForTesting
    protected SirenHelper() {
    }

    public static SirenHelper getInstance() {
        return instance;
    }

    String getPackageName(Context context) {
        return context.getPackageName();
    }

    int getDaysSinceLastCheck(Context context) {
        long lastCheckTimestamp = getLastVerificationDate(context);
        if (lastCheckTimestamp > 0) {
            return (int) (TimeUnit.MILLISECONDS.toDays(Calendar.getInstance().getTimeInMillis()) - TimeUnit.MILLISECONDS.toDays(lastCheckTimestamp));
        } else {
            return 0;
        }
    }

    int getVersionCode(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(context), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException igmored) {
            return 0;
        }
    }

    boolean isVersionSkippedByUser(Context context, String minAppVersion) {
        String skippedVersion = PreferenceManager.getDefaultSharedPreferences(context).getString(ApplicationLoader.appInstance().getString(R.string.PREFERENCES_SKIPPED_VERSION), "");
        return !skippedVersion.equals(minAppVersion);
    }

    void setLastVerificationDate(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putLong(ApplicationLoader.appInstance().getString(R.string.PREFERENCES_LAST_CHECK_DATE), Calendar.getInstance().getTimeInMillis())
                .commit();
    }

    long getLastVerificationDate(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(ApplicationLoader.appInstance().getString(R.string.PREFERENCES_LAST_CHECK_DATE), 0);
    }

    public void openGooglePlay(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(BuildConfig.marketUriApp));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage(BuildConfig.marketPackage);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ignored) {
            FirebaseCrash.report(ignored);
        }
    }

    void setVersionSkippedByUser(Context context, String skippedVersion) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(ApplicationLoader.appInstance().getString(R.string.PREFERENCES_SKIPPED_VERSION), skippedVersion)
                .commit();
    }

    String getVersionName(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(context), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
            FirebaseCrash.report(ignored);
            return "";
        }
    }

    boolean isGreater(String first, String second) {
        return TextUtils.isDigitsOnly(first) && TextUtils.isDigitsOnly(second) && Integer.parseInt(first) > Integer.parseInt(second);
    }

    boolean isEmpty(String appDescriptionUrl) {
        return TextUtils.isEmpty(appDescriptionUrl);
    }
}
