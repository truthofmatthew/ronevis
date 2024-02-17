package fragments.views;
/**
 * Created by mt.karimi on 4/23/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import fragments.lisetener.ImageResizeListener;

public class backImageView extends ImageView {
    private ImageResizeListener orl = null;
    private int width = 0;
    private int height = 0;

    public backImageView(Context context) {
        super(context);
    }

    public backImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public backImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void SetOnResizeListener(ImageResizeListener orlExt) {
        orl = orlExt;
    }

    @Override
    public void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        if (orl != null) {
            orl.OnResize(this.getId(), xNew, yNew, xOld, yOld);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getDrawable() != null) {
            int OrgX = getDrawable().getIntrinsicHeight();
            int OrgY = getDrawable().getIntrinsicWidth();
            if (OrgX < OrgY) {
                this.width = MeasureSpec.getSize(widthMeasureSpec);
                this.height = this.width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
            }
            if (OrgX > OrgY) {
                this.height = MeasureSpec.getSize(heightMeasureSpec);
                this.width = this.height * getDrawable().getIntrinsicWidth() / getDrawable().getIntrinsicHeight();
            }
            if (OrgX == OrgY) {
                this.width = MeasureSpec.getSize(widthMeasureSpec);
                this.height = (int) Math.ceil((float) width * (float) getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth());
            }
            setMeasuredDimension(this.width, this.height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
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

    public void setM(int width, int height) {
        setMeasuredDimension(width, height);
    }
}