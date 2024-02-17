package fragments.textEffects;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.azeesoft.lib.colorpicker.OpacityPicker;
import com.google.firebase.crash.FirebaseCrash;

import org.acra.sender.AcraLSender;

import java.lang.reflect.Field;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.graphic.UIColorArt;
import fragments.objectHelper.BackGroundHelper;
import fragments.objects.TextProperties;
import fragments.views.EyeDropper;
import fragments.views.TextIcon;
import fragments.views.colorpicker.BoxColor;
import fragments.views.colorpicker.ColorPalette;
import fragments.views.colorpicker.OnColorChangedListener;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 6/11/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class ColorPallete extends BaseFragment {
    public static ColorPallete ColorPalleteinstance;
    BoxColor picker_Primary;
    BoxColor picker_Secondary;
    BoxColor picker_Array;
    String myNameIs;
    int OldColor;
    private LinearLayout picker_PreviewBox;
    private OpacityPicker picker_opacityBar;
    private int mColor = Color.parseColor("#ffffffff");
    private String mHexVal = "#ffffffff";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static int getBackgroundColor(View view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            if (Build.VERSION.SDK_INT >= 11) {
                return colorDrawable.getColor();
            }
            try {
                Field field = colorDrawable.getClass().getDeclaredField("mState");
                field.setAccessible(true);
                Object object = field.get(colorDrawable);
                field = object.getClass().getDeclaredField("mUseColor");
                field.setAccessible(true);
                return field.getInt(object);
            } catch (Exception ignored) {
                FirebaseCrash.report(ignored);
            }
        }
        return 0;
    }

    @Override
    public String myNameIs() {
        return "ColorPallete" + getArguments().getString("Name");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ColorPalleteinstance = null;
        picker_PreviewBox = null;
        picker_opacityBar = null;
        picker_Primary = null;
        picker_Secondary = null;
        picker_Array = null;
    }

    @Override
    public ViewGroup onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        ColorPalleteinstance = this;
        ViewGroup f_colorPallete = (ViewGroup) inflater.inflate(R.layout.f_color, group, false);
        TextIcon picker_btn_procolor = (TextIcon) f_colorPallete.findViewById(R.id.picker_btn_procolor);
        picker_btn_procolor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    ColorPickerDialog colorPickerDialog =
                            ColorPickerDialog.createColorPickerDialog(getActivity(), ColorPickerDialog.DARK_THEME);
                    colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
                        @Override
                        public void onColorPicked(int c, String hexVal) {
                            refreshPreviewBox(c, picker_opacityBar.getProgress());
                        }
                    });
                    colorPickerDialog.showOpacityBar();
                    colorPickerDialog.show();
                }
            }
        });


