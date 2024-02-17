package fragments.ButtonBar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.orhanobut.logger.Logger;

import java.io.File;

import activities.BaseActivityPermission;
import activities.DownloadActivity;
import activities.MainActivity;
import activities.TourActivity;
import androidx.fragment.app.FragmentActivity;
import fragments.Image_Category_Loader;
import fragments.MTViews.AndroidUtilities;
import fragments.objectHelper.BackGroundHelper;
import fragments.objectHelper.ImageHelper;
import fragments.objectHelper.RonevisHelper;
import fragments.objectHelper.SaveImage;
import fragments.objects.ImageProperties;
import fragments.objects.TextProperties;
import fragments.textEffects.Alignment;
import fragments.textEffects.BGGradient;
import fragments.textEffects.BGSizeDialog;
import fragments.textEffects.ColorPallete;
import fragments.textEffects.Fonts;
import fragments.textEffects.Gradient;
import fragments.textEffects.Position;
import fragments.textEffects.Rotation;
import fragments.textEffects.Shadow;
import fragments.textEffects.Stroke;
import fragments.textEffects.TextBg;
import fragments.tool.CropConfig;
import fragments.tool.FragmentController;
import fragments.tool.Typefaces;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import fragments.views.TextButton;
import fragments.views.TextIcon;
import fragments.views.mtAlertDialog;
import fragments.views.mtDialog;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

import static mt.karimi.ronevis.R.color.text_secondary_light;

