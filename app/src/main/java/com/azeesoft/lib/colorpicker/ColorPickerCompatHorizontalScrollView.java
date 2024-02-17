package com.azeesoft.lib.colorpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class ColorPickerCompatHorizontalScrollView extends HorizontalScrollView {
    private boolean isScrollDisabled = false;

    public ColorPickerCompatHorizontalScrollView(Context context) {
        super(context);
        isScrollDisabled = false;
    }

    public ColorPickerCompatHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isScrollDisabled = false;
    }

    public ColorPickerCompatHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isScrollDisabled = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !isScrollDisabled && super.onInterceptTouchEvent(ev);
    }

    public void setScrollDisabled(boolean b) {
        isScrollDisabled = b;
    }
}
