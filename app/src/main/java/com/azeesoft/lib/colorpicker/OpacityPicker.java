package com.azeesoft.lib.colorpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import mt.karimi.ronevis.R;

public class OpacityPicker extends OrientedSeekBar {
    private static final int MIN_SIZE_DIP = 200;
    private OnOpacityPickedListener onOpacityPickedListener;
    private boolean canUpdateHexVal = true;

    public OpacityPicker(Context context) {
        super(context);
        init(context);
    }

    public OpacityPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OpacityPicker, 0, 0);
        try {
            if (a.getBoolean(R.styleable.OpacityPicker_cp_showOpacityBar, true)) {
                setVisibility(View.VISIBLE);
            } else {
                setVisibility(View.GONE);
            }
        } finally {
            a.recycle();
        }
    }

    private void init(Context context) {
        setMax(255);
        setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setOp(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void setOnOpacityPickedListener(OnOpacityPickedListener onOpacityPickedListener) {
        this.onOpacityPickedListener = onOpacityPickedListener;
    }

    private void setOp(int opacity) {
        if (onOpacityPickedListener != null)
            onOpacityPickedListener.onPicked(opacity);
    }

    public boolean isCanUpdateHexVal() {
        return canUpdateHexVal;
    }

    public void setCanUpdateHexVal(boolean canUpdateHexVal) {
        this.canUpdateHexVal = canUpdateHexVal;
    }

    public interface OnOpacityPickedListener {
        void onPicked(int opacity);
    }
}
