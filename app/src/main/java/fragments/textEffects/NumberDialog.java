package fragments.textEffects;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.tool.InputFilterMinMax;
import fragments.views.TextIcon;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/7/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class NumberDialog extends BaseFragment {
    private EditText NEdit;

    public static NumberDialog newInstance() {
        NumberDialog ftext = new NumberDialog();
        Bundle bundle = new Bundle();
        ftext.setArguments(bundle);
        return ftext;
    }

    @Override
    public String myNameIs() {
        return "NumberDialog";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NEdit = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        View f_numberdialog = inflater.inflate(R.layout.f_numberdialog, group, false);
        f_numberdialog.setClickable(true);
        f_numberdialog.setEnabled(false);
        Bundle NumberDialogBundle = getArguments();
        final int min = NumberDialogBundle.getInt("UserMin");
        final int max = NumberDialogBundle.getInt("UserMax");
        final int curr = NumberDialogBundle.getInt("UserCurr");
        final int UserSeekBar = NumberDialogBundle.getInt("UserSeekBar");
        final SeekBar UserSeek = (SeekBar) MainActivity.mainInstance().findViewById(UserSeekBar);
//        UserSeek instanceof SeekBar ? ((SeekBar) UserSeek) : ((SeekBar) UserSeek)
        TextIcon NNegative = (TextIcon) f_numberdialog.findViewById(R.id.NNegative);
        TextIcon Nplus = (TextIcon) f_numberdialog.findViewById(R.id.Nplus);
        NEdit = (EditText) f_numberdialog.findViewById(R.id.NEdit);
        NEdit.setText(String.valueOf(curr));
        NEdit.setFilters(new InputFilter[]{new InputFilterMinMax(min >= 10 ? "0" : String.valueOf(min), max > -10 ? String.valueOf(max) : "0")});
        NEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    if (Integer.parseInt(NEdit.getText().toString()) >= max) {
                        if (UserSeek != null) {
                            UserSeek.setProgress(max);
                        }
                    } else if (Integer.parseInt(NEdit.getText().toString()) <= min) {
                        if (UserSeek != null) {
                            UserSeek.setProgress(min);
                        }
                    } else {
                        if (UserSeek != null) {
                            UserSeek.setProgress(Integer.parseInt(s.toString()));
                        }
                    }
                }
            }
        });
        Nplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NEdit.getText().toString().isEmpty()) {
                    if (Integer.parseInt(NEdit.getText().toString()) < max) {
                        NEdit.setText(String.valueOf(Integer.parseInt(NEdit.getText().toString()) + 1));
                    }
                }
            }
        });
        NNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NEdit.getText().toString().isEmpty()) {
                    if (Integer.parseInt(NEdit.getText().toString()) > min) {
                        NEdit.setText(String.valueOf(Integer.parseInt(NEdit.getText().toString()) - 1));
                    }
                }
            }
        });
        return f_numberdialog;
    }
}
