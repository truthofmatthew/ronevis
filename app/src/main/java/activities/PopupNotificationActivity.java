package activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import fragments.objectHelper.ImageHelper;
import fragments.views.backImageView;
import fragments.views.mtAlertDialog;
import mt.karimi.ronevis.R;

public class PopupNotificationActivity extends AppCompatActivity {
    private PowerManager.WakeLock wakeLock = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle msg = getIntent().getExtras().getBundle("remoteMessage");
        String ContentTitle = msg != null ? msg.getString("ContentTitle") : "";
        final String ActionUrl = msg != null ? msg.getString("ActionUrl") : "";
        final String PictureUrl = msg != null ? msg.getString("PictureUrl") : "";
        String PButton = msg != null ? msg.getString("PButton") : " ";
        String PText = msg != null ? msg.getString("PText") : " ";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mtAlertDialog.on(this)
                .with()
                .title(ContentTitle != null ? ContentTitle : "")
                .message(PText)
                .icon(ImageHelper.getDrawable(this, R.drawable.ic_launcher))
                .cancelable(false)
                .layout(R.layout.activity_banner)
                .when(PButton, new mtAlertDialog.Positive() {
                    @Override
                    public void clicked(DialogInterface dialog) {
                        try {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ActionUrl));
                            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(browserIntent);
                        } catch (Exception ignored) {
                        }
                        dialog.dismiss();
                    }
                })
                .when(R.string.cancel, new mtAlertDialog.Neutral() {
                    @Override
                    public void clicked(DialogInterface dialog) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show(new mtAlertDialog.View() {
                    @Override
                    public void prepare(@Nullable View view, final AlertDialog alertDialog) {
                        if (PictureUrl != null) {
                            Bitmap remote_picture = null;
                            try {
                                remote_picture = BitmapFactory.decodeStream((InputStream) new URL(PictureUrl).getContent());
                            } catch (IOException ignored) {
//                                AcraLSender acraLSender = new AcraLSender();
//                                acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "PopupNotificationActivity_78");
                            }
                            if (remote_picture != null) {
                                backImageView bannerImage = (backImageView) view.findViewById(R.id.bannerImage);
                                bannerImage.setImageBitmap(remote_picture);
                            }
                        }
                    }
                });
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? super.onCreateView(parent, name, context, attrs) : super.onCreateView(name, context, attrs);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}