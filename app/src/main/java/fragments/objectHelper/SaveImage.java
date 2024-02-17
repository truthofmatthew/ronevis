package fragments.objectHelper;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import android.view.View;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Locale;

import activities.MainActivity;
import fragments.FireHelper;
import fragments.imageHelper.webPHelper;
import fragments.tool.Util;
import fragments.views.TextButton;
import fragments.views.mtDialog;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/13/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class SaveImage {
    private static final int DOWNLOAD_NOTIFICATION_ID_DONE = 911;
    static Activity msActivity;
    private static String value;
    private static String formatType;
    private static Bitmap.CompressFormat bmpCompress;
    Thread saveArt;

    private static int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_matt : R.drawable.ic_launcher;
    }

    private static void showNotification(@NonNull File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri photoURI = FileProvider.getUriForFile(msActivity, msActivity.getApplicationContext().getPackageName() + ".provider", file);
//        Uri.fromFile(file)
        intent.setDataAndType(photoURI, "image/*");
        Bitmap remote_picture = null;
        try {
            remote_picture = BitmapFactory.decodeFile(file.getAbsolutePath());
        } catch (Exception ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
        NotificationCompat.Builder mNotification = new NotificationCompat.Builder(MainActivity.mainInstance());
        mNotification
                .setContentTitle(Util.Persian(R.string.app_name))
                .setContentText(Util.Persian(R.string.notification_image_saved_click_to_preview))
                .setTicker(Util.Persian(R.string.notification_image_saved))
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(ImageHelper.drawableToBitmap(ImageHelper.getDrawable(R.drawable.ic_launcher)))
                .setOngoing(false)
                .setStyle(
                        new androidx.core.app.NotificationCompat.BigPictureStyle()
                                .setBigContentTitle(Util.Persian(R.string.notification_image_saved))
                                .setSummaryText(Util.Persian(R.string.notification_image_saved_click_to_preview))
                                .bigPicture(remote_picture))
                .setContentIntent(PendingIntent.getActivity(MainActivity.mainInstance(), 0, intent, 0))
                .setAutoCancel(true);
        ((NotificationManager) MainActivity.mainInstance().getSystemService(Context.NOTIFICATION_SERVICE)).notify(DOWNLOAD_NOTIFICATION_ID_DONE, mNotification.build());
    }

    public static void sharefile(final Activity activity) {
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathFolder), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisExportPathFolder), false);
        mtDialog.create(activity, R.style.alert_dialog, new mtDialog.View() {
            @Override
            public void prepare(@Nullable View view, final Dialog dDialog) {
                TextButton btn_png = (TextButton) view.findViewById(R.id.btn_png);
                TextButton btn_webp = (TextButton) view.findViewById(R.id.btn_webp);
                TextButton btn_project = (TextButton) view.findViewById(R.id.btn_project);
                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);
                btn_png.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setIndeterminate(true);
                        saveArtToFile(activity, dDialog);
                        dDialog.dismiss();
                    }
                });
                btn_webp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setIndeterminate(true);
                        formatType = ".webp";
                        bmpCompress = Bitmap.CompressFormat.WEBP;
                        MainActivity.mainInstance()._exportroot.setDrawingCacheEnabled(false);
                        MainActivity.mainInstance()._exportroot.setDrawingCacheEnabled(true);
                        MainActivity.mainInstance()._exportroot.buildDrawingCache(true);
                        Bitmap bm = MainActivity.mainInstance()._exportroot.getDrawingCache();
                        File file = ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathTemp));
                        if (file.exists()) {
                            file.delete();
                        }
                        File storedImagePath = new File(file, "Sticker_" + System.currentTimeMillis() + formatType);
                        try {
                            byte[] webpData = webPHelper.bitmapToWebp(bm);
                            webPHelper.writeFileFromByteArray(storedImagePath, webpData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        compressAndSaveImage(storedImagePath, bm);
//                            addImageToGallery(ApplicationLoader.appInstance().getContentResolver(), formatType, storedImagePath);
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/*");
//                share.putExtra(Intent.EXTRA_SUBJECT, "Made by ronevis");
//                share.putExtra(Intent.EXTRA_TEXT, "#ronevis");
                        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(storedImagePath));
                        MainActivity.mainInstance().startActivity(Intent.createChooser(share, Util.Persian(activity, R.string.dsBtnShare)));
//                saveFile(activity,storedImagePath.getAbsolutePath());
                        dDialog.dismiss();
                    }
                });
                btn_project.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RonevisHelper.SaveProject(activity);
                        dDialog.dismiss();
                    }
                });
            }
        })
                .setdTitle(R.string.dsTitle)
//                .setdIcon(Util.getDrawableIcon(R.string.Icon_content_save, text_secondary_light))
                .setdCancelable(true)
                .setdView(R.layout.dialog_share)
                .show();
    }

    public static void saveArtToFile(Activity activity, Dialog dDialog) {
        msActivity = activity;
        new SaveArtToFile(activity, dDialog).execute();
    }

    private static File getImagesDirectory() {
//        ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisExportPathFolder), nameString + ronevisIMGFileExtension);
        File file = ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisExportPathFolder));
        if (file.mkdirs() || file.isDirectory()) {
            return file;
        }
        return file;
    }

    private static File generateImagePath(String title, String imgType) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
//        return new File(getImagesDirectory(), title + "_" + sdf.format(new Date()) + "." + imgType);
        return new File(getImagesDirectory(), title + imgType);
    }

    private static boolean compressAndSaveImage(File file, Bitmap bitmap) {
        boolean result = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if ((result = bitmap.compress(bmpCompress, 100, fos))) {
            }
            fos.close();
        } catch (IOException ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
        return result;
    }

    private static Uri addImageToGallery(ContentResolver cr, String imgType, File filepath) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "#ronevis");
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "Made by ronevis App");
        values.put(MediaStore.Images.Media.DESCRIPTION, "#ronevis");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + imgType);
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATA, filepath.toString());
        return cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private static byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
        }
        return os.toByteArray();
    }

    private void copyFileToDownloads(Uri croppedFileUri) throws Exception {
        String downloadsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String filename = String.format(Locale.ENGLISH, "%d_%s", Calendar.getInstance().getTimeInMillis(), croppedFileUri.getLastPathSegment());
        File saveFile = new File(downloadsDirectoryPath, filename);
        FileInputStream inStream = new FileInputStream(new File(croppedFileUri.getPath()));
        FileOutputStream outStream = new FileOutputStream(saveFile);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
        showNotification(saveFile);
    }

    static class SaveArtToFile extends AsyncTask<String, Void, Bitmap> {
        private final Activity mActivity;
        private final Dialog mDialog;
        private ProgressDialog dialog;
        private File storedImagePath;
        private Bitmap bm;

        public SaveArtToFile(Activity activity, Dialog dDialog) {
            this.mActivity = activity;
            this.mDialog = dDialog;
        }

        @Override
        protected void onPreExecute() {
//            dialog = ProgressDialog.show(mActivity, "MyTitle", "Loading .....", true);
        }
//        @Override
//        public void destroy() {
//            stopAutoBlurUpdate();
//            blurAlgorithm.destroy();
//            if (internalBitmap != null) {
//                internalBitmap.recycle();
//            }
//        }

        @Override
        protected Bitmap doInBackground(String... regIds) {
            formatType = ".png";
            bmpCompress = Bitmap.CompressFormat.PNG;
            long time = System.currentTimeMillis();
            value = "ronevis_" + time;
            MainActivity.mainInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.mainInstance()._exportroot.setDrawingCacheEnabled(false);
                    MainActivity.mainInstance()._exportroot.setDrawingCacheEnabled(true);
                    MainActivity.mainInstance()._exportroot.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    MainActivity.mainInstance()._exportroot.buildDrawingCache(true);
                    bm = MainActivity.mainInstance()._exportroot.getDrawingCache();
                    if (bm != null) {
                        storedImagePath = generateImagePath(value, formatType);
                        compressAndSaveImage(storedImagePath, bm);
                        addImageToGallery(ApplicationLoader.appInstance().getContentResolver(), formatType, storedImagePath);
                        MainActivity.mainInstance()._exportroot.setDrawingCacheEnabled(false);
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap response) {
            SavedDialog.bgBitmap(mActivity, storedImagePath.getAbsolutePath());
            showNotification(storedImagePath);
//            if( dialog!=null)
//                 dialog.dismiss();
            mDialog.dismiss();
        }
    }
}
