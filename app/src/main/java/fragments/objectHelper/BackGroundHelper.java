package fragments.objectHelper;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.commit451.nativestackblur.NativeStackBlur;

import java.io.InputStream;

import activities.MainActivity;
import fragments.tool.Util;
import fragments.views.MTFrameLayout;

public class BackGroundHelper {
    private static Point ImageBitmapSize = new Point(0, 0);

    public static void setBackGroundColor(int backGroundColor) {
//        if (MainActivity.mainInstance().backGroundProperties.getBackGroundSizeFixed()) {
//            RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
//            LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams, MainActivity.mainInstance().backGroundProperties.getBackGroundSize().x, MainActivity.mainInstance().backGroundProperties.getBackGroundSize().y);
//        } else {
//            if (MainActivity.mainInstance().MainImageBG.getDrawable() == null) {
//                RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
//                LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams, false);
//            }
//        }
        MainActivity.mainInstance()._exportroot.setBackgroundColor(backGroundColor);
        MainActivity.mainInstance().backGroundProperties.setBackGroundColor(backGroundColor);
        MainActivity.mainInstance().backGroundProperties.setBackGroundColorHas(true);
    }

    public static void setBackGroundRemove() {
        try {
            MTFrameLayout.LayoutParams ColorBoardLParams = new MTFrameLayout.LayoutParams(MTFrameLayout.LayoutParams.MATCH_PARENT, MTFrameLayout.LayoutParams.MATCH_PARENT);
            ColorBoardLParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
//            ColorBoardLParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            if (MainActivity.mainInstance().BluredBackGround != null) {
                MainActivity.mainInstance().BluredBackGround.recycle();
                MainActivity.mainInstance().BluredBackGround = null;
            }
            if (MainActivity.mainInstance().OriginalBackGround != null) {
                MainActivity.mainInstance().OriginalBackGround.recycle();
                MainActivity.mainInstance().OriginalBackGround = null;
            }
            MainActivity.mainInstance().MainImageBG.setLayoutParams(ColorBoardLParams);
//            MainActivity.mainInstance()._exportroot.setLayoutParams(ColorBoardLParams);
            MainActivity.mainInstance().MainImageBG.requestLayout();
            MainActivity.mainInstance()._exportroot.requestLayout();
            BackGroundHelper.BlurBackGround(0);
//            MainActivity.mainInstance().backGroundProperties.setBackGroundHaveImage(false);
//            if (MainActivity.mainInstance().backGroundProperties.getBackGroundSizeFixed()) {
//                RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
//                LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams, MainActivity.mainInstance().backGroundProperties.getBackGroundSize().x, MainActivity.mainInstance().backGroundProperties.getBackGroundSize().y);
//            } else {
            RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
            LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams, false);
//            }
            Util.RemoveDrawbleViewGroup(MainActivity.mainInstance()._exportroot);
            Util.RemoveDrawbleImageView(MainActivity.mainInstance().MainImageBG);
            SetBackGroundResetProperties();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    static void SetBackGroundResetProperties() {
//        Tools_bg_setting.chkFixed.setChecked(false);
        MainActivity.mainInstance().backGroundProperties.setBackGroundBlurRatio(0);
        MainActivity.mainInstance().backGroundProperties.setBackGroundBlur(false);
        MainActivity.mainInstance().backGroundProperties.setBackGroundScaleType();
        MainActivity.mainInstance().backGroundProperties.setBackGroundSize(new Point(0, 0));
        MainActivity.mainInstance().backGroundProperties.setBackGroundSizeFixed(false);
        MainActivity.mainInstance().backGroundProperties.setBackGroundGravity();
        MainActivity.mainInstance().backGroundProperties.setBackGroundSrc("");
        MainActivity.mainInstance().backGroundProperties.setBackGroundAlpha(1.0f);
        MainActivity.mainInstance().backGroundProperties.setBackGroundColor(Color.parseColor("#FF000000"));
        MainActivity.mainInstance().backGroundProperties.setBackGroundColorHas(false);
        MainActivity.mainInstance().backGroundProperties.setBackGroundBitmap(null);
        MainActivity.mainInstance().backGroundProperties.setBackGroundHaveImage(false);
    }

    public static void SetBackGroundReset() {
        try {
            MainActivity.mainInstance().MainImageBG.setImageBitmap(null);
            MainActivity.mainInstance().MainImageBG.setImageDrawable(null);
            MainActivity.mainInstance().MainImageBG.setImageResource(android.R.color.transparent);
            MainActivity.mainInstance().backGroundProperties.setBackGroundHaveImage(false);
            MainActivity.mainInstance().OriginalBackGround.recycle();
            MainActivity.mainInstance().OriginalBackGround = null;
            BackGroundHelper.BlurBackGround(0);
            MainActivity.mainInstance().backGroundProperties.setBackGroundAlpha(1.0f);
            Manimator.Alhpa(MainActivity.mainInstance().MainImageBG, 1.0f, 0);
        } catch (Exception ignored) {
        }
    }

