package com.trung.fcm.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Encapsulate behaviour based on {@link android.content.SharedPreferences}.
 */
public class PrefsHelper {
    public static final String FCM_TOKEN = "fcm_token";

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    /**
     * non instantiable class.
     */
    private PrefsHelper() {

    }

    /**
     * Save the FCM token in {@link android.content.SharedPreferences}.
     *
     * @param context  holding Context
     * @param fcmToken token to be saved
     */
    public static void saveFCMToken(Context context, String fcmToken) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        pref.edit().putString(FCM_TOKEN, fcmToken).apply();
    }

    /**
     * Retrieve FCM Token from {@link android.content.SharedPreferences}.
     *
     * @param context holding context
     * @return string represents FCM Token
     */
    public static String getFCMToken(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return pref.getString(FCM_TOKEN, "");
    }

    /**
     * Check if token is null
     *
     * @param context holding Context
     * @param sent    true if token is not null, false otherwise
     */
    public static void sendFCMTokenToServer(Context context, boolean sent) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        pref.edit().putBoolean(SENT_TOKEN_TO_SERVER, sent).apply();
    }

    /**
     * Check if there is any token not null
     *
     * @param context holding Context
     * @return true if token is not null, false otherwise
     */
    public static boolean hasFCMToken(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return pref.getBoolean(SENT_TOKEN_TO_SERVER, false);
    }
}
