package fragments.textEffects;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.graphics.SumPathEffect;
import android.graphics.SweepGradient;

/**
 * Created by mt.karimi on 22/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class Shaders {
    public static PathEffect[] mEffects;
    private int[] mColors;
    private float mPhase;

    private static Shader makeSweep() {
        return new SweepGradient(150, 25, new int[]{0xFFFF0000,
                0xFF00FF00, 0xFF0000FF, 0xFFFF0000}, null);
    }

    public static Shader makeLinear(float length_X, float length_Y, String FColor, String SColor, Shader.TileMode TileMode) {
        return new LinearGradient(
                0,
                length_X,
                0,
                length_Y, new int[]{Color.parseColor(FColor), Color.parseColor(SColor)},
                new float[]{0, 1f},
                TileMode);
    }

    private static Shader makeLinear() {
        return new LinearGradient(0, 0, 50, 50, new int[]{0xFFFF0000,
                0xFF00FF00, 0xFF0000FF}, null, Shader.TileMode.MIRROR);
    }

    private static Shader makeTiling() {
        int[] pixels = new int[]{0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0};
        Bitmap bm = Bitmap.createBitmap(pixels, 2, 2,
                Bitmap.Config.ARGB_8888);
        return new BitmapShader(bm, Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT);
    }

    public static PathEffect makeDash(float phase) {
        return new DashPathEffect(new float[]{15, 5, 8, 5}, phase);
    }

    public static Path makePathDash() {
        Path p = new Path();
        p.moveTo(4, 0);
        p.lineTo(0, -4);
        p.lineTo(8, -4);
        p.lineTo(12, 0);
        p.lineTo(8, 4);
        p.lineTo(0, 4);
        return p;
    }

    public static Path makeFollowPath() {
        Path p = new Path();
        p.moveTo(0, 0);
        for (int i = 1; i <= 15; i++) {
            p.lineTo(i * 20, (float) Math.random() * 35);
        }
        return p;
    }

    public static void makeEffects(PathEffect[] e, float phase) {
        e[0] = null;     // no effect
        e[1] = new CornerPathEffect(10);
        e[2] = new DashPathEffect(new float[]{10, 5, 5, 5}, phase);
        e[3] = new PathDashPathEffect(makePathDash(), 12, phase,
                PathDashPathEffect.Style.ROTATE);
        e[4] = new ComposePathEffect(e[2], e[1]);
        e[5] = new SumPathEffect(e[3], e[1]);
    }
//    new DiscretePathEffect(3.0f, 5.0f);
//
//    LinearLayout container = (LinearLayout) findViewById(R.id.container);
//    for (BlurMaskFilter.Blur style : BlurMaskFilter.Blur.values()) {
//        TextView textView = new TextView(this);
//        textView.setTextAppearance(this, R.style.TextAppearance_Huge_Green);
//        applyFilter(textView, style);
//
//        LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.CENTER;
//        container.addView(textView, params);
//    }
//}
//
//    private void applyFilter(
//            TextView textView, BlurMaskFilter.Blur style) {
//        if (Build.VERSION.SDK_INT >= 11) {
//            ViewUtil.setSoftwareLayerType(textView);
//        }
//        textView.setText(style.name());
//        BlurMaskFilter filter = new BlurMaskFilter(textView.getTextSize() / 10, style);
//        textView.getPaint().setMaskFilter(filter);
//    }
//
//    TextView emboss = (TextView) findViewById(R.id.emboss);
//    applyFilter(emboss, new float[] { 0f, 1f, 0.5f }, 0.8f, 3f, 3f);
//
//    // Deboss parameters from http://wiresareobsolete.com/2012/04/textview-inner-shadows/
//    TextView deboss = (TextView) findViewById(R.id.deboss);
//    applyFilter(deboss, new float[] { 0f, -1f, 0.5f }, 0.8f, 15f, 1f);
//    private void applyFilter(
//            TextView textView, float[] direction, float ambient, float specular, float blurRadius) {
//        if (Build.VERSION.SDK_INT >= 11) {
//            ViewUtil.setSoftwareLayerType(textView);
//        }
//        EmbossMaskFilter filter = new EmbossMaskFilter(direction, ambient, specular, blurRadius);
//        textView.getPaint().setMaskFilter(filter);
//    }
}
