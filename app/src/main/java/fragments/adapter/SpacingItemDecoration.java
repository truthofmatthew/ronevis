package fragments.adapter;

import android.graphics.Rect;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by mt.karimi on 6/14/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spacingPx;
    private boolean addStartSpacing;
    private boolean addEndSpacing;

    public SpacingItemDecoration(int spacingPx) {
        this(spacingPx, false);
    }

    public SpacingItemDecoration(int spacingPx, boolean addStartSpacing) {
        this.spacingPx = spacingPx;
        this.addStartSpacing = false;
        this.addEndSpacing = false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (spacingPx <= 0) {
            return;
        }
        if (addStartSpacing && parent.getChildLayoutPosition(view) < 1 || parent.getChildLayoutPosition(view) >= 1) {
            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.top = spacingPx;
            } else {
                outRect.left = spacingPx;
            }
        }
        if (addEndSpacing && parent.getChildAdapterPosition(view) == getTotalItemCount(parent) - 1) {
            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.bottom = spacingPx;
            } else {
                outRect.right = spacingPx;
            }
        }
    }

    private int getTotalItemCount(RecyclerView parent) {
        return parent.getAdapter().getItemCount();
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        } else {
            throw new IllegalStateException("SpacingItemDecoration can only be used with a LinearLayoutManager.");
        }
    }
}