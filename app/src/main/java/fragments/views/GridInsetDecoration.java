package fragments.views;

import android.content.Context;
import android.graphics.Rect;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 29/09/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class GridInsetDecoration extends RecyclerView.ItemDecoration {
    private int insetHorizontal;
    private int insetVertical;

    public GridInsetDecoration(Context context) {
        insetHorizontal = context.getResources()
                .getDimensionPixelSize(R.dimen.item_margin);
        insetVertical = context.getResources()
                .getDimensionPixelOffset(R.dimen.item_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        GridLayoutManager.LayoutParams layoutParams
                = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int position = layoutParams.getViewPosition();
        if (position == RecyclerView.NO_POSITION) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        // add edge margin only if item edge is not the grid edge
        int itemSpanIndex = layoutParams.getSpanIndex();
        // is left grid edge?
        outRect.left = itemSpanIndex == 0 ? 0 : insetHorizontal;
        // is top grid edge?
        outRect.top = itemSpanIndex == position ? 0 : insetVertical;
        outRect.right = 0;
        outRect.bottom = 0;
    }
}