package fragments.adapter;

import android.animation.Animator;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fragments.animation.BaseAnimation;
import fragments.animation.ScaleInAnimation;
import fragments.download.listener.OnItemClickListener;
import fragments.tool.LoadImage;
import mt.karimi.ronevis.R;

public class RecyclerImageAdapter extends RecyclerSwipeAdapter<RecyclerImageAdapter.ImageViewHolder> {
    private List<File> mFiles;
    private OnItemClickListener<File> mListener;
    private boolean isBacks;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = -1;
    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation = new ScaleInAnimation();
    private LoadImage loadImage;

    public RecyclerImageAdapter(boolean setBacks) {
        this.mFiles = new ArrayList<>();
        this.isBacks = setBacks;
    }

    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (holder.getLayoutPosition() > mLastPosition) {
            BaseAnimation animation;
            if (mCustomAnimation != null) {
                animation = mCustomAnimation;
            } else {
                animation = mSelectAnimation;
            }
            for (Animator anim : animation.getAnimators(holder.itemView)) {
                startAnim(anim, holder.getLayoutPosition());
            }
            mLastPosition = holder.getLayoutPosition();
        }
    }

    protected void startAnim(Animator anim, int index) {
        int mDuration = 300;
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    public void setOnItemClickListener(OnItemClickListener<File> listener) {
        this.mListener = listener;
    }

    public void setData(List<File> appInfos) {
        this.mFiles.clear();
        this.mFiles.addAll(appInfos);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_bg_single, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        bindData(holder, position);
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    private void bindData(ImageViewHolder holder, final int position) {
//        mItemManger.bindView( holder.swipeLayout, position);
//        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
//        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewWithTag("Bottom2"));
//        holder.swipeLayout.addRevealListener(R.id.bottom_wrapper_child1, new SwipeLayout.OnRevealListener() {
//            @Override
//            public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
//                View star = child.findViewById(R.id.singleRowFontDelete);
//                ViewHelper.setScaleX(star, fraction);
//                ViewHelper.setScaleY(star, fraction);
//            }
//        });
        final File currentImage = mFiles.get(position);
        holder.imgbgsingle.setTag("imv" + (position * 3));
        String pathString = isBacks ? currentImage.getAbsolutePath().replace("img", "tmb") : currentImage.getAbsolutePath();
//        loadImage =   new LoadImage(holder.imgbgsingle, pathString, isBacks, "imv" + (position * 3));
//        loadImage.execute();
//        Ion.with(holder.imgbgsingle.getContext())
//                .load(pathString)
//                .withBitmap()
//                .placeholder(R.drawable.empty_ronevis)
//                .intoImageView(holder.imgbgsingle);
        Ion.with(holder.imgbgsingle).smartSize(true)
                .placeholder(R.drawable.empty_ronevis)
                .load(pathString);
        holder.imgbgsingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, currentImage);
                }
            }
        });
//        holder.singleRowFontDelete.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("NewApi")
//            @Override
//            public void onClick(View v) {
//                if (mListener != null) {
//                    mListener.onItemClick(v, position, currentImage);
//                }
//            }
//        });
    }

    public static final class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgbgsingle;
//        SwipeLayout swipeLayout;
//        TextIcon singleRowFontDelete;

        ImageViewHolder(View itemView) {
            super(itemView);
            imgbgsingle = (ImageView) itemView.findViewById(R.id.imgbgsingle);
//            singleRowFontDelete = (TextIcon) itemView.findViewById(R.id.singleRowFontDelete);
//            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
        }
    }
}
