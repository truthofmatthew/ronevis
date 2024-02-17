package users;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.messaging.RemoteMessage;

import org.acra.sender.AcraLSender;
import org.apprater.AppRater;

import activities.DialogActivity;
import activities.PopupNotificationActivity;
import mt.karimi.ronevis.ApplicationLoader;

public class NotificationHandler {
    public NotificationHandler() {
    }

    public static void sendNotification(Context context, RemoteMessage remoteMessage) {
        try {
            String click_action = remoteMessage.getNotification().getClickAction();

            String GCMTYPE = remoteMessage.getData().get("Type");
            String ContentTitle = remoteMessage.getData().get("ContentTitle");
            String ContentText = remoteMessage.getData().get("ContentText");
            String ContentIcon = remoteMessage.getData().get("ContentIcon");
            String BigContentTitle = remoteMessage.getData().get("BigContentTitle");
            String SummaryText = remoteMessage.getData().get("SummaryText");
            String Action = remoteMessage.getData().get("Action");
            String ActionUrl = remoteMessage.getData().get("ActionUrl");
            String Vibrate = remoteMessage.getData().get("Vibrate");
            String PictureUrl = remoteMessage.getData().get("PictureUrl");
            String AppVersion = remoteMessage.getData().get("AppVersion");
            String AppLink = remoteMessage.getData().get("AppLink");
            if (GCMTYPE != null && GCMTYPE.equals("Message")) {
                new MTNotification()
                        .setContentTitle(ContentTitle)
                        .setContentText(ContentText)
                        .setAction(Action)
                        .setActionUrl(ActionUrl)
                        .setContext(context)
                        .Build();
            } else if (GCMTYPE != null && GCMTYPE.equals("Link")) {
                new MTNotification()
                        .setAction(Action)
                        .setActionUrl(ActionUrl)
                        .setContext(context)
                        .setClickAction(click_action)
                        .NotifLink();
            } else if (GCMTYPE != null && GCMTYPE.equals("PictureBanner")) {
                new MTNotification()
                        .setContentTitle(ContentTitle)
                        .setContentText(ContentText)
                        .setAction(Action)
                        .setActionUrl(ActionUrl)
                        .setContext(context)
                        .setPictureUrl(PictureUrl)
                        .setBigContentTitle(BigContentTitle)
                        .setSummaryText(SummaryText)
                        .Build();
            } else if (GCMTYPE != null && GCMTYPE.equals("PopBanner")) {
                Intent PopBanner = new Intent(context, PopupNotificationActivity.class);
                PopBanner.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                PopBanner.putExtra("remoteMessage", remoteMessage);
                context.startActivity(PopBanner);
                new MTNotification()
                        .setContentTitle(ContentTitle)
                        .setContentText(ContentText)
                        .setAction(Action)
                        .setActionUrl(ActionUrl)
                        .setContext(context)
                        .setPictureUrl(PictureUrl)
                        .setBigContentTitle(BigContentTitle)
                        .setSummaryText(SummaryText)
                        .Build();
            } else if (GCMTYPE != null && GCMTYPE.equals("Update")) {
                new MTNotification()
                        .setVerLast(AppVersion)
                        .Update();
            } else if (GCMTYPE != null && GCMTYPE.equals("Comment")) {
                AppRater.rateNow(context);
            } else if (GCMTYPE != null && GCMTYPE.equals("Dialog")) {
                Intent PopBanner = new Intent(context, DialogActivity.class);
                PopBanner.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                PopBanner.putExtra("remoteMessage", remoteMessage);
                context.startActivity(PopBanner);
                new MTNotification()
                        .setContentTitle(ContentTitle)
                        .setContentText(ContentText)
                        .setAction(Action)
                        .setActionUrl(ActionUrl)
                        .setContext(context)
                        .setPictureUrl(PictureUrl)
                        .setBigContentTitle(BigContentTitle)
                        .setSummaryText(SummaryText)
                        .Build();
            }
        } catch (IllegalArgumentException ignored) {
            FirebaseCrash.report(ignored);

        }
    }
}
