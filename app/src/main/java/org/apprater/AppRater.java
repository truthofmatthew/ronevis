package org.apprater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ShareCompat;
import android.widget.Toast;

import activities.MainActivity;
import fragments.FireHelper;
import fragments.tool.Util;
import fragments.views.mtAlertDialog;
import mt.karimi.ronevis.BuildConfig;
import mt.karimi.ronevis.R;

public class AppRater {
    private final static String PREF_NAME = "apprater";
    private final static String PREF_LAUNCH_COUNT = "launch_count";
    private final static String PREF_FIRST_LAUNCHED = "date_firstlaunch";
    private final static String PREF_DONT_SHOW_AGAIN = "dontshowagain";
    private final static String PREF_REMIND_LATER = "remindmelater";
    private final static String PREF_APP_VERSION_NAME = "app_version_name";
    private final static String PREF_APP_VERSION_CODE = "app_version_code";
    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 7;
    private static int DAYS_UNTIL_PROMPT_FOR_REMIND_LATER = 3;
    private static int LAUNCHES_UNTIL_PROMPT_FOR_REMIND_LATER = 7;
    private static boolean isVersionNameCheckEnabled;
    private static boolean isVersionCodeCheckEnabled;

    public static void setVersionNameCheckEnabled(boolean versionNameCheck) {
        isVersionNameCheckEnabled = versionNameCheck;
    }

    public static void setVersionCodeCheckEnabled(boolean versionCodeCheck) {
        isVersionCodeCheckEnabled = versionCodeCheck;
    }

    private static void setNumDaysForRemindLater(int daysUntilPromt) {
        DAYS_UNTIL_PROMPT_FOR_REMIND_LATER = daysUntilPromt;
    }

    private static void setNumLaunchesForRemindLater(int launchesUntilPrompt) {
        LAUNCHES_UNTIL_PROMPT_FOR_REMIND_LATER = launchesUntilPrompt;
    }

    public static void app_launched(Activity activity) {
        app_launched(activity, DAYS_UNTIL_PROMPT, LAUNCHES_UNTIL_PROMPT);
    }

    public static void app_launched(Activity activity, int daysUntilPrompt, int launchesUntilPrompt, int daysForRemind, int launchesForRemind) {
        setNumDaysForRemindLater(daysForRemind);
        setNumLaunchesForRemindLater(launchesForRemind);
        app_launched(activity, daysUntilPrompt, launchesUntilPrompt);
    }

    private static void app_launched(Activity activity, int daysUntilPrompt, int launchesUntilPrompt) {
        SharedPreferences prefs = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        ApplicationRatingInfo ratingInfo = ApplicationRatingInfo.createApplicationInfo(activity);
        int days;
        int launches;
        if (isVersionNameCheckEnabled) {
            if (!ratingInfo.getApplicationVersionName().equals(prefs.getString(PREF_APP_VERSION_NAME, "none"))) {
                editor.putString(PREF_APP_VERSION_NAME, ratingInfo.getApplicationVersionName());
                resetData(activity);
                commitOrApply(editor);
            }
        }
        if (isVersionCodeCheckEnabled) {
            if (ratingInfo.getApplicationVersionCode() != (prefs.getInt(PREF_APP_VERSION_CODE, -1))) {
                editor.putInt(PREF_APP_VERSION_CODE, ratingInfo.getApplicationVersionCode());
                resetData(activity);
                commitOrApply(editor);
            }
        }
        if (prefs.getBoolean(PREF_DONT_SHOW_AGAIN, false)) {
            return;
        } else if (prefs.getBoolean(PREF_REMIND_LATER, false)) {
            days = DAYS_UNTIL_PROMPT_FOR_REMIND_LATER;
            launches = LAUNCHES_UNTIL_PROMPT_FOR_REMIND_LATER;
        } else {
            days = daysUntilPrompt;
            launches = launchesUntilPrompt;
        }
        long launch_count = prefs.getLong(PREF_LAUNCH_COUNT, 0) + 1;
        editor.putLong(PREF_LAUNCH_COUNT, launch_count);
        Long date_firstLaunch = prefs.getLong(PREF_FIRST_LAUNCHED, 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong(PREF_FIRST_LAUNCHED, date_firstLaunch);
        }
        if (launch_count >= launches || (System.currentTimeMillis() >= date_firstLaunch + (days * 24 * 60 * 60 * 1000))) {
            showRateAlertDialog(activity, editor);
        }
        commitOrApply(editor);
    }

