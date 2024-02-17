package fragments.textEffects;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.logging.Logger;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.lisetener.CheckedChangeListener;
import fragments.tool.FragmentController;
import fragments.tool.Util;
import fragments.views.SwitchButton;
import fragments.views.TextButton;
import fragments.views.TextIcon;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/7/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class Shadow extends BaseFragment {
    public static SeekBar seekshadowx, seekshadowy, seekshadowdensity;
//    public static SwitchButton SwitchShadow;
    public static TextIcon eyeShadow;
    private static TextView seekshadowxtext;
    private static TextView seekshadowytext;
    private static TextView seekshadowdensitytext;
    ShadowButtonListener shadowButtonListener;
    SeekBarChangeListener seekBarChangeListener;

    public static TextBg newInstance() {
        TextBg ftext = new TextBg();
        Bundle bundle = new Bundle();
        ftext.setArguments(bundle);
        return ftext;
    }

    @Override
    public String myNameIs() {
        return "Shadow";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        seekshadowx = null;
        seekshadowy = null;
        seekshadowdensity = null;
//        SwitchShadow = null;
        seekshadowxtext = null;
        seekshadowytext = null;
        seekshadowdensitytext = null;
        shadowButtonListener = null;
        seekBarChangeListener = null;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        View f_shadow = inflater.inflate(R.layout.f_shadow, group, false);
        f_shadow.setClickable(true);
        f_shadow.setEnabled(false);
        shadowButtonListener = new ShadowButtonListener();
        seekBarChangeListener = new SeekBarChangeListener();
        TextIcon shadowmagiccolor = (TextIcon) f_shadow.findViewById(R.id.shadowmagiccolor);
        TextIcon btnshadowX = (TextIcon) f_shadow.findViewById(R.id.btnshadowX);
        TextIcon btnshadowY = (TextIcon) f_shadow.findViewById(R.id.btnshadowY);
        TextIcon btnshadowD = (TextIcon) f_shadow.findViewById(R.id.btnshadowD);

       eyeShadow = (TextIcon) f_shadow.findViewById(R.id.eyeTextShadow);


        eyeShadow.setOnCheckedChangeListener(new CheckedChangeListener() {
            @Override
            public void onCheckedChanged(TextIcon buttonView, boolean isChecked) {
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextShadow(isChecked);
                    MainActivity.mainInstance().SelecetedTextView.setTextShadow(isChecked);
                    if (MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadow()) {
                        MainActivity.mainInstance().SelecetedTextView.setTextShadowD(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowD());
                        MainActivity.mainInstance().SelecetedTextView.setTextShadowX(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowX());
                        MainActivity.mainInstance().SelecetedTextView.setTextShadowY(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY());
                        MainActivity.mainInstance().SelecetedTextView.setTextShadowColor(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowColor());
                        seekshadowx.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowX());
                        seekshadowy.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY());
                        seekshadowdensity.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowD());
                        MainActivity.mainInstance().SelecetedTextView.setTextPadding(new Rect(0, 0, MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowX(), MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY()));
                    } else {
                        MainActivity.mainInstance().SelecetedTextView.setTextShadowD(0);
                        MainActivity.mainInstance().SelecetedTextView.setTextShadowX(0);
                        MainActivity.mainInstance().SelecetedTextView.setTextShadowY(0);
                        MainActivity.mainInstance().SelecetedTextView.setTextShadowColor("#00000000");
                        // usershadowcolor.setBackgroundColor(Color.parseColor("#000000"));
                        MainActivity.mainInstance().SelecetedTextView.setTextPadding(new Rect(0, 0, 0, 0));
                    }

                }
            }
        });

        shadowmagiccolor.setOnClickListener(shadowButtonListener);
        btnshadowX.setOnClickListener(shadowButtonListener);
        btnshadowY.setOnClickListener(shadowButtonListener);
        btnshadowD.setOnClickListener(shadowButtonListener);
        seekshadowxtext = (TextView) f_shadow.findViewById(R.id.seekshadowxtext);
        seekshadowytext = (TextView) f_shadow.findViewById(R.id.seekshadowytext);
        seekshadowdensitytext = (TextView) f_shadow.findViewById(R.id.seekshadowdensitytext);
        seekshadowx = (SeekBar) f_shadow.findViewById(R.id.seekshadowx);
        seekshadowy = (SeekBar) f_shadow.findViewById(R.id.seekshadowy);
        seekshadowdensity = (SeekBar) f_shadow.findViewById(R.id.seekshadowdensity);
        seekshadowx.setOnSeekBarChangeListener(seekBarChangeListener);
        seekshadowy.setOnSeekBarChangeListener(seekBarChangeListener);
        seekshadowdensity.setOnSeekBarChangeListener(seekBarChangeListener);
