package activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import fragments.objectHelper.ImageHelper;
import fragments.tool.Util;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 7/25/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class DialogActivity extends AppCompatActivity {
    DialogActivityButtonListener backgroundButtonListener;
    String PushUrl1;
    String PushUrl2;
    private CharSequence mTitle;
    private CharSequence mMessage;
    private Drawable mIcon;
    private ImageView mIconView;
    private TextView mTitleView;
    private Button mButtonPositive;
    private CharSequence mButtonPositiveText;
    private Button mButtonNegative;
    private CharSequence mButtonNegativeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_gcm);
        final ViewGroup parentPanel = (ViewGroup) findViewById(R.id.pushParentPanel);
        final ViewGroup TopPanel = (ViewGroup) parentPanel.findViewById(R.id.pushTopPanel);
//      final ViewGroup ContentPanel = (ViewGroup) parentPanel.findViewById(R.id.contentPanel);
        final ViewGroup ButtonPanel = (ViewGroup) parentPanel.findViewById(R.id.pushButtonPanel);
        backgroundButtonListener = new DialogActivityButtonListener();
        Bundle msg = getIntent().getExtras().getBundle("remoteMessage");
        String PushTitle = msg != null ? msg.getString("PushTitle") : "";
        String PushMessage = msg != null ? msg.getString("PushMessage") : "";
        String PushImage = msg != null ? msg.getString("PushImage") : "";
        String PushBtn1 = msg != null ? msg.getString("PushBtn1") : "";
        PushUrl1 = msg != null ? msg.getString("PushUrl1") : "";
        String PushBtn2 = msg != null ? msg.getString("PushBtn2") : "";
        PushUrl2 = msg != null ? msg.getString("PushUrl2") : "";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mButtonPositiveText = PushBtn1;
        mButtonNegativeText = PushBtn2;
        mTitle = PushTitle;
        mMessage = PushMessage;
        mIcon = ImageHelper.getDrawable(this, R.drawable.ic_launcher);
        setupButtons(ButtonPanel);
        setupTitle(TopPanel);
        if (PushImage != null) {
            Bitmap remote_picture = null;
            try {
                remote_picture = BitmapFactory.decodeStream((InputStream) new URL(PushImage).getContent());
            } catch (IOException ignored) {
//                                AcraLSender acraLSender = new AcraLSender();
//                                acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "PopupNotificationActivity_78");
            }
            if (remote_picture != null) {
                ImageView bannerImage = (ImageView) findViewById(R.id.pushBodyImage);
                bannerImage.setImageBitmap(remote_picture);
            }
        }
        TextView pushBodyText = (TextView) findViewById(R.id.pushBodyText);
        final boolean hasTextMessage = !TextUtils.isEmpty(mMessage);
        if (hasTextMessage) {
            pushBodyText.setText(mMessage);
        } else {
            pushBodyText.setVisibility(View.GONE);
        }
        pushBodyText.setTypeface(Util.GetSelfTypeFace(this, 1));
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? super.onCreateView(parent, name, context, attrs) : super.onCreateView(name, context, attrs);
    }

    private void setupTitle(ViewGroup topPanel) {
        final boolean hasTextTitle = !TextUtils.isEmpty(mTitle);
        if (hasTextTitle) {
            mIconView = (ImageView) findViewById(R.id.pushTitleIcon);
            mTitleView = (TextView) findViewById(R.id.pushTitleText);
            mTitleView.setText(mTitle);
            mTitleView.setTypeface(Util.GetSelfTypeFace(this, 5));
            // Do this last so that if the user has supplied any icons we
            // use them instead of the default ones. If the user has
            // specified 0 then make it disappear.
            if (mIcon != null) {
                mIconView.setImageDrawable(mIcon);
            } else {
                // Apply the padding from the icon to ensure the title is
                // aligned correctly.
                mTitleView.setPadding(mIconView.getPaddingLeft(),
                        mIconView.getPaddingTop(),
                        mIconView.getPaddingRight(),
                        mIconView.getPaddingBottom());
                mIconView.setVisibility(View.GONE);
            }
        } else {
            // Hide the title template
            final View titleTemplate = findViewById(R.id.pushTopPanel);
            titleTemplate.setVisibility(View.GONE);
//            mIconView.setVisibility(View.GONE);
//            topPanel.setVisibility(View.GONE);
        }
    }

    public void setIcon(Drawable icon) {
        mIcon = icon;
        if (mIconView != null) {
            if (icon != null) {
                mIconView.setVisibility(View.VISIBLE);
                mIconView.setImageDrawable(icon);
            } else {
                mIconView.setVisibility(View.GONE);
            }
        }
    }

    private void setupButtons(ViewGroup buttonPanel) {
        int BIT_BUTTON_POSITIVE = 1;
        int BIT_BUTTON_NEGATIVE = 2;
        int whichButtons = 0;
        mButtonPositive = (Button) buttonPanel.findViewById(R.id.button1);
        mButtonPositive.setTypeface(Util.GetSelfTypeFace(this, 5));
        mButtonPositive.setOnClickListener(backgroundButtonListener);
        if (TextUtils.isEmpty(mButtonPositiveText)) {
            mButtonPositive.setVisibility(View.GONE);
        } else {
            mButtonPositive.setText(mButtonPositiveText);
            mButtonPositive.setVisibility(View.VISIBLE);
            whichButtons = whichButtons | BIT_BUTTON_POSITIVE;
        }
        mButtonNegative = (Button) buttonPanel.findViewById(R.id.button2);
        mButtonNegative.setTypeface(Util.GetSelfTypeFace(this, 5));
        mButtonNegative.setOnClickListener(backgroundButtonListener);
        if (TextUtils.isEmpty(mButtonNegativeText)) {
            mButtonNegative.setVisibility(View.GONE);
        } else {
            mButtonNegative.setText(mButtonNegativeText);
            mButtonNegative.setVisibility(View.VISIBLE);
            whichButtons = whichButtons | BIT_BUTTON_NEGATIVE;
        }
        final boolean hasButtons = whichButtons != 0;
        if (!hasButtons) {
            buttonPanel.setVisibility(View.GONE);
        }
    }
//    public String getActionUrl(String actionUrl) {
//        return actionUrl.startsWith("http://") ? actionUrl : ("http://" + actionUrl);
//    }

    private class DialogActivityButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button1:
                    if (!TextUtils.isEmpty(PushUrl1)) {
                        try {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PushUrl1));
                            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(browserIntent);
                        } catch (Exception ignored) {
                        }
                    } else {
                        finish();
                    }
                    break;
                case R.id.button2:
                    if (!TextUtils.isEmpty(PushUrl2)) {
                        try {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PushUrl2));
                            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(browserIntent);
                        } catch (Exception ignored) {
                        }
                    } else {
                        finish();
                    }
                    break;
            }
        }
    }
}
