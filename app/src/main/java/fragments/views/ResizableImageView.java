package fragments.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ResizableImageView extends ImageView {
    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int myWidth = (int) (parentHeight * 0.5);
        super.onMeasure(MeasureSpec.makeMeasureSpec(myWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
    }
}