//        SwitchShadow = (SwitchButton) f_shadow.findViewById(R.id.SwitchBtnTitle);
//        SwitchShadow.setText(Util.Persian(R.string.shadow));
//        SwitchShadow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        });
        if (MainActivity.mainInstance().SelecetedTextView != null) {
            eyeShadow.setChecked(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadow());
            seekshadowx.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowX());
            seekshadowy.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY());
            seekshadowdensity.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowD());
        }
        return f_shadow;
    }

    private static class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekshadowx:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        if (MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadow()) {
                            MainActivity.mainInstance().SelecetedTextView.setTextShadowX(progress);
                            MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextShadowX(progress);
                            seekshadowxtext.setText(String.valueOf(progress));
                            // MainActivity.mainInstance().TextViewMap.get(i).TextShadowColor = String.valueOf(fhexColorShadow);
                            MainActivity.mainInstance().SelecetedTextView.setTextPadding(new Rect(0, 0, progress, MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY()));
                            MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextPadding(new Rect(0, 0, MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowX(), MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY()));
                        }
                    }
                    break;
                case R.id.seekshadowy:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        if (MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadow()) {
                            MainActivity.mainInstance().SelecetedTextView.setTextShadowY(progress);
                            MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextShadowY(progress);
                            seekshadowytext.setText(String.valueOf(progress));
                            MainActivity.mainInstance().SelecetedTextView.setTextPadding(new Rect(0, 0, MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowX(), MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY()));
                            MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextPadding(new Rect(0, 0, MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowX(), MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY()));
                            // MainActivity.mainInstance().TextViewMap.get(i).TextShadowColor = String.valueOf(fhexColorShadow);
                        }
                    }
                    break;
                case R.id.seekshadowdensity:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        if (MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadow()) {
                            if (progress >= 1) {
                                MainActivity.mainInstance().SelecetedTextView.setTextShadowD(progress);
                                MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextShadowD(progress);
                                seekshadowdensitytext.setText(String.valueOf(progress));
                            } else {
                                seekshadowdensitytext.setText(String.valueOf(1));
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
                case R.id.btnshadowY:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Util.NumberDialog(Util.getInt(R.integer.textShadowMin), Util.getInt(R.integer.textShadowMax), MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY(), seekshadowy.getId(), getActivity());
                    }
                    break;
                case R.id.btnshadowX:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Util.NumberDialog(Util.getInt(R.integer.textShadowMin), Util.getInt(R.integer.textShadowMax), MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowX(), seekshadowx.getId(), getActivity());
                    }
                    break;
                case R.id.btnshadowD:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Util.NumberDialog(Util.getInt(R.integer.textShadowDenMin), Util.getInt(R.integer.textShadowDenMax), MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowD(), seekshadowdensity.getId(), getActivity());
                    }
                    break;
                case R.id.shadowmagiccolor:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Bundle UserColorBundle = new Bundle();
                        UserColorBundle.putInt("UserColorBundle", Color.parseColor(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowColor().trim()));
                        UserColorBundle.putBoolean("UserColorShadow", true);
                        UserColorBundle.putString("Name", "UserColorShadow");
                        FragmentController.on(MainActivity.mainInstance(), getActivity())
                                .fragment(new ColorPallete())
                                .viewGroup(MainActivity.mainInstance().mainLayout)
                                .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                                .Message(R.string.dfChooseTextError)
                                .CheckView(true)
                                .Bundle(UserColorBundle)
                                .BackStackName("UserColorShadow")
                                .show();
                    }
                    break;
            }
        }
    }
