package users;

import android.app.IntentService;
import android.content.Intent;
//import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
    NotificationHandler notificationHandler = new NotificationHandler();

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        System.out.println("GcmIntentService");
//        try {
//            Bundle extras = intent.getExtras();
//            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
//            String messageType = gcm.getMessageType(intent);
//            if (!extras.isEmpty()) {
//                if (messageType != null) {
//                    if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
//                        notificationHandler.sendNotification(this, extras);
//                    }
//                }
//            }
//        } catch (Exception ignored) {
//            ignored.printStackTrace();
//            AcraLSender acraLSender = new AcraLSender();
//            acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "GcmIntentService_37");
//        }
//        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
