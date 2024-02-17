package fragments.tool;
/**
 * Created by mt.karimi on 5/7/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.text.Editable;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class MyDigitsKeyListener extends NumberKeyListener {
    private static final char[] CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] CHARACTERS_WITH_DECIMAL
            = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
    private boolean decimal = false;
    private EditText e;

    public MyDigitsKeyListener(EditText e) {
        super();
        this.e = e;
    }

    public int getInputType() {
        return InputType.TYPE_CLASS_PHONE;
    }

    @Override
    public void clearMetaKeyState(View view, Editable content, int states) {
        Log.d("clearMetaKeyState", "" + content);
        super.clearMetaKeyState(view, content, states);
    }

    @Override
    protected char[] getAcceptedChars() {
        if (!decimal) {
            return CHARACTERS_WITH_DECIMAL;
        }
        return CHARACTERS;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String currentText = e.getText().toString();
//        if(currentText.contains(".") && source.equals(".")){
//            source = source.toString().replace(".", "");
//            decimal = true;
//        }
        source = source.toString();
        if (source.length() != 0) {
            CharSequence result = super.filter(source, start, end, dest, dstart, dend);
            if (source.equals(".")) {
                decimal = true;
            }
            return result;
        }
        return "";
    }

    @Override
    public boolean backspace(View view, Editable content, int keyCode, KeyEvent event) {
        boolean result = super.backspace(view, content, keyCode, event);
        if (!content.toString().contains(".")) {
            decimal = false;
        }
        return result;
    }
}