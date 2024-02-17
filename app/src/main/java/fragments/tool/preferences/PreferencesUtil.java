package fragments.tool.preferences;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Set;

import fragments.tool.Base;
import fragments.tool.Util;

public class PreferencesUtil {
    private static String defaultName = PreferencesUtil.class.getCanonicalName();

    private static SharedPreferences getPreferences(String name) {
        return Base.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static void setDefaultName(String name) {
        defaultName = name;
    }

    public static boolean get(String key, boolean defValue) {
        return get(defaultName, key, defValue);
    }

    public static int get(String key, int defValue) {
        return get(defaultName, key, defValue);
    }

    public static float get(String key, float defValue) {
        return get(defaultName, key, defValue);
    }

    public static long get(String key, long defValue) {
        return get(defaultName, key, defValue);
    }

    public static String get(String key, String defValue) {
        return get(defaultName, key, defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> get(String key, Set<String> defValue) {
        return get(defaultName, key, defValue);
    }

    private static boolean get(String name, String key, boolean defValue) {
        return getPreferences(name).getBoolean(key, defValue);
    }

    private static int get(String name, String key, int defValue) {
        return getPreferences(name).getInt(key, defValue);
    }

    private static float get(String name, String key, float defValue) {
        return getPreferences(name).getFloat(key, defValue);
    }

    private static long get(String name, String key, long defValue) {
        return getPreferences(name).getLong(key, defValue);
    }

    private static String get(String name, String key, String defValue) {
        return getPreferences(name).getString(key, defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static Set<String> get(String name, String key, Set<String> defValue) {
        return getPreferences(name).getStringSet(key, defValue);
    }

    public static void put(String key, boolean value) {
        put(defaultName, key, value);
    }

    public static void put(String key, int value) {
        put(defaultName, key, value);
    }

    public static void put(String key, float value) {
        put(defaultName, key, value);
    }

    public static void put(String key, long value) {
        put(defaultName, key, value);
    }

    public static void put(String key, String value) {
        put(defaultName, key, value);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void put(String key, Set<String> value) {
        put(defaultName, key, value);
    }

    private static void put(String name, String key, boolean value) {
        if (Util.getSDK(9))
            getPreferences(name).edit().putBoolean(key, value).apply();
        else
            getPreferences(name).edit().putBoolean(key, value).apply();
    }

    private static void put(String name, String key, int value) {
        if (Util.getSDK(9))
            getPreferences(name).edit().putInt(key, value).apply();
        else
            getPreferences(name).edit().putInt(key, value).apply();
    }

    private static void put(String name, String key, float value) {
        if (Util.getSDK(9))
            getPreferences(name).edit().putFloat(key, value).apply();
        else
            getPreferences(name).edit().putFloat(key, value).apply();
    }

    private static void put(String name, String key, long value) {
        if (Util.getSDK(9))
            getPreferences(name).edit().putLong(key, value).apply();
        else
            getPreferences(name).edit().putLong(key, value).apply();
    }

    private static void put(String name, String key, String value) {
        if (Util.getSDK(9))
            getPreferences(name).edit().putString(key, value).apply();
        else
            getPreferences(name).edit().putString(key, value).apply();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void put(String name, String key, Set<String> value) {
        if (Util.getSDK(9))
            getPreferences(name).edit().putStringSet(key, value).apply();
        else
            getPreferences(name).edit().putStringSet(key, value).apply();
    }

    public static void remove(String key) {
        remove(defaultName, key);
    }

    private static void remove(String name, String key) {
        if (Util.getSDK(9))
            getPreferences(name).edit().remove(key).apply();
        else
            getPreferences(name).edit().remove(key).apply();
    }

    public static void clear() {
        clear(defaultName);
    }

    private static void clear(String name) {
        if (Util.getSDK(9))
            getPreferences(name).edit().clear().apply();
        else
            getPreferences(name).edit().clear().apply();
    }
}