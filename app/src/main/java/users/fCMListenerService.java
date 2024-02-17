package users;
/**
 * Created by mt.karimi on 27/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.orhanobut.logger.Logger;
import com.trung.fcm.library.FCMListenerService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 27/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class fCMListenerService extends FCMListenerService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Context context = getBaseContext();
        //sendNotification(remoteMessage.getData().get("newcase"));
//        Logger.wtf(  "onMessage:\nFrom:\n" + remoteMessage.getFrom());
//        Logger.wtf(  "onMessage:\nNotification Message 4:\n" + remoteMessage.getNotification().getBody());
//        Logger.wtf(  "onMessage:\nNotification Message 5:\n" + remoteMessage.getNotification().getTitle());
//        Logger.wtf(  "onMessage:\nNotification Message 6:\n" + remoteMessage.getData().keySet()  );
//        Logger.wtf(  "onMessage:\nNotification Message 7:\n" + remoteMessage.getData().get("Type")  );
        if (remoteMessage.getNotification() != null) {
            try {
                sendNotification(remoteMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            NotificationHandler.sendNotification(context, remoteMessage);
        }
        //Here is called even app is not working.
        //create your notification here.

//        String Type = remoteMessage.getData().get("Type");
//        String Action = remoteMessage.getData().get("Action");
//        String ActionUrl = remoteMessage.getData().get("ActionUrl");
//
//        Intent intent = new Intent(click_action);
//        intent.putExtra("Type", Type);
//        intent.putExtra("Action", Action);
//        intent.putExtra("ActionUrl", ActionUrl);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
//        notificationBuilder.setSmallIcon(R.drawable.ic_launcher)
//                .setContentTitle(remoteMessage.getNotification().getTitle())
//                .setContentText(remoteMessage.getNotification().getBody())
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendNotification(RemoteMessage remoteMessage) throws IOException {
        String click_action = remoteMessage.getNotification().getClickAction();

        Intent intent = new Intent(click_action);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap remote_picture = null;
        String PictureUrl = remoteMessage.getData().get("PictureUrl");

        remote_picture = BitmapFactory.decodeStream((InputStream) new URL(PictureUrl).getContent());

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_matt)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setStyle(new NotificationCompat.BigPictureStyle().setBigContentTitle("bbbb")
                        .setSummaryText("sss")
                        .bigPicture(remote_picture))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
//    Intent intent = new Intent(this, MainActivity.class);
//    // use System.currentTimeMillis() to have a unique ID for the pending intent
//    PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//
//if (Build.VERSION.SDK_INT < 16) {
//        Notification n  = new Notification.Builder(this)
//        .setContentTitle(messageTitle)
//        .setContentText(messageBody)
//        .setSmallIcon(R.mipmap.ic_launcher)
//        .setContentIntent(pIntent)
//        .setAutoCancel(true).getNotification();
//        NotificationManager notificationManager =
//        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        //notificationManager.notify(0, n);
//        notificationManager.notify(id, n);
//        } else {
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//
//        Notification n  = new Notification.Builder(this)
//        .setContentTitle(messageTitle)
//        .setContentText(messageBody)
//        .setSmallIcon(R.drawable.ic_stat_ic_notification)
//        .setLargeIcon(bm)
//        .setContentIntent(pIntent)
//        .setAutoCancel(true).build();
//
//        NotificationManager notificationManager =
//        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        //notificationManager.notify(0, n);
//        notificationManager.notify(id, n);
//        }