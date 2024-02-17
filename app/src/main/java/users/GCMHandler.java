package users;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import activities.MainActivity;
//import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMHandler {
    GCMHandler mGSMHandler = null;
    private Context mContext;
    //    private GoogleCloudMessaging gcm;
    private String regId = null;
//    public GCMHandler getInstance() {
//        if (mGSMHandler == null)
//            mGSMHandler = new GCMHandler();
//        return mGSMHandler;
//    }
//
//    public void registerGCM(final TaskListener listener) {
//        mContext = ApplicationLoader.appInstance();
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                String msg;
//                try {
//                    if (gcm == null) {
//                        gcm = GoogleCloudMessaging.getInstance(mContext);
//                    }
//                    regId = gcm.register(ApplicationLoader.appInstance().getString(R.string.google_app_id));
//                    msg = regId;
//                } catch (Exception ignored) {
////                    AcraLSender acraLSender = new AcraLSender();
////                    acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "GCMHandler_44");
//                    listener.onFail(ignored.toString());
//                    return "";
//                }
//                storeRegistrationId(mContext, regId);
//                return msg;
//            }
//
//            @Override
//            protected void onPostExecute(String msg) {
//                listener.onSuccess(msg);
//            }
//        }.execute(null, null, null);
//    }
//
//    public void unRegisterGCM() {
//        if (gcm == null) {
//            gcm = GoogleCloudMessaging.getInstance(mContext);
//        }
//    }

    public String getRegistrationId() {
        final SharedPreferences prefs = getGCMPreferences(mContext);
        return prefs.getString("registration_id", null);
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("registration_id", regId);
        String PROPERTY_APP_VERSION = "appVersion";
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.apply();
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return context.getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}

