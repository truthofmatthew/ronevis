package fragments.textEffects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.views.SwitchButton;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/7/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class BGTexture extends BaseFragment {
    public static SwitchButton gradient_Switch;
//    public static View gradient_view_demo;
//    public static HorizontalWheelView gradient_Wheel;
//    ShadowButtonListener shadowButtonListener;

    public static BGTexture newInstance() {
        BGTexture ftext = new BGTexture();
        Bundle bundle = new Bundle();
        ftext.setArguments(bundle);
        return ftext;
    }

    @Override
    public String myNameIs() {
        return "Texture";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gradient_Switch = null;
//        shadowButtonListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        View f_texture = inflater.inflate(R.layout.f_texture, group, false);
        f_texture.setClickable(true);
        f_texture.setEnabled(false);
        gradient_Switch = (SwitchButton) f_texture.findViewById(R.id.gradient_Switch);
        gradient_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextTexture(isChecked);
                    MainActivity.mainInstance().SelecetedTextView.setTextTexture(isChecked);
                    if (MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextTexture()) {
                    }
                }
            }
        });
        if (MainActivity.mainInstance().SelecetedTextView != null) {
            gradient_Switch.setChecked(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextTexture());
//            gradient_Wheel.setDegreesAngle((int) MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextTextureRotate());
        }
        return f_texture;
    }
}