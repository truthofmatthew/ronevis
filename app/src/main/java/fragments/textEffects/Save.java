package fragments.textEffects;

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
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.appcompat.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.commit451.nativestackblur.NativeStackBlur;
import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.ButtonBar.BarButton_Layout;
import fragments.objectHelper.RonevisHelper;
import fragments.tool.IntentConstants;
import fragments.tool.ResizeCalculator;
import fragments.tool.Util;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

public class Save extends BaseFragment {
    static String SavedImagePath;
    static DialogActivityButtonListener backgroundButtonListener;
    static Activity mActivity;
    static ImageView bannerImage, savedImageBlur;
    private static CharSequence mTitle;
    private static CharSequence mMessage;
    private static AlertDialog alertDialogs;
    private static EditText editName;
    private static RadioGroup radioGroupFormat;
    private static Bitmap.CompressFormat bmpCompress;

    public static Save newInstance() {
        Save ftext = new Save();
        Bundle bundle = new Bundle();
        ftext.setArguments(bundle);
        return ftext;
    }

    public static Bitmap processs(Bitmap bitmap) {
        DisplayMetrics displayMetrics = mActivity.getResources().getDisplayMetrics();
        int resizeWidth = displayMetrics.widthPixels;
        int resizeHeight = displayMetrics.heightPixels;
        resizeWidth /= 4;
        resizeHeight /= 4;
        ResizeCalculator result1 = new ResizeCalculator();
        ResizeCalculator.Result result = result1.calculator(bitmap.getWidth(), bitmap.getHeight(), resizeWidth, resizeHeight, ImageView.ScaleType.CENTER_CROP, true);
        Bitmap newBitmap = Bitmap.createBitmap(result.imageWidth, result.imageHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bitmap, result.srcRect, result.destRect, null);
        canvas.drawColor(Color.parseColor("#55ffffff"));
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
        BarButton_Layout BtnNew = (BarButton_Layout) buttonPanel.findViewById(R.id.button1);
        BtnNew.setOnClickListener(backgroundButtonListener);
        BarButton_Layout BtnShare = (BarButton_Layout) buttonPanel.findViewById(R.id.button2);
        BtnShare.setOnClickListener(backgroundButtonListener);
        BarButton_Layout BtnEdit = (BarButton_Layout) buttonPanel.findViewById(R.id.button3);
        BtnEdit.setOnClickListener(backgroundButtonListener);
        BarButton_Layout meAparat = (BarButton_Layout) ButtonSocial.findViewById(R.id.button6);
        meAparat.setOnClickListener(backgroundButtonListener);
        BarButton_Layout meInsta = (BarButton_Layout) ButtonSocial.findViewById(R.id.button5);
        meInsta.setOnClickListener(backgroundButtonListener);
        BarButton_Layout meTelegram = (BarButton_Layout) ButtonSocial.findViewById(R.id.button4);
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
            FirebaseCrash.report(ignored);

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

    @Override
    public String myNameIs() {
        return "Save";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        View activity_save_image = inflater.inflate(R.layout.activity_save_image, group, false);
        activity_save_image.setClickable(true);
        activity_save_image.setEnabled(false);
        String formatType = ".png";
        bmpCompress = Bitmap.CompressFormat.PNG;
        long time = System.currentTimeMillis();
        String value = "ronevis_" + time;
        MainActivity.mainInstance()._exportroot.setDrawingCacheEnabled(false);
        MainActivity.mainInstance()._exportroot.setDrawingCacheEnabled(true);
        MainActivity.mainInstance()._exportroot.buildDrawingCache(true);
        Bitmap bm = MainActivity.mainInstance()._exportroot.getDrawingCache();
        File storedImagePath = generateImagePath(value, formatType);
        compressAndSaveImage(storedImagePath, bm);
        addImageToGallery(ApplicationLoader.appInstance().getContentResolver(), formatType, storedImagePath);
        mActivity = getActivity();
        final ViewGroup parentPanel = (ViewGroup) activity_save_image.findViewById(R.id.savedParentPanel);
        final ViewGroup TopPanel = (ViewGroup) parentPanel.findViewById(R.id.savedTopPanel);
        final ViewGroup ButtonPanel = (ViewGroup) parentPanel.findViewById(R.id.savedButtonPanel);
        final ViewGroup ButtonSocial = (ViewGroup) parentPanel.findViewById(R.id.savedSocialButtonPanel);
        backgroundButtonListener = new DialogActivityButtonListener();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setupButtons(ButtonPanel, ButtonSocial);
        setupTitle(TopPanel);
        bannerImage = (ImageView) activity_save_image.findViewById(R.id.savedBodyImage);
//
        savedImageBlur = (ImageView) activity_save_image.findViewById(R.id.savedImageBlur);
        bgBitmap(mActivity, storedImagePath.getAbsolutePath());
        return activity_save_image;
    }

    static class BGBitmap extends AsyncTask<String, Void, Bitmap[]> {
        private final Activity mActivity;
        private final String mPictureUrl;
        private ProgressDialog dialog;
        private File storedImagePath;
        private Bitmap bm;

        public BGBitmap(Activity activity, String PictureUrl) {
            this.mActivity = activity;
            this.mPictureUrl = PictureUrl;
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
//            mtAlertDialog.on(mActivity,R.style.FullDialog)
//                    .with()
////                .title(R.string.dsSaved)
//
//                    .icon(Util.getDrawableIcon(R.string.Icon_content_save, R.color.icons))
//                    .cancelable(true).layout(R.layout.activity_save_image)
//                    .show(new mtAlertDialog.View() { // assign value to view element
//
//                        @Override
//                        public void prepare(@Nullable View view, final AlertDialog alertDialog) {
//
//                            alertDialogs = alertDialog;
//
//                            final ViewGroup parentPanel = (ViewGroup) view.findViewById(R.id.savedParentPanel);
//                            final ViewGroup TopPanel = (ViewGroup) parentPanel.findViewById(R.id.savedTopPanel);
//                            final ViewGroup ButtonPanel = (ViewGroup) parentPanel.findViewById(R.id.savedButtonPanel);
//                            final ViewGroup ButtonSocial = (ViewGroup) parentPanel.findViewById(R.id.savedSocialButtonPanel);
//
//                            backgroundButtonListener = new DialogActivityButtonListener();
//
//                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                            StrictMode.setThreadPolicy(policy);
//
////        mIcon = Util.getDrawableIcon(R.string.Icon_check_circle, R.color.icons);
//
//                            setupButtons(ButtonPanel,ButtonSocial);
//                            setupTitle(TopPanel);
//
            //
            bannerImage.setImageBitmap(response[0]);
            savedImageBlur.setImageBitmap(response[1]);
//            Manimator.Alhpa(bannerImage,1,300);
//            Manimator.Alhpa(savedImageBlur,1,300);
//
//
//                        }
//
//                    });
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
                    break;
                case R.id.button2:
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/*");
                    share.putExtra(Intent.EXTRA_SUBJECT, "Made by ronevis");
                    share.putExtra(Intent.EXTRA_TEXT, "#ronevis");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(SavedImagePath)));
                    MainActivity.mainInstance().startActivity(Intent.createChooser(share, Util.Persian(mActivity, R.string.dsBtnShare)));
                    break;
                case R.id.button3:
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

