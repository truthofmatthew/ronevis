package fragments.MTViews.ReboundHelpers;
/**
 * Created by mt.karimi on 03/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class RecyclerViewAnimator {
//    private RecyclerViewAnimator mAnimator;
//    public MyAdapter(RecyclerView recyclerView) {
//        mAnimator = new RecyclerViewAnimator(recyclerView);
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.view_item, parent, false);
//        ViewHolder vh = new ViewHolder(v);
//
//        /**
//         * First item's entrance animations.
//         */
//        mAnimator.onCreateViewHolder(v);
//
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.mTextView.setText(getTitle(position));
//
//        Glide.with(holder.mBackground.getContext())
//                .load(getBackgroundImage(position))
//                .centerCrop()
//                .into(holder.mBackground);
//
//        /**
//         * Item's entrance animations during scroll are performed here.
//         */
//        mAnimator.onBindViewHolder(holder.itemView, position);
//    }
//    private String getTitle(int position) {
//        if (position % 5 == 0) {
//            return "Shoreline of Algarve Portugal";
//        } else if (position % 4 == 0) {
//            return "Houses and buildings in Porto";
//        } else if (position % 3 == 0) {
//            return "Pavilhão Rosa Mota";
//        } else if (position % 2 == 0) {
//            return "Tower at dusk in Lisbon";
//        } else {
//            return "Dramatic clouds over Lisbon";
//        }
//    }
    /**
     * Initial delay before to show items - in ms
     */
    private static final int INIT_DELAY = 1000;
    /**
     * Initial entrance tension parameter.
     * See https://facebook.github.io/rebound/
     */
    private static final int INIT_TENSION = 250;
    /**
     * Initial entrance friction parameter.
     */
    private static final int INIT_FRICTION = 18;
    /**
     * Scroll entrance animation tension parameter.
     */
    private static final int SCROLL_TENSION = 300;
    /**
     * Scroll entrance animation friction parameter.
     */
    private static final int SCROLL_FRICTION = 30;
    private int mHeight;
    private RecyclerView mRecyclerView;
    private SpringSystem mSpringSystem;
    private boolean mFirstViewInit = true;
    private int mLastPosition = -1;
    private int mStartDelay;

    public RecyclerViewAnimator(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mSpringSystem = SpringSystem.create();
        // Use height of RecyclerView to slide-in items from bottom.
        mHeight = mRecyclerView.getResources().getDisplayMetrics().heightPixels;
        mStartDelay = INIT_DELAY;
    }

    public void onCreateViewHolder(View item) {
        /**
         * mFirstViewInit is used because we only want to show animation once at initialization.
         * (onCreateViewHolder can be called after if you use multiple view types).
         */
        if (mFirstViewInit) {
            slideInBottom(item, mStartDelay, INIT_TENSION, INIT_FRICTION);
            mStartDelay += 70;
        }
    }

    public void onBindViewHolder(View item, int position) {
        /**
         * After init, animate once item by item when user scroll down.
         */
        if (!mFirstViewInit && position > mLastPosition) {
            slideInBottom(item, 0, SCROLL_TENSION, SCROLL_FRICTION);
            mLastPosition = position;
        }
    }

    private void slideInBottom(final View item,
                               final int delay,
                               final int tension,
                               final int friction) {
        // Move item far outside the RecyclerView
        item.setTranslationY(mHeight);
        Runnable startAnimation = new Runnable() {
            @Override
            public void run() {
                SpringConfig config = new SpringConfig(tension, friction);
                Spring spring = mSpringSystem.createSpring();
                spring.setSpringConfig(config);
                spring.addListener(new SimpleSpringListener() {
                    @Override
                    public void onSpringUpdate(Spring spring) {
                        /**
                         * Decrease translationY until 0.
                         */
                        float val = (float) (mHeight - spring.getCurrentValue());
                        item.setTranslationY(val);
                    }

                    @Override
                    public void onSpringEndStateChange(Spring spring) {
                        mFirstViewInit = false;
                    }
                });
                // Set the spring in motion; moving from 0 to height
                spring.setEndValue(mHeight);
            }
        };
        mRecyclerView.postDelayed(startAnimation, delay);
    }
}