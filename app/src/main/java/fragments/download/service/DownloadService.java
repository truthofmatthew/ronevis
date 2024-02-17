package fragments.download.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.File;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import fragments.db.dl.FilesInfo;
import fragments.download.util.PackageInstall;
import fragments.download.util.Utils;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;
import multithreaddownload.CallBack;
import multithreaddownload.DownloadException;
import multithreaddownload.DownloadManager;
import multithreaddownload.DownloadRequest;

public class DownloadService extends Service {
    public static final String ACTION_DOWNLOAD_BROAD_CAST = "mt.karimi.ronevis:action_download_broad_cast";
    public static final String ACTION_DOWNLOAD = "mt.karimi.ronevis:action_download";
    public static final String ACTION_PAUSE = "mt.karimi.ronevis:action_pause";
    public static final String ACTION_CANCEL = "mt.karimi.ronevis:action_cancel";
    public static final String ACTION_PAUSE_ALL = "mt.karimi.ronevis:action_pause_all";
    public static final String ACTION_CANCEL_ALL = "mt.karimi.ronevis:action_cancel_all";
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_TAG = "extra_tag";
    public static final String EXTRA_FRAGMENT_TAG = "extra_fragment_tag";
    public static final String EXTRA_APP_INFO = "extra_app_info";
    private static final String TAG = DownloadService.class.getSimpleName();
    static Context context;
    private File mDownloadDir;
    private DownloadManager mDownloadManager;
    private NotificationManagerCompat mNotificationManager;

