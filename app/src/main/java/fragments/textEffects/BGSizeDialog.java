package fragments.textEffects;

import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.lisetener.CheckedChangeListener;
import fragments.lisetener.SizeChangeListenerUtil;
import fragments.objectHelper.BackGroundHelper;
import fragments.objectHelper.LayoutHelper;
import fragments.tool.InputFilterMinMax;
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
public class BGSizeDialog extends BaseFragment {
    static final BigDecimal golden_ratio = new BigDecimal("1.618033988749894848204586834365638117720309179805762862135448622705260462818902449707207204189391138");
    public static TextIcon eyeBGSize;
    public static EditText FrameXSizeFixed, FrameYSizeFixed;
    static boolean inhibit_spinner = true;
    private static Spinner OpenProjectSpinner;
    private static Point ImageBitmapSize = new Point(0, 0);

    public static BGSizeDialog newInstance() {
        BGSizeDialog ftext = new BGSizeDialog();
        Bundle bundle = new Bundle();
        ftext.setArguments(bundle);
        return ftext;
    }

    @Override
    public String myNameIs() {
        return "SeekDialog";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        View f_bgsize = inflater.inflate(R.layout.f_bgsize, group, false);
        f_bgsize.setClickable(true);
        f_bgsize.setEnabled(false);
        Bundle SeekDialogBundle = getArguments();
        final int UserWidth = SeekDialogBundle.getInt("UserWidth");
        final int UserHeight = SeekDialogBundle.getInt("UserHeight");
        final boolean UserFixed = SeekDialogBundle.getBoolean("UserFixed");
        BackgroundTextChangeListener backgroundTextChangeListener = new BackgroundTextChangeListener();
        FrameXSizeFixed = (EditText) f_bgsize.findViewById(R.id.FrameXSizeFixed);
        FrameYSizeFixed = (EditText) f_bgsize.findViewById(R.id.FrameYSizeFixed);
        eyeBGSize = (TextIcon) f_bgsize.findViewById(R.id.eyeBGSize);
        eyeBGSize.setOnCheckedChangeListener(new CheckedChangeListener() {
            @Override
            public void onCheckedChanged(TextIcon buttonView, boolean isChecked) {
                if (TextUtils.isEmpty(FrameXSizeFixed.getText())) {
                    FrameXSizeFixed.setText("0");
                }
                if (TextUtils.isEmpty(FrameYSizeFixed.getText())) {
                    FrameYSizeFixed.setText("0");
                }
                MainActivity.mainInstance().backGroundProperties.setBackGroundSizeFixed(isChecked);
                if (eyeBGSize.isChecked()) {
                    RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
                    LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams, Integer.parseInt(FrameXSizeFixed.getText().toString()), Integer.parseInt(FrameYSizeFixed.getText().toString()));
                    MainActivity.mainInstance().backGroundProperties.setBackGroundSize(new Point(Integer.parseInt(FrameXSizeFixed.getText().toString()), Integer.parseInt(FrameYSizeFixed.getText().toString())));
                } else {
                    MainActivity.mainInstance().runOnUiThread(new Runnable() {
                        public void run() {
                            BackGroundHelper.SetBackGroundRefresh();
//                            ImageBitmapSize = Ion.with(MainActivity.mainInstance().MainImageBG).getBitmapInfo().originalSize;
//
//                            RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
//                            Beforeparams.width = ImageBitmapSize.x ;
//                            Beforeparams.height = ImageBitmapSize.y ;
//                            MainActivity.mainInstance()._exportroot.setLayoutParams(Beforeparams);
//                            MainActivity.mainInstance()._exportroot.invalidate();
//                            MainActivity.mainInstance()._exportroot.requestLayout();
                        }
                    });
                }
            }
        });
        FrameXSizeFixed.setFilters(new InputFilter[]{new InputFilterMinMax("0", String.valueOf(Util.getInt(R.integer.ImageFixedMaxSize)))});
        FrameYSizeFixed.setFilters(new InputFilter[]{new InputFilterMinMax("0", String.valueOf(Util.getInt(R.integer.ImageFixedMaxSize)))});
        FrameXSizeFixed.addTextChangedListener(backgroundTextChangeListener);
        FrameYSizeFixed.addTextChangedListener(backgroundTextChangeListener);
        if (MainActivity.mainInstance().backGroundProperties.getBackGroundSizeFixed()) {
            FrameXSizeFixed.setText(MainActivity.mainInstance().backGroundProperties.getBackGroundSize().x + "");
            FrameYSizeFixed.setText(MainActivity.mainInstance().backGroundProperties.getBackGroundSize().y + "");
//            Logger.d("x2 " + MainActivity.mainInstance().backGroundProperties.getBackGroundSize().x);
//            Logger.d("y2 " + MainActivity.mainInstance().backGroundProperties.getBackGroundSize().y);
            eyeBGSize.setChecked(MainActivity.mainInstance().backGroundProperties.getBackGroundSizeFixed());
        } else {
            FrameXSizeFixed.setText(SizeChangeListenerUtil.getxNew() + "");
            FrameYSizeFixed.setText(SizeChangeListenerUtil.getyNew() + "");
        }
        return f_bgsize;
    }

    private static class FileSpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//            if (inhibit_spinner) {
