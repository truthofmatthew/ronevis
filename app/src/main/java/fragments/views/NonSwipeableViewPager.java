package fragments.views;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import fragments.ButtonBar.BarButton_Layout;
import fragments.tool.preferences.Pref;

public class NonSwipeableViewPager extends ViewPager {
    private static final String TAG = NonSwipeableViewPager.class.getSimpleName();
    private int height = 0;
    private int decorHeight = 0;
    private int widthMeasuredSpec;
    private boolean animateHeight;
    private int rightHeight;
    private int leftHeight;
    private int scrollingPosition = -1;

    public NonSwipeableViewPager(Context context) {
        super(context);
        init();
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        addOnPageChangeListener(new OnPageChangeListener() {
            public int state;

            @Override
            public void onPageScrolled(int position, float offset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (state == SCROLL_STATE_IDLE) {
                    height = 0;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                this.state = state;
            }
        });
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        height = 0; // so we measure the new content in onMeasure
        super.setAdapter(new PagerAdapterWrapper(adapter));
    }

    public int recursiveLoopChildren(ViewGroup parent) {
        boolean isChecked = Pref.get("btn_layout_type", true);
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                recursiveLoopChildren((ViewGroup) child);
                if (child instanceof BarButton_Layout) {
                    child.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    height = isChecked ? child.getMeasuredHeight() + child.getMeasuredHeight() : child.getMeasuredHeight();
                    return height;
                }
            }
//            else {
//                if (child != null) {
//                    int hhh = child.getMeasuredHeight();
//                    Logger.d("getMeasuredHeight " + hhh + " \n getName " + child.getClass().getName());
//                }
//            }
        }
        return measureViewHeight(parent);
    }

    private int measureViewHeight(View view) {
        view.measure(getChildMeasureSpec(widthMeasuredSpec, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        return view.getMeasuredHeight();
    }

    /**
     * Allows to redraw the view size to wrap the content of the bigger child.
     *
     * @param widthMeasureSpec  with measured
     * @param heightMeasureSpec height measured
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMeasuredSpec = widthMeasureSpec;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            if (height == 0) {
                // measure vertical decor (i.e. PagerTitleStrip) based on ViewPager implementation
                decorHeight = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    if (lp != null && lp.isDecor) {
                        int vgrav = lp.gravity & Gravity.VERTICAL_GRAVITY_MASK;
                        boolean consumeVertical = vgrav == Gravity.TOP || vgrav == Gravity.BOTTOM;
                        if (consumeVertical) {
                            decorHeight += child.getMeasuredHeight();
                        }
                    }
                }
                // make sure that we have an height (not sure if this is necessary because it seems that onPageScrolled is called right after
                int position = getCurrentItem();
                View child = getViewAtPosition(position);
                if (child != null) {
//                    height = measureViewHeight(child);
                    height = recursiveLoopChildren(this);
                }
            }
            int totalHeight = height + decorHeight + getPaddingBottom() + getPaddingTop();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((totalHeight), MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void onPageScrolled(int position, float offset, int positionOffsetPixels) {
        super.onPageScrolled(position, offset, positionOffsetPixels);
//         cache scrolled view heights
//        if (scrollingPosition != position) {
//            scrollingPosition = position;
//            // scrolled position is always the left scrolled page
//            View leftView = getViewAtPosition(position);
//            View rightView = getViewAtPosition(position + 1);
//            if (leftView != null && rightView != null) {
//                leftHeight = measureViewHeight(leftView);
//                rightHeight = measureViewHeight(rightView);
//                animateHeight = true;
//                Log.d(TAG, "onPageScrolled heights left:" + leftHeight + " right:" + rightHeight);
//            } else {
//                animateHeight = false;
//            }
//        }
//        if (animateHeight) {
//            int newHeight = (int) (leftHeight * (1 - offset) + rightHeight * (offset));
//            if (height != newHeight) {
//                Log.d(TAG, "onPageScrolled height change:" + newHeight);
//                height = recursiveLoopChildren(this);
//                requestLayout();
//                invalidate();
//            }
//        }
    }

    protected View getViewAtPosition(int position) {
        if (getAdapter() != null) {
            Object objectAtPosition = ((PagerAdapterWrapper) getAdapter()).getObjectAtPosition(position);
            if (objectAtPosition != null) {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    if (child != null && getAdapter().isViewFromObject(child, objectAtPosition)) {
                        return child;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Wrapper for PagerAdapter so we can ask for Object at index
     */
    private class PagerAdapterWrapper extends PagerAdapter {
        private final PagerAdapter innerAdapter;
        private SparseArray<Object> objects;

        public PagerAdapterWrapper(PagerAdapter adapter) {
            this.innerAdapter = adapter;
            this.objects = new SparseArray<>(adapter.getCount());
        }

        @Override
        public void startUpdate(ViewGroup container) {
            innerAdapter.startUpdate(container);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object object = innerAdapter.instantiateItem(container, position);
            objects.put(position, object);
            return object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            innerAdapter.destroyItem(container, position, object);
            objects.remove(position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            innerAdapter.setPrimaryItem(container, position, object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            innerAdapter.finishUpdate(container);
        }

        @Override
        public Parcelable saveState() {
            return innerAdapter.saveState();
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            innerAdapter.restoreState(state, loader);
        }

        @Override
        public int getItemPosition(Object object) {
            return innerAdapter.getItemPosition(object);
        }

        @Override
        public void notifyDataSetChanged() {
            innerAdapter.notifyDataSetChanged();
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            innerAdapter.registerDataSetObserver(observer);
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            innerAdapter.unregisterDataSetObserver(observer);
        }

        @Override
        public float getPageWidth(int position) {
            return innerAdapter.getPageWidth(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return innerAdapter.getPageTitle(position);
        }

        @Override
        public int getCount() {
            return innerAdapter.getCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return innerAdapter.isViewFromObject(view, object);
        }

        public Object getObjectAtPosition(int position) {
            return objects.get(position);
        }
    }
}