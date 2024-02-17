package fragments.objects;

import android.graphics.Rect;
import android.view.Gravity;

import java.util.Map;

import activities.MainActivity;
import fragments.tool.Util;

public class AddViews {
    public static void TextViews(Map<Integer, TextProperties> TextViewMapOpen) {
        for (Map.Entry<Integer, TextProperties> entry : TextViewMapOpen.entrySet()) {
            int textID;
            textID = Util.randInt();
            TextProperties tp = new TextProperties()
                    .setTextId(textID)
                    .setTextId(textID)
                    .setTextLock(entry.getValue().getTextLock())
                    .setText(entry.getValue().getText())
                    .setTextColor(entry.getValue().getTextColor())
                    .setTextBGColor(entry.getValue().getTextBGColor())
                    .setTextSizeCrop(entry.getValue().getTextSizeCrop())
                    .setTextCropX(entry.getValue().getTextCropX())
                    .setTextCropY(entry.getValue().getTextCropY())
                    .setTextCropCorner(entry.getValue().getTextCropCorner())
                    .setTextBGColorHave(entry.getValue().getTextBGColorHave())
                    .setTextTypeFacePath(entry.getValue().getTextTypeFacePath())
                    .setTextSize(entry.getValue().getTextSize())
                    .setTextBold(entry.getValue().getTextBold())
                    .setTextItalic(entry.getValue().getTextItalic())
                    .setTextStrike(entry.getValue().getTextStrike())
                    .setTextUnderline(entry.getValue().getTextUnderline())
                    .setTextAlpha(entry.getValue().getTextAlpha())
                    .setTextLineSpacing(entry.getValue().getTextLineSpacing())
                    .setTextShadow(entry.getValue().getTextShadow())
                    .setTextShadowColor(entry.getValue().getTextShadowColor())
                    .setTextShadowX(entry.getValue().getTextShadowX())
                    .setTextShadowY(entry.getValue().getTextShadowY())
                    .setTextShadowD(entry.getValue().getTextShadowD())
                    .setTextGradient(entry.getValue().getTextGradient())
                    .setTextGradientFColor(entry.getValue().getTextGradientFColor())
                    .setTextGradientSColor(entry.getValue().getTextGradientSColor())
                    .setTextTexture(entry.getValue().getTextTexture())
                    .setTextTextureAlpha(entry.getValue().getTextTextureAlpha())
                    .setTextTexturePath(entry.getValue().getTextTexturePath())
                    .setTextStroke(entry.getValue().getTextStroke())
                    .setTextStrokeWidth(entry.getValue().getTextStrokeWidth())
                    .setTextStrokeColor(entry.getValue().getTextStrokeColor())
                    .setTextRotateX(entry.getValue().getTextRotateX())
                    .setTextRotateY(entry.getValue().getTextRotateY())
                    .setTextRotateZ(entry.getValue().getTextRotateZ())
                    .setTextPadding(new Rect(0, 0, entry.getValue().getTextShadowX(), entry.getValue().getTextShadowY()))
                    .setTextPosition(entry.getValue().getTextPosition())
                    .setTextGravity(entry.getValue().getTextGravity());
            MainActivity.mainInstance().TextViewMap.put(textID, tp);
            tp.AddTextView(MainActivity.mainInstance()._exportroot);
        }
    }

    public static void ImageViews(Map<Integer, ImageProperties> ImageViewMapOpen) {
        for (Map.Entry<Integer, ImageProperties> entry : ImageViewMapOpen.entrySet()) {
            int imageID;
            imageID = Util.randInt();
            ImageProperties tp = new ImageProperties()
                    .setImageViewID(imageID)
                    .setImageViewSrc(entry.getValue().getImageViewSrc())
                    .setImageViewScaleType(entry.getValue().getImageViewScaleType())
                    .setImageViewAlpha(entry.getValue().getImageViewAlpha())
                    .setImageViewRotate(entry.getValue().getImageViewRotate())
                    .setImageViewSize(entry.getValue().getImageViewSize())
                    .setImageViewPosition(entry.getValue().getImageViewPosition())
                    .setImageViewGravity(Gravity.CENTER_HORIZONTAL);
            MainActivity.mainInstance().ImageViewMap.put(imageID, tp);
            tp.AddImageView(MainActivity.mainInstance()._exportroot);
        }
    }

    public static void BackGroundViews(Map<Integer, BackGroundProperties> ImageViewMapOpen) {
        for (Map.Entry<Integer, BackGroundProperties> entry : ImageViewMapOpen.entrySet()) {
            MainActivity.mainInstance().backGroundProperties
                    .setBackGroundSrc(entry.getValue().getBackGroundSrc())
                    .setBackGroundHaveImage(entry.getValue().getBackGroundHaveImage())
                    .setBackGroundSizeFixed(entry.getValue().getBackGroundSizeFixed())
                    .setBackGroundSize(entry.getValue().getBackGroundSize())
                    .setBackGroundColorHas(entry.getValue().getBackGroundColorHas())
                    .setBackGroundColor(entry.getValue().getBackGroundColor())
                    .setBackGroundAlpha(entry.getValue().getBackGroundAlpha())
                    .setBackGroundBlur(entry.getValue().getBackGroundBlur())
                    .setBackGroundBlurRatio(entry.getValue().getBackGroundBlurRatio())
                    .setBackGroundTexture(entry.getValue().getBackGroundTexture())
                    .setBackGroundTextureAlpha(entry.getValue().getBackGroundTextureAlpha())
                    .setBackGroundTexturePath(entry.getValue().getBackGroundTexturePath())
                    .setBackGroundTextureTileMode(entry.getValue().getBackGroundTextureTileMode())
                    .setBackGroundGradient(entry.getValue().getBackGroundGradient())
                    .setBackGroundGradientAlpha(entry.getValue().getBackGroundGradientAlpha())
                    .setBackGroundGradientFColor(entry.getValue().getBackGroundGradientFColor())
                    .setBackGroundGradientSColor(entry.getValue().getBackGroundGradientSColor())
                    .setBackGroundGradientRotate(entry.getValue().getBackGroundGradientRotate())
                    .setBackGroundGradientTileMode(entry.getValue().getBackGroundGradientTileMode())
                    .SetBackGround();
            MainActivity.mainInstance().BackGroundMap.put(MainActivity.mainInstance().MainImageBG.getId(), MainActivity.mainInstance().backGroundProperties);
        }
    }
}
