package activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import fragments.tool.WeakHandler;
import mt.karimi.ronevis.R;

public class SplashActivity extends AppCompatActivity {
    public WeakHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new WeakHandler();
        setContentView(R.layout.activity_splash);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        switch (AppChecker.checkAppStart(this)) {
            case NORMAL:

                break;
            case FIRST_TIME_VERSION:
                break;
            case FIRST_TIME:
//                Pref.get("btn_title_visibile", true);
//                Pref.get("btn_layout_type", true);
                break;
            default:
                break;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkShowTutorial();
                finish();
            }
        }, 1000);
    }

    private void checkShowTutorial() {
//        int oldVersionCode = PrefConstants.getAppPrefInt(this, "version_code");
//        int currentVersionCode = SAppUtil.getAppVersionCode(this);
//        if (currentVersionCode > oldVersionCode) {
//            startActivity(new Intent(this, AgreementActivity.class));
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//            PrefConstants.putAppPrefInt(this, "version_code", currentVersionCode);
//        } else {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
