package fragments.textEffects;

import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.util.logging.Logger;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.objectHelper.BackGroundHelper;
import fragments.objectHelper.Manimator;
import fragments.objects.TextProperties;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import fragments.views.TextIcon;
import fragments.views.dragLayout.DargInnerViews;
import fragments.views.gregacucnik.EditableSeekBar;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/7/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class SeekDialog extends BaseFragment {
    static SeekDialog CurrentInstance;
    static EditableSeekBar esBar;
    private float MIN_SCALE = 0.5f;
    private float MAX_SCALE = 1.2f;

    public static SeekDialog newInstance() {
        SeekDialog ftext = new SeekDialog();
        Bundle bundle = new Bundle();
        ftext.setArguments(bundle);
        return ftext;
    }

    public static SeekDialog getCurrentInstance() {
        return CurrentInstance;
    }

    public static void setSeekValue(int curr) {
        esBar.setValue(curr);
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
        CurrentInstance = this;
//        View f_seekdialog = inflater.inflate(R.layout.f_seekdialog, group, false);
        View f_seekdialog = DargInnerViews.SeekEditableBar(getContext(), 0/*, getActivity()*/);
        f_seekdialog.setClickable(true);
        f_seekdialog.setEnabled(false);
        Bundle SeekDialogBundle = getArguments();
        final int min = SeekDialogBundle.getInt("UserMin");
        final int max = SeekDialogBundle.getInt("UserMax");
        final int curr = SeekDialogBundle.getInt("UserCurr");
        final String UserSeekBar = SeekDialogBundle.getString("UserSeekType");
        final int UserSeekIcon = SeekDialogBundle.getInt("UserSeekIcon");


        esBar = (EditableSeekBar) f_seekdialog.findViewById(R.id.esBar);

        TextIcon esBtn = (TextIcon) f_seekdialog.findViewById(R.id.esBtn);
        esBtn.setText(UserSeekIcon);
        esBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.NumberesDialog(min, max, esBar.getValue(), esBar.getId(), getActivity());

            }
        });
