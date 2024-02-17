package fragments.objects;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import activities.MainActivity;
import fragments.objectHelper.BackGroundHelper;
import fragments.objectHelper.LayoutHelper;
import fragments.objectHelper.Manimator;
import fragments.textEffects.BGGradient;

public class BackGroundProperties {
    private int BackGroundID;
    private int BackGroundColor = Color.parseColor("#FF000000");
    private Boolean BackGroundColorHas = false;
    private String BackGroundSrc;
    private Bitmap BackGroundBitmap;
    private ImageView.ScaleType BackGroundScaleType;
    private float BackGroundAlpha = 1.0f;
    private Boolean BackGroundSizeFixed = false;
    private Point BackGroundSize = new Point(0, 0);
    private Boolean BackGroundBlur = false;
    private float BackGroundBlurRatio;
    private Boolean BackGroundHaveImage = false;
    private int BackGroundGravity = 1;
    private int BackGroundTextureAlpha = 255;
    private Boolean BackGroundTexture = false;
    private String BackGroundTexturePath = "";
    private Shader.TileMode BackGroundTextureTileMode = Shader.TileMode.REPEAT;
    private Boolean BackGroundGradient = false;
    private String BackGroundGradientFColor = "#FFFFFFFF";
    private String BackGroundGradientSColor = "#FF000000";
    private float BackGroundGradientRotate = 0.0f;
    private Shader.TileMode BackGroundGradientTileMode = Shader.TileMode.CLAMP;
    private int BackGroundGradientAlpha = 255;

    public BackGroundProperties() {
    }

    public static BackGroundProperties GenerateImage() {
        return new BackGroundProperties();
    }

    public int getBackGroundTextureAlpha() {
        return BackGroundTextureAlpha;
    }

    public BackGroundProperties setBackGroundTextureAlpha(int backGroundTextureAlpha) {
        BackGroundTextureAlpha = backGroundTextureAlpha;
        return this;
    }

    public Boolean getBackGroundTexture() {
        return BackGroundTexture;
    }

    public BackGroundProperties setBackGroundTexture(Boolean backGroundTexture) {
        BackGroundTexture = backGroundTexture;
        return this;
    }

    public String getBackGroundTexturePath() {
        return BackGroundTexturePath;
    }

    public BackGroundProperties setBackGroundTexturePath(String backGroundTexturePath) {
        BackGroundTexturePath = backGroundTexturePath;
        return this;
    }

    public TileMode getBackGroundTextureTileMode() {
        return BackGroundTextureTileMode;
    }

    public BackGroundProperties setBackGroundTextureTileMode(TileMode backGroundTextureTileMode) {
        BackGroundTextureTileMode = backGroundTextureTileMode;
        return this;
    }

    public int getBackGroundGradientAlpha() {
        return BackGroundGradientAlpha;
    }

    public BackGroundProperties setBackGroundGradientAlpha(int backGroundGradientAlpha) {
        BackGroundGradientAlpha = backGroundGradientAlpha;
        return this;
    }

    public TileMode getBackGroundGradientTileMode() {
        return BackGroundGradientTileMode;
    }

    public BackGroundProperties setBackGroundGradientTileMode(TileMode backGroundGradientTileMode) {
        BackGroundGradientTileMode = backGroundGradientTileMode;
        return this;
    }

    public float getBackGroundGradientRotate() {
        return BackGroundGradientRotate;
    }

    public BackGroundProperties setBackGroundGradientRotate(float backGroundGradientRotate) {
        BackGroundGradientRotate = backGroundGradientRotate;
        return this;
    }

    public String getBackGroundGradientSColor() {
        return BackGroundGradientSColor;
    }

    public BackGroundProperties setBackGroundGradientSColor(String backGroundGradientSColor) {
        BackGroundGradientSColor = backGroundGradientSColor;
        return this;
    }

    public String getBackGroundGradientFColor() {
        return BackGroundGradientFColor;
    }

    public BackGroundProperties setBackGroundGradientFColor(String backGroundGradientFColor) {
        BackGroundGradientFColor = backGroundGradientFColor;
        return this;
    }

    public Boolean getBackGroundGradient() {
        return BackGroundGradient;
    }

    public BackGroundProperties setBackGroundGradient(Boolean backGroundGradient) {
        BackGroundGradient = backGroundGradient;
        return this;
    }

    public int getBackGroundID() {
        return BackGroundID;
    }

    public BackGroundProperties setBackGroundID(int backGroundID) {
        BackGroundID = backGroundID;
        return this;
    }

    public int getBackGroundColor() {
        return BackGroundColor;
    }

    public BackGroundProperties setBackGroundColor(int backGroundColor) {
        BackGroundColor = backGroundColor;
        return this;
    }