    public static void showRateDialog(final Activity activity) {
        showRateAlertDialog(activity, null);
    }

    public static void rateNow(final Context context) {
        try {
            Intent intent = new Intent(BuildConfig.marketIntent);
            intent.setData(Uri.parse(BuildConfig.marketUri));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage(BuildConfig.marketPackage);
            context.startActivity(intent);
        } catch (ActivityNotFoundException ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
    }

    private static void showRateAlertDialog(final Activity activity, final SharedPreferences.Editor editor) {
        mtAlertDialog.on(activity)
                .title(R.string.drTitle)
                .message(R.string.drMessage)
                .icon(Util.getDrawableIcon(R.string.Icon_star, R.color.colorAccent))
                .cancelable(false)
                .when(R.string.drPositiveButton, new mtAlertDialog.Positive() {
                    @Override
                    public void clicked(DialogInterface dialog) {
                        rateNow(activity);
                        if (editor != null) {
                            editor.putBoolean(PREF_DONT_SHOW_AGAIN, true);
                            commitOrApply(editor);
                        }
                        dialog.dismiss();
                    }
                })
                .when(R.string.drNegativeButton, new mtAlertDialog.Negative() {
                    @Override
                    public void clicked(DialogInterface dialog) {
                        if (editor != null) {
                            editor.putBoolean(PREF_REMIND_LATER, true);
                            editor.putBoolean(PREF_DONT_SHOW_AGAIN, false);
                            long date_firstLaunch = System.currentTimeMillis();
                            editor.putLong(PREF_FIRST_LAUNCHED, date_firstLaunch);
                            editor.putLong(PREF_LAUNCH_COUNT, 0);
                            commitOrApply(editor);
                            try {
                                ShareCompat.IntentBuilder EmailBuilder = ShareCompat.IntentBuilder.from(MainActivity.mainInstance());
                                EmailBuilder.setType("message/rfc822");
                                EmailBuilder.addEmailTo("support@ronevis.com");
                                EmailBuilder.setSubject(Util.Persian(R.string.daaMailSubject));
                                EmailBuilder.setText(Util.Persian(R.string.daaMailBody));
                                EmailBuilder.setChooserTitle(Util.Persian(R.string.daaMailChoose));
                                EmailBuilder.startChooser();
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(MainActivity.mainInstance(), Util.Persian(R.string.daaMailErr), Toast.LENGTH_LONG).show();
                            }
                        }
                        dialog.dismiss();
                    }
                })
                .when(R.string.drNeutralButton, new mtAlertDialog.Neutral() {
                    @Override
                    public void clicked(DialogInterface dialog) {
                        if (editor != null) {
                            Long date_firstLaunch = System.currentTimeMillis();
                            editor.putLong(PREF_FIRST_LAUNCHED, date_firstLaunch);
                            editor.putLong(PREF_LAUNCH_COUNT, 0);
                            editor.putBoolean(PREF_REMIND_LATER, true);
                            editor.putBoolean(PREF_DONT_SHOW_AGAIN, false);
                            commitOrApply(editor);
                        }
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @SuppressLint("NewApi")
    private static void commitOrApply(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT > 8) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    private static void resetData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREF_DONT_SHOW_AGAIN, false);
        editor.putBoolean(PREF_REMIND_LATER, false);
        editor.putLong(PREF_LAUNCH_COUNT, 0);
        long date_firstLaunch = System.currentTimeMillis();
        editor.putLong(PREF_FIRST_LAUNCHED, date_firstLaunch);
        commitOrApply(editor);
    }
}