//     public LinearLayout InitViews(){
//
//         LinearLayout shadow_First_Linear = new LinearLayout(getContext());
//         shadow_First_Linear.setBackgroundResource(R.drawable.bottom_divider_background);
//         shadow_First_Linear.setClickable(false);
//         shadow_First_Linear.setFocusable(false);
//         shadow_First_Linear.setFocusableInTouchMode(false);
//         shadow_First_Linear.setOrientation(LinearLayout.VERTICAL);
//
//         shadow_First_Linear.setLayoutParams(LayoutHelper.createLinear(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
//
//         LinearLayout shadow_Tools_Linear = new LinearLayout(getContext());
//         shadow_Tools_Linear.setOrientation(LinearLayout.HORIZONTAL);
//         shadow_Tools_Linear.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
//         shadow_First_Linear.addView(shadow_Tools_Linear,LayoutHelper.createLinear(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,4,4,4,0));
//
//         fragments.views.TextIcon shadowmagiccolor = new fragments.views.TextIcon(getContext());
//         shadowmagiccolor.setId(R.id.shadowmagiccolor);
//         shadowmagiccolor.setBackgroundResource(0);
//         shadowmagiccolor.setGravity(Gravity.CENTER);
//         shadowmagiccolor.setTextColor(getResources().getColor(R.color.text_secondary_light));
//         shadowmagiccolor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
//         shadowmagiccolor.setButtonDrawable(null);
//         shadowmagiccolor.setText(R.string.Icon_palette);
//         shadow_Tools_Linear.addView(shadowmagiccolor,LayoutHelper.createLinear(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER,4,4,4,0));
//
//
//         SwitchButton SwitchBtnTitle = new SwitchButton(getContext(),null,R.style.SwitchButtonStyle);
//         SwitchBtnTitle.setId(R.id.SwitchBtnTitle);
//         SwitchBtnTitle.setGravity(Gravity.CENTER);
//         SwitchBtnTitle.setClickable(true);
//         SwitchBtnTitle.setFocusable(true);
//         SwitchBtnTitle.setBackDrawable(Util.getDrawable(R.drawable.miui_back_drawable));
//         SwitchBtnTitle.setBackMeasureRatio(2.0f);
//         shadow_Tools_Linear.addView(SwitchBtnTitle,LayoutHelper.createLinear(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT ,4,4,4,0));
//
//
//         LinearLayout Shadow_X_Linear = new LinearLayout(getContext());
//         Shadow_X_Linear.setOrientation(LinearLayout.HORIZONTAL);
//         Shadow_X_Linear.setWeightSum(7.0f);
//
//         fragments.views.TextIcon btnshadowX = new fragments.views.TextIcon(getContext());
//         btnshadowX.setId(R.id.btnshadowX);
//         btnshadowX.setBackgroundResource(0);
//         btnshadowX.setGravity(Gravity.CENTER);
//         btnshadowX.setTextColor(getResources().getColor(R.color.text_secondary_light));
//         btnshadowX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
//         btnshadowX.setButtonDrawable(null);
//         btnshadowX.setText(R.string.Icon_arrow_right);
//         Shadow_X_Linear.addView(btnshadowX,LayoutHelper.createLinear(0,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f,Gravity.CENTER,0,0,0,0));
//
//         PhotoEditorSeekBar seekshadowx = new PhotoEditorSeekBar(getContext());
//         seekshadowx.setId(R.id.seekshadowx);
//         seekshadowx.setMinMax(-100, 100);
//         seekshadowx.setProgress(0, false);
//         Shadow_X_Linear.addView(seekshadowx,LayoutHelper.createLinear(0,LinearLayout.LayoutParams.MATCH_PARENT,5.0f,Gravity.CENTER,4,0,4,0));
//
//         TextView seekshadowxtext = new TextView(getContext());
//         seekshadowxtext.setId(R.id.seekshadowxtext);
//         seekshadowxtext.setBackgroundResource(0);
//         seekshadowxtext.setGravity(Gravity.CENTER);
//         seekshadowxtext.setTextColor(getResources().getColor(R.color.text_secondary_light));
//         seekshadowxtext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//         seekshadowxtext.setTypeface(Typefaces.getTypeface(getContext(), "self/fa_IRANSans_4.ttf"));
//         Shadow_X_Linear.addView(seekshadowxtext,LayoutHelper.createLinear(0,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f,Gravity.CENTER,0,0,0,0));
//
//         shadow_First_Linear.addView(Shadow_X_Linear, LayoutHelper.createLinear(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT ,Gravity.CENTER,0,4,0,4));
//
//
//
//
//         LinearLayout Shadow_Y_Linear = new LinearLayout(getContext());
//         Shadow_Y_Linear.setOrientation(LinearLayout.HORIZONTAL);
//         Shadow_Y_Linear.setWeightSum(7.0f);
//         fragments.views.TextIcon btnshadowY = new fragments.views.TextIcon(getContext());
//         btnshadowY.setId(R.id.btnshadowY);
//         btnshadowY.setBackgroundResource(0);
//         btnshadowY.setGravity(Gravity.CENTER);
//         btnshadowY.setTextColor(getResources().getColor(R.color.text_secondary_light));
//         btnshadowY.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
//         btnshadowY.setButtonDrawable(null);
//         btnshadowY.setText(R.string.Icon_arrow_down);
//         Shadow_Y_Linear.addView(btnshadowY,LayoutHelper.createLinear(0,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f,Gravity.CENTER,0,0,0,0));
//
//         PhotoEditorSeekBar seekshadowy = new PhotoEditorSeekBar(getContext());
//         seekshadowy.setId(R.id.seekshadowy);
//         seekshadowy.setMinMax(-100, 100);
//         seekshadowy.setProgress(0, false);
//         Shadow_Y_Linear.addView(seekshadowy,LayoutHelper.createLinear(0,LinearLayout.LayoutParams.MATCH_PARENT,5.0f,Gravity.CENTER,4,0,4,0));
//
//         TextView seekshadowytext = new TextView(getContext());
//         seekshadowytext.setId(R.id.seekshadowytext);
//         seekshadowytext.setBackgroundResource(0);
//         seekshadowytext.setGravity(Gravity.CENTER);
//         seekshadowytext.setTextColor(getResources().getColor(R.color.text_secondary_light));
//         seekshadowytext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//         seekshadowytext.setTypeface(Typefaces.getTypeface(getContext(), "self/fa_IRANSans_4.ttf"));
//         Shadow_Y_Linear.addView(seekshadowytext,LayoutHelper.createLinear(0,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f,Gravity.CENTER,0,0,0,0));
//
//         shadow_First_Linear.addView(Shadow_Y_Linear, LayoutHelper.createLinear(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT ,Gravity.CENTER,0,4,0,4));
//
//
//
//         LinearLayout Shadow_D_Linear = new LinearLayout(getContext());
//         Shadow_D_Linear.setOrientation(LinearLayout.HORIZONTAL);
//         Shadow_D_Linear.setWeightSum(7.0f);
//         fragments.views.TextIcon btnshadowD = new fragments.views.TextIcon(getContext());
//         btnshadowD.setId(R.id.btnshadowD);
//         btnshadowD.setBackgroundResource(0);
//         btnshadowD.setGravity(Gravity.CENTER);
//         btnshadowD.setTextColor(getResources().getColor(R.color.text_secondary_light));
//         btnshadowD.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
//         btnshadowD.setButtonDrawable(null);
//         btnshadowD.setText(R.string.Icon_blur);
//         Shadow_D_Linear.addView(btnshadowD,LayoutHelper.createLinear(0,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f,Gravity.CENTER,0,0,0,0));
//
//         PhotoEditorSeekBar seekshadowd = new PhotoEditorSeekBar(getContext());
//         seekshadowd.setId(R.id.seekshadowdensity);
//         seekshadowd.setMinMax(0, 100);
//         seekshadowd.setProgress(0, false);
//         Shadow_D_Linear.addView(seekshadowd,LayoutHelper.createLinear(0,LinearLayout.LayoutParams.MATCH_PARENT,5.0f,Gravity.CENTER,4,0,4,0));
//
//         TextView seekshadowdtext = new TextView(getContext());
//         seekshadowdtext.setId(R.id.seekshadowdensitytext);
//         seekshadowdtext.setBackgroundResource(0);
//         seekshadowdtext.setGravity(Gravity.CENTER);
//         seekshadowdtext.setTextColor(getResources().getColor(R.color.text_secondary_light));
//         seekshadowdtext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//         seekshadowdtext.setTypeface(Typefaces.getTypeface(getContext(), "self/fa_IRANSans_4.ttf"));
//         Shadow_D_Linear.addView(seekshadowdtext,LayoutHelper.createLinear(0,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f,Gravity.CENTER,0,0,0,0));
//
//         shadow_First_Linear.addView(Shadow_D_Linear, LayoutHelper.createLinear(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT ,Gravity.CENTER,0,4,0,4));
//
//return shadow_First_Linear;
//     }
}