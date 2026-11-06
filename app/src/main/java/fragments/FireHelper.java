package fragments;

import android.util.Log;

/**
 * Created by mt.karimi on 31/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
// Firebase Crash Reporting was shut down in 2019, so errors go to logcat.
public class FireHelper {
    private static final String TAG = "Ronevis";

    public static void report(Throwable throwable) {
        Log.e(TAG, "Handled error", throwable);
    }

    public void SendLog(String mLog) {
        Log.i(TAG, mLog);
    }

    public void SendReport(Throwable throwable) {
        report(throwable);
    }
}
