package fragments.views.dragLayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import fragments.MTViews.LayoutHelper;
import fragments.tool.Typefaces;
import fragments.tool.Util;
import fragments.views.MTIcon;
import fragments.views.NonSwipeableViewPager;
import fragments.views.TextIcon;
import fragments.views.gregacucnik.EditableSeekBar;
import mt.karimi.ronevis.R;

import static fragments.tool.Base.getResources;

/**
 * Created by mt.karimi on 18/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class DargInnerViews {
    public DargInnerViews() {
    }

    public static FrameLayout dragger_handle(Context context, View.OnClickListener onClickListener) {
        FrameLayout dragger_layout = new FrameLayout(context);
        dragger_layout.setId(R.id.dragger_layout);
        dragger_layout.setTag("drag_Handle");
        dragger_layout.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 32));
        dragger_layout.setBackgroundColor(Color.parseColor("#50000000"));
        if (onClickListener != null) {
            TextIcon dragger_setting = new TextIcon(context);
            dragger_setting.setId(R.id.dragger_setting);
            dragger_setting.setClickable(true);
            dragger_setting.setFocusable(false);
            dragger_setting.setFocusableInTouchMode(false);
            dragger_setting.setGravity(Gravity.CENTER);
            dragger_setting.setTextOn(getResources().getString(R.string.Icon_format_text));
            dragger_setting.setTextOff(getResources().getString(R.string.Icon_format_clear));
            dragger_setting.setColorOn(getResources().getColor(R.color.text_secondary_light));
            dragger_setting.setColorOff(getResources().getColor(R.color.text_secondary_light));
//            dragger_setting.setTitle_text(getResources().getString(R.string.Icon_settings));
//            dragger_setting.setTitle_text_on(getResources().getString(R.string.Icon_settings));
            dragger_setting.setRadio(true);
//            dragger_setting.setChecked(true);
            dragger_setting.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            dragger_setting.setLayoutParams(LayoutHelper.createFrame(32, 32, Gravity.LEFT));
            dragger_setting.setTypeface(Typefaces.getTypeface(context, "self/icons.ttf"));
            dragger_setting.setOnClickListener(onClickListener);
            dragger_layout.addView(dragger_setting);
            TextIcon dragger_layoutmanager = new TextIcon(context);
            dragger_layoutmanager.setId(R.id.dragger_layoutmanager);
            dragger_layoutmanager.setClickable(true);
            dragger_layoutmanager.setFocusable(false);
            dragger_layoutmanager.setFocusableInTouchMode(false);
            dragger_layoutmanager.setGravity(Gravity.CENTER);
            dragger_layoutmanager.setTextOn(getResources().getString(R.string.Icon_view_grid));
            dragger_layoutmanager.setTextOff(getResources().getString(R.string.Icon_view_column));
            dragger_layoutmanager.setColorOn(getResources().getColor(R.color.text_secondary_light));
            dragger_layoutmanager.setColorOff(getResources().getColor(R.color.text_secondary_light));
//            dragger_layoutmanager.setTitle_text(getResources().getString(R.string.Icon_settings));
//            dragger_layoutmanager.setTitle_text_on(getResources().getString(R.string.Icon_settings));
            dragger_layoutmanager.setRadio(true);
//            dragger_layoutmanager.setChecked(true);
            dragger_layoutmanager.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            dragger_layoutmanager.setLayoutParams(LayoutHelper.createFrame(32, 32, Gravity.LEFT, 32, 0, 0, 0));
            dragger_layoutmanager.setOnClickListener(onClickListener);
            dragger_layout.addView(dragger_layoutmanager);
        }
        MTIcon dragger_icon = new MTIcon(context);
        dragger_icon.setId(R.id.dragger_icon);
        dragger_icon.setClickable(true);
        dragger_icon.setFocusable(false);
        dragger_icon.setFocusableInTouchMode(false);
        dragger_icon.setGravity(Gravity.CENTER);
        dragger_icon.setText(getResources().getString(R.string.Icon_unfold_more));
        dragger_icon.setTextColor(getResources().getColor(R.color.gold_2));
        dragger_icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        dragger_icon.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 32, Gravity.RIGHT, 64, 0, 64, 0));
        dragger_layout.addView(dragger_icon);
        dragger_icon.setTypeface(Typefaces.getTypeface(context, "self/icons.ttf"));
        return dragger_layout;
    }

    public static LinearLayout container_NonSwipeablePager(Context context) {
        LinearLayout container_Pager = new LinearLayout(context);
        container_Pager.setId(R.id.container);
        container_Pager.setOrientation(LinearLayout.VERTICAL);
        container_Pager.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        SmartTabLayout tab_bar = new SmartTabLayout(context);
        tab_bar.setId(R.id.tab_bar);
        tab_bar.setClickable(true);
        tab_bar.setFocusable(false);
        tab_bar.setFocusableInTouchMode(false);
        tab_bar.setDividerColors(0);
        tab_bar.setSelectedIndicatorColors(0);
//        tab_bar.setBackground(R.drawable.bottom_divider_background);
//        Util.ImageViewSetBG(tab_bar,R.drawable.bottom_divider_background);
        tab_bar.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 48, Gravity.CENTER));
        tab_bar.setDistributeEvenly(true);
//        tab_bar.setDividerColors(R.color.bg_divider);
//        tab_bar.setSelectedIndicatorColors(R.color.colorAccent);
//        tab_bar.setIndicationInterpolator(SmartTabIndicationInterpolator.LINEAR);
        tab_bar.setCustomTabView(R.layout.custom_tab, R.id.custom_text);
        container_Pager.addView(tab_bar);
        NonSwipeableViewPager tab_pager = new NonSwipeableViewPager(context);
        tab_pager.setId(R.id.tab_pager);
        tab_pager.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        tab_pager.setBackgroundColor(Util.getColorWrapper(R.color.bg_tools));
        container_Pager.addView(tab_pager);
        return container_Pager;
    }

    public static LinearLayout container_Pager(final Context context, PagerAdapter pagerAdapter) {
        LinearLayout container_Pager = new LinearLayout(context);
        container_Pager.setId(R.id.container);
        container_Pager.setOrientation(LinearLayout.VERTICAL);
        container_Pager.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
//        if (pagerAdapter.getCount() > 0) {
        SmartTabLayout tab_bar = new SmartTabLayout(context);
        tab_bar.setId(R.id.tab_bar);
        tab_bar.setClickable(true);
        tab_bar.setFocusable(false);
        tab_bar.setFocusableInTouchMode(false);
        tab_bar.setDividerColors(0);
        tab_bar.setSelectedIndicatorColors(0);
        tab_bar.setBackgroundColor(Util.getColorWrapper(R.color.bg_tools));
        tab_bar.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 48, Gravity.CENTER));
        tab_bar.setDistributeEvenly(true);
//        tab_bar.setDividerColors(R.color.bg_divider);
//        tab_bar.setSelectedIndicatorColors(R.color.colorAccent);
//        tab_bar.setIndicationInterpolator(SmartTabIndicationInterpolator.LINEAR);
        tab_bar.setCustomTabView(R.layout.custom_tab, R.id.custom_text);
        container_Pager.addView(tab_bar);
        ViewPager tab_pager = new ViewPager(context);
        tab_pager.setId(R.id.tab_pager);
        tab_pager.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        tab_pager.setBackgroundColor(Util.getColorWrapper(R.color.bg_tools));
        container_Pager.addView(tab_pager);
        tab_pager.setAdapter(pagerAdapter);
        tab_bar.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                LayoutInflater inflater = LayoutInflater.from(container.getContext());
                Resources res = container.getContext().getResources();
                View tab = inflater.inflate(R.layout.custom_tab, container, false);
                TextView customText = (TextView) tab.findViewById(R.id.custom_text);
                customText.setTypeface(Util.GetSelfTypeFace(context, 5));
                customText.setText(adapter.getPageTitle(position));
                return tab;
            }
        });
        tab_bar.setViewPager(tab_pager);
//        }else{
//
//        }
        return container_Pager;
    }

    public static DragLayout DragLayoutLinear(Context context, boolean autoFix, int minHeight) {
        DragLayout Drag_Main_View = new DragLayout(context);
        Drag_Main_View.setId(R.id.Drag_Main_View);
        Drag_Main_View.setOrientation(LinearLayout.VERTICAL);
        Drag_Main_View.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        Drag_Main_View.setAutoFix(autoFix);
        Drag_Main_View.setMinHeight(minHeight);
//        Drag_Main_View.setBackgroundColor(Color.RED);
        return Drag_Main_View;
    }

    public static DragLayout DragLayoutRelative(Context context, boolean autoFix, int minHeight) {
        DragLayout Drag_Main_View = new DragLayout(context);
        Drag_Main_View.setId(R.id.Drag_Main_View);
        Drag_Main_View.setOrientation(LinearLayout.VERTICAL);
        Drag_Main_View.setLayoutParams(LayoutHelper.createRelative(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, RelativeLayout.ALIGN_PARENT_BOTTOM));
        Drag_Main_View.setAutoFix(autoFix);
        Drag_Main_View.setMinHeight(minHeight);
        return Drag_Main_View;
    }

    public static LinearLayout SeekEditableBar(final Context context, int Icon/*, final FragmentActivity fragmentActivity*/) {
        LinearLayout SeekEditableBarLayout = new LinearLayout(context);
        SeekEditableBarLayout.setBackgroundResource(R.drawable.bottom_divider_background);
        SeekEditableBarLayout.setOrientation(LinearLayout.HORIZONTAL);
        SeekEditableBarLayout.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 0, 16, 0, 16));
        SeekEditableBarLayout.setWeightSum(7f);
        TextIcon esBtn = new TextIcon(context);
        esBtn.setId(R.id.esBtn);
        esBtn.setClickable(true);
        esBtn.setFocusable(false);
        esBtn.setFocusableInTouchMode(false);
        esBtn.setGravity(Gravity.CENTER);
        esBtn.setTextColor(getResources().getColor(R.color.text_secondary_light));
//        esBtn.setText(getResources().getString(Icon));
        esBtn.setRadio(false);
        esBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        esBtn.setLayoutParams(LayoutHelper.createLinear(0, LayoutHelper.WRAP_CONTENT, 1f));
        esBtn.setTypeface(Typefaces.getTypeface(context, "self/icons.ttf"));
        SeekEditableBarLayout.addView(esBtn);
        final EditableSeekBar esBar = new EditableSeekBar(context);
        esBar.setId(R.id.esBar);
        esBar.setLayoutParams(LayoutHelper.createLinear(0, LayoutHelper.WRAP_CONTENT, 6f));
        SeekEditableBarLayout.addView(esBar);

//
//        esBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Util.NumberDialog(Util.getInt(R.integer.textShadowMin), Util.getInt(R.integer.textShadowMax), (int) TextProperties.getCurrent().getTextStrokeWidth(), esBar.getId(), fragmentActivity);
//
//            }
//        });



        return SeekEditableBarLayout;
    }
}
