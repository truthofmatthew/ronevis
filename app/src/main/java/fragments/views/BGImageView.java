package fragments.views;
/**
 * Created by mt.karimi on 4/23/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.content.Context;
import android.util.AttributeSet;

import fragments.lisetener.BGResizeListener;

public class BGImageView extends androidx.appcompat.widget.AppCompatImageView {
    private BGResizeListener WowHappen;

    public BGImageView(Context context) {
        super(context);
    }

    public BGImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BGImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    //                            RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
//                            Beforeparams.width = xNew ;
//                            Beforeparams.height = yNew ;
//                            MainActivity.mainInstance()._exportroot.setLayoutParams(Beforeparams);
//                            MainActivity.mainInstance()._exportroot.invalidate();
//                            MainActivity.mainInstance()._exportroot.requestLayout();
//
//                            Manimator.Alhpa(MainActivity.mainInstance().checkerBoard,1,400);
//                            Manimator.Alhpa(MainActivity.mainInstance().MainImageBG,1,300);
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        if (getDrawable() != null) {
//            Drawable drawable = getDrawable();
//
//
//            int original_width = drawable.getIntrinsicWidth();
//            int original_height = drawable.getIntrinsicHeight();
//            int bound_width = MainActivity.mainInstance().appWH[0];
//            int bound_height = MainActivity.mainInstance().appWH[1];
//            int new_width = original_width;
//            int new_height = original_height;
//
//            // first check if we need to scale width
//            if (original_width > bound_width) {
//                //scale width to fit
//                new_width = bound_width;
//                //scale height to maintain aspect ratio
//                new_height = (new_width * original_height) / original_width;
//            }
//
//            // then check if we need to scale even with the new height
//            if (new_height > bound_height) {
//                //scale height to fit instead
//                new_height = bound_height;
//                //scale width to maintain aspect ratio
//                new_width = (new_height * original_width) / original_height;
//            }
//
//            setSS(new_width, new_height);
////            setMeasuredDimension(new_width, new_height);
//            Logger.d("OrgX \n" + new_width + "OrgY \n" + new_height);
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
////            Bitmap   bd= Ion.with(this).getBitmap();
//
////            Logger.d("OrgX \n" + OrgX + "OrgY \n" + OrgY);
//////            Logger.d("getWidth \n" + bd.getWidth() + "getHeight \n" + bd.getHeight());
////            Logger.d("MeasureSpec W \n" + MeasureSpec.getSize(widthMeasureSpec) + "MeasureSpec H \n" + MeasureSpec.getSize(heightMeasureSpec));
////
////            if (OrgX > MainActivity.mainInstance().appWH[0]) {
////                width = MeasureSpec.getSize(widthMeasureSpec);
////                height = width * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
////                setSS(width, height);
////                setMeasuredDimension(width, height);
////            }
////            if (OrgY < MainActivity.mainInstance().appWH[1]) {
////                height = MeasureSpec.getSize(heightMeasureSpec);
////                width = height * drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
////                setSS(width, height);
////                setMeasuredDimension(width, height);
////            } else if (OrgX == OrgY) {
////                width = drawable.getIntrinsicWidth();
////                height = drawable.getIntrinsicHeight();
//////                width = MeasureSpec.getSize(widthMeasureSpec);
//////                height = (int) Math.ceil((float) width * (float) drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth());
////                setSS(width, height);
////                setMeasuredDimension(width, height);
////            }
//
////            if (OrgX == OrgY) {
////                width = MeasureSpec.getSize(widthMeasureSpec);
////                height = (int) Math.ceil((float) width * (float) drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth());
////            }
//
////            WowHappen.OnResize(getId(), width, height, 0, 0);
//        } else {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//    }
    //
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        try {
//            setImageDrawable(null);
//            ((BitmapDrawable) this.getDrawable()).getBitmap().recycle();
//        } catch (Exception ignored) {
//        }
//    }
}