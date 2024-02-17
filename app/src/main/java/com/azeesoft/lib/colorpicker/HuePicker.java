package com.azeesoft.lib.colorpicker;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;

public class HuePicker extends OrientedSeekBar {
    private static final int MIN_SIZE_DIP = 200;
    private boolean canUpdateHexVal = true;
    private OnHuePickedListener onHuePickedListener;

    public HuePicker(Context context) {
        super(context);
        init(context);
    }

    public HuePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        int minSizePx = (int) Stools.dipToPixels(context, MIN_SIZE_DIP);
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                HuePicker.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (orientation == ORIENTATION_HORIZONTAL)
                    setProgressDrawable(new BitmapDrawable(BitmapsGenerator.getHueBitmap(getMeasuredWidth(), getMeasuredHeight())));
                else
                    setProgressDrawable(new BitmapDrawable(BitmapsGenerator.getHueBitmap(getMeasuredHeight(), getMeasuredWidth())));
            }
        });
        setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setHue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setHue(float hue) {
        if (onHuePickedListener != null)
            onHuePickedListener.onPicked(hue);
    }

    public void setOnHuePickedListener(OnHuePickedListener onHuePickedListener) {
        this.onHuePickedListener = onHuePickedListener;
    }

    public boolean isCanUpdateHexVal() {
        return canUpdateHexVal;
    }

    public void setCanUpdateHexVal(boolean canUpdateHexVal) {
        this.canUpdateHexVal = canUpdateHexVal;
    }

    public interface OnHuePickedListener {
        void onPicked(float hue);
    }
}