    private static int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_matt : R.drawable.ic_launcher;
    }

    public static void intentDownload(Context context, int position, String tag, String fragmenttag, FilesInfo info) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA_TAG, tag);
        intent.putExtra(EXTRA_FRAGMENT_TAG, fragmenttag);
        intent.putExtra(EXTRA_APP_INFO, info);
        context.startService(intent);
    }

    public static void intentPause(Context context, String tag, String fragmenttag) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_PAUSE);
        intent.putExtra(EXTRA_TAG, tag);
        intent.putExtra(EXTRA_FRAGMENT_TAG, fragmenttag);
        context.startService(intent);
    }

    public static void intentPauseAll(Context context) {
        Intent intent = new Intent(context, DownloadService.class);
        context.startService(intent);
    }

    public static void destory(Context context) {
        Intent intent = new Intent(context, DownloadService.class);
        context.stopService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            int position = intent.getIntExtra(EXTRA_POSITION, 0);
            FilesInfo appInfo = (FilesInfo) intent.getSerializableExtra(EXTRA_APP_INFO);
            String tag = intent.getStringExtra(EXTRA_TAG);
            String fragmenttag = intent.getStringExtra(EXTRA_FRAGMENT_TAG);
            switch (action) {
                case ACTION_DOWNLOAD:
                    download(position, appInfo, tag, fragmenttag);
                    break;
                case ACTION_PAUSE:
                    pause(tag);
                    break;
                case ACTION_CANCEL:
                    cancel(tag);
                    break;
                case ACTION_PAUSE_ALL:
                    pauseAll();
                    break;
                case ACTION_CANCEL_ALL:
                    cancelAll();
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void download(final int position, final FilesInfo appInfo, String tag, String fragmenttag) {
        final DownloadRequest request = new DownloadRequest.Builder()
                .setName(appInfo.getName())
                .setUri(appInfo.getUrl())
                .setFolder(mDownloadDir)
                .build();
        mDownloadManager.download(request, tag, new DownloadCallBack(position, appInfo, mNotificationManager, getApplicationContext(), fragmenttag));
    }

    private void pause(String tag) {
        mDownloadManager.pause(tag);
    }

    private void cancel(String tag) {
        mDownloadManager.cancel(tag);
    }

    private void pauseAll() {
        mDownloadManager.pauseAll();
    }

    private void cancelAll() {
        mDownloadManager.cancelAll();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mDownloadManager = DownloadManager.getInstance();
        mNotificationManager = NotificationManagerCompat.from(getApplicationContext());
//        mDownloadDir = new File(Environment.getExternalStorageDirectory(), "Download");
        mDownloadDir = new File(getCacheDir(), "Download");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDownloadManager.pauseAll();
    }

    public static class DownloadCallBack implements CallBack {
        private final File mDownloadDir = new File(ApplicationLoader.appInstance().getCacheDir(), "Download");
        public int notifNum = 1000;
        PackageInstall packageInstall;
        private int mPosition;
        private FilesInfo mFilesInfo;
        private String mfragmenttag;
        private LocalBroadcastManager mLocalBroadcastManager;
        private NotificationCompat.Builder mBuilder;
        private NotificationManagerCompat mNotificationManager;
        private long mLastTime;

        public DownloadCallBack(int position, FilesInfo appInfo, NotificationManagerCompat notificationManager, Context context, String fragmenttag) {
            mPosition = position;
            mFilesInfo = appInfo;
            mfragmenttag = fragmenttag;
            mNotificationManager = notificationManager;
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
            mBuilder = new NotificationCompat.Builder(context);
        }

        @Override
        public void onStarted() {
            mBuilder.setSmallIcon(getNotificationIcon())
//                    .setOngoing(true)
                    .setContentTitle(mFilesInfo.getName_fa())
                    .setContentText(ApplicationLoader.appInstance().getString(R.string.STATUS_DOWNLOAD_INIT))
                    .setProgress(100, 0, true)
                    .setTicker(ApplicationLoader.appInstance().getString(R.string.STATUS_DOWNLOADING) + ' ' + mFilesInfo.getName_fa());
            updateNotification();
        }

        @Override
        public void onConnecting() {
            mBuilder.setContentText(ApplicationLoader.appInstance().getString(R.string.STATUS_CONNECTING))
                    .setProgress(100, 0, true);
            updateNotification();
            mFilesInfo.setStatus(FilesInfo.STATUS_CONNECTING);
            sendBroadCast(mFilesInfo);
        }

        @Override
        public void onConnected(long total, boolean isRangeSupport) {
            mBuilder.setContentText(ApplicationLoader.appInstance().getString(R.string.STATUS_CONNECTED))
                    .setProgress(100, 0, true);
            updateNotification();
        }

        @Override
        public void onProgress(long finished, long total, int progress) {
            if (mLastTime == 0) {
                mLastTime = System.currentTimeMillis();
            }
            mFilesInfo.setStatus(FilesInfo.STATUS_DOWNLOADING);
            mFilesInfo.setProgress(progress);
            mFilesInfo.setDownloadPerSize(Utils.getDownloadPerSize(finished, total));
            long currentTime = System.currentTimeMillis();
            if (currentTime - mLastTime > 300) {
                mBuilder.setContentText(
                        ApplicationLoader.appInstance().getString(R.string.STATUS_DOWNLOADING));
                mBuilder.setProgress(100, progress, false);
                updateNotification();
                sendBroadCast(mFilesInfo);
                mLastTime = currentTime;
            }
        }

        @Override
        public void onCompleted() {
            if (mFilesInfo.getType().equals("background")) {
                this.notifNum = 1100;
            }
            if (mFilesInfo.getType().equals("font")) {
                this.notifNum = 1200;
            }
            if (mFilesInfo.getType().equals("sticker")) {
                this.notifNum = 1300;
            }
            mBuilder.setContentText(ApplicationLoader.appInstance().getString(R.string.STATUS_COMPLETE));
            mBuilder.setProgress(0, 0, false);
            mBuilder.setTicker(mFilesInfo.getName_fa() + " " + ApplicationLoader.appInstance().getString(R.string.STATUS_COMPLETE));
            updateNotification();
            mNotificationManager.cancel(mPosition + notifNum);
            mFilesInfo.setStatus(FilesInfo.STATUS_COMPLETE);
            mFilesInfo.setProgress(100);
            installPack(mFilesInfo, mPosition);
            sendBroadCast(mFilesInfo);
        }

        @Override
        public void onDownloadPaused() {
            mBuilder.setContentText(ApplicationLoader.appInstance().getString(R.string.STATUS_PAUSED));
            mBuilder.setTicker(mFilesInfo.getName_fa() + " " + ApplicationLoader.appInstance().getString(R.string.STATUS_PAUSED));
            updateNotification();
            mFilesInfo.setStatus(FilesInfo.STATUS_PAUSED);
            sendBroadCast(mFilesInfo);
        }

        @Override
        public void onDownloadCanceled() {
            if (mFilesInfo.getType().equals("background")) {
                this.notifNum = 1100;
            }
            if (mFilesInfo.getType().equals("font")) {
                this.notifNum = 1200;
            }
            if (mFilesInfo.getType().equals("sticker")) {
                this.notifNum = 1300;
            }
            mBuilder.setContentText(ApplicationLoader.appInstance().getString(R.string.STATUS_NOT_DOWNLOAD));
            mBuilder.setTicker(mFilesInfo.getName_fa() + " " + ApplicationLoader.appInstance().getString(R.string.STATUS_NOT_DOWNLOAD));
            updateNotification();
            mNotificationManager.cancel(mPosition + notifNum);
            mFilesInfo.setStatus(FilesInfo.STATUS_NOT_DOWNLOAD);
            mFilesInfo.setProgress(0);
            mFilesInfo.setDownloadPerSize("");
            sendBroadCast(mFilesInfo);
        }

        @Override
        public void onFailed(DownloadException e) {
            e.printStackTrace();
            mBuilder.setContentText(ApplicationLoader.appInstance().getString(R.string.STATUS_DOWNLOAD_ERROR));
            mBuilder.setTicker(mFilesInfo.getName_fa() + " " + ApplicationLoader.appInstance().getString(R.string.STATUS_DOWNLOAD_ERROR));
            mBuilder.setProgress(100, mFilesInfo.getProgress(), false);
            updateNotification();
            mFilesInfo.setStatus(FilesInfo.STATUS_DOWNLOAD_ERROR);
            sendBroadCast(mFilesInfo);
        }

        private void updateNotification() {
            if (mFilesInfo.getType().equals("background")) {
                this.notifNum = 1100;
            }
            if (mFilesInfo.getType().equals("font")) {
                this.notifNum = 1200;
            }
            if (mFilesInfo.getType().equals("sticker")) {
                this.notifNum = 1300;
            }
            mNotificationManager.notify(mPosition + notifNum, mBuilder.build());
        }

        private void sendBroadCast(FilesInfo appInfo) {
            Intent intent = new Intent();
            intent.setAction(DownloadService.ACTION_DOWNLOAD_BROAD_CAST);
            intent.putExtra(EXTRA_POSITION, mPosition);
            intent.putExtra(EXTRA_APP_INFO, appInfo);
            intent.putExtra(EXTRA_FRAGMENT_TAG, mfragmenttag);
            mLocalBroadcastManager.sendBroadcast(intent);
        }

        public void installPack(FilesInfo filesInfo, int position) {
            try {
                if (filesInfo.getName().startsWith("back")) {
                    packageInstall = new PackageInstall(new File(mDownloadDir, filesInfo.getName()),
                            ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathBackGrounds) + "/"), context, filesInfo, position);
                    packageInstall.execute();
                } else if (filesInfo.getName().startsWith("sticker")) {
                    packageInstall = new PackageInstall(new File(mDownloadDir, filesInfo.getName()),
                            ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathStickers) + "/"), context, filesInfo, position);
                    packageInstall.execute();
                } else if (filesInfo.getName().startsWith("font_fa")) {
                    packageInstall = new PackageInstall(new File(mDownloadDir, filesInfo.getName()),
                            ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsFa) + "/"), context, filesInfo, position);
                    packageInstall.execute();
                } else if (filesInfo.getName().startsWith("font_en")) {
                    packageInstall = new PackageInstall(new File(mDownloadDir, filesInfo.getName()),
                            ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsEn) + "/"), context, filesInfo, position);
                    packageInstall.execute();
                } else if (filesInfo.getName().startsWith("Pattern")) {
                    packageInstall = new PackageInstall(new File(mDownloadDir, filesInfo.getName()),
                            ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathTexture) + "/"), context, filesInfo, position);
                    packageInstall.execute();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
