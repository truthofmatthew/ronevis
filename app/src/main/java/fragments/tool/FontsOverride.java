package fragments.tool;

import android.content.Context;
import android.graphics.Typeface;

import com.google.firebase.crash.FirebaseCrash;

import org.acra.sender.AcraLSender;

import java.lang.reflect.Field;

import mt.karimi.ronevis.ApplicationLoader;

/**
 * Created by mt.karimi on 4/27/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public final class FontsOverride {
    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    private static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (Exception ignored) {
            FirebaseCrash.report(ignored);

        }
    }
}