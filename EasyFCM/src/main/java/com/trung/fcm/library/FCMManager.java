package com.trung.fcm.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Singleton which manage the registration &
 */
public class FCMManager {
    /**
     * Class instance
     */
    private static FCMManager instance = null;

    /**
     * Holding Context
     */
    private Context mContext;

    /**
     * FCM Listener
     */
    private FCMListener mFCMListener;

    /**
     * Private constructor
     *
     * @param mContext
     */
    private FCMManager(Context mContext) {
        this.mContext = mContext;
        init();
    }

    /**
     * Singleton instance method
     *
     * @param context
     * @return
     */
    public static FCMManager getInstance(Context context) {
        if (instance == null)
            instance = new FCMManager(context);
        return instance;
    }

    /**
     * Register listener
     *
     * @param fcmListener
     */
    public void registerListener(FCMListener fcmListener) {
        this.mFCMListener = fcmListener;
    }

    /**
     * Unregister listener. No longer need to notify.
     */
    public void unRegisterListener() {
        mFCMListener = null;
    }

    /**
     * Initializes shared preferences, checks google play services if available,
     * and register device to FCM server.
     */
    public void init() {

        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver,
                new IntentFilter(PrefsHelper.REGISTRATION_COMPLETE));

        /**
         * check if device has Google play service on it
         */
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int status = api.isGooglePlayServicesAvailable(mContext);

        /**
         * If true, process the {@link SaveFCMIdService}
         */
        if (status == ConnectionResult.SUCCESS) {
            mContext.startService(new Intent(mContext, SaveFCMIdService.class));
        } else {
            if (mFCMListener != null)
                mFCMListener.onPlayServiceError();
        }
    }

    /**
     * Message receiver onReceive method called when registration ID(Token) is
     * available
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (PrefsHelper.hasFCMToken(context) && mFCMListener != null) {
                mFCMListener.onDeviceRegistered(PrefsHelper.getFCMToken(context));
            }
        }
    };

    /**
     * Called by service when message received. Notify Listener
     * if it s not null.
     */
    public void onMessage(RemoteMessage remoteMessage) {
        if (mFCMListener != null)
            mFCMListener.onMessage(remoteMessage);
    }

    /**
     * Subscribe to topic
     *
     * @param topicName
     */
    public void subscribeTopic(String topicName) {
        if (PrefsHelper.hasFCMToken(mContext)) {
            FirebaseMessaging.getInstance().subscribeToTopic(topicName);
        }
    }

    /**
     * Unsucbsribe from topic
     *
     * @param topicName
     */
    public void unSubscribeTopic(String topicName) {
        if (PrefsHelper.hasFCMToken(mContext)) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topicName);
        }
    }
}
