package fragments.views;

import android.view.View;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by mt.karimi on 29/09/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class StaggeredGridSpanSizeLookupHelper extends InsetItemDecoration.SpanSizeLookupHelper<StaggeredGridLayoutManager> {
    protected StaggeredGridSpanSizeLookupHelper(StaggeredGridLayoutManager layoutManager) {
        super(layoutManager);
    }

    @Override
    public int getSpanCount() {
        return layoutManager.getSpanCount();
    }

    @Override
    public int getSpanIndex(int position) {
        StaggeredGridLayoutManager.LayoutParams params = getLayoutParams(position);
        return params.getSpanIndex();
    }

    @Override
    public int getSpanSize(int position) {
        StaggeredGridLayoutManager.LayoutParams params = getLayoutParams(position);
        return params.isFullSpan() ? getSpanCount() : 1;
    }

    @Override
    public int getOrientation() {
        return layoutManager.getOrientation();
    }

    private StaggeredGridLayoutManager.LayoutParams getLayoutParams(int position) {
        View view = layoutManager.getChildAt(position);
        return (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
    }
}