package fragments.textEffects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.objectHelper.Manimator;
import fragments.views.horizontalwheelview.HorizontalWheelView;
import mt.karimi.ronevis.R;

public class Rotation extends BaseFragment {
    public static HorizontalWheelView WheelRotate_X, WheelRotate_Y, WheelRotate_Z;
    //    RotationButtonListener rotationButtonListener;
    static Rotation CurrentInstance;
    private static TextView seekrotatextext;
    private static TextView seekrotateytext;
    private static TextView seekrotateztext;

    public static Rotation getCurrentInstance() {
        return CurrentInstance;
    }

    public static Rotation newInstance() {
        Rotation ftext = new Rotation();
        Bundle bundle = new Bundle();
        ftext.setArguments(bundle);
        return ftext;
    }

    @Override
    public String myNameIs() {
        return "Rotation";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        seekrotateztext = null;
//        rotationButtonListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        CurrentInstance = this;
        View f_rotation;
        if (getTag().equals("RotationSticker")) {
            f_rotation = inflater.inflate(R.layout.f_rotation_s, group, false);
        } else {
            f_rotation = inflater.inflate(R.layout.f_rotation, group, false);
            seekrotateytext = (TextView) f_rotation.findViewById(R.id.seekrotateytext);
            seekrotatextext = (TextView) f_rotation.findViewById(R.id.seekrotatextext);
            WheelRotate_X = (HorizontalWheelView) f_rotation.findViewById(R.id.WheelRotate_X);
            WheelRotate_X.setDegreesAngle(0.5);
            WheelRotate_X.setListener(new HorizontalWheelView.Listener() {
                @Override
                public void onRotationChanged(double radians) {
                    if (seekrotatextext != null) {
                        String text = String.format(Locale.US, "%.0f°", WheelRotate_X.getDegreesAngle());
                        float angle = Math.round(WheelRotate_X.getDegreesAngle());
                        seekrotatextext.setText(String.valueOf(text));
                        if (MainActivity.mainInstance().SelecetedTextView != null) {
                            MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextRotateX(angle);
                            Manimator.RotateX(MainActivity.mainInstance().SelecetedTextView, angle, 0);
                        }
                    }
                }
            });
            WheelRotate_Y = (HorizontalWheelView) f_rotation.findViewById(R.id.WheelRotate_Y);
            WheelRotate_Y.setDegreesAngle(0.5);
            WheelRotate_Y.setListener(new HorizontalWheelView.Listener() {
                @Override
                public void onRotationChanged(double radians) {
                    if (seekrotateytext != null) {
                        String text = String.format(Locale.US, "%.0f°", WheelRotate_Y.getDegreesAngle());
                        float angle = Math.round(WheelRotate_Y.getDegreesAngle());
                        seekrotateytext.setText(String.valueOf(text));
                        if (MainActivity.mainInstance().SelecetedTextView != null) {
                            MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextRotateY(angle);
                            Manimator.RotateY(MainActivity.mainInstance().SelecetedTextView, angle, 0);
                        }
                    }
                }
            });
        }
        f_rotation.setClickable(true);
        f_rotation.setEnabled(false);
        seekrotateztext = (TextView) f_rotation.findViewById(R.id.seekrotateztext);
        WheelRotate_Z = (HorizontalWheelView) f_rotation.findViewById(R.id.WheelRotate_Z);
        WheelRotate_Z.setDegreesAngle(0.5);
        WheelRotate_Z.setListener(new HorizontalWheelView.Listener() {
            @Override
            public void onRotationChanged(double radians) {
                if (seekrotateztext != null) {
                    String text = String.format(Locale.US, "%.0f°", WheelRotate_Z.getDegreesAngle());
                    float angle = Math.round(WheelRotate_Z.getDegreesAngle());
                    seekrotateztext.setText(String.valueOf(text));
                    if (MainActivity.mainInstance().SelecetedTextView != null && Rotation.this.getTag().equals("RotationText")) {
                        MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextRotateZ(angle);
                        Manimator.RotateZ(MainActivity.mainInstance().SelecetedTextView, angle, 0);
                    } else if (MainActivity.mainInstance().SelectedImage != null && Rotation.this.getTag().equals("RotationSticker")) {
                        MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).setImageViewRotate(angle);
                        Manimator.RotateZ(MainActivity.mainInstance().SelectedImage, angle, 0);
                    }
                }
            }
        });
        if (MainActivity.mainInstance().SelecetedTextView != null && Rotation.this.getTag().equals("RotationText")) {
            WheelRotate_Z.setDegreesAngle((int) MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextRotateZ());
        } else if (MainActivity.mainInstance().SelectedImage != null && Rotation.this.getTag().equals("RotationSticker")) {
            WheelRotate_Z.setDegreesAngle((int) MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewRotate());
        }
        return f_rotation;
    }
}

