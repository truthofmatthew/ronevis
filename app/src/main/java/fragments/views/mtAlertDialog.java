package fragments.views;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import java.io.IOException;

import fragments.tool.Util;
import mt.karimi.ronevis.R;

public class mtAlertDialog {
    private final Activity activity;
    private final AlertDialog.Builder builder;
    private int PositiveName;
    private String PositiveNameString;
    private int NegativeName;
    private int NeutralName;
    private Positive positive;
    private Negative negative;
    private Neutral neutral;
    private int gravity = 0;
    private AlertDialog alertDialog;
    private android.view.View lView;
    private boolean showKey = false;

    private mtAlertDialog(Activity activity) {
        this.activity = activity;
        builder = new AlertDialog.Builder(activity);
    }

    private mtAlertDialog(Activity activity, int animation) {
        this.activity = activity;
        builder = new AlertDialog.Builder(activity, animation);
    }

    public static mtAlertDialog on(Activity activity) {
        return new mtAlertDialog(activity);
    }

    public static mtAlertDialog on(Activity activity, int animation) {
        return new mtAlertDialog(activity, animation);
    }

    public boolean isShowKey() {
        return showKey;
    }

    public mtAlertDialog setShowKey(boolean showKey) {
        this.showKey = showKey;
        return this;
    }

    public mtAlertDialog with() {
        return this;
    }

    public mtAlertDialog title(@StringRes int title) {
        builder.setTitle(Util.Persian(activity, title));
        return this;
    }

    public mtAlertDialog title(@NonNull android.view.View view) {
        builder.setCustomTitle(view);
        return this;
    }

    public mtAlertDialog icon(@NonNull Drawable icon) {
        builder.setIcon(icon);
        return this;
    }

    public mtAlertDialog cancelable(boolean cancelable) {
        builder.setCancelable(cancelable);
        return this;
    }

    public mtAlertDialog icon(@DrawableRes int icon) {
        builder.setIcon(icon);
        return this;
    }

    public mtAlertDialog title(@NonNull CharSequence title) {
        builder.setTitle(title.toString());
        return this;
    }

    public mtAlertDialog message(@StringRes int message) {
        builder.setMessage(Util.Persian(activity, message));
        return this;
    }

    public mtAlertDialog message(@NonNull android.view.View message) {
        builder.setView(message);
        return this;
    }

    public mtAlertDialog message(@NonNull CharSequence message) {
        builder.setMessage(message.toString());
        return this;
    }

    public mtAlertDialog layout(@LayoutRes int layout) {
        lView = activity.getLayoutInflater().inflate(layout, null);
        builder.setView(lView);
        return this;
    }

    public mtAlertDialog gravity(@StringRes int gravity) {
        this.gravity = gravity;
        return this;
    }

    private void createDialog() {
        if (positive != null) {
            if (PositiveNameString != null) {
                builder.setPositiveButton(PositiveNameString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            positive.clicked(dialog);
                        } catch (IOException ignored) {
                            FirebaseCrash.report(ignored);
                        }
                    }
                });
            } else {
                int buttonId = PositiveName;
                builder.setPositiveButton(buttonId, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            positive.clicked(dialog);
                        } catch (IOException ignored) {
                            FirebaseCrash.report(ignored);
                        }
                    }
                });
            }
        }
        if (negative != null) {
            int buttonId = NegativeName;
            builder.setNegativeButton(buttonId, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    try {
                        negative.clicked(dialog);
                    } catch (IOException ignored) {
                        FirebaseCrash.report(ignored);
                    }
                }
            });
        }
        if (neutral != null) {
            int buttonId = NeutralName;
            builder.setNeutralButton(buttonId, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    try {
                        neutral.clicked(dialog);
                    } catch (IOException ignored) {
                        FirebaseCrash.report(ignored);
                    }
                }
            });
        }
        alertDialog = builder.create();
        if (!this.activity.isFinishing()) {
            if (isShowKey()) {
                alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
            alertDialog.show();
        }
        if (gravity > 0) {
            Window window = alertDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = gravity;
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setAttributes(wlp);
        }
        TextView titleId;
        titleId = (TextView) alertDialog.findViewById(androidx.appcompat.R.id.alertTitle);
        if (titleId != null) {
            titleId.setTypeface(Util.GetSelfTypeFace(activity, Util.getInt(R.integer.ronevisButtonFont)));
            titleId.setTextColor(Util.getColorWrapper(R.color.text_primary_light));
//            titleId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        TextView messageId;
        messageId = (TextView) alertDialog.findViewById(android.R.id.message);
        if (messageId != null) {
            messageId.setTypeface(Util.GetSelfTypeFace(activity, Util.getInt(R.integer.ronevisMainFont)));
            messageId.setTextColor(Util.getColorWrapper(R.color.text_secondary_light));
        }
        Button b_pos;
        b_pos = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (b_pos != null) {
            b_pos.setTextColor(Util.getColorWrapper(activity, R.color.text_primary_light));
            b_pos.setTypeface(Util.GetSelfTypeFace(activity, Util.getInt(R.integer.ronevisButtonFont)));
            b_pos.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    try {
                        positive.clicked(alertDialog);
                    } catch (IOException ignored) {
                        FirebaseCrash.report(ignored);
                    }
                }
            });
        }
        Button b_neg;
        b_neg = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (b_neg != null) {
            b_neg.setTextColor(Util.getColorWrapper(activity, R.color.text_secondary_light));
            b_neg.setTypeface(Util.GetSelfTypeFace(activity, Util.getInt(R.integer.ronevisButtonFont)));
        }
        Button b_neu;
        b_neu = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        if (b_neu != null) {
            b_neu.setTextColor(Util.getColorWrapper(activity, R.color.text_other_light));
            b_neu.setTypeface(Util.GetSelfTypeFace(activity, Util.getInt(R.integer.ronevisButtonFont)));
        }
    }

    public mtAlertDialog show() {
        createDialog();
        return this;
    }

    public mtAlertDialog show(@NonNull mtAlertDialog.View mtaView) {
        createDialog();
        mtaView.prepare(lView, this.alertDialog);
        return this;
    }

    public mtAlertDialog when(@NonNull Positive positive) {
        this.positive = positive;
        return this;
    }

    public mtAlertDialog when(@NonNull Neutral neutral) {
        this.neutral = neutral;
        return this;
    }

    public mtAlertDialog when(@NonNull Negative negative) {
        this.negative = negative;
        return this;
    }

    public mtAlertDialog when(@StringRes int positiveName, @NonNull Positive positive) {
        this.positive = positive;
        this.PositiveName = positiveName;
        return this;
    }

    public mtAlertDialog when(String positiveName, @NonNull Positive positive) {
        this.positive = positive;
        this.PositiveNameString = positiveName;
        return this;
    }

    public mtAlertDialog when(@StringRes int neutralName, @NonNull Neutral neutral) {
        this.neutral = neutral;
        this.NeutralName = neutralName;
        return this;
    }

    public mtAlertDialog when(@StringRes int negativeName, @NonNull Negative nah) {
        this.negative = nah;
        this.NegativeName = negativeName;
        return this;
    }

    private interface Clickable {
        void clicked(DialogInterface dialog) throws IOException;
    }

    public interface Positive extends Clickable {
    }

    public interface Negative extends Clickable {
    }

    public interface Neutral extends Clickable {
    }

    public interface View {
        //        void prepare(@Nullable android.view.View view);
        void prepare(@Nullable android.view.View view, AlertDialog alertDialog);
    }
}
