    public Boolean getBackGroundColorHas() {
        return BackGroundColorHas;
    }

    public BackGroundProperties setBackGroundColorHas(Boolean backGroundColorHas) {
        BackGroundColorHas = backGroundColorHas;
        return this;
    }

    public String getBackGroundSrc() {
        return BackGroundSrc;
    }

    public BackGroundProperties setBackGroundSrc(String backGroundSrc) {
        BackGroundSrc = backGroundSrc;
        return this;
    }

    public Bitmap getBackGroundBitmap() {
        return BackGroundBitmap;
    }

    public BackGroundProperties setBackGroundBitmap(Bitmap backGroundBitmap) {
        BackGroundBitmap = backGroundBitmap;
        return this;
    }

    public ImageView.ScaleType getBackGroundScaleType() {
        return BackGroundScaleType;
    }

    public BackGroundProperties setBackGroundScaleType() {
        BackGroundScaleType = ImageView.ScaleType.FIT_CENTER;
        return this;
    }

    public float getBackGroundAlpha() {
        return BackGroundAlpha;
    }

    public BackGroundProperties setBackGroundAlpha(float backGroundAlpha) {
        BackGroundAlpha = backGroundAlpha;
        return this;
    }

    public Boolean getBackGroundSizeFixed() {
        return BackGroundSizeFixed;
    }

    public BackGroundProperties setBackGroundSizeFixed(Boolean backGroundSizeFixed) {
        BackGroundSizeFixed = backGroundSizeFixed;
        return this;
    }

    public Point getBackGroundSize() {
        return BackGroundSize;
    }

    public BackGroundProperties setBackGroundSize(Point backGroundSize) {
        BackGroundSize = backGroundSize;
        return this;
    }

    public Boolean getBackGroundBlur() {
        return BackGroundBlur;
    }

    public BackGroundProperties setBackGroundBlur(Boolean backGroundBlur) {
        BackGroundBlur = backGroundBlur;
        return this;
    }

    public float getBackGroundBlurRatio() {
        return BackGroundBlurRatio;
    }

    public BackGroundProperties setBackGroundBlurRatio(float backGroundBlurRatio) {
        BackGroundBlurRatio = backGroundBlurRatio;
        return this;
    }

    public int getBackGroundGravity() {
        return BackGroundGravity;
    }

    public BackGroundProperties setBackGroundGravity() {
        BackGroundGravity = android.view.Gravity.CENTER_HORIZONTAL;
        return this;
    }

    public Boolean getBackGroundHaveImage() {
        return BackGroundHaveImage;
    }

    public BackGroundProperties setBackGroundHaveImage(Boolean backGroundHaveImage) {
        BackGroundHaveImage = backGroundHaveImage;
        return this;
    }

    public BackGroundProperties SetBackGround() {
        if (getBackGroundHaveImage()) {
            setBackGroundBitmap(BackGroundHelper.GetBitmap(getBackGroundSrc()));
            BackGroundHelper.ImageViewSetBG(MainActivity.mainInstance().MainImageBG, BackGroundHelper.GetBitmap(getBackGroundSrc()));
        }
//        Tools_bg_setting.chkFixed.setChecked(getBackGroundSizeFixed());
//        if (getBackGroundSizeFixed()) {
//            Tools_bg_setting.FrameXSizeFixed.setText(String.valueOf(getBackGroundSize().x));
//            Tools_bg_setting.FrameYSizeFixed.setText(String.valueOf(getBackGroundSize().y));
//        }
        if (getBackGroundColorHas()) {
            BackGroundHelper.setBackGroundColor(getBackGroundColor());
        }
        MainActivity.mainInstance().backGroundProperties.setBackGroundAlpha((int) (getBackGroundAlpha() * 100));
        Manimator.Alhpa(MainActivity.mainInstance().MainImageBG, (int) (getBackGroundAlpha() * 100), 0);
        if (getBackGroundBlur()) {
            BackGroundHelper.BlurBackGround((int) getBackGroundBlurRatio());
            BackGroundHelper.BlurBackGround((int) getBackGroundBlurRatio());
        } else {
            BackGroundHelper.BlurBackGround(0);
        }
        if (getBackGroundGradient()) {
            BGGradient.FillCustomGradient();
        } else {
        }
        MainActivity.mainInstance().runOnUiThread(new Runnable() {
            public void run() {
                BackGroundHelper.SetBackGroundRefresh();
            }
        });
        if (getBackGroundSizeFixed()) {
            RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
            LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams, getBackGroundSize().x, getBackGroundSize().y);
//            Logger.d("x1 " + getBackGroundSize().x);
//            Logger.d("y1 " + getBackGroundSize().y);
        } else {
        }
        return this;
    }
}
