package com.azeesoft.lib.colorpicker;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import java.lang.reflect.Field;

import fragments.tool.Util;
import mt.karimi.ronevis.R;

public class ColorPickerDialog extends Dialog {
    public static final int DARK_THEME = R.style.ColorPicker_Dark;
    private HuePicker huePicker;
    private OpacityPicker opacityPicker;
    private SatValPicker satValPicker;
    private LinearLayout colorPreviewBox, oldColorPreviewBox;
    private EditText hexVal;
    private TextView hex, hue, sat, val, red, green, blue, alpha;
    private ImageView hsvEditIcon, rgbEditIcon;
    private AppCompatButton pickButton, cancelButton;
    private LinearLayout colorComponents;
    private RelativeLayout hexHolder;
    private ColorPickerCompatScrollView colorPickerCompatScrollView;
    private ColorPickerCompatHorizontalScrollView colorPickerCompatHorizontalScrollView;
    private ColorPickerRootView colorPickerRootView;
    private ColorEditDialog colorEditDialog;
    private int initColor;
    private boolean skipHexValChange = false;
    private int mColor = Color.parseColor("#ffffffff");
    private String mHexVal = "#ffffffff";
    private OnColorPickedListener onColorPickedListener;
    private OnClosedListener onClosedListener;

    private ColorPickerDialog(Context context) {
        super(context);
        init(context);
    }

    private ColorPickerDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    public static ColorPickerDialog createColorPickerDialog(Context context, int theme) {
        return new ColorPickerDialog(new ContextThemeWrapper(context, theme), theme);
    }

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

    public static String getLastColorAsHexa(Context context) {
        return Stools.loadLastColor(context);
    }

    private static int getLastColor(Context context) {
        String lastColorHex = Stools.loadLastColor(context);
        if (lastColorHex == null)
            return Color.parseColor("#00ffffff");
        else
            return Color.parseColor(lastColorHex);
    }

