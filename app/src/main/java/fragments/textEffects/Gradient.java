package fragments.textEffects;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.lisetener.CheckedChangeListener;
import fragments.objects.TextProperties;
import fragments.tool.FragmentController;
import fragments.views.SwitchButton;
import fragments.views.TextIcon;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/7/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class Gradient extends BaseFragment {
    public static TextIcon eyeTextGradient;
    public static View gradient_view_demo;
    //    public static HorizontalWheelView gradient_Wheel;
    ShadowButtonListener shadowButtonListener;

    public static Gradient newInstance() {
        Gradient ftext = new Gradient();
        Bundle bundle = new Bundle();
        ftext.setArguments(bundle);
        return ftext;
    }

    //    private void FillCustomGradient(View v) {
    public static void FillCustomGradient() {
        final View view = gradient_view_demo;
        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(0, 0, 0, view.getHeight(),
                        Color.parseColor(TextProperties.getCurrent().getTextGradientFColor()), // please input your color from resource for color-4
//                                getResources().getColor(R.color.color2),
//                                getResources().getColor(R.color.color3),
                        Color.parseColor(TextProperties.getCurrent().getTextGradientSColor()),
                        Shader.TileMode.CLAMP);
//                LinearGradient lg = new LinearGradient(0, 0, width, height,
//                        new int[]{Color.GREEN, Color.GREEN, Color.WHITE, Color.WHITE},
//                        new float[]{0,0.5f,.55f,1}, Shader.TileMode.REPEAT);
                return lg;
            }
        };
        PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        view.setBackgroundDrawable((Drawable) p);
//        Drawable[] layers = new Drawable[1];
//
//        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
//            @Override
//            public Shader resize(int width, int height) {
//                LinearGradient lg = new LinearGradient(
//                        0,
//                        0,
//                        0,
//                        view.getHeight(),
//                        new int[] {
//                                Color.parseColor(TextProperties.getCurrent().getTextGradientFColor()), // please input your color from resource for color-4
////                                getResources().getColor(R.color.color2),
////                                getResources().getColor(R.color.color3),
//                                Color.parseColor(TextProperties.getCurrent().getTextGradientSColor())},
//                        new float[] { 0, 0.49f, 0.50f, 1 },
//                        Shader.TileMode.CLAMP);
//                return lg;
//            }
//        };
//        PaintDrawable p = new PaintDrawable();
//        p.setShape(new RectShape());
//        p.setShaderFactory(sf);
//        p.setCornerRadii(new float[] { 5, 5, 5, 5, 0, 0, 0, 0 });
//        layers[0] = (Drawable) p;
//
//        LayerDrawable composite = new LayerDrawable(layers);
//        view.setBackgroundDrawable(composite);
    }

    @Override
    public String myNameIs() {
        return "Gradient";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eyeTextGradient = null;
        shadowButtonListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        View f_gradient = inflater.inflate(R.layout.f_gradient, group, false);
        f_gradient.setClickable(true);
        f_gradient.setEnabled(false);
        shadowButtonListener = new ShadowButtonListener();
        TextIcon gradient_btn_Fcolor = (TextIcon) f_gradient.findViewById(R.id.gradient_btn_Fcolor);
        TextIcon gradient_btn_Scolor = (TextIcon) f_gradient.findViewById(R.id.gradient_btn_Scolor);
//        TextIcon gradient_Alpha = (TextIcon) f_gradient.findViewById(R.id.gradient_Alpha);
        gradient_btn_Fcolor.setOnClickListener(shadowButtonListener);
        gradient_btn_Scolor.setOnClickListener(shadowButtonListener);
//        gradient_Alpha.setOnClickListener(shadowButtonListener);
//        gradient_Wheel = (HorizontalWheelView) f_gradient.findViewById(R.id.gradient_Wheel);
//
//        gradient_Wheel.setDegreesAngle(0.5);
//        gradient_Wheel.setListener(new HorizontalWheelView.Listener() {
//            @Override
//            public void onRotationChanged(double radians) {
//
//                    String text = String.format(Locale.US, "%.0f°", gradient_Wheel.getDegreesAngle());
//                    float angle = Math.round(gradient_Wheel.getDegreesAngle());
//
//                    if (MainActivity.mainInstance().SelecetedTextView != null ) {
//                        TextProperties.getCurrent().setTextGradientRotate(angle);
//                        MainActivity.mainInstance().SelecetedTextView.setTextGradientRotate(angle);
////                        Manimator.RotateZ(MainActivity.mainInstance().SelecetedTextView, angle, 0);
//                    }
//
//
//            }
//        });
//        eyeShadow = (TextIcon) f_shadow.findViewById(R.id.eyeTextShadow);
//        TextIcon eyeTextGradient
        eyeTextGradient = (TextIcon) f_gradient.findViewById(R.id.eyeTextGradient);
        gradient_view_demo = f_gradient.findViewById(R.id.gradient_view_demo);
        eyeTextGradient.setOnCheckedChangeListener(new CheckedChangeListener() {
            @Override
            public void onCheckedChanged(TextIcon buttonView, boolean isChecked) {
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    TextProperties.getCurrent().setTextGradient(isChecked);
                    MainActivity.mainInstance().SelecetedTextView.setTextGradient(isChecked);
                    if (TextProperties.getCurrent().getTextGradient()) {
                        MainActivity.mainInstance().SelecetedTextView.setTextGradientFColor(TextProperties.getCurrent().getTextGradientFColor());
                        MainActivity.mainInstance().SelecetedTextView.setTextGradientSColor(TextProperties.getCurrent().getTextGradientSColor());
//                        FillCustomGradient(gradient_view_demo);
                        FillCustomGradient();
                    } else {
                        MainActivity.mainInstance().SelecetedTextView.setTextGradientFColor("#00000000");
                        MainActivity.mainInstance().SelecetedTextView.setTextGradientSColor("#00000000");
                    }
                }
            }
        });
        if (MainActivity.mainInstance().SelecetedTextView != null) {
            eyeTextGradient.setChecked(TextProperties.getCurrent().getTextGradient());
//            gradient_Wheel.setDegreesAngle((int) TextProperties.getCurrent().getTextGradientRotate());
        }
        return f_gradient;
    }

    private class ShadowButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case R.id.gradient_Alpha:
