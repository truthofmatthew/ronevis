package fragments.views;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import fragments.MTViews.LayoutHelper;
import fragments.tool.Typefaces;

import static fragments.tool.Base.getResources;

/**
 * Created by mt.karimi on 22/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class ViewGenerator {
    public static LinearLayout Generate_Linear(Context context, int Id, int Orientation) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setId(Id);
        linearLayout.setOrientation(Orientation);
        linearLayout.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        return linearLayout;
    }

    public static FrameLayout Generate_Frame(Context context, int Id) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setId(Id);
        frameLayout.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        return frameLayout;
    }

    public static TextIcon Generate_TextIcon(Context context, int Id, int Text, int TextColor, int TextSize) {
        TextIcon textIcon = new TextIcon(context);
        textIcon.setId(Id);
//        textIcon.setButtonDrawable(null);
        textIcon.setClickable(true);
        textIcon.setFocusable(false);
        textIcon.setFocusableInTouchMode(false);
        textIcon.setGravity(Gravity.CENTER);
        textIcon.setText(getResources().getString(Text));
        textIcon.setTextColor(getResources().getColor(TextColor));
        textIcon.setTextSize(TypedValue.COMPLEX_UNIT_SP, TextSize);
        textIcon.setLayoutParams(LayoutHelper.createFrame(32, 32, Gravity.LEFT));
        textIcon.setTypeface(Typefaces.getTypeface(context, "self/icons.ttf"));
        textIcon.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        return textIcon;
    }
}
