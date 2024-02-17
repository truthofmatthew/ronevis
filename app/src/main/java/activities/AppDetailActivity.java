package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import fragments.download.fragment.DlFilesListFragment;
import fragments.download.model.Dlfile;
import fragments.lisetener.OnBackPressedListener;
import mt.karimi.ronevis.R;
//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AppDetailActivity extends AppCompatActivity {
    private static AppDetailActivity AppDetailInstance;
    public OnBackPressedListener onBackPressedListener;

    public static AppDetailActivity appDetailInstance() {
        return AppDetailInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDetailInstance = this;
        setContentView(R.layout.activity_app_detail);
        Intent intent = getIntent();
        Dlfile appInfo = (Dlfile) intent.getSerializableExtra("EXTRA_PACKINFO");
//        if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, DlFilesListFragment.newInstance(appInfo), appInfo.getType())
                .commit();
//        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.Rtoolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
            if (toolbar != null) {
                TextView mTitle = (TextView) toolbar.findViewById(R.id.Rtoolbar_title);
                mTitle.setText(appInfo.getTitle());
            }
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        onBackPressedListener = null;
        super.onDestroy();
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }
}
