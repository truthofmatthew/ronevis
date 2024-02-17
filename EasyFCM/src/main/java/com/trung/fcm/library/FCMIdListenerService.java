package com.trung.fcm.library;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * This service do the unique task ; save FCM Id token locally & remotely
 */
public class FCMIdListenerService extends FirebaseInstanceIdService {
    private static final String TAG = FCMIdListenerService.class.getSimpleName();

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onTokenRefresh() {
        startService(new Intent(this, SaveFCMIdService.class));
    }
}
