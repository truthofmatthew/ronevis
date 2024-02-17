package fragments.views;
/**
 * Created by mt.karimi on 29/09/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import androidx.recyclerview.widget.GridLayoutManager;

public class GridSpanSizeLookupHelper extends InsetItemDecoration.SpanSizeLookupHelper<GridLayoutManager> {
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;

    public GridSpanSizeLookupHelper(GridLayoutManager layoutManager) {
        super(layoutManager);
        spanSizeLookup = layoutManager.getSpanSizeLookup();
        spanSizeLookup.setSpanIndexCacheEnabled(true);
    }

    @Override
    public int getSpanCount() {
        return layoutManager.getSpanCount();
    }

    @Override
    public int getSpanIndex(int position) {
        return spanSizeLookup.getSpanIndex(position, getSpanCount());
    }

    @Override
    public int getSpanSize(int position) {
        return spanSizeLookup.getSpanSize(position);
    }

    @Override
    public int getOrientation() {
        return layoutManager.getOrientation();
    }
}