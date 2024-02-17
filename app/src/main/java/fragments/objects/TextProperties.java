package fragments.objects;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import activities.MainActivity;
import fragments.Interface.TaskProcess;
import fragments.animation.Spring_Helper;
import fragments.views.MTFrameLayout;
import fragments.views.MTextView;
import mt.karimi.ronevis.ApplicationLoader;

public class TextProperties {
    private int TextId;
    private String Text;
    private String TextColor;
    private String TextBGColor;
    private String TextTypeFacePath;
    private float TextSize;
    private Boolean TextBold = false;
    private Boolean TextItalic = false;
    private Boolean TextUnderline = false;
    private Boolean TextStrike = false;
    private Boolean TextLock = false;
    private int TextAlpha = 255;
    private float TextLineSpacing = 1.0f;
    private Boolean TextShadow = false;
    private String TextShadowColor;
    private int TextShadowX = 0;
    private int TextShadowY = 0;
    private int TextShadowD = 0;
    private float TextRotateZ = 0.0f;
    private float TextRotateY = 0.0f;
    private float TextRotateX = 0.0f;
    private Rect TextPadding;
    private Point TextPosition = new Point(0, 0);
    private int TextGravity = Gravity.END;
    private Boolean TextGradient = false;
    private String TextGradientFColor = "#FFFFFFFF";
    private String TextGradientSColor = "#FF000000";
    private float TextGradientRotate = 0.0f;
    private Shader.TileMode TextGradientTileMode = Shader.TileMode.CLAMP;
    private int TextGradientAlpha = 255;
    private float TextStrokeWidth = 3f;
    private String TextStrokeColor = "#FF9E9E9E";
    private Boolean TextStroke = false;
    private int TextTextureAlpha = 255;
    private Boolean TextTexture = false;
    private String TextTexturePath = "";
    private Shader.TileMode TextTextureTileMode = Shader.TileMode.REPEAT;
//
//    private float textSizeX = 1.0f;
//    private float textSizeY = 1.0f;
//
//    public float getTextSizeX() {
//        return textSizeX;
//    }
//
//    public TextProperties setTextSizeX(float textSizeX) {
//        this.textSizeX = textSizeX;
//        return this;
//    }
//
//    public float getTextSizeY() {
//        return textSizeY;
//    }
//
//    public TextProperties setTextSizeY(float textSizeY) {
//        this.textSizeY = textSizeY;
//        return this;
//    }
    private Boolean TextBGColorHave = false;
    private float TextSizeCrop = 0;
    private float TextCropX = 0.0f;
    private float TextCropY = 0.0f;
    private float TextCropCorner = 0.0f;

    public TextProperties() {
    }

