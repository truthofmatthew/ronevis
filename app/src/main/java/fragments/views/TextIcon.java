package fragments.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;

import fragments.lisetener.CheckedChangeListener;
import fragments.tool.Util;
import mt.karimi.ronevis.R;

import static mt.karimi.ronevis.R.color.text_secondary_light;

public class TextIcon extends TextView implements Checkable {
    private boolean isOn = true;
    private boolean isRadio = false;
    private Typeface typeface;
    private String TextOn = "aa";
    private String TextOff = "bb";
    private int ColorOn;
    private int ColorOff;

    public TextIcon(Context context) {
        super(context);
        init(context, null);
    }

    public TextIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public int getColorOn() {
        return ColorOn;
    }

    public void setColorOn(int colorOn) {
        ColorOn = colorOn;
    }

    public int getColorOff() {
        return ColorOff;
    }

    public void setColorOff(int colorOff) {
        ColorOff = colorOff;
    }

    public boolean isRadio() {
        return isRadio;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }

    public String getTextOff() {
        return TextOff;
    }

    public void setTextOff(String textOff) {
        TextOff = textOff;
    }

    public String getTextOn() {
        return TextOn;
    }

    public void setTextOn(String textOn) {
        TextOn = textOn;
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(typeface);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        syncDrawableState();
    }

    private void init(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextIcon, 0, 0);
            try {
                isRadio = ta.getBoolean(R.styleable.TextIcon_isradio, false);
                TextOn = ta.getString(R.styleable.TextIcon_TextOn);
                TextOff = ta.getString(R.styleable.TextIcon_TextOff);
                ColorOn = ta.getInteger(R.styleable.TextIcon_ColorOn, R.color.colorAccent);
                ColorOff = ta.getInteger(R.styleable.TextIcon_ColorOff, text_secondary_light);
            } finally {
                ta.recycle();
            }
            syncDrawableState();
            typeface = Util.GetSelfTypeFace(context, Util.getInt(R.integer.IconFontName));
            setTypeface(typeface);
        }
    }

    private void syncDrawableState() {
        if (isRadio) {
            if (!isChecked()) {
                setTextColor(ColorOn);
                setText(TextOn);
            } else {
                setTextColor(ColorOff);
                setText(TextOff);
            }

        } else {
            setTextColor(getTextColors());
            setText(getText());
        }
    }

    @Override
    public boolean isChecked() {
        return isOn;
    }

    @Override
    public void setChecked(boolean checked) {
        if (isOn != checked) {
            isOn = checked;


            if (isRadio) {
//            if (MainActivity.mainInstance().SelecetedTextView != null) {
                syncDrawableState();
                refreshDrawableState();
//            invalidate();
//            requestLayout();
//            }
            }
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, isOn);
            }

        }
    }


    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    @Override
    public void toggle() {
        setChecked(!isOn);
//        isOn = !isOn;
//        syncDrawableState();
//        refreshDrawableState();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.max(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }

    private CheckedChangeListener mOnCheckedChangeListener;

    public void setOnCheckedChangeListener(CheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }
}
