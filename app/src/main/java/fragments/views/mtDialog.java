package fragments.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import java.io.IOException;

import fragments.tool.Util;
import mt.karimi.ronevis.R;
import old.ButtonBarLayout;
import old.DialogTitle;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by mt.karimi on 9/23/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class mtDialog extends Dialog implements View.OnClickListener {
    static mtDialog.View mtDView;
    private static Activity dActivity;
    private String PositiveName = null;
    private String NegativeName = null;
    private String NeutralName = null;
    private mtDialog.Positive positive;
    private mtDialog.Negative negative;
    private mtDialog.Neutral neutral;
    private int dGravity = 0;
    private Dialog dDialog;
    private android.view.View lView;
    private int dView = 0;
    private boolean showKey = false;
    private String dTitle;
    private String dMessage;
    private boolean dCancelable;
    private Drawable dIcon;
    private int dIconId = 0;
    private LinearLayout dparentPanel;
    private LinearLayout dtopPanel;
    private LinearLayout dTitleTemplate;
    private ImageView dvIcon;
    private DialogTitle dvTitle;
    private FrameLayout dContentPanel;
    private NestedScrollView dScrollView;
    private TextView dvMessage;
    private FrameLayout dCustomPanel;
    private FrameLayout dCustom;
    private ButtonBarLayout dButtonPanel;
    private Button dButton1;
    private Button dButton2;
    private Button dButton3;

    public mtDialog(Context context) {
        super(context);
    }

    public mtDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected mtDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
//    @Override
//    public void onBackPressed()
//    {
//        keyboardWatcher.destroy();
//        super.onBackPressed();
//    }

    public static mtDialog create(Activity activity) {
        dActivity = activity;
        return new mtDialog(dActivity);
    }

    public static mtDialog create(Activity activity, mtDialog.View mtaView) {
        dActivity = activity;
        mtDView = mtaView;
        return new mtDialog(dActivity);
    }

    public static mtDialog create(Activity activity, int dStyle) {
        dActivity = activity;
        return new mtDialog(dActivity, dStyle);
    }

    public static mtDialog create(Activity activity, int dStyle, mtDialog.View mtaView) {
        dActivity = activity;
        mtDView = mtaView;
        return new mtDialog(dActivity, dStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getdView() != 0) {
//            setContentView(getdView());
//        }
//        else {
        setContentView(R.layout.dialog_mt);
        dvIcon = (ImageView) findViewById(R.id.dIcon);
        dvTitle = (DialogTitle) findViewById(R.id.dTitle);
        dvMessage = (TextView) findViewById(R.id.dMessage);
        dCustom = (FrameLayout) findViewById(R.id.dCustom);
        dtopPanel = (LinearLayout) findViewById(R.id.dtopPanel);
        dTitleTemplate = (LinearLayout) findViewById(R.id.dTitle_template);
        dparentPanel = (LinearLayout) findViewById(R.id.dparentPanel);
        dButtonPanel = (ButtonBarLayout) findViewById(R.id.dButtonPanel);
        dButton1 = (Button) findViewById(R.id.dButton1);
        dButton2 = (Button) findViewById(R.id.dButton2);
        dButton3 = (Button) findViewById(R.id.dButton3);
//            .setOnClickListener(this)
//        }
        final boolean hasTextMessage = !TextUtils.isEmpty(dMessage);
        if (hasTextMessage) {
            dvMessage.setTypeface(Util.GetSelfTypeFace(getContext(), Util.getInt(R.integer.ronevisMainFont)));
            dvMessage.setTextColor(Util.getColorWrapper(R.color.text_secondary_light));
            dvMessage.setText(dMessage);
        } else {
            dvMessage.setVisibility(android.view.View.GONE);
        }
        final boolean hasTextTitle = !TextUtils.isEmpty(dTitle);
        if (hasTextTitle) {
            dvTitle.setText(dTitle);
            if (dIconId != 0) {
                dvIcon.setImageResource(dIconId);
            } else if (dIcon != null) {
                dvIcon.setImageDrawable(dIcon);
            } else {
                dvTitle.setPadding(dvIcon.getPaddingLeft(),
                        dvIcon.getPaddingTop(),
                        dvIcon.getPaddingRight(),
                        dvIcon.getPaddingBottom());
                dvTitle.setGravity(Gravity.CENTER);
                dvIcon.setVisibility(android.view.View.GONE);
            }
            dvTitle.setTypeface(Util.GetSelfTypeFace(getContext(), Util.getInt(R.integer.ronevisButtonFont)));
            dvTitle.setTextColor(Util.getColorWrapper(R.color.text_primary_light));
        } else {
            dTitleTemplate.setVisibility(android.view.View.GONE);
            dvIcon.setVisibility(android.view.View.GONE);
            dtopPanel.setVisibility(android.view.View.GONE);
        }
        setupButtons(dButtonPanel);
        if (getdView() != 0) {
            final android.view.View customView;
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            customView = inflater.inflate(getdView(), dCustom, false);
            final boolean hasCustomView = customView != null;
            if (hasCustomView) {
                dCustom.addView(customView, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            } else {
                dCustomPanel.setVisibility(android.view.View.GONE);
            }
            mtDView.prepare(dCustom, this);
        }
//        if (isShowKey()) {
//           getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        }
        setCancelable(isdCancelable());
        setCanceledOnTouchOutside(isdCancelable());
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            getWindow().setLayout(width, height);
    }

    private void setupButtons(ViewGroup buttonPanel) {
        int BIT_BUTTON_POSITIVE = 1;
        int BIT_BUTTON_NEGATIVE = 2;
        int BIT_BUTTON_NEUTRAL = 4;
        int whichButtons = 0;
        dButton1.setOnClickListener(this);
        if (TextUtils.isEmpty(PositiveName)) {
            dButton1.setVisibility(android.view.View.GONE);
        } else {
            dButton1.setText(PositiveName);
            dButton1.setVisibility(android.view.View.VISIBLE);
            dButton1.setTextColor(Util.getColorWrapper(getContext(), R.color.text_primary_light));
            dButton1.setTypeface(Util.GetSelfTypeFace(getContext(), Util.getInt(R.integer.ronevisButtonFont)));
            whichButtons = whichButtons | BIT_BUTTON_POSITIVE;
        }
        dButton2.setOnClickListener(this);
        if (TextUtils.isEmpty(NegativeName)) {
            dButton2.setVisibility(android.view.View.GONE);
        } else {
            dButton2.setText(NegativeName);
            dButton2.setVisibility(android.view.View.VISIBLE);
            dButton2.setTextColor(Util.getColorWrapper(getContext(), R.color.text_secondary_light));
            dButton2.setTypeface(Util.GetSelfTypeFace(getContext(), Util.getInt(R.integer.ronevisButtonFont)));
            whichButtons = whichButtons | BIT_BUTTON_NEGATIVE;
        }
        dButton3.setOnClickListener(this);
        if (TextUtils.isEmpty(NeutralName)) {
            dButton3.setVisibility(android.view.View.GONE);
        } else {
            dButton3.setText(NeutralName);
            dButton3.setVisibility(android.view.View.VISIBLE);
            dButton3.setTextColor(Util.getColorWrapper(getContext(), R.color.text_other_light));
            dButton3.setTypeface(Util.GetSelfTypeFace(getContext(), Util.getInt(R.integer.ronevisButtonFont)));
            whichButtons = whichButtons | BIT_BUTTON_NEUTRAL;
        }
        final boolean hasButtons = whichButtons != 0;
        if (!hasButtons) {
            buttonPanel.setVisibility(android.view.View.GONE);
        }
    }

    public String getPositiveName() {
        return PositiveName;
    }

    public mtDialog setPositiveName(int positiveName) {
        PositiveName = Util.Persian(positiveName);
        return this;
    }

    public String getNegativeName() {
        return NegativeName;
    }

    public mtDialog setNegativeName(int negativeName) {
        NegativeName = Util.Persian(negativeName);
        return this;
    }

    public String getNeutralName() {
        return NeutralName;
    }

    public mtDialog setNeutralName(int neutralName) {
        NeutralName = Util.Persian(neutralName);
        return this;
    }

    public mtDialog.Positive getPositive() {
        return positive;
    }

    public mtDialog setPositive(mtDialog.Positive positive) {
        this.positive = positive;
        return this;
    }

    public mtDialog.Negative getNegative() {
        return negative;
    }

    public mtDialog setNegative(mtDialog.Negative negative) {
        this.negative = negative;
        return this;
    }

    public mtDialog.Neutral getNeutral() {
        return neutral;
    }

    public mtDialog setNeutral(mtDialog.Neutral neutral) {
        this.neutral = neutral;
        return this;
    }

    public int getdGravity() {
        return dGravity;
    }

    public mtDialog setdGravity(int dGravity) {
        this.dGravity = dGravity;
        return this;
    }

    public Dialog getdDialog() {
        return dDialog;
    }

    public mtDialog setdDialog(Dialog dDialog) {
        this.dDialog = dDialog;
        return this;
    }

    public android.view.View getlView() {
        return lView;
    }

    public mtDialog setlView(android.view.View lView) {
        this.lView = lView;
        return this;
    }

    public boolean isShowKey() {
        return showKey;
    }

    public mtDialog setShowKey(boolean showKey) {
        this.showKey = showKey;
        return this;
    }

    public String getdTitle() {
        return dTitle;
    }

    public mtDialog setdTitle(int dTitle) {
        this.dTitle = Util.Persian(dTitle);
        return this;
    }

    public String getdMessage() {
        return dMessage;
    }

    public mtDialog setdMessage(int dMessage) {
        this.dMessage = Util.Persian(dMessage);
        return this;
    }

    public boolean isdCancelable() {
        return dCancelable;
    }

    public mtDialog setdCancelable(boolean dCancelable) {
        this.dCancelable = dCancelable;
        return this;
    }

    public Drawable getdIcon() {
        return dIcon;
    }

    public mtDialog setdIcon(Drawable dIcon) {
        this.dIcon = dIcon;
        return this;
    }

    public int getdIconId() {
        return dIconId;
    }

    public mtDialog setdIconId(int dIconId) {
        this.dIconId = dIconId;
        return this;
    }

    public int getdView() {
        return dView;
    }

    public mtDialog setdView(int dView) {
        this.dView = dView;
        return this;
    }

    public void dShow() {
        this.show();
    }

    public mtDialog when(@NonNull mtDialog.Positive positive) {
        this.positive = positive;
        return this;
    }

    public mtDialog when(@NonNull mtDialog.Neutral neutral) {
        this.neutral = neutral;
        return this;
    }

    public mtDialog when(@NonNull mtDialog.Negative negative) {
        this.negative = negative;
        return this;
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.dButton3:
                try {
                    neutral.clicked(this);
                } catch (IOException ignored) {
                    FirebaseCrash.report(ignored);
                }
                break;
            case R.id.dButton2:
                try {
                    negative.clicked(this);
                } catch (IOException ignored) {
                    FirebaseCrash.report(ignored);
                }
                break;
            case R.id.dButton1:
                try {
                    positive.clicked(this);
                } catch (IOException ignored) {
                    FirebaseCrash.report(ignored);
                }
                break;
        }
    }

    private interface Clickable {
        void clicked(DialogInterface dialog) throws IOException;
    }

    public interface Positive extends mtDialog.Clickable {
    }

    public interface Negative extends mtDialog.Clickable {
    }

    public interface Neutral extends mtDialog.Clickable {
    }

    public interface View {
        //        void prepare(@Nullable android.view.View view);
        void prepare(@Nullable android.view.View view, Dialog alertDialog);
    }
}
