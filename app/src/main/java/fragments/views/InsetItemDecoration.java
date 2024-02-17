package fragments.views;
/**
 * Created by mt.karimi on 29/09/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.graphics.Rect;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;

/**
 * An ItemDecoration to center the content of a RecyclerView.
 * This class comes in handy when you want to build a responsive UI while still receiving touch- and scroll-events on the "borders" of the {@link androidx.appcompat.widget.RecyclerView}
 */
public class InsetItemDecoration extends RecyclerView.ItemDecoration {
    protected SpanSizeLookupHelper spanSizeLookupHelper;
    private float maxContentSize;
    private float parentSize;
    private boolean setup = false;

    public InsetItemDecoration(float maxContentSize) {
        this.maxContentSize = maxContentSize;
    }

    public InsetItemDecoration(GridLayoutManager layoutManager, float maxContentSize) {
        this(new GridSpanSizeLookupHelper(layoutManager), maxContentSize);
    }

    public InsetItemDecoration(LinearLayoutManager layoutManager, float maxContentSize) {
        this(new LinearSpanSizeLookupHelper(layoutManager), maxContentSize);
    }

    public InsetItemDecoration(StaggeredGridLayoutManager layoutManager, float maxContentSize) {
        this(new StaggeredGridSpanSizeLookupHelper(layoutManager), maxContentSize);
    }

