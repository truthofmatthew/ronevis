package fragments.tool;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.google.firebase.crash.FirebaseCrash;

import org.acra.sender.AcraLSender;

import activities.MainActivity;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 4/27/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class IntentConstants {
    public static boolean isInstalled(String packageName) {
        PackageManager packageManager = Base.getContext().getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String getPackageName() {
        return Base.getContext().getPackageName();
    }

    public static Intent FacebookIntent() {
        final PackageManager packageManager = MainActivity.mainInstance().getPackageManager();
        Uri uri = Uri.parse("https://www.facebook.com/ronevis/");
        try {
            if (isPackageInstalled("com.facebook.katana")) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0);
                if (applicationInfo.enabled) {
                    uri = Uri.parse("fb://facewebmodal/fragment?href=" + "https://www.facebook.com/ronevis/");
                }
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            FirebaseCrash.report(ignored);
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent InstagramIntent() {
        final PackageManager packageManager = MainActivity.mainInstance().getPackageManager();
        Uri uri = Uri.parse("ronevisapp/");
        try {
            if (isPackageInstalled("com.instagram.android")) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.instagram.android", 0);
                if (applicationInfo.enabled) {
                    uri = Uri.parse("http://instagram.com/_u/" + "ronevisapp/");
                    Intent insta_intent = packageManager.getLaunchIntentForPackage("com.instagram.android");
                    insta_intent.setComponent(new ComponentName("com.instagram.android", "com.instagram.android.activity.UrlHandlerActivity"));
                    insta_intent.setData(uri);
                    return insta_intent;
                }
            } else {
                uri = Uri.parse("http://instagram.com/_u/" + "ronevisapp/");
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            FirebaseCrash.report(ignored);
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent TelegramIntent() {
        final PackageManager packageManager = MainActivity.mainInstance().getPackageManager();
        Uri uri = Uri.parse("http://telegram.me/ronevisapp");
        try {
            if (isPackageInstalled("org.telegram.messenger")) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo("org.telegram.messenger", 0);
                if (applicationInfo.enabled) {
                    uri = Uri.parse("https://t.me/joinchat/AAAAADzmxJriGnYUo_Uk5g");
                }
            } else {
                uri = Uri.parse("https://t.me/joinchat/AAAAADzmxJriGnYUo_Uk5g");
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            FirebaseCrash.report(ignored);
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent LinkedinIntent() {
        final PackageManager packageManager = MainActivity.mainInstance().getPackageManager();
        Uri uri = Uri.parse("ronevis");
        try {
            if (isPackageInstalled("com.linkedin.android")) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.linkedin.android", 0);
                if (applicationInfo.enabled) {
                    uri = Uri.parse("linkedin://company/" + "ronevis");
                }
            } else {
                uri = Uri.parse("https://www.linkedin.com/company/" + "ronevis");
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            FirebaseCrash.report(ignored);
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent EmailIntent() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{ApplicationLoader.appInstance().getResources().getString(R.string.daaEmail)});
        i.putExtra(Intent.EXTRA_SUBJECT, Util.Persian(R.string.daaMailSubject));
        i.putExtra(Intent.EXTRA_TEXT, Util.Persian(R.string.daaMailBody));
        return Intent.createChooser(i, Util.Persian(R.string.daaMailChoose));
    }

    public static Intent AparatIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        final PackageManager packageManager = MainActivity.mainInstance().getPackageManager();
        try {
            if (isPackageInstalled("com.aparat")) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.aparat", 0);
                if (applicationInfo.enabled) {
                    intent.setData(Uri.parse("aparat://profile?name=ronevis"));
                    intent.setPackage("com.aparat");
                }
            } else {
                intent.setData(Uri.parse("http://www.aparat.com/ronevis"));
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            FirebaseCrash.report(ignored);
        }
        return intent;
    }

    public static Intent GalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.fromFile(ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisExportPathFolder)));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    public static Intent CallIntent() {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse("tel:09013737731"));
        return callIntent;
    }

    private static boolean isPackageInstalled(String packagename) {
        final PackageManager packageManager = MainActivity.mainInstance().getPackageManager();
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
