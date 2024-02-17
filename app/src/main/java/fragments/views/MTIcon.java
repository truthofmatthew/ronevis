package fragments.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import fragments.tool.Typefaces;

/**
 * Created by mt.karimi on 02/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class MTIcon extends TextView {
    public MTIcon(Context context) {
        super(context);
        init(context);
    }

    public MTIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MTIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        setTypeface(Typefaces.getTypeface(context, "self/icons.ttf"));
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
//            setTextColor(Color.RED);
//
//        } if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
//            setTextColor(Color.BLUE);
//        }
//
//         return super.onTouchEvent(event);
//    }
}
