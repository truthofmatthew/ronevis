package fragments;

import com.google.firebase.crash.FirebaseCrash;

/**
 * Created by mt.karimi on 31/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class FireHelper {
    public void SendLog(String mLog) {
        FirebaseCrash.log(mLog);

    }

    public void SendReport(Throwable throwable) {
        FirebaseCrash.report(throwable);
    }
}
