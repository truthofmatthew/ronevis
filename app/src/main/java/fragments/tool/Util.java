package fragments.tool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

import com.eggheadgames.siren.Siren;
import com.eggheadgames.siren.SirenAlertType;
import com.eggheadgames.siren.SirenVersionCheckType;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

import activities.MainActivity;
import fragments.objectHelper.ImageHelper;
import fragments.textEffects.NumberDialog;
import fragments.textEffects.NumberesDialog;
import fragments.textEffects.SeekDialog;
import fragments.views.BGImageView;
import fragments.views.MTFrameLayout;
import fragments.views.TextDrawable;
import fragments.views.mtAlertDialog;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

import static mt.karimi.ronevis.R.color.text_secondary_light;

public class Util {
    private static final int FLAG_TAG_BR = 1;
    private static final int FLAG_TAG_BOLD = 2;
    private static final int FLAG_TAG_COLOR = 4;
    private static final int FLAG_TAG_ALL = FLAG_TAG_BR | FLAG_TAG_BOLD | FLAG_TAG_COLOR;
    private static final int HeaderPNG[] = new int[]{0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a};
    private static final int HeaderTTF[] = new int[]{0x00, 0x01, 0x00, 0x00, 0x00};

    public static void checkCurrentAppVersion(Context context) {
        Siren siren = Siren.getInstance(context);
        siren.setMajorUpdateAlertType(SirenAlertType.FORCE);
        siren.setMinorUpdateAlertType(SirenAlertType.FORCE);
        siren.setPatchUpdateAlertType(SirenAlertType.FORCE);
        siren.setRevisionUpdateAlertType(SirenAlertType.FORCE);
        siren.setVersionCodeUpdateAlertType(SirenAlertType.FORCE);
        siren.checkVersion(MainActivity.mainInstance(), SirenVersionCheckType.IMMEDIATELY, "http://www.ronevis.com/update/update.txt");
    }

