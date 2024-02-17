package fragments.views;
/**
 * Created by mt.karimi on 30/09/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.view.View;

import androidx.customview.widget.ViewDragHelper;
import fragments.MTViews.DragHelper;

public class ViewDragHelperCallback extends ViewDragHelper.Callback {
    private DragHelper dragHelper;

    /**
     * The constructor get the instance of DragHelper
     *
     * @param dragHelper provide the instance of DragHelper
     */
    public ViewDragHelperCallback(DragHelper dragHelper) {
        this.dragHelper = dragHelper;
    }

    /**
     * Check if view on focus is the DraggerView
     *
     * @param child     return the view on focus
     * @param pointerId return the id of view
     * @return if the child on focus is equals the DraggerView
     */
    @Override
    public boolean tryCaptureView(View child, int pointerId) {
        Object tag = child.getTag();
        if (tag instanceof Integer && dragHelper != null) {
            View view = dragHelper.getDragView((Integer) tag);
            if (view != null) {
                return child.equals(view);
            }
        }
        return false;
    }

    /**
     * Return the value of slide based
     * on left and width of the element
     *
     * @param child return the view on focus
     * @param left  return the left size of DraggerView
     * @param dx    return the scroll on x-axis
     * @return the offset of slide
     */
    @Override
    public int clampViewPositionHorizontal(View child, int left, int dx) {
        Object tag = child.getTag();
        if (tag instanceof Integer && dragHelper != null) {
            View view = dragHelper.getDragView((Integer) tag);
            return Math.min(Math.max(left, dragHelper.getPaddingLeft()),
                    dragHelper.getWidth() - (view != null ? view.getWidth() : 0));
        } else {
            return 0;
        }
    }

    /**
     * Return the value of slide based
     * on top and height of the element
     *
     * @param child return the view on focus
     * @param top   return the top size of ContainerViewView
     * @param dy    return the scroll on y-axis
     * @return the offset of slide
     */
    @Override
    public int clampViewPositionVertical(View child, int top, int dy) {
        Object tag = child.getTag();
        if (tag instanceof Integer && dragHelper != null) {
            View view = dragHelper.getDragView((Integer) tag);
            return Math.min(Math.max(top, dragHelper.getPaddingTop()),
                    dragHelper.getHeight() - (view != null ? view.getHeight() : 0));
        } else {
            return 0;
        }
    }

    /**
     * Return the max value of view that can slide
     * based on #camplViewPositionHorizontal
     *
     * @param child return the view on focus
     * @return max horizontal distance that view on focus can slide
     */
    @Override
    public int getViewHorizontalDragRange(View child) {
        return dragHelper != null ? (int) dragHelper.getHorizontalDragRange() : 0;
    }

    /**
     * Return the max value of view that can slide
     * based on #clampViewPositionVertical
     *
     * @param child return the view on focus
     * @return max vertical distance that view on focus can slide
     */
    @Override
    public int getViewVerticalDragRange(View child) {
        return dragHelper != null ? (int) dragHelper.getVerticalDragRange() : 0;
    }
}