package org.apprater;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.acra.sender.AcraLSender;

import fragments.FireHelper;
import mt.karimi.ronevis.ApplicationLoader;

public final class ApplicationRatingInfo {
    private String applicationName;
    private int applicationVersionCode;
    private String applicationVersionName;

    private ApplicationRatingInfo() {
    }

    public static ApplicationRatingInfo createApplicationInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(
                    context.getApplicationInfo().packageName, 0);
            packageInfo = packageManager.getPackageInfo(
                    context.getApplicationInfo().packageName, 0);
        } catch (final PackageManager.NameNotFoundException ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
        ApplicationRatingInfo resultInfo = new ApplicationRatingInfo();
        resultInfo.applicationName = packageManager.getApplicationLabel(
                applicationInfo).toString();
        resultInfo.applicationVersionCode = packageInfo != null ? packageInfo.versionCode : 0;
        resultInfo.applicationVersionName = packageInfo != null ? packageInfo.versionName : "";
        return resultInfo;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public int getApplicationVersionCode() {
        return applicationVersionCode;
    }

    public String getApplicationVersionName() {
        return applicationVersionName;
    }
}