package com.azeesoft.lib.colorpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ColorPickerCompatScrollView extends ScrollView {
    private boolean isScrollDisabled = false;

    public ColorPickerCompatScrollView(Context context) {
        super(context);
        isScrollDisabled = false;
    }

    public ColorPickerCompatScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isScrollDisabled = false;
    }

    public ColorPickerCompatScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
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
