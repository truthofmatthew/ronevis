package com.azeesoft.lib.colorpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.MotionEvent;

import mt.karimi.ronevis.R;

public class OrientedSeekBar extends AppCompatSeekBar {
    protected static final int ORIENTATION_VERTICAL = 2;
    static final int ORIENTATION_HORIZONTAL = 1;
    int orientation = ORIENTATION_HORIZONTAL;
    private OnSeekBarChangeListener seekBarChangeListener;
    private ColorPickerCompatScrollView colorPickerCompatScrollView;
    private ColorPickerCompatHorizontalScrollView colorPickerCompatHorizontalScrollView;

    public OrientedSeekBar(Context context) {
        super(context);
    }

    public OrientedSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OrientedSeekBar, 0, 0);
        try {
            orientation = a.getInt(R.styleable.OrientedSeekBar_orientation, ORIENTATION_HORIZONTAL);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener mListener) {
        this.seekBarChangeListener = mListener;
        super.setOnSeekBarChangeListener(mListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (orientation == ORIENTATION_HORIZONTAL) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        } else {
            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
            setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (orientation == ORIENTATION_HORIZONTAL)
            super.onSizeChanged(w, h, oldw, oldh);
        else
            super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onDraw(Canvas c) {
        if (orientation != ORIENTATION_HORIZONTAL) {
            c.rotate(-90);
            c.translate(-getHeight(), 0);
        }
        super.onDraw(c);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (orientation == ORIENTATION_HORIZONTAL) {
            return super.onTouchEvent(event);
        }
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                int i;
                i = getMax() - (int) Math.ceil((getMax() * event.getY() / getHeight()));
                setProgress(i);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                if (seekBarChangeListener != null) {
                    seekBarChangeListener.onStartTrackingTouch(this);
                }
                disableScroll();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int i;
                i = getMax() - (int) Math.ceil((getMax() * event.getY() / getHeight()));
                setProgress(i);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                disableScroll();
                break;
            }
            case MotionEvent.ACTION_UP: {
                int i;
                i = getMax() - (int) Math.ceil((getMax() * event.getY() / getHeight()));
                setProgress(i);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                if (seekBarChangeListener != null) {
                    seekBarChangeListener.onStopTrackingTouch(this);
                }
                enableScroll();
                break;
            }
            case MotionEvent.ACTION_CANCEL:
                if (seekBarChangeListener != null) {
                    seekBarChangeListener.onStopTrackingTouch(this);
                }
                enableScroll();
                break;
            default:
                enableScroll();
        }
        return true;
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    private void disableScroll() {
        if (colorPickerCompatScrollView != null)
            colorPickerCompatScrollView.setScrollDisabled(true);
        if (colorPickerCompatHorizontalScrollView != null)
            colorPickerCompatHorizontalScrollView.setScrollDisabled(true);
    }

    private void enableScroll() {
        if (colorPickerCompatScrollView != null)
            colorPickerCompatScrollView.setScrollDisabled(false);
        if (colorPickerCompatHorizontalScrollView != null)
            colorPickerCompatHorizontalScrollView.setScrollDisabled(false);
    }

    public void setColorPickerCompatScrollView(ColorPickerCompatScrollView colorPickerCompatScrollView) {
        this.colorPickerCompatScrollView = colorPickerCompatScrollView;
    }

    public void setColorPickerCompatHorizontalScrollView(ColorPickerCompatHorizontalScrollView colorPickerCompatHorizontalScrollView) {
        this.colorPickerCompatHorizontalScrollView = colorPickerCompatHorizontalScrollView;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
