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
public class Stroke extends BaseFragment {
    public static SeekBar stroke_seek_weight;
    public static TextIcon eyeTextStroke;
    private static TextView stroke_seek_weight_val;
    StrokeButtonListener strokeButtonListener;
    SeekBarChangeListener seekBarChangeListener;

    public static Stroke newInstance() {
        Stroke stroke = new Stroke();
        Bundle bundle = new Bundle();
        stroke.setArguments(bundle);
        return stroke;
    }

    @Override
    public String myNameIs() {
        return "Stroke";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stroke_seek_weight = null;
        eyeTextStroke = null;
        stroke_seek_weight_val = null;
        strokeButtonListener = null;
        seekBarChangeListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        View f_stroke = inflater.inflate(R.layout.f_stroke, group, false);
        f_stroke.setClickable(true);
        f_stroke.setEnabled(false);
        strokeButtonListener = new StrokeButtonListener();
        seekBarChangeListener = new SeekBarChangeListener();
        TextIcon stroke_btn_color = (TextIcon) f_stroke.findViewById(R.id.stroke_btn_color);
        TextIcon stroke_btn_weight = (TextIcon) f_stroke.findViewById(R.id.stroke_btn_weight);
        stroke_btn_color.setOnClickListener(strokeButtonListener);
        stroke_btn_weight.setOnClickListener(strokeButtonListener);
        stroke_seek_weight_val = (TextView) f_stroke.findViewById(R.id.stroke_seek_weight_val);
        stroke_seek_weight = (SeekBar) f_stroke.findViewById(R.id.stroke_seek_weight);
        stroke_seek_weight.setOnSeekBarChangeListener(seekBarChangeListener);
        eyeTextStroke = (TextIcon) f_stroke.findViewById(R.id.eyeTextStroke);
        eyeTextStroke.setText(Util.Persian(R.string.btnStroke));
        eyeTextStroke.setOnCheckedChangeListener(new CheckedChangeListener() {
            @Override
            public void onCheckedChanged(TextIcon buttonView, boolean isChecked) {
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    TextProperties.getCurrent().setTextStroke(isChecked);
                    MainActivity.mainInstance().SelecetedTextView.setTextStroke(isChecked);
                    if (TextProperties.getCurrent().getTextStroke()) {
                        TextProperties.getCurrent().setTextStrokeWidth(TextProperties.getCurrent().getTextStrokeWidth());
                        MainActivity.mainInstance().SelecetedTextView.setTextStrokeWidth(TextProperties.getCurrent().getTextStrokeWidth());
                        stroke_seek_weight_val.setText(String.valueOf(TextProperties.getCurrent().getTextStrokeWidth()));
                    } else {
//                      TextProperties.getCurrent().setTextStrokeWidth(0);
                        MainActivity.mainInstance().SelecetedTextView.setTextStrokeWidth(0);
                        stroke_seek_weight_val.setText(String.valueOf(0));
                    }
                }
            }
        });
        if (MainActivity.mainInstance().SelecetedTextView != null) {
            eyeTextStroke.setChecked(TextProperties.getCurrent().getTextStroke());
            stroke_seek_weight.setProgress((int) TextProperties.getCurrent().getTextStrokeWidth());
        }
        return f_stroke;
    }

    private static class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.stroke_seek_weight:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        if (TextProperties.getCurrent() != null && TextProperties.getCurrent().getTextStroke()) {
                            TextProperties.getCurrent().setTextStrokeWidth(progress);
                            MainActivity.mainInstance().SelecetedTextView.setTextStrokeWidth(progress);
                            stroke_seek_weight_val.setText(String.valueOf(progress));
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

    private class StrokeButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.stroke_btn_weight:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Util.NumberDialog(Util.getInt(R.integer.textShadowMin), Util.getInt(R.integer.textShadowMax), (int) TextProperties.getCurrent().getTextStrokeWidth(), stroke_seek_weight.getId(), getActivity());
                    }
                    break;
                case R.id.stroke_btn_color:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Bundle UserColorBundle = new Bundle();
                        UserColorBundle.putInt("UserColorBundle", Color.parseColor(TextProperties.getCurrent().getTextStrokeColor().trim()));
                        UserColorBundle.putBoolean("UserStrokeColor", true);
                        FragmentController.on(MainActivity.mainInstance(), getActivity())
                                .fragment(new ColorPallete())
                                .viewGroup(MainActivity.mainInstance().mainLayout)
                                .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                                .Message(R.string.dfChooseTextError)
                                .CheckView(true)
                                .Bundle(UserColorBundle)
                                .BackStackName("UserStrokeColor")
                                .show();
                    }
                    break;
            }
        }
    }
}