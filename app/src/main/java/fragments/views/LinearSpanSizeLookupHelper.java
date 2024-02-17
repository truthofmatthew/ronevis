package fragments.views;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by mt.karimi on 29/09/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class LinearSpanSizeLookupHelper extends InsetItemDecoration.SpanSizeLookupHelper<LinearLayoutManager> {
    public LinearSpanSizeLookupHelper(LinearLayoutManager layoutManager) {
        super(layoutManager);
    }

    @Override
    public int getSpanCount() {
        return 1;
    }

    /**
     * As the {@link androidx.appcompat.widget.LinearLayoutManager} only has one column, this method always returns 0;
     * <p>
     * {@inheritDoc}
     */
    @Override
    public int getSpanIndex(int position) {
        return 0;
    }

    /**
     * As the {@link androidx.appcompat.widget.LinearLayoutManager} only has one column, this method always returns 1;
     * <p>
     * {@inheritDoc}
     */
    @Override
    public int getSpanSize(int position) {
        return 1;
    }

    @Override
    public int getOrientation() {
        return layoutManager.getOrientation();
    }
}