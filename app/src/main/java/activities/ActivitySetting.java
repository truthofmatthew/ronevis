package activities;
/**
 * Created by mt.karimi on 9/21/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import android.widget.CompoundButton;

import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import fragments.views.SwitchButton;
import fragments.views.TextButton;
import mt.karimi.ronevis.R;

public class ActivitySetting extends AppCompatActivity {
    int SETTING_CHANGE = 1920;
    private AppCompatTextView prefBtnCat;
    private TextButton prefBtnDemo;
    private AppCompatTextView prefBtnTitle;
    private SwitchButton prefBtnSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        prefBtnCat = (AppCompatTextView) findViewById(R.id.Pref_Btn_Cat);
        prefBtnDemo = (TextButton) findViewById(R.id.Pref_Btn_Demo);
        prefBtnTitle = (AppCompatTextView) findViewById(R.id.Pref_Btn_Title);
        prefBtnSwitch = (SwitchButton) findViewById(R.id.Pref_Btn_Switch);
        prefBtnCat.setTypeface(Util.GetSelfTypeFace(null, Util.getInt(R.integer.ronevisTitleFont)));
        prefBtnSwitch.setChecked(Pref.get("btn_title_visibile", true));
        prefBtnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Pref.put("btn_title_visibile", isChecked);
                prefBtnDemo.setShowCaption(isChecked);
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(SETTING_CHANGE);
        super.onBackPressed();
    }
}