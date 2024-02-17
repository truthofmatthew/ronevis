package fragments.download.listener;

import android.view.View;

/**
 * Created by mt.karimi on 2015/7/22.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public interface OnItemClickListener<T> {
    void onItemClick(View v, int position, T t);

    void onItemClick(View v, int position, T t, String fragmenttag);
}
