package fragments.views.gregacucnik;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 13/11/15.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class EditableSeekBar extends RelativeLayout implements SeekBar.OnSeekBarChangeListener, TextWatcher, View.OnFocusChangeListener, ESB_EditText.OnEditTextListener {
    private static final int SEEKBAR_DEFAULT_MAX = 100;
    private static final int SEEKBAR_DEFAULT_MIN = 0;
    private static final int EDITTEXT_DEFAULT_WIDTH = 50;
    private static final int EDITTEXT_DEFAULT_FONT_SIZE = 18;
    private static final int ANIMATION_DEFAULT_DURATION = 300;
    private TextView esbTitle;
    private SeekBar esbSeekBar;
    private ESB_EditText esbEditText;
    private boolean selectOnFocus;
    //    private boolean animateChanges;
    private ValueAnimator seekBarAnimator;
    private int currentValue = 0;
    private int minValue = 0;
    private int maxValue = 100;
    private boolean touching = false;
    private OnEditableSeekBarChangeListener mListener;

    public EditableSeekBar(Context context) {
        super(context);
        InitMe(context, null);
    }

    public EditableSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitMe(context, attrs);
    }

    public void InitMe(Context context, AttributeSet attrs) {
        inflate(getContext(), R.layout.editable_seekbar, this);
        setSaveEnabled(true);
        esbTitle = (TextView) findViewById(R.id.esbTitle);
        esbSeekBar = (SeekBar) findViewById(R.id.esbSeekBar);
        esbEditText = (ESB_EditText) findViewById(R.id.esbEditText);
        float defaultEditTextWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, EDITTEXT_DEFAULT_WIDTH, getResources().getDisplayMetrics());
        int defaultEditTextFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, EDITTEXT_DEFAULT_FONT_SIZE, getResources().getDisplayMetrics());
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EditableSeekBar,
                0, 0);
        try {
            setTitle(a.getString(R.styleable.EditableSeekBar_esbTitle));
            esbTitle.setTextAppearance(getContext(), a.getResourceId(R.styleable.EditableSeekBar_esbTitleAppearance, 0));
            selectOnFocus = a.getBoolean(R.styleable.EditableSeekBar_esbSelectAllOnFocus, true);
//            animateChanges = a.getBoolean(R.styleable.EditableSeekBar_esbAnimateSeekBar, true);
            esbEditText.setSelectAllOnFocus(selectOnFocus);
            esbEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.EditableSeekBar_esbEditTextFontSize, defaultEditTextFontSize));
            int min = a.getInteger(R.styleable.EditableSeekBar_esbMin, SEEKBAR_DEFAULT_MIN);
            int max = a.getInteger(R.styleable.EditableSeekBar_esbMax, SEEKBAR_DEFAULT_MAX);
            setRange(min, max);
            setValue(a.getInteger(R.styleable.EditableSeekBar_esbValue, translateToRealValue(getRange() / 2)));
            setEditTextWidth(a.getDimension(R.styleable.EditableSeekBar_esbEditTextWidth, defaultEditTextWidth));
        } finally {
            a.recycle();
        }
        esbSeekBar.setOnSeekBarChangeListener(this);
        esbEditText.addTextChangedListener(this);
        esbEditText.setOnFocusChangeListener(this);
        esbEditText.setOnKeyboardDismissedListener(this);
        esbSeekBar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });
    }

    private void setEditTextWidth(float width) {
        ViewGroup.LayoutParams params = esbEditText.getLayoutParams();
        params.width = (int) width;
        esbEditText.setLayoutParams(params);
    }

    /**
     * Set callback listener for changes of EditableSeekBar.
     *
     * @param listener OnEditableSeekBarChangeListener
     */
    public void setOnEditableSeekBarChangeListener(OnEditableSeekBarChangeListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currentValue = translateToRealValue(progress);
        if (fromUser) {
            setEditTextValue(currentValue);
            if (selectOnFocus)
                esbEditText.selectAll();
            else
                esbEditText.setSelection(esbEditText.getText().length());
        }
        if (mListener != null)
            mListener.onEditableSeekBarProgressChanged(seekBar, currentValue, fromUser);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mListener != null)
            mListener.onStartTrackingTouch(seekBar);
        touching = true;
        esbEditText.requestFocus();
        if (selectOnFocus)
            esbEditText.selectAll();
        else
            esbEditText.setSelection(esbEditText.getText().length());
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mListener != null)
            mListener.onStopTrackingTouch(seekBar);
        touching = false;
        currentValue = translateToRealValue(seekBar.getProgress());
        if (mListener != null)
            mListener.onEditableSeekBarValueChanged(currentValue);
    }

    @Override
    public void onEditTextKeyboardDismissed() {
        checkValue();
        if (mListener != null)
            mListener.onEditableSeekBarValueChanged(currentValue);
    }

    @Override
    public void onEditTextKeyboardDone() {
        checkValue();
        if (mListener != null)
            mListener.onEditableSeekBarValueChanged(currentValue);
        hideKeyboard();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (touching)
            return;
        if (!s.toString().isEmpty() && isNumber(s.toString())) {
            int value = Integer.parseInt(s.toString());
            if (value > maxValue && currentValue != maxValue) {
                value = maxValue;
                setEditTextValue(value);
                if (selectOnFocus)
                    esbEditText.selectAll();
                else
                    esbEditText.setSelection(esbEditText.getText().length());
                if (mListener != null) {
                    mListener.onEnteredValueTooHigh();
//                    mListener.onEditableSeekBarValueChanged(currentValue);
                }
            }
            if (value < minValue && currentValue != minValue) {
                value = minValue;
                setEditTextValue(value);
                if (selectOnFocus)
                    esbEditText.selectAll();
                else
                    esbEditText.setSelection(esbEditText.getText().length());
                if (mListener != null) {
                    mListener.onEnteredValueTooLow();
//                    mListener.onEditableSeekBarValueChanged(currentValue);
                }
            }
            if (value >= minValue && value <= maxValue && value != currentValue) {
                currentValue = value;
                setSeekBarValue(translateFromRealValue(currentValue));
                if (mListener != null)
                    mListener.onEditableSeekBarValueChanged(currentValue);
            }
        } else {
//            currentValue = minValue;
//            setSeekBarValue(translateFromRealValue(currentValue));
        }
    }

    private void checkValue() {
        setEditTextValue(currentValue);
    }

    private boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v instanceof EditText) {
            if (!hasFocus) {
                boolean sendValueChanged = esbEditText.getText().toString().isEmpty() || !isNumber(esbEditText.getText().toString()) || !isInRange(Integer.parseInt(esbEditText.getText().toString()));
                if (sendValueChanged) {
                    checkValue();
                }
                if (mListener != null && sendValueChanged)
                    mListener.onEditableSeekBarValueChanged(currentValue);
//                    setEditTextValue(translateFromRealValue(currentValue));
            } else {
                if (selectOnFocus)
                    esbEditText.selectAll();
                else
                    esbEditText.setSelection(esbEditText.getText().length());
            }
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(esbEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Set title for EditableSeekBar. Title hidden if empty or null.
     *
     * @param title String
     */
    public void setTitle(String title) {
        if (title != null && !title.isEmpty()) {
            esbTitle.setText(title);
            esbTitle.setVisibility(View.VISIBLE);
        } else
            esbTitle.setVisibility(View.GONE);
    }

    /**
     * Set title color.
     *
     * @param color integer
     */
    public void setTitleColor(int color) {
        esbTitle.setTextColor(color);
    }

    private void setEditTextValue(int value) {
        if (esbEditText != null) {
//            esbEditText.removeTextChangedListener(this);
            esbEditText.setText(Integer.toString(value));
//            esbEditText.addTextChangedListener(this);
        }
    }

    private void setSeekBarValue(int value) {
        if (esbSeekBar != null) {
//            if(animateChanges)
//                animateSeekbar(esbSeekBar.getProgress(), value);
//            else
            esbSeekBar.setProgress(value);
        }
    }

    private int translateFromRealValue(int realValue) {
        return realValue < 0 ? Math.abs(realValue - minValue) : realValue - minValue;
    }

    private int translateToRealValue(int sbValue) {
        return minValue + sbValue;
    }

    /***
     * Set range for EditableSeekBar. Min value must be smaller than max value.
     *
     * @param min integer
     * @param max integer
     */
    public void setRange(int min, int max) {
        if (min > max) {
            minValue = SEEKBAR_DEFAULT_MIN;
            maxValue = SEEKBAR_DEFAULT_MAX;
        } else {
            minValue = min;
            maxValue = max;
        }
        esbSeekBar.setMax(getRange());
        if (!isInRange(currentValue)) {
            if (currentValue < minValue)
                currentValue = minValue;
            if (currentValue > maxValue)
                currentValue = maxValue;
            setValue(currentValue);
        }
    }

    /**
     * Get range of EditableSeekBar.
     *
     * @return integer - Absolute range
     */
    public int getRange() {
        return maxValue < 0 ? Math.abs(maxValue - minValue) : maxValue - minValue;
    }

    private boolean isInRange(int value) {
        if (value < minValue) {
            if (mListener != null)
                mListener.onEnteredValueTooLow();
            return false;
        }
        if (value > maxValue) {
            if (mListener != null)
                mListener.onEnteredValueTooHigh();
            return false;
        }
        return true;
    }

    /**
     * Returns current value of EditableSeekBar.
     *
     * @return integer
     */
    public int getValue() {
        return currentValue;
    }

    /**
     * Programmatically set value for EditableSeekBar. If out of range, appropriate callback sent and value set to closest (min or max).
     *
     * @param value integer
     */
    public void setValue(Integer value) {
        if (value == null)
            return;
        if (!isInRange(value)) {
            if (value < minValue)
                value = minValue;
            if (value > maxValue)
                value = maxValue;
        }
        currentValue = value;
        setEditTextValue(currentValue);
        setSeekBarValue(translateFromRealValue(currentValue));
    }

    /**
     * Set maximum value for EditableSeekBar.
     *
     * @param max integer
     */
    public void setMaxValue(int max) {
        setRange(minValue, max);
    }

    /**
     * Set minimum value for EditableSeekBar.
     *
     * @param min integer
     */
    public void setMinValue(int min) {
        setRange(min, maxValue);
    }

    /**
     * Enable or disable SeekBar animation on value change
     * //     * @param animate true/false
     */
//    public void setAnimateSeekBar(boolean animate){
//        this.animateChanges = animate;
//    }
//    private static class SavedState extends BaseSavedState {
//        int value;
//        boolean focus;
//        boolean animate;
//
//        SavedState(Parcelable superState) {
//            super(superState);
//        }
//
//        private SavedState(Parcel in) {
//            super(in);
//            value = in.readInt();
//            focus = in.readInt() == 1;
//            animate = in.readInt() == 1;
//        }
//
//        @Override
//        public void writeToParcel(Parcel out, int flags) {
//            super.writeToParcel(out, flags);
//            out.writeInt(value);
//            out.writeInt(focus ? 1 : 0);
//            out.writeInt(animate ? 1 : 0);
//        }
//
//        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
//            public SavedState createFromParcel(Parcel in) {
//                return new SavedState(in);
//            }
//
//            public SavedState[] newArray(int size) {
//                return new SavedState[size];
//            }
//        };
//    }
//    @Override
//    public Parcelable onSaveInstanceState() {
//        Parcelable superState = super.onSaveInstanceState();
//        SavedState ss = new SavedState(superState);
//        ss.value = currentValue;
//        ss.focus = selectOnFocus;
//        ss.animate = animateChanges;
//        return ss;
//    }
//
//    @Override
//    public void onRestoreInstanceState(Parcelable state) {
//        SavedState ss = (SavedState) state;
//        super.onRestoreInstanceState(ss.getSuperState());
//        setValue(ss.value);
//        animateChanges = ss.animate;
//        selectOnFocus = ss.focus;
//    }
    @Override
    protected void dispatchSaveInstanceState(SparseArray container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray container) {
        super.dispatchThawSelfOnly(container);
    }

    public interface OnEditableSeekBarChangeListener {
        void onEditableSeekBarProgressChanged(SeekBar seekBar, int progress, boolean fromUser);

        void onStartTrackingTouch(SeekBar seekBar);

        void onStopTrackingTouch(SeekBar seekBar);

        void onEnteredValueTooHigh();

        void onEnteredValueTooLow();

        void onEditableSeekBarValueChanged(int value);
    }
//    private void animateSeekbar(int startValue, int endValue){
//        if(seekBarAnimator != null && seekBarAnimator.isRunning())
//            seekBarAnimator.cancel();
//
//        if(seekBarAnimator == null){
//            seekBarAnimator = ValueAnimator.ofInt(startValue, endValue);
//            seekBarAnimator.setInterpolator(new DecelerateInterpolator());
//            seekBarAnimator.setDuration(ANIMATION_DEFAULT_DURATION);
//
//            seekBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    esbSeekBar.setProgress((Integer) animation.getAnimatedValue());
//                }
//            });
//        }else
//            seekBarAnimator.setIntValues(startValue, endValue);
//
//        seekBarAnimator.start();
//    }
}
