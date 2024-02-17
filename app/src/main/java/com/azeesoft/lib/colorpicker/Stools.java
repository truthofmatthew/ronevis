package com.azeesoft.lib.colorpicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.google.firebase.crash.FirebaseCrash;

import org.acra.sender.AcraLSender;

import mt.karimi.ronevis.ApplicationLoader;

class Stools {
    private final static String SP_KEY_LAST_COLOR = "lastColor";

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static void saveLastColor(Context context, String hexaVal) {
        try {
            Color.parseColor(hexaVal);
            SharedPreferences sharedPreferences = context.getSharedPreferences("colpick", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SP_KEY_LAST_COLOR, hexaVal);
            editor.apply();
        } catch (Exception ignored) {
            FirebaseCrash.report(ignored);

        }
    }

    public static String loadLastColor(Context context) {
        String s = "#ffffffff";
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("colpick", Context.MODE_PRIVATE);
            s = sharedPreferences.getString(SP_KEY_LAST_COLOR, null);
            if (s != null && !s.isEmpty()) {
                Color.parseColor(!s.isEmpty() ? s : "#ffffffff");
            }
        } catch (Exception ignored) {
            FirebaseCrash.report(ignored);

        }
        return s != null && !s.isEmpty() ? s : "#ffffffff";
    }

    public static Drawable tintDrawable(Drawable drawable, int color) {
        int red = (color & 0xFF0000) / 0xFFFF;
        int green = (color & 0xFF00) / 0xFF;
        int blue = color & 0xFF;
        float[] matrix = {0, 0, 0, 0, red,
                0, 0, 0, 0, green,
                0, 0, 0, 0, blue,
                0, 0, 0, 1, 0};
        ColorFilter colorFilter = new ColorMatrixColorFilter(matrix);
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
        return drawable;
    }
}
