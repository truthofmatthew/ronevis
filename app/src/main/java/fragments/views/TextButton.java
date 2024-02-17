package fragments.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import fragments.tool.Typefaces;
import fragments.tool.Util;
import mt.karimi.ronevis.R;

public class TextButton extends LinearLayout implements Checkable {
    public boolean showCaption = true;
    TextView buttonIcon;
    TextView buttonText;
    private boolean mChecked = false;
    private boolean text_checkable = false;
    private String text_icon;
    private String text_icon_on;
    private int text_icon_color;
    private int text_icon_color_on;
    private String text_caption;
    private String text_caption_on;
    private int text_caption_color;
    private int text_caption_color_on;

    public TextButton(Context context) {
        super(context);
        init(context, null);
    }

    public TextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextButton, 0, 0);
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            inflater.inflate(R.layout.text_button, this);
            buttonIcon = (TextView) findViewById(R.id.buttonIcon);
            buttonText = (TextView) findViewById(R.id.buttonText);
            buttonIcon.setTypeface(Typefaces.getTypeface(context, "self/icons.ttf"));
            buttonText.setTypeface(Util.GetSelfTypeFace(context, 3));
            text_icon = a.getString(R.styleable.TextButton_text_icon);
            text_icon_on = a.getString(R.styleable.TextButton_text_icon_on);
            text_icon_color = a.getColor(R.styleable.TextButton_text_icon_color, getResources().getColor(R.color.grey_light));
            text_icon_color_on = a.getColor(R.styleable.TextButton_text_icon_color_on, getResources().getColor(R.color.grey_light));
            text_caption = a.getString(R.styleable.TextButton_text_caption);
            text_caption_on = a.getString(R.styleable.TextButton_text_caption_on);
            text_caption_color = a.getColor(R.styleable.TextButton_text_caption_color, getResources().getColor(R.color.grey_dark));
            text_caption_color_on = a.getColor(R.styleable.TextButton_text_caption_color_on, getResources().getColor(R.color.grey_dark));
            final boolean checked = a.getBoolean(R.styleable.TextButton_text_checked, false);
            text_checkable = a.getBoolean(R.styleable.TextButton_text_checkable, false);
            setClickable(true);
            setChecked(checked);
            syncDrawableState();
        } finally {
            a.recycle();
        }
    }

    public void setTextOut(String caption) {
        text_caption = caption;
        buttonText.setText(caption);
    }

    public void setIconOut(String icon) {
        text_icon = icon;
        buttonIcon.setText(icon);
    }

    public void setShowCaption(boolean showCaption) {
        this.showCaption = showCaption;
        syncDrawableState();
    }

    public void setIcon(String icon) {
        buttonIcon.setText(icon);
    }

    public void setIconColor(int IconColor) {
        buttonIcon.setTextColor(IconColor);
    }

    public void setTextColor(int IconColor) {
        buttonText.setTextColor(IconColor);
    }

    public void setCaption(String caption) {
        if (showCaption) {
            buttonText.setText(caption);
            buttonText.setVisibility(View.VISIBLE);
        } else {
            buttonText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    private void syncDrawableState() {
        if (text_checkable) {
            if (isChecked()) {
                setCaption(text_caption_on);
                setIcon(text_icon_on);
                setIconColor(text_icon_color_on);
                setTextColor(text_caption_color_on);
            } else {
                setCaption(text_caption);
                setIcon(text_icon);
                setIconColor(text_icon_color);
                setTextColor(text_caption_color);
            }
        } else {
            setCaption(text_caption);
            setIcon(text_icon);
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void setChecked(final boolean checked) {
        mChecked = checked;
        if (text_checkable) {
            syncDrawableState();
        }
    }

    @Override
    public void toggle() {
        mChecked = !mChecked;
        refreshDrawableState();
    }
}