    public static boolean checkPlayServices(final Context context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                mtAlertDialog.on(MainActivity.mainInstance())
                        .with()
                        .title(R.string.dpsTitle)
                        .message(R.string.dpsMessageUp)
                        .icon(Util.getDrawableIcon(R.string.Icon_google_play, text_secondary_light))
                        .cancelable(true)
                        .when(R.string.dpsPositiveButtonUp, new mtAlertDialog.Positive() {
                            @Override
                            public void clicked(DialogInterface dialog) {
                                try {
                                    MainActivity.mainInstance().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?TextId=" + "com.google.android.gms")));
                                } catch (android.content.ActivityNotFoundException e) {
                                    MainActivity.mainInstance().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?TextId=" + "com.google.android.gms")));
                                }
                                MainActivity.mainInstance().finish();
                                dialog.dismiss();
                            }
                        })
                        .when(R.string.dpsNegativeButton, new mtAlertDialog.Negative() {
                            @Override
                            public void clicked(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return false;
            } else {
                mtAlertDialog.on(MainActivity.mainInstance())
                        .with()
                        .title(R.string.dpsTitle)
                        .message(R.string.dpsMessage)
                        .icon(Util.getDrawableIcon(R.string.Icon_google_play, text_secondary_light))
                        .cancelable(true)
                        .when(R.string.dpsPositiveButton, new mtAlertDialog.Positive() {
                            @Override
                            public void clicked(DialogInterface dialog) {
                                try {
                                    MainActivity.mainInstance().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?TextId=" + "com.google.android.gms")));
                                } catch (android.content.ActivityNotFoundException e) {
                                    MainActivity.mainInstance().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?TextId=" + "com.google.android.gms")));
                                }
                                MainActivity.mainInstance().finish();
                                dialog.dismiss();
                            }
                        })
                        .when(R.string.dpsNegativeButton, new mtAlertDialog.Negative() {
                            @Override
                            public void clicked(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
            return false;
        }
        return true;
    }

    public static boolean isPNG(File filename) throws Exception {
        FileInputStream ins = new FileInputStream(filename);
        try {
            for (int aHeaderPNG : HeaderPNG) {
                if (ins.read() != aHeaderPNG) {
                    return false;
                }
            }
            return true;
        } finally {
            ins.close();
        }
    }

    public static boolean isTTF(File filename) throws Exception {
        FileInputStream ins = new FileInputStream(filename);
        try {
            for (int aHeaderTTF : HeaderTTF) {
                if (ins.read() != aHeaderTTF) {
                    return false;
                }
            }
            return true;
        } finally {
            ins.close();
        }
    }

    public static void NumberDialog(final int min, final int max, final int curr, final int UserSeek , FragmentActivity fragmentActivity) {
        Bundle UserColorBundle = new Bundle();
        UserColorBundle.putInt("UserMin", min);
        UserColorBundle.putInt("UserMax", max);
        UserColorBundle.putInt("UserCurr", curr);
        UserColorBundle.putInt("UserSeekBar", UserSeek);
        FragmentController.on(MainActivity.mainInstance(), fragmentActivity)
                .fragment(new NumberDialog())
                .viewGroup(MainActivity.mainInstance().mainLayout)
                .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                .Message(R.string.dfChooseTextError)
                .Bundle(UserColorBundle)
                .BackStackName("UserNumberDialog")
                .show();
    }
public static void NumberesDialog(final int min, final int max, final int curr, final int UserSeek , FragmentActivity fragmentActivity) {
        Bundle UserColorBundle = new Bundle();
        UserColorBundle.putInt("UserMin", min);
        UserColorBundle.putInt("UserMax", max);
        UserColorBundle.putInt("UserCurr", curr);
        UserColorBundle.putInt("UserSeekBar", UserSeek);
        FragmentController.on(MainActivity.mainInstance(), fragmentActivity)
                .fragment(new NumberesDialog())
                .viewGroup(MainActivity.mainInstance().mainLayout)
                .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                .Message(R.string.dfChooseTextError)
                .Bundle(UserColorBundle)
                .BackStackName("UserNumberDialog")
                .show();
    }

    public static void SeekDialog(final int min, final int max, final int curr, String UserSeekType, int UserSeekIcon, FragmentActivity fragmentActivity) {
        Bundle UserColorBundle = new Bundle();
        UserColorBundle.putInt("UserMin", min);
        UserColorBundle.putInt("UserMax", max);
        UserColorBundle.putInt("UserCurr", curr);
        UserColorBundle.putString("UserSeekType", UserSeekType);
        UserColorBundle.putInt("UserSeekIcon", UserSeekIcon);
        FragmentController.on(MainActivity.mainInstance(), fragmentActivity)
                .fragment(new SeekDialog())
                .viewGroup(MainActivity.mainInstance().mainLayout)
//                .viewChecker(MainActivity.mainInstance().SelecetedTextView)
//                .Message(R.string.dfChooseTextError)
                .Bundle(UserColorBundle)
                .BackStackName(UserSeekType)
                .show();
    }

    public static String Persian(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String Persian(int id) {
        return ApplicationLoader.appInstance().getApplicationContext().getResources().getString(id);
    }

    public static Typeface GetSelfTypeFace(Context context, int Size) {
        if (context == null) {
            context = ApplicationLoader.appInstance();
        }
        switch (Size) {
            case 1:
                return Typefaces.getTypeface(context, context.getString(R.string.IR_UltraLight_Fa));
            case 2:
                return Typefaces.getTypeface(context, context.getString(R.string.IR_Light_Fa));
            case 3:
                return Typefaces.getTypeface(context, context.getString(R.string.IR_Regular_Fa));
            case 4:
                return Typefaces.getTypeface(context, context.getString(R.string.IR_Medium_Fa));
            case 5:
                return Typefaces.getTypeface(context, context.getString(R.string.IR_Bold_Fa));
            case 6:
                return Typefaces.getTypeface(context, context.getString(R.string.IR_Icons));
        }
        return null;
    }

    public static Drawable getDrawableIcon(int resource, int color) {
        TextDrawable d = new TextDrawable(ApplicationLoader.appInstance());
        d.setTypeface(Util.GetSelfTypeFace(null, 6));
        d.setText(ApplicationLoader.appInstance().getResources().getString(resource));
        d.setTextColor(getColorWrapper(color));
        d.setTextSize(30);
        return d;
    }

    public static Drawable getDrawableIcon(Context context, int resource, int color) {
        TextDrawable d = new TextDrawable(context);
        d.setTypeface(Util.GetSelfTypeFace(context, 6));
        d.setText(context.getResources().getString(resource));
        d.setTextColor(getColorWrapper(color));
        d.setTextSize(30);
        return d;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void setAllParentsClip(View v, boolean enabled) {
        while (v.getParent() != null && v.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v.getParent();
            viewGroup.setClipChildren(enabled);
            viewGroup.setClipToPadding(enabled);
            v = viewGroup;
        }
    }

    public static void disableClipOnParents(View v) {
        if (v.getParent() == null) {
            return;
        }
        if (v instanceof ViewGroup) {
            ((ViewGroup) v).setClipChildren(false);
        }
        if (v.getParent() instanceof View) {
            disableClipOnParents((View) v.getParent());
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("deprecation")
    public static Drawable getDrawable(int resource) {
        if (getSDK(21)) {
            return ApplicationLoader.appInstance().getResources().getDrawable(resource, null);
        } else {
            return ApplicationLoader.appInstance().getResources().getDrawable(resource);
        }
    }

    @TargetApi(23)
    @SuppressWarnings("deprecation")
    public static int getColorWrapper(Context context, int id) {
        if (getSDK(23)) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    @TargetApi(23)
    @SuppressWarnings("deprecation")
    public static int getColorWrapper(int id) {
        if (getSDK(23)) {
            return ApplicationLoader.appInstance().getColor(id);
        } else {
            return ApplicationLoader.appInstance().getResources().getColor(id);
        }
    }

    public static boolean getSDK(int id) {
        return ApplicationLoader.appInstance().sdkVersion >= id;
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId =
                ApplicationLoader.appInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ApplicationLoader.appInstance().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @TargetApi(16)
    @SuppressWarnings("deprecation")
    public static void OnGlobalLayout(View view, OnGlobalLayoutListener OnGlobalListener) {
        if (Util.getSDK(16)) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(OnGlobalListener);
        } else {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(OnGlobalListener);
        }
    }

    @TargetApi(13)
    @SuppressWarnings("deprecation")
    public static int getHeight(Activity activity) {
        if (getSDK(13)) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.y;
        } else {
            Display display = activity.getWindowManager().getDefaultDisplay();
            return display.getHeight();
        }
    }

    @TargetApi(13)
    @SuppressWarnings("deprecation")
    public static int getWidth(Activity activity) {
        if (getSDK(13)) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        } else {
            Display display = activity.getWindowManager().getDefaultDisplay();
            return display.getWidth();
        }
    }

    //    @TargetApi(13)
//    @SuppressWarnings("deprecation")
    public static int[] getWH(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        MainActivity.mainInstance().appWH[0] = displaymetrics.widthPixels;
        MainActivity.mainInstance().appWH[1] = displaymetrics.heightPixels;
        return MainActivity.mainInstance().appWH;
    }

    public static int dp(float value) {
        float density = ApplicationLoader.appInstance().getResources().getDisplayMetrics().density;
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    public static int getInt(int integ) {
        return ApplicationLoader.appInstance().getResources().getInteger(integ);
    }

    public static String getString(int stringID) {
        return ApplicationLoader.appInstance().getResources().getString(stringID);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void ImageViewRemoveBG(View view) {
        if (Util.getSDK(16)) {
            view.setBackground(null);
        } else {
            view.setBackgroundDrawable(null);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void RemoveDrawbleImageView(BGImageView view) {
        Drawable drawable = view.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap ImageBGBitmap = bitmapDrawable.getBitmap();
            ImageBGBitmap.recycle();
        }
        if (Util.getSDK(16)) {
            view.setBackground(null);
            view.setImageDrawable(null);
            view.setImageBitmap(null);
        } else {
            view.setBackgroundDrawable(null);
            view.setImageDrawable(null);
            view.setImageBitmap(null);
        }
        view.destroyDrawingCache();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void RemoveDrawbleViewGroup(ViewGroup view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap ImageBGBitmap = bitmapDrawable.getBitmap();
            ImageBGBitmap.recycle();
        }
        if (Util.getSDK(16)) {
            view.setBackground(null);
            view.setBackgroundResource(0);
            view.setBackgroundColor(0);
        } else {
            view.setBackgroundDrawable(null);
            view.setBackgroundResource(0);
            view.setBackgroundColor(0);
        }
    }

    @TargetApi(16)
    @SuppressWarnings("deprecation")
    public static void ImageViewSetBG(View view, int resource) {
        if (Util.getSDK(16)) {
            view.setBackground(ImageHelper.getDrawable(resource));
        } else {
            view.setBackgroundDrawable(ImageHelper.getDrawable(resource));
        }
    }

    @TargetApi(16)
    @SuppressWarnings("deprecation")
    public static void ImageViewSetBG(ViewGroup viewgroup, BitmapDrawable bitmapDrawable) {
        if (Util.getSDK(16)) {
            viewgroup.setBackground(bitmapDrawable);
        } else {
            viewgroup.setBackgroundDrawable(bitmapDrawable);
        }
    }

    @TargetApi(16)
    @SuppressWarnings("deprecation")
    public static void ImageViewSetBG(View viewgroup, BitmapDrawable bitmapDrawable) {
        if (Util.getSDK(16)) {
            viewgroup.setBackground(bitmapDrawable);
        } else {
            viewgroup.setBackgroundDrawable(bitmapDrawable);
        }
    }

    @TargetApi(16)
    @SuppressWarnings("deprecation")
    public static void ImageViewsetDrawable(ImageView view, int resource) {
        if (Util.getSDK(16)) {
            view.setImageDrawable(ImageHelper.getDrawable(resource));
        } else {
            view.setImageDrawable(ImageHelper.getDrawable(resource));
        }
    }

    @TargetApi(16)
    @SuppressWarnings("deprecation")
    public static void ImageViewSetBG(MTFrameLayout mtFrameLayout, BitmapDrawable bitmapDrawable) {
        if (Util.getSDK(16)) {
            mtFrameLayout.setBackground(bitmapDrawable);
        } else {
            mtFrameLayout.setBackgroundDrawable(bitmapDrawable);
        }
    }

    public static int randInt() {
        Random rand = new Random();
        int randomNum = rand.nextInt(90001);
        while (MainActivity.mainInstance().NumberListRND.contains(randomNum)) {
            randomNum = rand.nextInt(89999);
        }
        MainActivity.mainInstance().NumberListRND.add(randomNum);
        return randomNum;
    }

    public static boolean assetExists(Context context, String path) {
        boolean bAssetOk = false;
        try {
            InputStream stream = context.getAssets().open(path);
            stream.close();
            bAssetOk = true;
        } catch (Exception ignored) {
        }
        return bAssetOk;
    }
}