//        TextIcon picker_btn_eyedrop = (TextIcon) f_colorPallete.findViewById(R.id.picker_btn_eyedrop);
//        picker_btn_eyedrop.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new EyeDropper(MainActivity.mainInstance().MainImageBG, new EyeDropper.ColorSelectionListener() {
//                    @Override
//                    public void onColorSelected(@ColorInt int color) {
////                (findViewById(R.id.colorSlot)).setBackgroundColor(color);
//                        MainActivity.mainInstance().toolbar.setBackgroundColor(color);
//                    }
//                });
//            }
//        });


        f_colorPallete.setClickable(true);
        f_colorPallete.setEnabled(false);
        picker_Primary = (BoxColor) f_colorPallete.findViewById(R.id.picker_Primary);
        picker_Secondary = (BoxColor) f_colorPallete.findViewById(R.id.picker_Secondary);
        picker_Array = (BoxColor) f_colorPallete.findViewById(R.id.picker_Array);
        picker_Primary.setColors(ColorPalette.getBaseColors(MainActivity.mainInstance().getApplicationContext()));
        picker_Secondary.setColors(ColorPalette.getColors(MainActivity.mainInstance().getApplicationContext(), picker_Primary.getColor()));
        if (MainActivity.mainInstance().backGroundProperties.getBackGroundHaveImage()) {
            picker_Array.setColors(UIColorArt.GetColorsAll());
            picker_Array.setSelectedColor(getArguments().getInt("UserColorBundle"));
        } else {
            picker_Array.setVisibility(View.GONE);
        }
        picker_Primary.setOnColorChangedListener(new OnColorChangedListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onColorChanged(int c) {
                picker_Secondary.setColors(ColorPalette.getColors(MainActivity.mainInstance().getApplicationContext(), picker_Primary.getColor()));
                picker_Secondary.setSelectedColor(picker_Primary.getColor());
            }
        });
        picker_Secondary.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                refreshPreviewBox(c, picker_opacityBar.getProgress());
            }
        });
        picker_Array.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                refreshPreviewBox(c, picker_opacityBar.getProgress());
            }
        });
        picker_PreviewBox = (LinearLayout) f_colorPallete.findViewById(R.id.picker_PreviewBox);
        picker_opacityBar = (OpacityPicker) f_colorPallete.findViewById(R.id.picker_opacityBar);
        picker_opacityBar.setOnOpacityPickedListener(new OpacityPicker.OnOpacityPickedListener() {
            @Override
            public void onPicked(int opacity) {
                ColorDrawable colorDrawable = (ColorDrawable) picker_PreviewBox.getBackground();
                if (colorDrawable == null)
                    return;
                int color = getBackgroundColor(picker_PreviewBox);
                refreshPreviewBox(color, opacity);
                picker_opacityBar.setCanUpdateHexVal(true);
            }
        });
        setCurrentColor(getArguments().getInt("UserColorBundle"));
        return f_colorPallete;
    }

    public void setCurrentColor(int color) {
//        float[] hsv = new float[3];
//        Color.colorToHSV(color, hsv);
//        satValPicker.setCanUpdateHexVal(updateHexVal);
//        opacityPicker.setCanUpdateHexVal(updateHexVal);
//        satValPicker.setSaturationAndValue(hsv[1], hsv[2], false);
//        if (huePicker.getProgress() != (int) hsv[0])
//            huePicker.setProgress((int) hsv[0]);
//        else
//            satValPicker.refreshSatValPicker(huePicker.getProgress());
//        picker_Primary.setSelectedColor(color);
        refreshPreviewBox(color, Color.alpha(color));
//        picker_opacityBar.setProgress(Color.alpha(color));
        if (OldColor != color) {
            setSelectedColor(color);
        }
    }

    private void refreshPreviewBox(int color, int opacity) {
        picker_opacityBar.setProgress(opacity);
        color = Color.argb(opacity, Color.red(color), Color.green(color), Color.blue(color));
        picker_PreviewBox.setBackgroundColor(color);
        mHexVal = String.format("#%08X", color);
        mColor = color;
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        if (MainActivity.mainInstance().SelecetedTextView != null) {
            if (getArguments().getBoolean("UserColorText")) {
                MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextColor(mHexVal);
                MainActivity.mainInstance().SelecetedTextView.setTextUserColor(mHexVal);
            }
            if (getArguments().getBoolean("UserColorShadow")) {
                MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextShadowColor(mHexVal);
                MainActivity.mainInstance().SelecetedTextView.setTextShadowColor(mHexVal);
                Shadow.eyeShadow.setChecked(true);
            }
            if (getArguments().getBoolean("UserColorFGradient")) {
                MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextGradientFColor(mHexVal);
                MainActivity.mainInstance().SelecetedTextView.setTextGradientFColor(mHexVal);
                Gradient.eyeTextGradient.setChecked(true);
                Gradient.FillCustomGradient();
            }
            if (getArguments().getBoolean("UserColorSGradient")) {
                MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextGradientSColor(mHexVal);
                MainActivity.mainInstance().SelecetedTextView.setTextGradientSColor(mHexVal);
                Gradient.eyeTextGradient.setChecked(true);
                Gradient.FillCustomGradient();
            }
            if (getArguments().getBoolean("UserStrokeColor")) {
                TextProperties.getCurrent().setTextStrokeColor(mHexVal);
                MainActivity.mainInstance().SelecetedTextView.setTextStrokeColor(mHexVal);
                Stroke.eyeTextStroke.setChecked(true);
            }
            if (getArguments().getBoolean("Color_Text_BG_Color")) {
                TextProperties.getCurrent().setTextBGColor(mHexVal);
                MainActivity.mainInstance().SelecetedTextView.setTextBGColor(mHexVal);
            }
        }
        if (getArguments().getBoolean("UserColorBG")) {
            BackGroundHelper.setBackGroundColor(color);
        }
        if (getArguments().getBoolean("BGColorFGradient")) {
            MainActivity.mainInstance().backGroundProperties.setBackGroundGradientFColor(mHexVal);
            BGGradient.eyeTextGradient.setChecked(true);
            BGGradient.FillCustomGradient();
        }
        if (getArguments().getBoolean("BGColorSGradient")) {
            MainActivity.mainInstance().backGroundProperties.setBackGroundGradientSColor(mHexVal);
            BGGradient.eyeTextGradient.setChecked(true);
            BGGradient.FillCustomGradient();
        }
//        MainActivity.mainInstance().ArraySelectedColors.add(Color.parseColor(mHexVal));
//        sat.setText("S: " + (int) (hsv[1] * 100) + " %");
//        val.setText("V: " + (int) (hsv[2] * 100) + " %");
//        if (updateHexVal)
//            setHexValText(mHexVal);
//        red.setText("R: " + Color.red(color));
//        green.setText("G: " + Color.green(color));
//        blue.setText("B: " + Color.blue(color));
//        alpha.setText("A: " + Color.alpha(color));
    }

    public void setSelectedColor(int color) {
        picker_Primary.setSelectedColor(color);
        picker_Primary.requestLayout();
        if (MainActivity.mainInstance().backGroundProperties.getBackGroundHaveImage()) {
            picker_Array.setSelectedColor(color);
            picker_Array.requestLayout();
        }
    }
}
