package users;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import activities.SplashActivity;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import mt.karimi.ronevis.R;

class MTNotification {
    String ContentTitle = null;
    String ClickAction = null;
    String ContentText = null;
    String ContentIcon = null;
    String BigContentTitle = null;
    String SummaryText = null;
    String Action = null;
    String ActionUrl = null;
    String Vibrate = null;
    String PictureUrl = null;
    String AppVersion = null;
    String AppLink = null;
    Context context = null;
    String verLast = null;
    NotificationCompat.Style NotificationStyle;

    public MTNotification() {
    }

    public String getVerLast() {
        return verLast;
    }

    public MTNotification setVerLast(String verLast) {
        this.verLast = verLast;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public MTNotification setContext(Context context) {
        this.context = context;
        return this;
    }

    public String getContentTitle() {
        return ContentTitle;
    }

    public MTNotification setContentTitle(String contentTitle) {
        ContentTitle = contentTitle;
        return this;
    }
    public MTNotification setClickAction(String click_action) {
        ClickAction = click_action;
        return this;
    }

    public String getContentText() {
        return ContentText;
    }

    public MTNotification setContentText(String contentText) {
        ContentText = contentText;
        return this;
    }

    public String getContentIcon() {
        return ContentIcon;
    }

    public MTNotification setContentIcon(String contentIcon) {
        ContentIcon = contentIcon;
        return this;
    }

    public String getBigContentTitle() {
        return BigContentTitle;
    }

    public MTNotification setBigContentTitle(String bigContentTitle) {
        BigContentTitle = bigContentTitle;
        return this;
    }

    public String getSummaryText() {
        return SummaryText;
    }

    public MTNotification setSummaryText(String summaryText) {
        SummaryText = summaryText;
        return this;
    }

    public String getAction() {
        return Action;
    }

    public MTNotification setAction(String action) {
        Action = action;
        return this;
    }

    public String getActionUrl() {
        return ActionUrl;
    }

    public MTNotification setActionUrl(String actionUrl) {
        ActionUrl = actionUrl.startsWith("http://") ? actionUrl : ("http://" + actionUrl);
        return this;
    }

    public String getVibrate() {
        return Vibrate;
    }

    public MTNotification setVibrate(String vibrate) {
        Vibrate = vibrate;
        return this;
    }

    public String getPictureUrl() {
        return PictureUrl;
    }

    public MTNotification setPictureUrl(String pictureUrl) {
        PictureUrl = pictureUrl;
        return this;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public MTNotification setAppVersion(String appVersion) {
        AppVersion = appVersion;
        return this;
    }

    public String getAppLink() {
        return AppLink;
    }

    public MTNotification setAppLink(String appLink) {
        AppLink = appLink;
        return this;
    }

    public NotificationCompat.Style getNotificationStyle() {
        return NotificationStyle;
    }

    public MTNotification setNotificationStyle(NotificationCompat.Style notificationStyle) {
        NotificationStyle = notificationStyle;
        return this;
    }

    public MTNotification Build() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent intentNotifyMessage;
        PendingIntent pendingNotifyMessage;
        if (Action.contains("URL")) {
            intentNotifyMessage = new Intent(ClickAction, Uri.parse(getActionUrl()));
            intentNotifyMessage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingNotifyMessage =
                    PendingIntent.getActivity(getContext(), 0, intentNotifyMessage,
                            PendingIntent.FLAG_CANCEL_CURRENT);
            // getContext().startActivity(IntentNotifyMessage);
        } else {
            // IntentNotifyMessage = new Intent(getContext(), MainActivity.class);
            // IntentNotifyMessage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // PendingNotifyMessage = PendingIntent.getActivity(getContext(), 0, IntentNotifyMessage,
            // PendingIntent.FLAG_UPDATE_CURRENT);
            intentNotifyMessage = new Intent(getContext(), SplashActivity.class);
            intentNotifyMessage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // getContext().startActivity(IntentNotifyMessage);
            pendingNotifyMessage =
                    PendingIntent.getActivity(getContext(), 0, intentNotifyMessage,
                            PendingIntent.FLAG_UPDATE_CURRENT);
        }
        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(getContext());
        if (getPictureUrl() != null) {
            Bitmap remote_picture = null;
            try {
                remote_picture = BitmapFactory.decodeStream((InputStream) new URL(getPictureUrl()).getContent());
            } catch (IOException ignored) {
//                AcraLSender acraLSender = new AcraLSender();
//                acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "MTNotification_204");
            }
            if (remote_picture != null) {
                NotificationStyle = new NotificationCompat.BigPictureStyle()
                        .setBigContentTitle(getBigContentTitle())
                        .setSummaryText(getSummaryText())
                        .bigPicture(remote_picture);
            } else {
                NotificationStyle = new NotificationCompat.BigTextStyle().bigText(Util
                        .Persian(R.string.daPositiveButton));
            }
        } else {
            NotificationStyle = new NotificationCompat.BigTextStyle().bigText(Util
                    .Persian(R.string.daPositiveButton));
        }
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(getNotificationIcon())
                        .setContentTitle(getContentTitle() != null ? getContentTitle() : "")
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setStyle(getNotificationStyle())
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher))
                        .setContentText(getContentText() != null ? getContentText() : "")
                        .setContentIntent(pendingNotifyMessage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
        }
        mNotificationManager.notify(0, mBuilder.build());
        show(mBuilder.build(), mNotificationManager);
        return this;
    }

    public void show(Notification notification, NotificationManagerCompat notificationManager) {
        notificationManager.notify(0, notification);
    }

    public void NotifLink() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getActionUrl()));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getContext().startActivity(browserIntent);
    }

    public void Update() {
        Pref.setDefaultName("ronevisEditor");
        Pref.put("LastAppVersion", getVerLast());
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_luncher_white : R.drawable.ic_launcher;
    }
}