    public static TextProperties getCurrent() {
        if (MainActivity.mainInstance().textArrayID != 0) {
            return MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID);
        } else {
            return null;
        }
    }

    public static void disableHardwareRendering(View v) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public float getTextCropX() {
        return TextCropX;
    }

    public TextProperties setTextCropX(float textCropX) {
        TextCropX = textCropX;
        return this;
    }

    public float getTextCropY() {
        return TextCropY;
    }

    public TextProperties setTextCropY(float textCropY) {
        TextCropY = textCropY;
        return this;
    }

    public float getTextCropCorner() {
        return TextCropCorner;
    }

    public TextProperties setTextCropCorner(float textCropCorner) {
        TextCropCorner = textCropCorner;
        return this;
    }

    public Boolean getTextBGColorHave() {
        return TextBGColorHave;
    }

    public TextProperties setTextBGColorHave(Boolean textBGColorHave) {
        TextBGColorHave = textBGColorHave;
        return this;
    }

    public float getTextSizeCrop() {
        return TextSizeCrop;
    }

    public TextProperties setTextSizeCrop(float textSizeCrop) {
        TextSizeCrop = textSizeCrop;
        return this;
    }

    public String getTextBGColor() {
        return TextBGColor;
    }

    public TextProperties setTextBGColor(String textBGColor) {
        TextBGColor = textBGColor;
        return this;
    }

    public Boolean getTextUnderline() {
        return TextUnderline;
    }

    public TextProperties setTextUnderline(Boolean textUnderline) {
        TextUnderline = textUnderline;
        return this;
    }

    public Boolean getTextStrike() {
        return TextStrike;
    }

    public TextProperties setTextStrike(Boolean textStrike) {
        TextStrike = textStrike;
        return this;
    }

    public int getTextTextureAlpha() {
        return TextTextureAlpha;
    }

    public TextProperties setTextTextureAlpha(int textTextureAlpha) {
        TextTextureAlpha = textTextureAlpha;
        return this;
    }

    public Boolean getTextTexture() {
        return TextTexture;
    }

    public TextProperties setTextTexture(Boolean textTexture) {
        TextTexture = textTexture;
        return this;
    }

    public String getTextTexturePath() {
        return TextTexturePath;
    }

    public TextProperties setTextTexturePath(String textTexturePath) {
        TextTexturePath = textTexturePath;
        return this;
    }

    public TileMode getTextTextureTileMode() {
        return TextTextureTileMode;
    }

    public TextProperties setTextTextureTileMode(TileMode textTextureTileMode) {
        TextTextureTileMode = textTextureTileMode;
        return this;
    }

    public int getTextGradientAlpha() {
        return TextGradientAlpha;
    }

    public void setTextGradientAlpha(int textGradientAlpha) {
        TextGradientAlpha = textGradientAlpha;
    }

    public Shader.TileMode getTextGradientTileMode() {
        return TextGradientTileMode;
    }

    public void setTextGradientTileMode(Shader.TileMode textGradientTileMode) {
        TextGradientTileMode = textGradientTileMode;
    }

    public float getTextGradientRotate() {
        return TextGradientRotate;
    }

    public void setTextGradientRotate(float textGradientRotate) {
        TextGradientRotate = textGradientRotate;
    }

    public Boolean getTextGradient() {
        return TextGradient;
    }

    public TextProperties setTextGradient(Boolean textGradient) {
        TextGradient = textGradient;
        return this;
    }

    public String getTextGradientFColor() {
        return TextGradientFColor;
    }

    public TextProperties setTextGradientFColor(String textGradientFColor) {
        TextGradientFColor = textGradientFColor;
        return this;
    }

    public String getTextGradientSColor() {
        return TextGradientSColor;
    }

    public TextProperties setTextGradientSColor(String textGradientSColor) {
        TextGradientSColor = textGradientSColor;
        return this;
    }

    public Boolean getTextStroke() {
        return TextStroke;
    }

    public TextProperties setTextStroke(Boolean textStroke) {
        TextStroke = textStroke;
        return this;
    }

    public String getTextStrokeColor() {
        return TextStrokeColor;
    }

    public TextProperties setTextStrokeColor(String textStrokeColor) {
        TextStrokeColor = textStrokeColor;
        return this;
    }

    public float getTextStrokeWidth() {
        return TextStrokeWidth;
    }

    public TextProperties setTextStrokeWidth(float textStrokeWidth) {
        TextStrokeWidth = textStrokeWidth;
        return this;
    }

    public int getTextId() {
        return TextId;
    }

    public TextProperties setTextId(int textId) {
        TextId = textId;
        return this;
    }

    public String getTextTypeFacePath() {
        return TextTypeFacePath;
    }

    public TextProperties setTextTypeFacePath(String textTypeFacePath) {
        TextTypeFacePath = textTypeFacePath;
        return this;
    }

    public Boolean getTextBold() {
        return TextBold;
    }

    public TextProperties setTextBold(Boolean textBold) {
        TextBold = textBold;
        return this;
    }

    public Boolean getTextItalic() {
        return TextItalic;
    }

    public TextProperties setTextItalic(Boolean textItalic) {
        TextItalic = textItalic;
        return this;
    }

    public Boolean getTextLock() {
        return TextLock;
    }

    public TextProperties setTextLock(Boolean textLock) {
        TextLock = textLock;
        return this;
    }

    public int getTextAlpha() {
        return TextAlpha;
    }

    public TextProperties setTextAlpha(int textAlpha) {
        TextAlpha = textAlpha;
        return this;
    }

    public float getTextLineSpacing() {
        return TextLineSpacing;
    }

    public TextProperties setTextLineSpacing(float textLineSpacing) {
        TextLineSpacing = textLineSpacing;
        return this;
    }

    public Boolean getTextShadow() {
        return TextShadow;
    }

    public TextProperties setTextShadow(Boolean textShadow) {
        TextShadow = textShadow;
        return this;
    }

    public String getTextShadowColor() {
        return TextShadowColor;
    }

    public TextProperties setTextShadowColor(String textShadowColor) {
        TextShadowColor = textShadowColor;
        return this;
    }

    public int getTextShadowX() {
        return TextShadowX;
    }

    public TextProperties setTextShadowX(int textShadowX) {
        TextShadowX = textShadowX;
        return this;
    }

    public int getTextShadowY() {
        return TextShadowY;
    }

    public TextProperties setTextShadowY(int textShadowY) {
        TextShadowY = textShadowY;
        return this;
    }

    public int getTextShadowD() {
        return TextShadowD;
    }

    public TextProperties setTextShadowD(int textShadowD) {
        TextShadowD = textShadowD;
        return this;
    }

    public float getTextRotateZ() {
        return TextRotateZ;
    }

    public TextProperties setTextRotateZ(float textRotateZ) {
        TextRotateZ = textRotateZ;
        return this;
    }

    public float getTextRotateY() {
        return TextRotateY;
    }

    public TextProperties setTextRotateY(float textRotateY) {
        TextRotateY = textRotateY;
        return this;
    }

    public float getTextRotateX() {
        return TextRotateX;
    }

    public TextProperties setTextRotateX(float textRotateX) {
        TextRotateX = textRotateX;
        return this;
    }

    public Rect getTextPadding() {
        return TextPadding;
    }

    public TextProperties setTextPadding(Rect padding) {
        TextPadding = padding;
        return this;
    }

    public Point getTextPosition() {
        return TextPosition;
    }

    public TextProperties setTextPosition(Point textPosition) {
        TextPosition = textPosition;
        return this;
    }

    public int getTextGravity() {
        return TextGravity;
    }

    public TextProperties setTextGravity(int gravity) {
        TextGravity = gravity;
        return this;
    }

    public String getText() {
        return Text;
    }

    public TextProperties setText(String text) {
        Text = text;
        return this;
    }

    public String getTextColor() {
        return TextColor;
    }

    public TextProperties setTextColor(String textColor) {
        TextColor = textColor;
        return this;
    }

    public float getTextSize() {
        return TextSize;
    }

    public TextProperties setTextSize(float textSize) {
        TextSize = textSize;
        return this;
    }

    public TextProperties AddTextView(final ViewGroup viewGroup) {
        if (MainActivity.mainInstance().SelecetedTextView != null) {
            MainActivity.mainInstance().SelecetedTextView.clearFocus();
            MainActivity.mainInstance().SelecetedTextView = null;
        }
        MainActivity.mainInstance().textArrayID = TextId;
        MainActivity.mainInstance().SelecetedTextView = new MTextView(ApplicationLoader.appInstance().getApplicationContext());
        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(MTFrameLayout.LayoutParams.WRAP_CONTENT, MTFrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = TextPosition.x;
        layoutParams.topMargin = TextPosition.y;
        viewGroup.addView(MainActivity.mainInstance().SelecetedTextView, layoutParams);
        MainActivity.mainInstance().SelecetedTextView.requestFocus();
        Spring_Helper.spring_Views(MainActivity.mainInstance().SelecetedTextView, 0f, 1f, new TaskProcess() {
            @Override
            public void onProgress(double progress) {
//                float value = (float) progress;
//                 MainActivity.mainInstance().SelecetedTextView .setScaleX(value);
//                MainActivity.mainInstance().SelecetedTextView .setScaleY(value);
            }

            @Override
            public void onDone() {
            }
        });
        return this;
    }
}