//        esBar.setAnimateSeekBar(false);
        esBar.setMinValue(min);
        esBar.setMaxValue(max);
        esBar.setValue(curr);
        esBar.setOnEditableSeekBarChangeListener(new EditableSeekBar.OnEditableSeekBarChangeListener() {
            @Override
            public void onEditableSeekBarProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (UserSeekBar.equals("textSize")) {
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        if (progress >= Util.getInt(R.integer.textSizeMin)) {
                            TextProperties.getCurrent().setTextSize(progress);
                            MainActivity.mainInstance().SelecetedTextView.setTextUserSize(progress);
                            Pref.put(ApplicationLoader.appInstance().getString(R.string.SharedPrefronevisLastFS), progress);
                        } else {
                            MainActivity.mainInstance().SelecetedTextView.setTextUserSize(Util.getInt(R.integer.textSizeMin));
                            Pref.put(ApplicationLoader.appInstance().getString(R.string.SharedPrefronevisLastFS), Util.getInt(R.integer.textSizeMin));
                        }
                    }
                }
                if (UserSeekBar.equals("btn_Text_Crop")) {
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        if (progress >= Util.getInt(R.integer.textCropMin)) {
                            MainActivity.mainInstance().SelecetedTextView.setTextSizeCrop(progress);
                            TextProperties.getCurrent().setTextSizeCrop(progress);
                        } else {
                            MainActivity.mainInstance().SelecetedTextView.setTextSizeCrop(Util.getInt(R.integer.textCropMin));
                        }
                    }
                }
                if (UserSeekBar.equals("textOpacity")) {
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
//                        if (progress >= Util.getInt(R.integer.textAlphaMin)) {
                        float f = (float) (seekBar.getProgress()) / (float) (seekBar.getMax());
                        int value = (int) (f * 255);
                        TextProperties.getCurrent().setTextAlpha(value);
                        MainActivity.mainInstance().SelecetedTextView.setTextAlpha(value);
//                            seekopacitytext.setText(FormatHelper.toPersianNumber(String.valueOf(progress)));
//                        } else {
//                            seekopacitytext.setText(FormatHelper.toPersianNumber(String.valueOf(Util.getInt(R.integer.textAlphaMin))));
//                        }
                    }
                }
                if (UserSeekBar.equals("TextGradientAlpha")) {
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
//                        if (progress >= Util.getInt(R.integer.textAlphaMin)) {
                        float f = (float) (seekBar.getProgress()) / (float) (seekBar.getMax());
                        int value = (int) (f * 255);
                        TextProperties.getCurrent().setTextGradientAlpha(value);
                        MainActivity.mainInstance().SelecetedTextView.setTextGradientAlpha(value);
//                            seekopacitytext.setText(FormatHelper.toPersianNumber(String.valueOf(progress)));
//                        } else {
//                            seekopacitytext.setText(FormatHelper.toPersianNumber(String.valueOf(Util.getInt(R.integer.textAlphaMin))));
//                        }
                    }
                }
                if (UserSeekBar.equals("btn_Text_Stroke")) {
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
//                        if (progress >= Util.getInt(R.integer.textAlphaMin)) {
                        float f = (float) (seekBar.getProgress()) / (float) (seekBar.getMax());
                        int value = (int) (f * 255);
                        TextProperties.getCurrent().setTextStrokeWidth(value);
                        MainActivity.mainInstance().SelecetedTextView.setTextStrokeWidth(value);
//                            seekopacitytext.setText(FormatHelper.toPersianNumber(String.valueOf(progress)));
//                        } else {
//                            seekopacitytext.setText(FormatHelper.toPersianNumber(String.valueOf(Util.getInt(R.integer.textAlphaMin))));
//                        }
                    }
                }
                if (UserSeekBar.equals("textLineSpace")) {
                    if (progress >= Util.getInt(R.integer.textLineSpaceMin)) {
                        float fprog = (float) (seekBar.getProgress()) / (float) (seekBar.getMax()) * 2;
                        if (MainActivity.mainInstance().SelecetedTextView != null) {
                            TextProperties.getCurrent().setTextLineSpacing(fprog);
                            MainActivity.mainInstance().SelecetedTextView.setTextLineSpacing(fprog);
//                            seeklinespacingtext.setText(FormatHelper.toPersianNumber(String.valueOf(progress)));
                        }
                    } else {
//                        seeklinespacingtext.setText(FormatHelper.toPersianNumber(String.valueOf(Util.getInt(R.integer.textLineSpaceMin))));
                    }
                }
                if (UserSeekBar.equals("stickerSeekOpacity")) {
                    if (MainActivity.mainInstance().SelectedImage != null) {
                        Manimator.Alhpa(MainActivity.mainInstance().SelectedImage, (float) (seekBar.getProgress()) / (float) (seekBar.getMax()), 0);
                        MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).setImageViewAlpha((float) (seekBar.getProgress()) / (float) (seekBar.getMax()));
                    }
                }
                if (UserSeekBar.equals("stickerSeekSize")) {
                    if (MainActivity.mainInstance().SelectedImage != null) {
                        Manimator.Alhpa(MainActivity.mainInstance().SelectedImage, (float) (seekBar.getProgress()) / (float) (seekBar.getMax()), 0);
                        MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).setImageViewAlpha((float) (seekBar.getProgress()) / (float) (seekBar.getMax()));
                    }
                }
                if (UserSeekBar.equals("bgSeekOpacity")) {
                    MainActivity.mainInstance().backGroundProperties.setBackGroundAlpha((float) (seekBar.getProgress()) / (float) (seekBar.getMax()));
                    Manimator.Alhpa(MainActivity.mainInstance().MainImageBG, MainActivity.mainInstance().backGroundProperties.getBackGroundAlpha(), 0);
                }
                if (UserSeekBar.equals("stickerSeekExpand")) {
                    if (MainActivity.mainInstance().SelectedImage != null) {
                        Matrix matrix = MainActivity.mainInstance().SelectedImage.getImageMatrix();
                        matrix.setScale((seekBar.getProgress() + 10) / 100f, (seekBar.getProgress() + 10) / 100f, 0, 0);
                        MainActivity.mainInstance().SelectedImage.setImageMatrix(matrix);
                        MainActivity.mainInstance().SelectedImage.invalidate();
                        MainActivity.mainInstance().SelectedImage.requestLayout();
                    }
                }
                if (UserSeekBar.equals("bgSeekBlur")) {
                    BackGroundHelper.BlurBackGround(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onEnteredValueTooHigh() {
            }

            @Override
            public void onEnteredValueTooLow() {
            }

            @Override
            public void onEditableSeekBarValueChanged(int value) {
            }
        });
        return f_seekdialog;
    }
}