    @Override
    public void show() {
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        super.show();
        reloadLastColor();
        if (opacityPicker != null) {
            opacityPicker.setProgress(255);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        initColor = getLastColor(getContext());
        if (onClosedListener != null) {
            onClosedListener.onClosed();
        }
    }

    private void init(final Context context) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_root, null));
        setTitle(R.string.dialog_title);
        initColor = getLastColor(context);
        colorEditDialog = new ColorEditDialog(context);
        colorEditDialog.setOnColorEditedListener(new ColorEditDialog.OnColorEditedListener() {
            @Override
            public void onColorEdited(int color) {
                setCurrentColor(color);
            }
        });
        huePicker = (HuePicker) findViewById(R.id.hueBar);
        opacityPicker = (OpacityPicker) findViewById(R.id.opacityBar);
        satValPicker = (SatValPicker) findViewById(R.id.satValBox);
        colorPreviewBox = (LinearLayout) findViewById(R.id.colorPreviewBox);
        oldColorPreviewBox = (LinearLayout) findViewById(R.id.oldColorPreviewBox);
        hexHolder = (RelativeLayout) findViewById(R.id.hexHolder);
        pickButton = (AppCompatButton) findViewById(R.id.pickButton);
        cancelButton = (AppCompatButton) findViewById(R.id.cancelButton);
        colorComponents = (LinearLayout) findViewById(R.id.colorComponents);
        RelativeLayout hsv = (RelativeLayout) findViewById(R.id.hsv);
        RelativeLayout rgb = (RelativeLayout) findViewById(R.id.rgb);
        colorPickerRootView = (ColorPickerRootView) findViewById(R.id.colorPickerRoot);
        hexVal = (EditText) findViewById(R.id.hexVal);
        View hScrollView = findViewById(R.id.CscrollView);
        if (hScrollView instanceof ColorPickerCompatScrollView)
            colorPickerCompatScrollView = (ColorPickerCompatScrollView) hScrollView;
        else if (hScrollView instanceof ColorPickerCompatHorizontalScrollView)
            colorPickerCompatHorizontalScrollView = (ColorPickerCompatHorizontalScrollView) hScrollView;
        hexVal.setImeOptions(EditorInfo.IME_ACTION_GO);
        hexVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (skipHexValChange) {
                    skipHexValChange = false;
                } else {
                    hexValTyped(s.toString());
                }
            }
        });
        hex = (TextView) findViewById(R.id.hex);
        hue = (TextView) findViewById(R.id.hue);
        sat = (TextView) findViewById(R.id.sat);
        val = (TextView) findViewById(R.id.val);
        red = (TextView) findViewById(R.id.red);
        green = (TextView) findViewById(R.id.green);
        blue = (TextView) findViewById(R.id.blue);
        alpha = (TextView) findViewById(R.id.alpha);
        hsvEditIcon = (ImageView) findViewById(R.id.hsvEditIcon);
        rgbEditIcon = (ImageView) findViewById(R.id.rgbEditIcon);
        huePicker.setOnHuePickedListener(new HuePicker.OnHuePickedListener() {
            @Override
            public void onPicked(float hue) {
                satValPicker.refreshSatValPicker(hue);
                ColorPickerDialog.this.hue.setText("H: " + (int) hue + " \u00b0");
            }
        });
        huePicker.setMax(360);
        huePicker.setProgress(0);
        huePicker.setColorPickerCompatScrollView(colorPickerCompatScrollView);
        huePicker.setColorPickerCompatHorizontalScrollView(colorPickerCompatHorizontalScrollView);
        satValPicker.setOnColorSelectedListener(new SatValPicker.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color, String hexVal) {
                refreshPreviewBox(color, opacityPicker.getProgress(), satValPicker.isCanUpdateHexVal());
                satValPicker.setCanUpdateHexVal(true);
            }
        });
        satValPicker.setColorPickerCompatScrollView(colorPickerCompatScrollView);
        satValPicker.setColorPickerCompatHorizontalScrollView(colorPickerCompatHorizontalScrollView);
        opacityPicker.setOnOpacityPickedListener(new OpacityPicker.OnOpacityPickedListener() {
            @Override
            public void onPicked(int opacity) {
                ColorDrawable colorDrawable = (ColorDrawable) colorPreviewBox.getBackground();
                if (colorDrawable == null)
                    return;
                int color = getBackgroundColor(colorPreviewBox);
                refreshPreviewBox(color, opacity, opacityPicker.isCanUpdateHexVal());
                opacityPicker.setCanUpdateHexVal(true);
            }
        });
        opacityPicker.setColorPickerCompatScrollView(colorPickerCompatScrollView);
        opacityPicker.setColorPickerCompatHorizontalScrollView(colorPickerCompatHorizontalScrollView);
        hsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String h = getPlainComponentValue(hue.getText().toString());
                    String s = getPlainComponentValue(sat.getText().toString());
                    String v = getPlainComponentValue(val.getText().toString());
                    int a = Integer.parseInt(getPlainComponentValue(alpha.getText().toString()));
                    colorEditDialog.setModeAndValues(ColorEditDialog.MODE_HSV, h, s, v, a);
                    colorEditDialog.show();
                } catch (Exception ignored) {
                }
            }
        });
        rgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String r = getPlainComponentValue(red.getText().toString());
                    String g = getPlainComponentValue(green.getText().toString());
                    String b = getPlainComponentValue(blue.getText().toString());
                    int a = Integer.parseInt(getPlainComponentValue(alpha.getText().toString()));
                    colorEditDialog.setModeAndValues(ColorEditDialog.MODE_RGB, r, g, b, a);
                    colorEditDialog.show();
                } catch (Exception ignored) {
                }
            }
        });
        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onColorPickedListener != null)
                    onColorPickedListener.onColorPicked(mColor, mHexVal);
                Stools.saveLastColor(getContext(), mHexVal);
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        applyTheme();
    }

    private String getPlainComponentValue(String s) {
        s = s.split(":", 2)[1];
        s = s.replaceAll("%", "");
        s = s.replaceAll("\u00b0", "");
        return s.replaceAll(" ", "");
    }

    private void hexValTyped(String s) {
        try {
            int color = Color.parseColor("#" + s);
            if (opacityPicker.getVisibility() != View.VISIBLE) {
                if (s.length() == 8) {
                    s = s.substring(2);
                    color = Color.parseColor("#" + s);
                }
            }
            setCurrentColor(color, false);
        } catch (Exception ignored) {
//            AcraLSender acraLSender = new AcraLSender();
//            acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "ColorPickerDialog_283");
        }
    }

    private void applyTheme() {
        if (colorPickerRootView.isFLAG_SHOW_HEX()) {
            showHexaDecimalValue();
        } else {
            hideHexaDecimalValue();
        }
        if (colorPickerRootView.isFLAG_SHOW_COLOR_COMPS()) {
            showColorComponentsInfo();
        } else {
            hideColorComponentsInfo();
        }
        setHexaDecimalTextColor(Color.parseColor("#ffffff"));
        int compsColor = colorPickerRootView.getFLAG_COMPS_COLOR();
        setColorComponentsTextColor(compsColor);
        Drawable hsvIcon = getContext().getResources().getDrawable(R.drawable.ic_mode_edit_white_24dp);
        Drawable rgbIcon = getContext().getResources().getDrawable(R.drawable.ic_mode_edit_white_24dp);
        hsvEditIcon.setImageDrawable(Stools.tintDrawable(hsvIcon, compsColor));
        rgbEditIcon.setImageDrawable(Stools.tintDrawable(rgbIcon, compsColor));
        setPositiveActionText(colorPickerRootView.getFLAG_POS_ACTION_TEXT());
        setNegativeActionText(colorPickerRootView.getFLAG_NEG_ACTION_TEXT());
        setPositiveActionTextColor(Util.getColorWrapper(R.color.colorAccent));
        setNegativeActionTextColor(Util.getColorWrapper(R.color.colorAccent));
        int sliderThumbColor = colorPickerRootView.getFLAG_SLIDER_THUMB_COLOR();
        setSliderThumbColor(sliderThumbColor);
        colorEditDialog.setBackgroundColor(colorPickerRootView.getFLAG_BACKGROUND_COLOR());
        colorEditDialog.setFontColor(colorPickerRootView.getFLAG_COMPS_COLOR());
        colorEditDialog.setDoneButtonColor(Util.getColorWrapper(R.color.colorAccent));
        colorEditDialog.setCancelButtonColor(Util.getColorWrapper(R.color.colorAccent));
    }

    private void reloadLastColor() {
        reloadLastColor(initColor);
    }

    private void reloadLastColor(int current_color) {
        String lastHexVal = Stools.loadLastColor(getContext());
        if (lastHexVal != null) {
            int lastColor = Color.parseColor(lastHexVal);
            oldColorPreviewBox.setBackgroundColor(lastColor);
        }
        setCurrentColor(current_color);
    }

    private void setCurrentColor(int color, boolean updateHexVal) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        satValPicker.setCanUpdateHexVal(updateHexVal);
        opacityPicker.setCanUpdateHexVal(updateHexVal);
        satValPicker.setSaturationAndValue(hsv[1], hsv[2], false);
        if (huePicker.getProgress() != (int) hsv[0])
            huePicker.setProgress((int) hsv[0]);
        else
            satValPicker.refreshSatValPicker(huePicker.getProgress());
        opacityPicker.setProgress(Color.alpha(color));
    }

    private void refreshPreviewBox(int color, int opacity, boolean updateHexVal) {
        color = Color.argb(opacity, Color.red(color), Color.green(color), Color.blue(color));
        colorPreviewBox.setBackgroundColor(color);
        mHexVal = String.format("#%08X", color);
        mColor = color;
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        sat.setText("S: " + (int) (hsv[1] * 100) + " %");
        val.setText("V: " + (int) (hsv[2] * 100) + " %");
        if (updateHexVal)
            setHexValText(mHexVal);
        red.setText("R: " + Color.red(color));
        green.setText("G: " + Color.green(color));
        blue.setText("B: " + Color.blue(color));
        alpha.setText("A: " + Color.alpha(color));
    }

    private void setHexValText(String s) {
        s = s.replace("#", "");
        skipHexValChange = true;
        hexVal.setText(s);
    }

    public void setOnColorPickedListener(OnColorPickedListener onColorPickedListener) {
        this.onColorPickedListener = onColorPickedListener;
    }

    public void setOnClosedListener(OnClosedListener onClosedListener) {
        this.onClosedListener = onClosedListener;
    }

    public int getCurrentColor() {
        return mColor;
    }

    private void setCurrentColor(int color) {
        setCurrentColor(color, true);
    }

    public String getCurrentColorAsHexa() {
        return mHexVal;
    }

    public void setInitialColor(String hexVal) {
        setInitialColor(Color.parseColor(hexVal));
    }

    private void setInitialColor(int color) {
        initColor = color;
    }

    public void setLastColor(int color) {
        setLastColor(String.format("#%08X", color));
    }

    private void setLastColor(String hexVal) {
        Stools.saveLastColor(getContext(), hexVal);
        initColor = Color.parseColor(hexVal);
        reloadLastColor();
    }

    public void showOpacityBar() {
        opacityPicker.setVisibility(View.VISIBLE);
    }

    public void hideOpacityBar() {
        opacityPicker.setVisibility(View.GONE);
    }

    private void showHexaDecimalValue() {
        hexHolder.setVisibility(View.VISIBLE);
    }

    private void hideHexaDecimalValue() {
        hexHolder.setVisibility(View.GONE);
    }

    private void showColorComponentsInfo() {
        colorComponents.setVisibility(View.VISIBLE);
    }

    private void hideColorComponentsInfo() {
        colorComponents.setVisibility(View.GONE);
    }

    public void setBackgroundColor(int color) {
        colorPickerRootView.setBackgroundColor(color);
    }

    private void setHexaDecimalTextColor(int color) {
        hex.setTextColor(color);
        hexVal.setTextColor(color);
        hexVal.getBackground().mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private void setColorComponentsTextColor(int color) {
        hue.setTextColor(color);
        sat.setTextColor(color);
        val.setTextColor(color);
        red.setTextColor(color);
        green.setTextColor(color);
        blue.setTextColor(color);
        alpha.setTextColor(color);
    }

    private void setPositiveActionText(String s) {
        pickButton.setText(s);
        pickButton.setTypeface(Util.GetSelfTypeFace(null, Util.getInt(R.integer.ronevisButtonFont)));
    }

    private void setPositiveActionTextColor(int color) {
        pickButton.setTextColor(Util.getColorWrapper(R.color.colorAccent));
    }

    private void setNegativeActionText(String s) {
        cancelButton.setText(s);
        cancelButton.setTypeface(Util.GetSelfTypeFace(null, Util.getInt(R.integer.ronevisButtonFont)));
    }

    private void setNegativeActionTextColor(int color) {
        cancelButton.setTextColor(color);
    }

    private void setSliderThumbColor(int color) {
        Drawable hueThumbDrawable = getContext().getResources().getDrawable(R.drawable.slider_thumb);
        Drawable opacityThumbDrawable =
                getContext().getResources().getDrawable(R.drawable.slider_thumb);
        hueThumbDrawable = Stools.tintDrawable(hueThumbDrawable, color);
        opacityThumbDrawable = Stools.tintDrawable(opacityThumbDrawable, color);
        huePicker.setThumb(hueThumbDrawable);
        opacityPicker.setThumb(opacityThumbDrawable);
    }

    public interface OnColorPickedListener {
        void onColorPicked(int color, String hexVal);
    }

    public interface OnClosedListener {
        void onClosed();
    }
}
