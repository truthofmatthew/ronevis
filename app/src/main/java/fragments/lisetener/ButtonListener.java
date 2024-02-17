package fragments.lisetener;

import android.content.ActivityNotFoundException;
import android.view.View;
import android.widget.Toast;

import activities.MainActivity;
import fragments.tool.IntentConstants;
import fragments.tool.Util;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 4/22/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class ButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Icon_email:
                try {
                    MainActivity.mainInstance().startActivity(IntentConstants.EmailIntent());
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.mainInstance(), Util.Persian(R.string.daaMailErr), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.Icon_linkedin_box:
                MainActivity.mainInstance().startActivity(IntentConstants.LinkedinIntent());
                break;
//            case R.id.Icon_facebook_box:
//                MainActivity.mainInstance().startActivity(IntentConstants.FacebookIntent());
//                break;
            case R.id.Icon_instagram:
                MainActivity.mainInstance().startActivity(IntentConstants.InstagramIntent());
                break;
            case R.id.Icon_telegram:
                MainActivity.mainInstance().startActivity(IntentConstants.TelegramIntent());
                break;
//            case R.id.Icon_phone_in_talk:
//                MainActivity.mainInstance().startActivity(IntentConstants.CallIntent());
//                break;
        }
    }
}