    private static boolean hasImage(ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);
        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }
        return hasImage;
    }

    public static void SetBackGroundRefresh() {
        try {
//            if ( MainActivity.mainInstance().MainImageBG.getDrawable() != null && MainActivity.mainInstance().backGroundProperties.getBackGroundSize().x  != 0) {
            if (hasImage(MainActivity.mainInstance().MainImageBG)) {
                MainActivity.mainInstance()._exportroot.requestLayout();
                RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
                LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams, MainActivity.mainInstance().backGroundProperties.getBackGroundSize().x, MainActivity.mainInstance().backGroundProperties.getBackGroundSize().y);
            } else {
//                if (MainActivity.mainInstance().MainImageBG.getDrawable() != null) {
//                    RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
//                    LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams,  false);
//                } else {
                RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
                LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams, false);
//                }
//                else if (MainActivity.mainInstance().backGroundProperties.getBackGroundSizeFixed()){
//                    RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
//                    LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams, MainActivity.mainInstance().backGroundProperties.getBackGroundSize().x,MainActivity.mainInstance().backGroundProperties.getBackGroundSize().y);
//                }
            }
            MainActivity.mainInstance().backGroundProperties.setBackGroundScaleType();
            MainActivity.mainInstance().backGroundProperties.setBackGroundGravity();
            MainActivity.mainInstance().BackGroundMap.put(MainActivity.mainInstance().MainImageBG.getId(), MainActivity.mainInstance().backGroundProperties);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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

    public static Bitmap GetBitmap(String ImagePath) {
        Bitmap bitmapLoad = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inScaled = false;
        if (ImagePath.startsWith("/storage") || ImagePath.startsWith("/data")) {
            bitmapLoad = BitmapFactory.decodeFile(ImagePath, bmOptions);
        } else {
            AssetManager assetManager = MainActivity.mainInstance().getAssets();
            InputStream istr;
            try {
                istr = assetManager.open(ImagePath);
                bitmapLoad = BitmapFactory.decodeStream(istr);
                istr.close();
            } catch (Exception ignored) {
            }
        }
        return bitmapLoad;
    }

    public static void BlurBackGround(final int progress) {
        try {
            MainActivity.mainInstance().runOnUiThread(new Runnable() {
                public void run() {
                    if (MainActivity.mainInstance().backGroundProperties.getBackGroundHaveImage()) {
//                        if(MainActivity.mainInstance().MainImageBG.getDrawable() instanceof BitmapDrawable) {
//                            BitmapDrawable d = (BitmapDrawable) MainActivity.mainInstance().MainImageBG.getDrawable();
//                        }else {
//                            Bitmap  d = Ion.with(MainActivity.mainInstance().MainImageBG).getBitmap();
//                        }
//                        if (MainActivity.mainInstance().BluredBackGround == null) {
                        MainActivity.mainInstance().OriginalBackGround = MainActivity.mainInstance().backGroundProperties.getBackGroundBitmap();
////                            BitmapDrawable d = (BitmapDrawable) MainActivity.mainInstance().MainImageBG.getDrawable();
//
//                            ImageBitmapSize = Ion.with(MainActivity.mainInstance().MainImageBG).getBitmapInfo().originalSize;
//
//                            MainActivity.mainInstance().BluredBackGround = ImageHelper.scaleBitmap(MainActivity.mainInstance().OriginalBackGround, (int) (ImageBitmapSize.x * 0.8), (int) (ImageBitmapSize.y * 0.8));
//                        }
                        if (progress > 0) {
                            MainActivity.mainInstance().MainImageBG.setImageBitmap(NativeStackBlur.process(MainActivity.mainInstance().OriginalBackGround, progress));
                            MainActivity.mainInstance().backGroundProperties.setBackGroundBlurRatio(progress);
                            MainActivity.mainInstance().backGroundProperties.setBackGroundBlur(true);
                        } else {
                            MainActivity.mainInstance().MainImageBG.setImageBitmap(MainActivity.mainInstance().backGroundProperties.getBackGroundBitmap());
                            MainActivity.mainInstance().backGroundProperties.setBackGroundBlurRatio(0);
                            MainActivity.mainInstance().backGroundProperties.setBackGroundBlur(false);
//                            MainActivity.mainInstance().BluredBackGround = null;
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(16)
    @SuppressWarnings("deprecation")
    public static void ImageViewSetBG(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
