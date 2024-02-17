package fragments.textEffects;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.lisetener.CheckedChangeListener;
import fragments.objects.TextProperties;
import fragments.tool.FragmentController;
import fragments.tool.Util;
import fragments.views.SwitchButton;
import fragments.views.TextIcon;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/7/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class TextBg extends BaseFragment {
    private static TextView textBgSeekXText;
    private static TextView textBgSeekYText;
    private static TextView textBgSeekAllText;
    private static TextView textBgSeekCornerText;
    ShadowButtonListener shadowButtonListener;
    SeekBarChangeListener seekBarChangeListener;
    private TextIcon textBgColor;
    public static TextIcon eyeTextBox;
    private TextIcon textBgX;
    private SeekBar textBgSeekX;
    private TextIcon textBgY;
    private SeekBar textBgSeekY;
    private TextIcon textBgAll;
    private SeekBar textBgSeekAll;
    private TextIcon textBgCorner;
    private SeekBar textBgSeekCorner;

    public static Shadow newInstance() {
        Shadow ftext = new Shadow();
        Bundle bundle = new Bundle();
        ftext.setArguments(bundle);
        return ftext;
    }

    @Override
    public String myNameIs() {
        return "TextBg";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shadowButtonListener = null;
        seekBarChangeListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_textboxsize, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setClickable(true);
        view.setEnabled(false);
        textBgColor = (TextIcon) view.findViewById(R.id.textBgColor);
        eyeTextBox = (TextIcon) view.findViewById(R.id.eyeTextBox);
        textBgX = (TextIcon) view.findViewById(R.id.textBgX);
        textBgSeekX = (SeekBar) view.findViewById(R.id.textBgSeekX);
        textBgSeekXText = (TextView) view.findViewById(R.id.textBgSeekXText);
        textBgY = (TextIcon) view.findViewById(R.id.textBgY);
        textBgSeekY = (SeekBar) view.findViewById(R.id.textBgSeekY);
        textBgSeekYText = (TextView) view.findViewById(R.id.textBgSeekYText);
        textBgAll = (TextIcon) view.findViewById(R.id.textBgAll);
        textBgSeekAll = (SeekBar) view.findViewById(R.id.textBgSeekAll);
        textBgSeekAllText = (TextView) view.findViewById(R.id.textBgSeekAllText);
        textBgCorner = (TextIcon) view.findViewById(R.id.textBgCorner);
        textBgSeekCorner = (SeekBar) view.findViewById(R.id.textBgSeekCorner);
        textBgSeekCornerText = (TextView) view.findViewById(R.id.textBgSeekCornerText);
        shadowButtonListener = new ShadowButtonListener();
        seekBarChangeListener = new SeekBarChangeListener();
        textBgColor.setOnClickListener(shadowButtonListener);
        textBgX.setOnClickListener(shadowButtonListener);
        textBgY.setOnClickListener(shadowButtonListener);
        textBgAll.setOnClickListener(shadowButtonListener);
        textBgCorner.setOnClickListener(shadowButtonListener);
        textBgSeekX.setOnSeekBarChangeListener(seekBarChangeListener);
        textBgSeekY.setOnSeekBarChangeListener(seekBarChangeListener);
        textBgSeekAll.setOnSeekBarChangeListener(seekBarChangeListener);
        textBgSeekCorner.setOnSeekBarChangeListener(seekBarChangeListener);
        eyeTextBox.setText(Util.Persian(R.string.shadow));
        eyeTextBox.setOnCheckedChangeListener(new CheckedChangeListener() {
            @Override
            public void onCheckedChanged(TextIcon buttonView, boolean isChecked) {
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    MainActivity.mainInstance().SelecetedTextView.setTextBGColorHave(isChecked);
                    TextProperties.getCurrent().setTextBGColorHave(isChecked);
                    if (TextProperties.getCurrent().getTextBGColorHave()) {
                        MainActivity.mainInstance().SelecetedTextView.setTextSizeCrop(TextProperties.getCurrent().getTextSizeCrop());
                        MainActivity.mainInstance().SelecetedTextView.setTextCropX(TextProperties.getCurrent().getTextCropX());
                        MainActivity.mainInstance().SelecetedTextView.setTextCropY(TextProperties.getCurrent().getTextCropY());
                        MainActivity.mainInstance().SelecetedTextView.setTextCropCorner(TextProperties.getCurrent().getTextCropCorner());
                        MainActivity.mainInstance().SelecetedTextView.setTextBGColor(TextProperties.getCurrent().getTextBGColor());
                    } else {
                        MainActivity.mainInstance().SelecetedTextView.setTextBGColor("#00000000");
                        MainActivity.mainInstance().SelecetedTextView.setTextCropX(0);
                        MainActivity.mainInstance().SelecetedTextView.setTextCropY(0);
                        MainActivity.mainInstance().SelecetedTextView.setTextCropCorner(0);
                        MainActivity.mainInstance().SelecetedTextView.setTextSizeCrop(0);
                    }
                }
            }
        });
        if (MainActivity.mainInstance().SelecetedTextView != null) {
            eyeTextBox.setChecked(TextProperties.getCurrent().getTextBGColorHave());
            textBgSeekX.setProgress((int) TextProperties.getCurrent().getTextCropX());
            textBgSeekY.setProgress((int) TextProperties.getCurrent().getTextCropY());
            textBgSeekAll.setProgress((int) TextProperties.getCurrent().getTextSizeCrop());
            textBgSeekCorner.setProgress((int) TextProperties.getCurrent().getTextCropCorner());
        }
    }

    private static class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.textBgSeekX:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        if (TextProperties.getCurrent().getTextBGColorHave()) {
                            if (progress >= Util.getInt(R.integer.textCropMin)) {
                                MainActivity.mainInstance().SelecetedTextView.setTextCropX(progress);
                                TextProperties.getCurrent().setTextCropX(progress);
                                textBgSeekXText.setText(String.valueOf(progress));
                            } else {
                                MainActivity.mainInstance().SelecetedTextView.setTextCropX(Util.getInt(R.integer.textCropMin));
                            }
                        }
                    }
                    break;
                case R.id.textBgSeekY:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        if (TextProperties.getCurrent().getTextBGColorHave()) {
                            if (progress >= Util.getInt(R.integer.textCropMin)) {
                                MainActivity.mainInstance().SelecetedTextView.setTextCropY(progress);
                                TextProperties.getCurrent().setTextCropY(progress);
                                textBgSeekYText.setText(String.valueOf(progress));
                            } else {
                                MainActivity.mainInstance().SelecetedTextView.setTextCropY(Util.getInt(R.integer.textCropMin));
                            }
                        }
                    }
                    break;
                case R.id.textBgSeekCorner:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        if (TextProperties.getCurrent().getTextBGColorHave()) {
                            if (progress >= Util.getInt(R.integer.textCropMin)) {
                                MainActivity.mainInstance().SelecetedTextView.setTextCropCorner(progress);
                                TextProperties.getCurrent().setTextCropCorner(progress);
                                textBgSeekCornerText.setText(String.valueOf(progress));
                            } else {
                                MainActivity.mainInstance().SelecetedTextView.setTextCropCorner(Util.getInt(R.integer.textCropMin));
                            }
                        }
                    }
                    break;
                case R.id.textBgSeekAll:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        if (TextProperties.getCurrent().getTextBGColorHave()) {
                            if (progress >= Util.getInt(R.integer.textCropMin)) {
                                MainActivity.mainInstance().SelecetedTextView.setTextSizeCrop(progress);
                                TextProperties.getCurrent().setTextSizeCrop(progress);
                                textBgSeekAllText.setText(String.valueOf(progress));
                            } else {
                                MainActivity.mainInstance().SelecetedTextView.setTextSizeCrop(Util.getInt(R.integer.textCropMin));
                            }
                        }
                    }
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    private class ShadowButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.textBgY:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Util.NumberDialog(Util.getInt(R.integer.textShadowMin), Util.getInt(R.integer.textShadowMax), (int) TextProperties.getCurrent().getTextCropY(), textBgSeekY.getId(), getActivity());
                    }
                    break;
                case R.id.textBgX:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Util.NumberDialog(Util.getInt(R.integer.textShadowMin), Util.getInt(R.integer.textShadowMax), (int) TextProperties.getCurrent().getTextCropX(), textBgSeekX.getId(), getActivity());
                    }
                    break;
                case R.id.textBgAll:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Util.NumberDialog(Util.getInt(R.integer.textShadowDenMin), Util.getInt(R.integer.textShadowDenMax), (int) TextProperties.getCurrent().getTextSizeCrop(), textBgSeekAll.getId(), getActivity());
                    }
                    break;
                case R.id.textBgCorner:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Util.NumberDialog(Util.getInt(R.integer.textShadowDenMin), Util.getInt(R.integer.textShadowDenMax), (int) TextProperties.getCurrent().getTextCropCorner(), textBgSeekCorner.getId(), getActivity());
                    }
                    break;
                case R.id.textBgColor:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Bundle UserColorBundle = new Bundle();
                        UserColorBundle.putInt("UserColorBundle", Color.parseColor(TextProperties.getCurrent().getTextBGColor().trim()));
                        UserColorBundle.putBoolean("Color_Text_BG_Color", true);
                        UserColorBundle.putString("Name", "Color_Text_BG_Color");
                        FragmentController.on(MainActivity.mainInstance(), getActivity())
                                .fragment(new ColorPallete())
                                .viewGroup(MainActivity.mainInstance().mainLayout)
                                .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                                .Message(R.string.dfChooseTextError)
                                .CheckView(true)
                                .Bundle(UserColorBundle)
                                .BackStackName("Color_Text_BG_Color")
                                .show();
                    }
                    break;
            }
        }
    }
}