package fragments.tool;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import fragments.views.TextIcon;

/**
 * Created by mt.karimi on 4/22/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class Typefaces {
    private static final String TAG = "Typefaces";
    //    public static Typeface get(Context c, String fontPath) {
//        Typeface t = null;
//        synchronized (cache) {
//            if (!cache.containsKey(fontPath)) {
//                try {
//                    if (fontPath.startsWith("self")) {
//                        t = Typeface.createFromAsset(c.getAssets(), fontPath);
//                    } else if (fontPath.startsWith("/storage")) {
//                        t = Typeface.createFromFile(fontPath);
//                    }
//                    cache.put(fontPath, t);
//                } catch (Exception ignored ) {
//                    AcraLSender acraLSender = new AcraLSender();
//                    acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "Typefaces_40");
//                    return null;
//                }
//            }
//            return cache.get(fontPath);
//        }
//    }
    private static final Map<String, Typeface> CACHE = new HashMap<>();
    private static final Hashtable<String, Typeface> cache = new Hashtable<>();

    public static Typeface getTypeface(Context context, String font) {
        try {
            if (null == font) {
                return null;
            }
            Typeface typeface = null;
            if (null == typeface) {
                if (font.startsWith("self")) {
                    typeface = Typeface.createFromAsset(context.getAssets(), font);
                } else {
                    typeface = Typeface.createFromFile(font);
                }
                CACHE.put(font, typeface);
            }
            return typeface;
        } catch (Exception ignored) {
//            AcraLSender acraLSender = new AcraLSender();
//            acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "Typefaces_40");
            return null;
        }
    }

    public static Typeface get(Context c, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(),
                            assetPath);
                    cache.put(assetPath, t);
                    Log.e(TAG, "Loaded '" + assetPath);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + assetPath
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }

    private static void markAsIconContainer(View v, Typeface typeface) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                markAsIconContainer(child, typeface);
            }
        } else if (v instanceof TextIcon) {
            ((TextIcon) v).setTypeface(typeface);
        }
    }
}