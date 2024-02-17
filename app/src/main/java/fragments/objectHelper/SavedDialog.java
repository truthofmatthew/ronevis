package fragments.objectHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.commit451.nativestackblur.NativeStackBlur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import activities.MainActivity;
import fragments.FireHelper;
import fragments.tool.IntentConstants;
import fragments.tool.ResizeCalculator;
import fragments.tool.Util;
import fragments.views.TextButton;
import fragments.views.mtAlertDialog;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

import static mt.karimi.ronevis.R.color.text_secondary_light;

public class SavedDialog {
    static DialogActivityButtonListener backgroundButtonListener;
    static String SavedImagePath;
    static Activity mActivity;
    private static CharSequence mTitle;
    private static CharSequence mMessage;
    private static AlertDialog alertDialogs;
    private static Bitmap.CompressFormat bmpCompress;
    private static String formatType;

    public static Bitmap processs(Bitmap bitmap) {
        DisplayMetrics displayMetrics = mActivity.getResources().getDisplayMetrics();
        int resizeWidth = displayMetrics.widthPixels;
        int resizeHeight = displayMetrics.heightPixels;
        resizeWidth /= 8;
        resizeHeight /= 8;
        ResizeCalculator result1 = new ResizeCalculator();
        ResizeCalculator.Result result = result1.calculator(bitmap.getWidth(), bitmap.getHeight(), resizeWidth, resizeHeight, ImageView.ScaleType.CENTER_CROP, true);
        Bitmap newBitmap = Bitmap.createBitmap(result.imageWidth, result.imageHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bitmap, result.srcRect, result.destRect, null);
        canvas.drawColor(Color.parseColor("#55000000"));
//        BlurAlgorithm blurAlgorithm;
//        blurAlgorithm = new StackBlur(true);
//        blurAlgorithm.blur(newBitmap, 5);
        return NativeStackBlur.process(newBitmap, 35);
    }

    public static void bgBitmap(Activity activity, String PictureUrl) {
        SavedImagePath = PictureUrl;
        mActivity = activity;
        new BGBitmap(activity, PictureUrl).execute();
    }

    private static void setupTitle(ViewGroup TopPanel) {
//            mIconView = (ImageView) TopPanel.findViewById(R.id.savedTitleIcon);
        TextView mTitleView = (TextView) TopPanel.findViewById(R.id.savedTitleText);
        mTitleView.setTypeface(Util.GetSelfTypeFace(TopPanel.getContext(), 5));
    }

    private static void setupButtons(ViewGroup buttonPanel, ViewGroup ButtonSocial) {
        TextButton BtnNew = (TextButton) buttonPanel.findViewById(R.id.button1);
        BtnNew.setOnClickListener(backgroundButtonListener);
        TextButton BtnShare = (TextButton) buttonPanel.findViewById(R.id.button2);
        BtnShare.setOnClickListener(backgroundButtonListener);
        TextButton BtnEdit = (TextButton) buttonPanel.findViewById(R.id.button3);
        BtnEdit.setOnClickListener(backgroundButtonListener);
        TextButton meAparat = (TextButton) ButtonSocial.findViewById(R.id.button6);
        meAparat.setOnClickListener(backgroundButtonListener);
        TextButton meInsta = (TextButton) ButtonSocial.findViewById(R.id.button5);
        meInsta.setOnClickListener(backgroundButtonListener);
        TextButton meTelegram = (TextButton) ButtonSocial.findViewById(R.id.button4);
        meTelegram.setOnClickListener(backgroundButtonListener);
//        BtnNew.setVisibility(View.INVISIBLE);
//        BtnShare.setVisibility(View.INVISIBLE);
//        BtnEdit.setVisibility(View.INVISIBLE);
//        meTelegram.setVisibility(View.INVISIBLE);
//        meInsta.setVisibility(View.INVISIBLE);
//        meAparat.setVisibility(View.INVISIBLE);
//
//         = new (mActivity,
//                R.anim.action_show,
//                R.anim.action_hidden,
//                500,
//                BtnNew,
//                BtnShare,
//                BtnEdit,
//                meTelegram,
//                meInsta,
//                meAparat  );
//
//        .start(true);
    }