    public InsetItemDecoration(SpanSizeLookupHelper spanSizeLookupHelper, float maxContentSize) {
        this.spanSizeLookupHelper = spanSizeLookupHelper;
        this.maxContentSize = maxContentSize;
        setup = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (!setup) {
            setup = generateSpanSizeLookupHelper(parent.getLayoutManager());
            if (!setup) {
                return;
            }
        }
        int position = spanSizeLookupHelper.getPosition(view);
        int spanSize = spanSizeLookupHelper.getSpanSize(position);
        int spanIndex = spanSizeLookupHelper.getSpanIndex(position);
        if (spanSizeLookupHelper.getOrientation() == OrientationHelper.VERTICAL) {
            parentSize = parent.getWidth();
        } else {
            parentSize = parent.getHeight();
        }
        if (parentSize <= maxContentSize) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }
        inset(outRect, spanIndex, spanSize);
    }

    protected boolean generateSpanSizeLookupHelper(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            spanSizeLookupHelper = new LinearSpanSizeLookupHelper((LinearLayoutManager) layoutManager);
        } else if (layoutManager instanceof GridLayoutManager) {
            spanSizeLookupHelper = new GridSpanSizeLookupHelper((GridLayoutManager) layoutManager);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanSizeLookupHelper = new StaggeredGridSpanSizeLookupHelper((StaggeredGridLayoutManager) layoutManager);
        } else {
            throw new UnsupportedLayoutManagerException();
        }
        return true;
    }

    /**
     * Applies a inset to the passed Rect depending on the spanIndex and spanSize;
     *
     * @param outRect   the {@link android.graphics.Rect} to modify
     * @param spanIndex the span-index of a view
     * @param spanSize  the span-size of a view
     */
    protected void inset(Rect outRect, int spanIndex, int spanSize) {
        final float totalSizeDifference = getTotalSizeDifference();
        final int baseInset = (int) (totalSizeDifference / 2f);
        if (spanSizeLookupHelper.getOrientation() == OrientationHelper.VERTICAL) {
            outRect.set(
                    (int) (baseInset - spanSizeLookupHelper.getSpanPositionDifference(spanIndex, totalSizeDifference))
                    , 0
                    , (int) -(baseInset - spanSizeLookupHelper.getSpanPositionDifference(spanIndex + spanSize, totalSizeDifference))
                    , 0);
        } else {
            outRect.set(
                    0
                    , (int) (baseInset - spanSizeLookupHelper.getSpanPositionDifference(spanIndex, totalSizeDifference))
                    , 0
                    , (int) (baseInset - spanSizeLookupHelper.getSpanPositionDifference(spanIndex + spanSize, totalSizeDifference)));
        }
    }

    /**
     * Returns the sizeDifference between the size of the parent {@link androidx.appcompat.widget.RecyclerView} and the passed maxContentSize
     *
     * @return 0 if maxContentSize is bigger than the size of the RecyclerView, else the difference of both sizes
     */
    protected float getTotalSizeDifference() {
        return Math.max(0, parentSize - maxContentSize);
    }

    /**
     * @return the max size of the RecyclerViews content in pixels
     */
    protected float getMaxContentSize() {
        return maxContentSize;
    }

    /**
     * @return the size of the RecyclerView
     */
    protected float getParentSize() {
        return parentSize;
    }

    protected boolean isSetup() {
        return setup;
    }

    /**
     * Invalidate the current {@link androidx.appcompat.widget.RecyclerView.LayoutManager} (and with that the {@link de.twoid.ui.decoration.InsetItemDecoration.SpanSizeLookupHelper})
     * and look far a new one in the next call of {@link #getItemOffsets(android.graphics.Rect, android.view.View, androidx.appcompat.widget.RecyclerView, androidx.appcompat.widget.RecyclerView.State)}
     * This has to be called when you set a new {@link androidx.appcompat.widget.RecyclerView.LayoutManager} to the {@link androidx.appcompat.widget.RecyclerView}
     */
    public void invalidateLayoutManager() {
        setup = false;
    }

    public void swapSpanSizeLookupHeler(SpanSizeLookupHelper spanSizeLookupHelper) {
        if (spanSizeLookupHelper == null) {
            setup = false;
        }
        this.spanSizeLookupHelper = spanSizeLookupHelper;
    }

    public static abstract class SpanSizeLookupHelper<E extends RecyclerView.LayoutManager> {
        protected E layoutManager;
        protected int spanCount;

        protected SpanSizeLookupHelper(E layoutManager, int spanCount) {
            this.layoutManager = layoutManager;
            this.spanCount = spanCount;
        }

        public SpanSizeLookupHelper(E layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * @param view
         * @return the position of the view in the {@link androidx.appcompat.widget.RecyclerView.LayoutManager}
         */
        public int getPosition(View view) {
            return layoutManager.getPosition(view);
        }

        /**
         * @return the span-count of the {@link androidx.appcompat.widget.RecyclerView.LayoutManager}
         */
        public abstract int getSpanCount();

        /**
         * @param position
         * @return the span-index for a view at the passed position
         */
        public abstract int getSpanIndex(int position);

        /**
         * @param position
         * @return the span-size for a view at the passed position
         */
        public abstract int getSpanSize(int position);

        /**
         * Generates a offset for a span-index;
         * This offset is used to inset and shrink a view according to its span-index and span-size
         *
         * @param spanIndex
         * @param sizeDifference
         * @return the offset for the passed spanIndex in pixels
         */
        public float getSpanPositionDifference(int spanIndex, float sizeDifference) {
            if (sizeDifference <= 0) {
                return 0;
            } else {
                return spanIndex * sizeDifference / ((float) getSpanCount());
            }
        }

        /**
         * @return the orientation of the {@link androidx.appcompat.widget.RecyclerView.LayoutManager}
         */
        public abstract int getOrientation();
    }

    public static class UnsupportedLayoutManagerException extends ClassCastException {
        private static final String ADDITIONAL_MESSAGE =
                InsetItemDecoration.class.getName() + " only supports "
                        + LinearLayoutManager.class.getName()
                        + ", " + GridLayoutManager.class.getName()
                        + " and " + StaggeredGridLayoutManager.class.getName()
                        + ". If you want to use a custom LayoutManager, you should create a new SpanSizeLookupHelper and pass it in the constructor";

        public UnsupportedLayoutManagerException() {
            super(ADDITIONAL_MESSAGE);
        }
    }
}