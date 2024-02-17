package fragments.views.gregacucnik;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mt.karimi on 15/12/15.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class ESB_EditText extends EditText {
    private OnEditTextListener mListener;

    public ESB_EditText(Context context) {
        super(context);
        init();
    }

    public ESB_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ESB_EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (mListener != null)
                        mListener.onEditTextKeyboardDone();
                    return true;
                }
                return false;
            }
        });
    }

    public void setOnKeyboardDismissedListener(OnEditTextListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (mListener != null)
                mListener.onEditTextKeyboardDismissed();
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    public interface OnEditTextListener {
        void onEditTextKeyboardDismissed();

        void onEditTextKeyboardDone();
    }
}