    private static void showPhoto(String photoUri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(photoUri), "image/*");
        mActivity.startActivity(intent);
    }

    private static File getImagesDirectory() {
        File file = ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisExportPathFolder));
        if (file.mkdirs() || file.isDirectory()) {
            return file;
        }
        return file;
    }

    private static File generateImagePath(String title, String imgType) {
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
        values.put(MediaStore.Images.Media.TITLE, "ronevis");
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "Made by ronevis App");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Designed by MTKarimi");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + imgType);
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATA, filepath.toString());
        return cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    static class BGBitmap extends AsyncTask<String, Void, Bitmap[]> {
        private final Activity mActivity;
        private final String mPictureUrl;
        private ProgressDialog dialog;
        private File storedImagePath;
        private Bitmap bm;

        public BGBitmap(Activity activity, String PictureUrl) {
            mActivity = activity;
            mPictureUrl = PictureUrl;
        }

        @Override
        protected Bitmap[] doInBackground(String... regIds) {
//            dialog = ProgressDialog.show(mActivity, "MyTitle", "Loading .....", true);
//            MainActivity.mainInstance().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//
//                }
//            });
            Bitmap[] arr = new Bitmap[2];
            arr[0] = BitmapFactory.decodeFile(mPictureUrl);
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inSampleSize = 8;
            o.inDither = true;
            Bitmap drawable = BitmapFactory.decodeFile(mPictureUrl, o);
            arr[1] = processs(drawable);
            return arr;
        }

        @Override
        protected void onPostExecute(final Bitmap[] response) {
            mtAlertDialog.on(mActivity, R.style.FullDialog)
                    .with()
//                .title(R.string.dsSaved)
                    .icon(Util.getDrawableIcon(R.string.Icon_content_save, text_secondary_light))
                    .cancelable(true).layout(R.layout.activity_save_image)
                    .show(new mtAlertDialog.View() { // assign value to view element
                        @Override
                        public void prepare(@Nullable View view, final AlertDialog alertDialog) {
                            alertDialogs = alertDialog;
                            final ViewGroup parentPanel = (ViewGroup) view.findViewById(R.id.savedParentPanel);
                            final ViewGroup TopPanel = (ViewGroup) parentPanel.findViewById(R.id.savedTopPanel);
                            final ViewGroup ButtonPanel = (ViewGroup) parentPanel.findViewById(R.id.savedButtonPanel);
                            final ViewGroup ButtonSocial = (ViewGroup) parentPanel.findViewById(R.id.savedSocialButtonPanel);
                            backgroundButtonListener = new DialogActivityButtonListener();
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            ImageView bannerImage = (ImageView) view.findViewById(R.id.savedBodyImage);
                            ImageView savedImageBlur = (ImageView) view.findViewById(R.id.savedImageBlur);
//        mIcon = Util.getDrawableIcon(R.string.Icon_check_circle, R.color.icons);
                            setupButtons(ButtonPanel, ButtonSocial);
                            setupTitle(TopPanel);
                            bannerImage.setImageBitmap(response[0]);
                            Manimator.Alhpa(bannerImage, 1, 3000);
                            savedImageBlur.setImageBitmap(response[1]);
                            Manimator.Alhpa(savedImageBlur, 1, 3000);
                        }
                    });
//            if( dialog!=null)
//                 dialog.dismiss();
//            mAlertDialog.dismiss();
        }
    }

    private static class DialogActivityButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button1:
                    RonevisHelper.NewProject(MainActivity.mainInstance());
                    alertDialogs.dismiss();
                    break;
                case R.id.button2:
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/*");
                    share.putExtra(Intent.EXTRA_SUBJECT, "Made by ronevis");
                    share.putExtra(Intent.EXTRA_TEXT, "#ronevis");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(SavedImagePath)));
                    MainActivity.mainInstance().startActivity(Intent.createChooser(share, Util.Persian(mActivity, R.string.dsBtnShare)));
                    alertDialogs.dismiss();
                    break;
                case R.id.button3:
                    alertDialogs.dismiss();
                    break;
                case R.id.button4:
                    MainActivity.mainInstance().startActivity(IntentConstants.TelegramIntent());
                    break;
                case R.id.button5:
                    MainActivity.mainInstance().startActivity(IntentConstants.InstagramIntent());
                    break;
                case R.id.button6:
                    MainActivity.mainInstance().startActivity(IntentConstants.AparatIntent());
                    break;
            }
        }
    }
}
