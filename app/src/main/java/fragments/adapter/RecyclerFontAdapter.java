package fragments.adapter;

import android.annotation.SuppressLint;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.nineoldandroids.view.ViewHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import activities.MainActivity;
import fragments.db.Font_DBAdapter;
import fragments.download.listener.OnItemClickListener;
import fragments.tool.Typefaces;
import fragments.tool.Util;
import fragments.views.TextIcon;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

public class RecyclerFontAdapter extends RecyclerSwipeAdapter<RecyclerFontAdapter.FontsViewHolder> {
    public Font_DBAdapter fontDBAdapter;
    RecyclerView recyclerView;
    private List<File> mFiles;
    private OnItemClickListener<File> mListener;
    private boolean isBacks;

    public RecyclerFontAdapter(RecyclerView recyclerView) {
        this.mFiles = new ArrayList<>();
        this.recyclerView = recyclerView;
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
    public FontsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.font_f_single, parent, false);
        fontDBAdapter = new Font_DBAdapter(parent.getContext());
        return new FontsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FontsViewHolder holder, int position) {
        bindData(holder, position);
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public void asddsd() {
        notifyItemRangeChanged(0, getItemCount());
    }

    public void deleteItem(int position) {
        mFiles.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void addItem(File appInfo, int position) {
        mFiles.add(position, appInfo);
        notifyItemInserted(position);
    }

    public void changeItem(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        if (position != RecyclerView.NO_POSITION) {
            mFiles.set(position, new File(""));
            notifyItemChanged(position);
        }
    }

    private void bindData(final FontsViewHolder holder, final int position) {
        mItemManger.bindView(holder.swipeLayout, position);
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewWithTag("Bottom2"));
        holder.swipeLayout.addRevealListener(R.id.bottom_wrapper_child1, new SwipeLayout.OnRevealListener() {
            @Override
            public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
                View star = child.findViewById(R.id.singleRowFontDelete);
                ViewHelper.setScaleX(star, fraction);
                ViewHelper.setScaleY(star, fraction);
                View star1 = child.findViewById(R.id.singleRowFontStar);
                ViewHelper.setScaleX(star1, fraction);
                ViewHelper.setScaleY(star1, fraction);
            }
        });
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
            }

            @Override
            public void onOpen(SwipeLayout layout) {
//                YoYo.with(Techniques.Flash).duration(500).delay(100).playOn(layout.findViewById(R.id.singleRowFontDelete));
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
            }

            @Override
            public void onClose(SwipeLayout layout) {
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
            }
        });
        final File currentImage = mFiles.get(position);
        File file = new File(currentImage.getAbsolutePath());
//        Logger.d("3" + currentImage.getAbsolutePath());
        holder.singleRowFontTextID.setText(position + 1 + "");
        MainActivity.mainInstance().tf_selected = Typefaces.getTypeface(ApplicationLoader.appInstance(), currentImage.getAbsolutePath());
        holder.tvtitlesection.setTypeface(MainActivity.mainInstance().tf_selected);
        if (MainActivity.mainInstance().SelecetedTextView != null) {
            holder.tvtitlesection.setText(MainActivity.mainInstance().SelecetedTextView.getText());
        } else {
            holder.tvtitlesection.setText(Util.Persian(R.string.app_namePer));
        }
        holder.tvtitlesection.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, currentImage);
                }
            }
        });
        holder.singleRowFontStar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, currentImage);
                }
            }
        });
        fontDBAdapter.open();
        if (fontDBAdapter.Exists(currentImage.getAbsolutePath())) {
            holder.singleRowFontStar.setChecked(true);
        } else {
            holder.singleRowFontStar.setChecked(false);
        }
        fontDBAdapter.close();
        holder.singleRowFontDelete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, currentImage);
                }
            }
        });
        if (file.exists()) {
            holder.singleRowFontDelete.setChecked(true);
        } else {
            holder.singleRowFontDelete.setChecked(false);
            holder.tvtitlesection.setText(Util.Persian(R.string.dfFileDeleted));
        }
    }

    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (startA + (int) (fraction * (endA - startA))) << 24 |
                (startR + (int) (fraction * (endR - startR))) << 16 |
                (startG + (int) (fraction * (endG - startG))) << 8 |
                (startB + (int) (fraction * (endB - startB)));
    }

    static final class FontsViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView tvtitlesection;
        TextView singleRowFontTextID;
        TextIcon singleRowFontStar;
        TextIcon singleRowFontDelete;

        public FontsViewHolder(View itemView) {
            super(itemView);
            tvtitlesection = (TextView) itemView.findViewById(R.id.singleRowFontText);
            singleRowFontTextID = (TextView) itemView.findViewById(R.id.singleRowFontTextID);
            singleRowFontStar = (TextIcon) itemView.findViewById(R.id.singleRowFontStar);
            singleRowFontDelete = (TextIcon) itemView.findViewById(R.id.singleRowFontDelete);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
        }
    }
}
