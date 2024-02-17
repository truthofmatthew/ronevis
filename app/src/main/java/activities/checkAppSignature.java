package activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;

/**
 * Created by mt.karimi on 11/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class checkAppSignature {
    static final String DEBUGKEY =
            "get the debug key from logcat after calling the function below once from the emulator";
    private static final int VALID = 0;
    private static final int INVALID = 1;
    private static final String PLAY_STORE_APP_ID = "com.android.vending";

    public static int checkAppSignature(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                byte[] signatureBytes = signature.toByteArray();
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                final String currentSignature = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("REMOVE_ME", "Include this string as a value for SIGNATURE:" + currentSignature);
                //compare signatures
                if ("TcPhgFNo2WEQD4URbMqdY86Zmh8=".equals(currentSignature)) {
                    Log.d("REMOVE_ME", "YEEESSSSSSSSSS" + currentSignature);
                    return VALID;
                } else {
                    Log.d("REMOVE_ME", "NOOOOOOOO" + currentSignature);
                }
            }
        } catch (Exception e) {
//assumes an issue in checking signature., but we let the caller decide on what to do.
        }
        return INVALID;
    }

    public static boolean verifyInstaller(final Context context) {
        final String installer = context.getPackageManager()
                .getInstallerPackageName(context.getPackageName());
        return installer != null
                && installer.startsWith(PLAY_STORE_APP_ID);
    }

    private static String getSystemProperty(String name)
            throws Exception {
        Class systemPropertyClazz = Class
                .forName("android.os.SystemProperties");
        return (String) systemPropertyClazz.getMethod("get", new Class[]{String.class})
                .invoke(systemPropertyClazz, new Object[]{name});
    }

    public static boolean checkEmulator() {
        try {
            boolean goldfish = getSystemProperty("ro.hardware").contains("goldfish");
            boolean emu = getSystemProperty("ro.kernel.qemu").length() > 0;
            boolean sdk = getSystemProperty("ro.product.model").equals("sdk");
            if (emu || goldfish || sdk) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean checkDebuggable(Context context) {
        return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    public static boolean signedWithDebugKey(Context context, Class<?> cls) {
        boolean result = false;
        try {
            ComponentName comp = new ComponentName(context, cls);
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(comp.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature sigs[] = pinfo.signatures;
            for (int i = 0; i < sigs.length; i++)
                Log.d("REMOVE_ME", sigs[i].toCharsString());
            if (DEBUGKEY.equals(sigs[0].toCharsString())) {
                result = true;
                Log.d("REMOVE_ME", "package has been signed with the debug key");
            } else {
                Log.d("REMOVE_ME", "package signed with a key other than the debug key");
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
        return result;
    }

    public void checkSignature(final Context context) {
        try {
            Signature[] signatures = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
            if (signatures[0].toCharsString() != "<YOUR CERTIFICATE STRING GOES HERE>") {
                // Kill the process without warning. If someone changed the certificate
                // is better not to give a hint about why the app stopped working
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } catch (PackageManager.NameNotFoundException ex) {
            // Must never fail, so if it does, means someone played with the apk, so kill the process
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private boolean checkLuckyPatcher() {
        if (packageExists("com.dimonvideo.luckypatcher")) {
            return true;
        }
        if (packageExists("com.chelpus.lackypatch")) {
            return true;
        }
        if (packageExists("com.android.vending.billing.InAppBillingService.LACK")) {
            return true;
        }
        return false;
    }

    private boolean packageExists(final String packageName) {
//        Context context;
//        try
//        {
//            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, 0);
//
//            if (info == null)
//            {
//                // No need really to test for null, if the package does not
//                // exist it will really rise an exception. but in case Google
//                // changes the API in the future lets be safe and test it
//                return false;
//            }
//
//            return true;
//        }
//        catch (Exception ex)
//        {
//            // If we get here only means the Package does not exist
//        }
        return false;
    }
//    http://stackoverflow.com/questions/13445598/lucky-patcher-how-can-i-protect-from-it
//    signatures[0].toCharsString();
//
//    example: YourTextView.setText(signatures[0].toCharsString());
//
//    if ((context.getApplicationContext().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE) != 0)
//    {
//        return;
//    }
//    String sigChk = MySigCheck.MySigCheck(context);
//    if (sigChk.equalsIgnoreCase("Y")){
//        handle signature pass
//    }
// http://stackoverflow.com/questions/10585961/way-to-protect-from-lucky-patcher-play-licensing
//
//    public class MySigCheck {
//        public static String MySigCheck(Context context) {
//            String sigChk = "B";
//
//            Signature[] signature = new Signature[0];
//
//            try {
//                signature = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
//
//                Log.d("yourapp", Integer.toString(signature[0].hashCode())); << Prints your signature. Remove once you know this and have changed it below.
//
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            if (signature[0].hashCode() == -343553346){
//                sigChk = "Y";
//            }
//
//            return sigChk;
//        }
//    }
//    http://stackoverflow.com/questions/17225590/prevent-make-it-difficult-to-patch-binary-assembly/17464629#17464629
}