//                    if (MainActivity.mainInstance().SelecetedTextView != null) {
//                        Util.SeekDialog(Util.getInt(R.integer.textAlphaMin), Util.getInt(R.integer.textAlphaMax), (TextProperties.getCurrent().getTextGradientAlpha()), "TextGradientAlpha", R.string.Icon_opacity, getActivity());
//                    }
//                    break;
                case R.id.gradient_btn_Fcolor:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Bundle UserColorBundle = new Bundle();
                        UserColorBundle.putInt("UserColorBundle", Color.parseColor(TextProperties.getCurrent().getTextGradientFColor().trim()));
                        UserColorBundle.putBoolean("UserColorFGradient", true);
                        UserColorBundle.putString("Name", "UserColorFGradient");
                        FragmentController.on(MainActivity.mainInstance(), getActivity())
                                .fragment(new ColorPallete())
                                .viewGroup(MainActivity.mainInstance().mainLayout)
                                .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                                .Message(R.string.dfChooseTextError)
                                .CheckView(true)
                                .Bundle(UserColorBundle)
                                .BackStackName("UserColorFGradient")
                                .show();
                    }
                    break;
                case R.id.gradient_btn_Scolor:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        Bundle UserColorBundle = new Bundle();
                        UserColorBundle.putInt("UserColorBundle", Color.parseColor(TextProperties.getCurrent().getTextGradientSColor().trim()));
                        UserColorBundle.putBoolean("UserColorSGradient", true);
                        UserColorBundle.putString("Name", "UserColorSGradient");
                        FragmentController.on(MainActivity.mainInstance(), getActivity())
                                .fragment(new ColorPallete())
                                .viewGroup(MainActivity.mainInstance().mainLayout)
                                .viewChecker(MainActivity.mainInstance().SelecetedTextView)
                                .Message(R.string.dfChooseTextError)
                                .CheckView(true)
                                .Bundle(UserColorBundle)
                                .BackStackName("UserColorSGradient")
                                .show();
                    }
                    break;
            }
        }
    }
}