/**
 * Created by mt.karimi on 22/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class BarButton_ButtonClicks {
    private static EditText edaddtext;
    private static String value = "";

    public static void onItemClick(View v, final int position, final FragmentActivity activity, final SnapRecyclerAdapter adapter) {
        Bundle bundleFileType, UserColorBundle;
        switch (v.getId()) {
            /////////////////////////// BG
            case R.id.btn_BG_Opacity:
                Util.SeekDialog(Util.getInt(R.integer.bgAlphaMin), Util.getInt(R.integer.bgAlphaMax), (int) (MainActivity.mainInstance().backGroundProperties.getBackGroundAlpha() * 100), "bgSeekOpacity", R.string.Icon_opacity, activity);
                break;
            case R.id.btn_BG_Blur:
                Util.SeekDialog(Util.getInt(R.integer.bgBlurMin), Util.getInt(R.integer.bgBlurMax), (int) MainActivity.mainInstance().backGroundProperties.getBackGroundBlurRatio(), "bgSeekBlur", R.string.Icon_blur, activity);
                break;
            case R.id.btn_BG_Vitrin:
                bundleFileType = new Bundle();
                bundleFileType.putString("bundleFileType", "background");
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new Image_Category_Loader())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .CheckView(false)
                        .BackStackName("ImagePreview")
                        .setFragSize(MainActivity.mainInstance().appWH[1] / 2)
                        .Bundle(bundleFileType)
                        .show();
                break;
            case R.id.btn_BG_Delete:
                BackGroundHelper.setBackGroundRemove();
                break;
            case R.id.btn_BG_Import:
                if (BaseActivityPermission.RequestAll(MainActivity.mainInstance())) {
                    CropConfig.pickFromGallery(MainActivity.mainInstance());
                }
                break;
            case R.id.btn_BG_Gradient:
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new BGGradient())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .CheckView(false)
                        .Message(R.string.dfChooseTextError)
                        .BackStackName("BG_Gradient")
                        .show();
                break;
//            case R.id.btn_BG_Texture:
//                FragmentController.on(MainActivity.mainInstance(), activity)
//                        .fragment(new BGTexture_Loader())
//                        .viewGroup(MainActivity.mainInstance().mainLayout)
//                        .CheckView(false)
//
//                        .setFragSize(MainActivity.mainInstance().appWH[1] / 2)
//                        .Message(R.string.dfChooseTextError)
//                        .BackStackName("BG_Texture")
//                        .show();
//
//                break;
            case R.id.btn_BG_Color:
                UserColorBundle = new Bundle();
                UserColorBundle.putInt("UserColorBundle", MainActivity.mainInstance().backGroundProperties.getBackGroundColor());
                UserColorBundle.putBoolean("UserColorBG", true);
                UserColorBundle.putString("Name", "UserColorBG");
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new ColorPallete())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .CheckView(false)
                        .BackStackName("ColorPalleteUserColorBG")
                        .Bundle(UserColorBundle)
//                        .setFragSize(MainActivity.mainInstance().appWH[1] / 2)
                        .show();
                break;
            case R.id.btn_BG_Size:
                Bundle BGSize = new Bundle();
                BGSize.putInt("BGSizeBundle", MainActivity.mainInstance().backGroundProperties.getBackGroundColor());
                BGSize.putBoolean("BGSizeBundle", true);
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new BGSizeDialog())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .CheckView(false)
                        .Bundle(BGSize)
                        .BackStackName("BGSizeDialog")
                        .show();
                break;

            case R.id.btn_BG_Image_Crop:

                if (MainActivity.mainInstance().backGroundProperties.getBackGroundSrc() != null) {
                    Uri  myUri= Uri.fromFile(new File(MainActivity.mainInstance().backGroundProperties.getBackGroundSrc()));
                    CropConfig.startCropActivity(
                            myUri,
                            activity ,
                            Uri.fromFile(ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathTemp), "Crop_" + System.currentTimeMillis() + ".jpeg"))
                    );
                }


                break;
/////////////////////////// STICKER
            case R.id.btn_Sticker_Opacity:
                if (MainActivity.mainInstance().SelectedImage != null) {
                    Util.SeekDialog(Util.getInt(R.integer.stickerAlphaMin), Util.getInt(R.integer.stickerAlphaMax), (int) (MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewAlpha() * 100), "stickerSeekOpacity", R.string.Icon_opacity, activity);
                }
                break;
            case R.id.btn_Sticker_Position:
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new Position())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .viewChecker(MainActivity.mainInstance().SelectedImage)
                        .CheckView(true)
                        .Message(R.string.dfChooseTextError)
                        .BackStackName("PositionFragment")
                        .show();
                break;
            case R.id.btn_Sticker_Expand:
                if (MainActivity.mainInstance().SelectedImage != null) {
                    float width = MainActivity.mainInstance().SelectedImage.getWidth();
                    float scaleFactorWidth;
                    scaleFactorWidth = width / (float) MainActivity.mainInstance().SelectedImage.getDrawable().getIntrinsicWidth();
                    Util.SeekDialog(10, 300, (int) (scaleFactorWidth * 100f), "stickerSeekExpand", R.string.Icon_arrow_expand, activity);
                }
                break;
            case R.id.btn_Sticker_Rotation:
//                if (MainActivity.mainInstance().SelectedImage != null) {
//                    Util.SeekDialog(0, 360, (int) (MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewRotate()), "stickerSeekRotation", R.string.Icon_rotate_3d, activity);
//                }
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new Rotation())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .viewChecker(MainActivity.mainInstance().SelectedImage)
                        .CheckView(true)
                        .Message(R.string.dfChooseTextError)
                        .BackStackName("RotationSticker")
                        .show();
                break;
            case R.id.btn_Sticker_Duplicate:
                if (MainActivity.mainInstance().SelectedImage != null) {
                    int textID;
                    textID = Util.randInt();
                    ImageProperties tp = new ImageProperties()
                            .setImageViewID(textID)
                            .setImageViewSrc(MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewSrc())
                            .setImageViewScaleType(MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewScaleType())
                            .setImageViewAlpha(MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewAlpha())
                            .setImageViewRotate(MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewRotate())
                            .setImageViewSize(MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewSize())
                            .setImageViewPosition(MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewPosition());
                    MainActivity.mainInstance().ImageViewMap.put(textID, tp);
                    tp.AddImageView(MainActivity.mainInstance()._exportroot);
                }
                break;
            case R.id.btn_Sticker_Vitrin:
                bundleFileType = new Bundle();
                bundleFileType.putString("bundleFileType", "sticker");
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new Image_Category_Loader())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .CheckView(false)
                        .BackStackName("StickerPreview")
                        .setFragSize(MainActivity.mainInstance().appWH[1] / 2)
                        .Bundle(bundleFileType)
                        .show();
                break;
            case R.id.btn_Sticker_Import:
                if (BaseActivityPermission.RequestAll(MainActivity.mainInstance())) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    MainActivity.mainInstance().startActivityForResult(i, 753);
                }
                break;
            case R.id.btn_Sticker_Delete:
                if (MainActivity.mainInstance().SelectedImage != null) {
                    mtAlertDialog.on(activity)
                            .with()
                            .gravity(Gravity.BOTTOM)
                            .title(R.string.ddTitle)
                            .message(R.string.ddMessage)
                            .icon(Util.getDrawableIcon(R.string.Icon_delete, text_secondary_light))
                            .cancelable(true)
                            .when(R.string.ddPositiveButton, new mtAlertDialog.Positive() {
                                @Override
                                public void clicked(final DialogInterface dialog) {
                                    MainActivity.mainInstance().SelectedImage.RemoveMe();
                                    dialog.dismiss();
                                }
                            })
                            .when(R.string.ddNegativeButton, new mtAlertDialog.Negative() {
                                @Override
                                public void clicked(DialogInterface dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                break;
            /////////////////////////// TEXT
            case R.id.btn_Text_Add:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    mtDialog.create(activity, R.style.FullDialog, new mtDialog.View() {
                        @Override
                        public void prepare(@Nullable View view, final Dialog dDialog) {
                            edaddtext = (EditText) view.findViewById(R.id.edaddtext);
                            edaddtext.setText(MainActivity.mainInstance().SelecetedTextView.getText());
                            int textLength = edaddtext.getText().length();
                            edaddtext.setSelection(textLength, textLength);
//                            if (edaddtext.requestFocus()) {
//                                InputMethodManager imm = (InputMethodManager)
//                                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//                                imm.showSoftInput(edaddtext, InputMethodManager.SHOW_FORCED);
//                            }
                            AndroidUtilities.showKeyboard(edaddtext);
                            TextButton dButton1 = (TextButton) view.findViewById(R.id.dButton1);
//                            dButton1.setTextColor(Util.getColorWrapper(activity, R.color.text_primary_light));
//                            dButton1.setTypeface(Util.GetSelfTypeFace(activity, Util.getInt(R.integer.ronevisButtonFont)));
                            dButton1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    value = edaddtext.getText().toString();
                                    if (!value.isEmpty()) {
                                        if (MainActivity.mainInstance().SelecetedTextView != null) {
                                            MainActivity.mainInstance().SelecetedTextView.setText(value);
                                            TextProperties.getCurrent().setText(value);
                                            dDialog.dismiss();
                                        }
                                    } else {
                                        Toast.makeText(ApplicationLoader.appInstance(), Util.Persian(R.string.deEditEmptyError), Toast.LENGTH_SHORT).show();
//                                        Manimator.InformAnimation(btndeltxt);
                                    }
                                }
                            });
                            TextButton dButton2 = (TextButton) view.findViewById(R.id.dButton2);
//                            dButton2.setTextColor(Util.getColorWrapper(activity, R.color.text_secondary_light));
//                            dButton2.setTypeface(Util.GetSelfTypeFace(activity, Util.getInt(R.integer.ronevisButtonFont)));
                            dButton2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dDialog.dismiss();
                                }
                            });
                        }
                    })
//                            .setdTitle(R.string.deTitle)
//                .setdIcon(Util.getDrawableIcon(R.string.Icon_pencil_box, text_secondary_light))
                            .setdCancelable(true)
                            .setdView(R.layout.dialog_text)
                            .setShowKey(true)
                            .show();
                } else {
                    mtDialog.create(activity, R.style.FullDialog, new mtDialog.View() {
//                    mtDialog.create(MainActivity.mainInstance(), R.style.FullDialog,new mtDialog.View() {

                        @Override
                        public void prepare(@Nullable View view, final Dialog dDialog) {
                            edaddtext = (EditText) view.findViewById(R.id.edaddtext);
                            if (edaddtext.requestFocus()) {
                                InputMethodManager imm = (InputMethodManager)
                                        MainActivity.mainInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(edaddtext, InputMethodManager.SHOW_IMPLICIT);
                            }
                            TextButton dButton1 = (TextButton) view.findViewById(R.id.dButton1);
//                            dButton1.setTextColor(Util.getColorWrapper(activity, R.color.text_primary_light));
//                            dButton1.setTypeface(Util.GetSelfTypeFace(activity, Util.getInt(R.integer.ronevisButtonFont)));
                            dButton1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    value = edaddtext.getText().toString();
                                    if (!value.isEmpty()) {
                                        if (!Pref.get(ApplicationLoader.appInstance().getString(R.string.SharedPrefronevisLastFont), "").isEmpty())
                                            MainActivity.mainInstance().tf_selected = Typefaces.getTypeface(ApplicationLoader.appInstance(), Pref.get(ApplicationLoader.appInstance().getString(R.string.SharedPrefronevisLastFont), ""));
                                        MainActivity.mainInstance().ts_last = Pref.get(ApplicationLoader.appInstance().getString(R.string.SharedPrefronevisLastFS), 0) > 0 ? Pref.get(ApplicationLoader.appInstance().getString(R.string.SharedPrefronevisLastFS), 45) : 45;
                                        MainActivity.mainInstance().ArraySelectedColors.add(0xFFFFFFFF);
                                        int textID;
                                        textID = Util.randInt();
                                        TextProperties tp = new TextProperties()
                                                .setTextId(textID)
                                                .setTextLock(false)
                                                .setText(value)
                                                .setTextColor("#FFFFFFFF")
                                                .setTextBGColor("#FF000000")
                                                .setTextTypeFacePath(Pref.get(ApplicationLoader.appInstance().getString(R.string.SharedPrefronevisLastFont), ""))
                                                .setTextSize(MainActivity.mainInstance().ts_last)
                                                .setTextBold(false)
                                                .setTextItalic(false)
                                                .setTextAlpha(255)
                                                .setTextLineSpacing(0.0f)
                                                .setTextShadow(false)
                                                .setTextShadowColor("#ffffffff")
                                                .setTextShadowX(4)
                                                .setTextShadowY(4)
                                                .setTextShadowD(10)
                                                .setTextGradient(false)
                                                .setTextGradientFColor("#FFFFFFFF")
                                                .setTextGradientSColor("#FF000000")
                                                .setTextTexture(false)
                                                .setTextTextureAlpha(255)
                                                .setTextTexturePath("")
                                                .setTextRotateX(0.0f)
                                                .setTextRotateY(0.0f)
                                                .setTextRotateZ(0.0f)
                                                .setTextPadding(new Rect(0, 0, 0, 0))
                                                .setTextPosition(new Point(0, 0))
                                                .setTextGravity(Gravity.END);
                                        MainActivity.mainInstance().TextViewMap.put(textID, tp);
                                        tp.AddTextView(MainActivity.mainInstance()._exportroot);
                                        dDialog.dismiss();
                                    } else {
                                        Toast.makeText(ApplicationLoader.appInstance(), Util.Persian(R.string.deAddEmptyError), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            TextButton dButton2 = (TextButton) view.findViewById(R.id.dButton2);
//                            dButton2.setTextColor(Util.getColorWrapper(activity, R.color.text_secondary_light));
//                            dButton2.setTypeface(Util.GetSelfTypeFace(activity, Util.getInt(R.integer.ronevisButtonFont)));
                            dButton2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dDialog.dismiss();
                                }
                            });
                        }
                    })
//                            .setdTitle(R.string.daTitle)
//                .setdIcon(Util.getDrawableIcon(R.string.Icon_pencil_box_outline, text_secondary_light))
                            .setdCancelable(true)
                            .setdView(R.layout.dialog_text)
                            .setShowKey(true)
                            .show();
                }
                break;
            case R.id.btn_Text_Font:
                if (BaseActivityPermission.RequestAll(MainActivity.mainInstance())) {
                    FragmentController.on(MainActivity.mainInstance(), activity)
                            .fragment(new Fonts())
                            .viewGroup(MainActivity.mainInstance().mainLayout)
                            .CheckView(true)
                            .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                            .Message(R.string.dfChooseTextError)
                            .BackStackName("FontPreview")
                            .setFragSize(MainActivity.mainInstance().appWH[1] / 2)
                            .show();
                }
                break;
            case R.id.btn_Text_Size:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    Util.SeekDialog(Util.getInt(R.integer.textSizeMin), Util.getInt(R.integer.textSizeMax), (int) TextProperties.getCurrent().getTextSize(), "textSize", R.string.Icon_format_size, activity);
                }
//                if (MainActivity.mainInstance().SelecetedTextView != null) {
////                    Util.SeekDialog(Util.getInt(R.integer.textCropMin), Util.getInt(R.integer.textCropMax), (int) TextProperties.getCurrent().getTextSizeCrop(), "btn_Text_Crop", R.string.Icon_crop_free, activity);
//
//                    FragmentController.on(MainActivity.mainInstance(), activity)
//                            .fragment(new TextSize())
//                            .viewGroup(MainActivity.mainInstance().mainLayout)
//                            .CheckView(true)
//                            .viewChecker(MainActivity.mainInstance().SelecetedTextView)
//                            .Message(R.string.dfChooseTextError)
//                            .BackStackName("TextSize")
//                            .show();
//
//                }
                break;
            case R.id.btn_Text_Crop:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
//                    Util.SeekDialog(Util.getInt(R.integer.textCropMin), Util.getInt(R.integer.textCropMax), (int) TextProperties.getCurrent().getTextSizeCrop(), "btn_Text_Crop", R.string.Icon_crop_free, activity);
                    FragmentController.on(MainActivity.mainInstance(), activity)
                            .fragment(new TextBg())
                            .viewGroup(MainActivity.mainInstance().mainLayout)
                            .CheckView(true)
                            .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                            .Message(R.string.dfChooseTextError)
                            .BackStackName("TextBg")
                            .show();
                }
                break;
            case R.id.btn_Text_Stroke:
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new Stroke())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .CheckView(true)
                        .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                        .Message(R.string.dfChooseTextError)
                        .BackStackName("StrokeFragment")
                        .show();
                break;
            case R.id.btn_Text_Shadow:
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new Shadow())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .CheckView(true)
                        .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                        .Message(R.string.dfChooseTextError)
                        .BackStackName("ShadowFragment")
                        .show();
                break;
            case R.id.btn_Text_Gradient:
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new Gradient())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .CheckView(true)
                        .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                        .Message(R.string.dfChooseTextError)
                        .BackStackName("GradientFragment")
                        .show();
                break;
//            case R.id.btn_Text_Texture:
//                FragmentController.on(MainActivity.mainInstance(), activity)
//                        .fragment(new Texture_Loader())
//                        .viewGroup(MainActivity.mainInstance().mainLayout)
//                        .CheckView(true)
//                        .viewChecker(MainActivity.mainInstance().SelecetedTextView)
//                        .setFragSize(MainActivity.mainInstance().appWH[1] / 2)
//                        .Message(R.string.dfChooseTextError)
//                        .BackStackName("Texture_Loader")
//                        .show();
//
//                break;
            case R.id.btn_Text_Color:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    UserColorBundle = new Bundle();
                    UserColorBundle.putInt("UserColorBundle", Color.parseColor(TextProperties.getCurrent().getTextColor().trim()));
                    UserColorBundle.putBoolean("UserColorText", true);
                    UserColorBundle.putString("Name", "UserColorText");
                    FragmentController.on(MainActivity.mainInstance(), activity)
                            .fragment(new ColorPallete())
                            .viewGroup(MainActivity.mainInstance().mainLayout)
                            .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                            .Message(R.string.dfChooseTextError)
                            .CheckView(true)
                            .Bundle(UserColorBundle)
                            .BackStackName("UserColorText")
                            .show();
                }
                break;
//            case R.id.btn_Text_BG_Color:
//                if (MainActivity.mainInstance().SelecetedTextView != null) {
//                    UserColorBundle = new Bundle();
//                    UserColorBundle.putInt("UserColorBundle", Color.parseColor(TextProperties.getCurrent().getTextBGColor().trim()));
//                    UserColorBundle.putBoolean("Color_Text_BG_Color", true);
//                    UserColorBundle.putString("Name", "Color_Text_BG_Color");
//                    FragmentController.on(MainActivity.mainInstance(), activity)
//                            .fragment(new ColorPallete())
//                            .viewGroup(MainActivity.mainInstance().mainLayout)
//                            .viewChecker(MainActivity.mainInstance().SelecetedTextView)
//                            .Message(R.string.dfChooseTextError)
//                            .CheckView(true)
//                            .Bundle(UserColorBundle)
//                            .BackStackName("Color_Text_BG_Color")
//                            .show();
//                }
//                break;
            case R.id.btn_Text_Opacity:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    Util.SeekDialog(Util.getInt(R.integer.textAlphaMin), Util.getInt(R.integer.textAlphaMax), (TextProperties.getCurrent().getTextAlpha()), "textOpacity", R.string.Icon_opacity, activity);
                }
                break;
            case R.id.btn_Text_LineSpace:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    Util.SeekDialog(Util.getInt(R.integer.textLineSpaceMin), Util.getInt(R.integer.textLineSpaceMax), (int) (TextProperties.getCurrent().getTextLineSpacing() * 100), "textLineSpace", R.string.Icon_format_line_spacing, activity);
                }
                break;
            case R.id.btn_Text_Duplicate:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    int textID = Util.randInt();
                    TextProperties tp = new TextProperties()
                            .setTextId(textID)
                            .setTextLock(TextProperties.getCurrent().getTextLock())
                            .setText(TextProperties.getCurrent().getText())
                            .setTextColor(TextProperties.getCurrent().getTextColor())
                            .setTextBGColor(TextProperties.getCurrent().getTextBGColor())
                            .setTextSizeCrop(TextProperties.getCurrent().getTextSizeCrop())
                            .setTextCropX(TextProperties.getCurrent().getTextCropX())
                            .setTextCropY(TextProperties.getCurrent().getTextCropY())
                            .setTextCropCorner(TextProperties.getCurrent().getTextCropCorner())
                            .setTextBGColorHave(TextProperties.getCurrent().getTextBGColorHave())
                            .setTextTypeFacePath(TextProperties.getCurrent().getTextTypeFacePath())
                            .setTextSize(TextProperties.getCurrent().getTextSize())
                            .setTextBold(TextProperties.getCurrent().getTextBold())
                            .setTextItalic(TextProperties.getCurrent().getTextItalic())
                            .setTextStrike(TextProperties.getCurrent().getTextStrike())
                            .setTextUnderline(TextProperties.getCurrent().getTextUnderline())
                            .setTextAlpha(TextProperties.getCurrent().getTextAlpha())
                            .setTextLineSpacing(TextProperties.getCurrent().getTextLineSpacing())
                            .setTextShadow(TextProperties.getCurrent().getTextShadow())
                            .setTextShadowColor(TextProperties.getCurrent().getTextShadowColor())
                            .setTextShadowX(TextProperties.getCurrent().getTextShadowX())
                            .setTextShadowY(TextProperties.getCurrent().getTextShadowY())
                            .setTextShadowD(TextProperties.getCurrent().getTextShadowD())
                            .setTextGradient(TextProperties.getCurrent().getTextGradient())
                            .setTextGradientFColor(TextProperties.getCurrent().getTextGradientFColor())
                            .setTextGradientSColor(TextProperties.getCurrent().getTextGradientSColor())
                            .setTextTexture(TextProperties.getCurrent().getTextTexture())
                            .setTextTextureAlpha(TextProperties.getCurrent().getTextTextureAlpha())
                            .setTextTexturePath(TextProperties.getCurrent().getTextTexturePath())
                            .setTextStroke(TextProperties.getCurrent().getTextStroke())
                            .setTextStrokeWidth(TextProperties.getCurrent().getTextStrokeWidth())
                            .setTextStrokeColor(TextProperties.getCurrent().getTextStrokeColor())
                            .setTextRotateX(TextProperties.getCurrent().getTextRotateX())
                            .setTextRotateY(TextProperties.getCurrent().getTextRotateY())
                            .setTextRotateZ(TextProperties.getCurrent().getTextRotateZ())
                            .setTextPadding(new Rect(0, 0, TextProperties.getCurrent().getTextShadowX(), TextProperties.getCurrent().getTextShadowY()))
                            .setTextPosition(TextProperties.getCurrent().getTextPosition())
                            .setTextGravity(TextProperties.getCurrent().getTextGravity());
                    MainActivity.mainInstance().TextViewMap.put(textID, tp);
                    try {
                        tp.AddTextView(MainActivity.mainInstance()._exportroot);
                    } catch (Exception ignored) {
                        FirebaseCrash.report(ignored);
                    }
                }
                break;
            case R.id.btn_Text_Lock:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    if (TextProperties.getCurrent().getTextLock()) {
                        TextProperties.getCurrent().setTextLock(false);
                    } else {
                        TextProperties.getCurrent().setTextLock(true);
                    }
                    adapter.refreshVisibleLayout(position);
                }
                break;
            case R.id.btn_Text_Align:
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new Alignment())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .CheckView(true)
                        .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                        .Message(R.string.dfChooseTextError)
                        .BackStackName("TextAlign")
                        .show();
                break;
            case R.id.btn_Text_Bold:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    if (TextProperties.getCurrent() != null) {
                        int tfStyle;
                        if (!TextProperties.getCurrent().getTextBold()) {
                            if (!TextProperties.getCurrent().getTextItalic()) {
                                tfStyle = Typeface.BOLD;
                            } else {
                                tfStyle = Typeface.BOLD_ITALIC;
                            }
                        } else {
                            if (!TextProperties.getCurrent().getTextItalic()) {
                                tfStyle = Typeface.NORMAL;
                            } else {
                                tfStyle = Typeface.ITALIC;
                            }
                        }
                        MainActivity.mainInstance().SelecetedTextView.setTypeface(Typefaces.getTypeface(ApplicationLoader.appInstance(), TextProperties.getCurrent().getTextTypeFacePath()), tfStyle);
                        TextProperties.getCurrent().setTextBold(!TextProperties.getCurrent().getTextBold());
                    }
                }
//                adapter.refreshWholeLayout();
                adapter.refreshVisibleLayout(position);
                adapter.refreshVisibleLayout(position + 1);
                break;
            case R.id.btn_Text_Italic:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    if (TextProperties.getCurrent() != null) {
                        int tfStyle;
                        if (!TextProperties.getCurrent().getTextItalic()) {
                            if (!TextProperties.getCurrent().getTextBold()) {
                                tfStyle = Typeface.ITALIC;
                            } else {
                                tfStyle = Typeface.BOLD_ITALIC;
                            }
                        } else {
                            if (!TextProperties.getCurrent().getTextBold()) {
                                tfStyle = Typeface.NORMAL;
                            } else {
                                tfStyle = Typeface.BOLD;
                            }
                        }
                        MainActivity.mainInstance().SelecetedTextView.setTypeface(Typefaces.getTypeface(ApplicationLoader.appInstance(), TextProperties.getCurrent().getTextTypeFacePath()), tfStyle);
                        TextProperties.getCurrent().setTextItalic(!TextProperties.getCurrent().getTextItalic());
                    }
                }
                adapter.refreshVisibleLayout(position);
                adapter.refreshVisibleLayout(position - 1);
                break;
            case R.id.btn_Text_Strikethrough:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    if (TextProperties.getCurrent() != null) {
                        TextProperties.getCurrent().setTextStrike(!TextProperties.getCurrent().getTextStrike());
                        MainActivity.mainInstance().SelecetedTextView.setTextStrike();
                        adapter.refreshVisibleLayout(position);
                        adapter.refreshVisibleLayout(position - 1);
                    }
                }
                break;
            case R.id.btn_Text_Underline:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    if (TextProperties.getCurrent() != null) {
                        TextProperties.getCurrent().setTextUnderline(!TextProperties.getCurrent().getTextUnderline());
                        MainActivity.mainInstance().SelecetedTextView.setTextUnderline();
                        adapter.refreshVisibleLayout(position);
                        adapter.refreshVisibleLayout(position - 1);
                    }
                }
                break;
            case R.id.btn_Text_Rotation:
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new Rotation())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                        .CheckView(true)
                        .Message(R.string.dfChooseTextError)
                        .BackStackName("RotationText")
                        .show();
                break;
            case R.id.btn_Text_Position:
                FragmentController.on(MainActivity.mainInstance(), activity)
                        .fragment(new Position())
                        .viewGroup(MainActivity.mainInstance().mainLayout)
                        .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                        .CheckView(true)
                        .Message(R.string.dfChooseTextError)
                        .BackStackName("PositionFragment")
                        .show();
                break;
            case R.id.btn_Text_Delete:
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    mtAlertDialog.on(activity)
                            .with()
                            .cancelable(true)
                            .gravity(Gravity.BOTTOM)
                            .title(R.string.ddTitle)
                            .message(R.string.ddMessage)
                            .icon(Util.getDrawableIcon(R.string.Icon_delete, text_secondary_light))
                            .cancelable(true)
                            .when(R.string.ddPositiveButton, new mtAlertDialog.Positive() {
                                @Override
                                public void clicked(final DialogInterface dialog) {
                                    MainActivity.mainInstance().SelecetedTextView.RemoveMe();
                                    dialog.dismiss();
                                    adapter.refreshWholeLayout();
                                }
                            })
                            .when(R.string.ddNegativeButton, new mtAlertDialog.Negative() {
                                @Override
                                public void clicked(DialogInterface dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                break;
            /////////////////////////// Tools
            case R.id.menuabout:
                mtAlertDialog.on(activity)
                        .with()
                        .title(R.string.daaTitle)
                        .icon(ImageHelper.getDrawable(R.drawable.ic_launcher))
                        .cancelable(true)
                        .layout(R.layout.activity_about)
                        .when(R.string.daaPositiveButton, new mtAlertDialog.Positive() {
                            @Override
                            public void clicked(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show(new mtAlertDialog.View() {
                            @Override
                            public void prepare(@Nullable View view, final AlertDialog alertDialog) {
                                TextView appabout = (TextView) view.findViewById(R.id.appabout);
                                TextView appversion = (TextView) view.findViewById(R.id.appversion);
                                appabout.setTypeface(Util.GetSelfTypeFace(null, Util.getInt(R.integer.ronevisTitleFont)));
                                appversion.setTypeface(Util.GetSelfTypeFace(null, Util.getInt(R.integer.ronevisTitleFont)));
                                appversion.setText(ApplicationLoader.appInstance().appVersionName);
                                TextIcon Icon_email = (TextIcon) view.findViewById(R.id.Icon_email);
                                TextIcon Icon_linkedin_box = (TextIcon) view.findViewById(R.id.Icon_linkedin_box);
//                                TextIcon Icon_facebook_box = (TextIcon) view.findViewById(R.id.Icon_facebook_box);
                                TextIcon Icon_instagram = (TextIcon) view.findViewById(R.id.Icon_instagram);
                                TextIcon Icon_telegram = (TextIcon) view.findViewById(R.id.Icon_telegram);
//                                TextIcon Icon_phone_in_talk = (TextIcon) view.findViewById(R.id.Icon_phone_in_talk);
                                Icon_email.setOnClickListener(MainActivity.mainInstance().buttonlistener);
                                Icon_linkedin_box.setOnClickListener(MainActivity.mainInstance().buttonlistener);
//                                Icon_facebook_box.setOnClickListener(MainActivity.mainInstance().buttonlistener);
                                Icon_instagram.setOnClickListener(MainActivity.mainInstance().buttonlistener);
                                Icon_telegram.setOnClickListener(MainActivity.mainInstance().buttonlistener);
//                                Icon_phone_in_talk.setOnClickListener(MainActivity.mainInstance().buttonlistener);
                            }
                        });
                break;
            case R.id.menunew:
                mtAlertDialog.on(activity)
                        .with()
                        .title(R.string.dpnTitle)
                        .message(R.string.dpnMessage)
                        .icon(ImageHelper.getDrawable(R.drawable.ic_launcher))
                        .cancelable(true)
                        .when(R.string.dpnPositiveButton, new mtAlertDialog.Positive() {
                            @Override
                            public void clicked(DialogInterface dialog) {
                                RonevisHelper.NewProject(MainActivity.mainInstance());
                                dialog.dismiss();
                            }
                        })
                        .when(R.string.dpnNegativeButton, new mtAlertDialog.Negative() {
                            @Override
                            public void clicked(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
//            case R.id.menusave:
//                if (BaseActivityPermission.RequestAll(activity)) {
//                    if (activity.getWindow() != null) {
//                        if (activity.getCurrentFocus() != null) {
//                            activity.getCurrentFocus().clearFocus();
//                        }
//                    }
//                    SaveImage.savefile();
//                }
//                break;
//            case R.id.menusaveFile:
//                RonevisHelper.SaveProject(activity);
//                break;
            case R.id.menuOpenFile:
                RonevisHelper.OpenProject(activity);
                break;
            case R.id.menushop:
//                switchToOrFromEditMode();
                activity.startActivity(new Intent(activity, DownloadActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.menutrain:
                activity.startActivity(new Intent(activity, TourActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.menushare:
                if (BaseActivityPermission.RequestAll(activity)) {
                    if (activity.getWindow() != null) {
                        if (activity.getCurrentFocus() != null) {
                            activity.getCurrentFocus().clearFocus();
                        }
                    }
                    SaveImage.sharefile(activity);
                }
                break;
        }
    }
}
