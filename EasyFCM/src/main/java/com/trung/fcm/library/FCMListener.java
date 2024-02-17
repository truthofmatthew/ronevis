package com.trung.fcm.library;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Callback to be implemented by all classes who wish receive notification
 */
public interface FCMListener {
    /**
     * Called when device is registered to FCM servers and received token
     *
     * @param deviceToken
     */
    void onDeviceRegistered(String deviceToken);

    /**
     * Called when downstream message receive by device.
     *
     * @param remoteMessage
     */
    void onMessage(RemoteMessage remoteMessage);

    /**
     * Called when device is unable to google play service.
     */
    void onPlayServiceError();
}