//                inhibit_spinner = false;
//            } else {
//
            switch (pos) {
                case 0:
                    FrameXSizeFixed.setText("512");
                    FrameYSizeFixed.setText("512");
                    eyeBGSize.setChecked(true);
                    break;
                case 1:
                    BigDecimal bg1;
                    bg1 = new BigDecimal(String.valueOf(MainActivity.mainInstance().appWH[0]));
                    int i = bg1.divide(golden_ratio, 0, RoundingMode.CEILING).intValue();
//                    String str = " " +i;
//
//                    System.out.println( str );
                    FrameXSizeFixed.setText(MainActivity.mainInstance().appWH[0] + "");
                    FrameYSizeFixed.setText("" + i);
//                    FrameXSizeFixed.setText("888");
                    eyeBGSize.setChecked(true);
                    break;
            }
//            }
        }

        public void onNothingSelected(AdapterView parent) {
        }
    }

    private static class BackgroundTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().isEmpty()) {
                if (eyeBGSize.isChecked()) {
                    RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
                    LayoutHelper.LayoutParamSetter(MainActivity.mainInstance()._exportroot, Beforeparams, Integer.parseInt(FrameXSizeFixed.getText().toString()), Integer.parseInt(FrameYSizeFixed.getText().toString()));
                    MainActivity.mainInstance().backGroundProperties.setBackGroundSize(new Point(Integer.parseInt(FrameXSizeFixed.getText().toString()), Integer.parseInt(FrameYSizeFixed.getText().toString())));
                }
            }
            if (TextUtils.isEmpty(FrameXSizeFixed.getText())) {
                FrameXSizeFixed.setText("0");
            }
            if (TextUtils.isEmpty(FrameYSizeFixed.getText())) {
                FrameYSizeFixed.setText("0");
            }
        }
    }





    /*FragmentHelper.removeAllFragments();    default sizeeee
    if (BaseActivityPermission.RequestAll(getActivity())) {
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathFolder), false);
        ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisExportPathFolder), false);
        mtAlertDialog.on(getActivity())
                .with()
                .gravity(Gravity.BOTTOM)
                .title(R.string.dbgsTitle)
                .layout(R.layout.dialog_open)
//                    .icon(Util.getDrawableIcon(R.string.Icon_check_circle, R.color.colorGreen))
                .cancelable(true)
                .when(R.string.dbgsNegativeButton, new mtAlertDialog.Negative() {
                    @Override
                    public void clicked(DialogInterface dialog) {
//                                        eyeBGSize.setChecked(false);
                        dialog.dismiss();
                    }
                })
                .when(R.string.dbgsPositiveButton, new mtAlertDialog.Positive() {
                    @Override
                    public void clicked(DialogInterface dialog) throws IOException {

                        dialog.dismiss();
                    }
                })
                .show(new mtAlertDialog.View() {
                    @Override
                    public void prepare(View view) {
                        OpenProjectSpinner = (Spinner) view.findViewById(R.id.OpenProjectSpinner);
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.BGDefaultSize, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        OpenProjectSpinner.setAdapter(adapter);
//                                        OpenProjectSpinner.setOnItemSelectedListener(new FileSpinnerSelectedListener());
                    }
                });
    }*/
}
