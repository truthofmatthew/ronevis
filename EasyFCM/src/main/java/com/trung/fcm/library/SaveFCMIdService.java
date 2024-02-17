package com.trung.fcm.library;

import android.app.IntentService;
import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Intent Service which manage the Id Registration to local & remote
 */
public class SaveFCMIdService extends IntentService {

    /**
     * Class tag
     */
    private static final String TAG = "SaveFCMIdService.class.getSimpleName()";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SaveFCMIdService() {
        super(TAG);
    }


    /**
     * This Intent is responsible for get token from FCM server and send
     * broadcast. After we get token, we save this token to shared preferences.
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            PrefsHelper.saveFCMToken(this, refreshedToken);

            /**
             * You should store a boolean that indicates whether the generated token has been
             * sent to your server. If the boolean is false, send the token to your server,
             * otherwise your server should have already received the token.
             */
            PrefsHelper.sendFCMTokenToServer(this, true);

        } catch (Exception e) {
            PrefsHelper.sendFCMTokenToServer(this, false);
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(PrefsHelper.REGISTRATION_COMPLETE));
    }
}
