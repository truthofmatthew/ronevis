package fragments.ButtonBar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import activities.MainActivity;
import fragments.tool.Typefaces;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 8/8/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class BarButton_Layout extends LinearLayout implements Checkable {
    TextView view_Icon;
    TextView view_title;
    private boolean btn_toggle = false;
    private boolean btn_checked = false;
    private String icon_text = null;
    private String icon_text_on = null;
    private String title_text = null;
    private String title_text_on = null;
    private boolean view_title_visible = false;

    public BarButton_Layout(Context context) {
        super(context);
        init(context, null);
    }

    public BarButton_Layout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BarButton_Layout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void setBtn_toggle(boolean btn_toggle) {
        this.btn_toggle = btn_toggle;
    }

    public void setIcon_text(String icon_text) {
        this.icon_text = icon_text;
    }

    public void setIcon_text_on(String icon_text_on) {
        this.icon_text_on = icon_text_on;
    }

    public void setTitle_text(String title_text) {
        this.title_text = title_text;
        syncDrawableState();
    }

    public void setTitle_text_on(String title_text_on) {
        this.title_text_on = title_text_on;
    }

    public void setButton_id(int button_id) {
        int button_id1 = button_id;
    }

    private void init(Context context, AttributeSet attrs) {
        int dp_8 = dpToPx(8);
        int intdp_24 = dpToPx(24);
//        setBackgroundColor(Color.RED);
        setId(R.id.buttonLayout);
        setClickable(false);
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.VERTICAL);
//        setBackgroundColor(Color.RED);
        setPadding(dp_8, dp_8, dp_8, dp_8);
        LayoutParams layout_171 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layout_171.gravity = Gravity.CENTER;
        setLayoutParams(layout_171);
        view_Icon = new TextView(context);
        view_Icon.setId(R.id.buttonIcon);
        view_Icon.setGravity(Gravity.CENTER);
        view_Icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//        view_Icon.setBackgroundColor(Color.YELLOW);
        LayoutParams buttonLayout_Icon = new LayoutParams(intdp_24, intdp_24);
        buttonLayout_Icon.bottomMargin = dp_8;
        buttonLayout_Icon.gravity = Gravity.CENTER;
        view_Icon.setLayoutParams(buttonLayout_Icon);
        view_title = new TextView(context);
        view_title.setId(R.id.buttonText);
        view_title.setGravity(Gravity.CENTER);
        view_title.setPadding(dp_8, 0, dp_8, 0);
        view_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//        view_title.setBackgroundColor(Color.GREEN);
        LayoutParams buttonLayout_Title = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        buttonLayout_Title.gravity = Gravity.CENTER;
        view_title.setLayoutParams(buttonLayout_Title);
        view_Icon.setTypeface(Typefaces.getTypeface(context, "self/icons.ttf"));
        view_Icon.setTextColor(getResources().getColorStateList(R.color.bar_button_icon));
        view_title.setTypeface(Util.GetSelfTypeFace(context, 3));
        view_title.setTextColor(getResources().getColorStateList(R.color.bar_button_text));
        view_title.setSingleLine(true);
        view_title.setMaxLines(1);
        addView(view_Icon);
        addView(view_title);
        TitleVisibility();
//            if (TextUtils.isEmpty(text_caption)){
//                view_title.setVisibility(View.GONE);
//            }
//            setClickable(true);
//            syncDrawableState();
    }

    private int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MainActivity.mainInstance().ButtonSize = MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.EXACTLY);
    }

    public void TitleVisibility() {
        view_title_visible = Pref.get("btn_title_visibile", true);
        if (!view_title_visible) {
            view_title.setVisibility(View.GONE);
            int dp_8 = dpToPx(8);
            int intdp_24 = dpToPx(24);
            LayoutParams buttonLayout_Icon = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            buttonLayout_Icon.width = intdp_24;
            buttonLayout_Icon.height = intdp_24;
            buttonLayout_Icon.setMargins(dp_8, dp_8, dp_8, dp_8);
            buttonLayout_Icon.gravity = Gravity.CENTER;
            view_Icon.setLayoutParams(buttonLayout_Icon);
            invalidate();
            requestLayout();
        }
    }
//    public void setView_title_visible(boolean visible) {
//        this.view_title_visible = visible;
//        TitleVisibility();
//    }

    public void setIcon(String icon) {
        view_Icon.setText(icon);
    }

    public void setTitle(String caption) {
        view_title.setText(caption);
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    public void syncDrawableState() {
        if (btn_toggle && btn_checked) {
            setTitle(title_text_on);
            setIcon(icon_text_on);
            view_title.setSelected(true);
            view_Icon.setSelected(true);
        } else {
            setTitle(title_text);
            setIcon(icon_text);
            view_title.setSelected(false);
            view_Icon.setSelected(false);
        }
    }

    @Override
    public boolean isChecked() {
        return btn_checked;
    }

    @Override
    public void setChecked(final boolean checked) {
        btn_checked = checked;
//        if (btn_toggle) {
        syncDrawableState();
//        }
//        refreshDrawableState();
        invalidate();
        requestLayout();
    }

    @Override
    public void toggle() {
//        btn_checked = !btn_checked;
        refreshDrawableState();
    }
}