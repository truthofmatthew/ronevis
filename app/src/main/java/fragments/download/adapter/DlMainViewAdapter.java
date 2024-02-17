package fragments.download.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import fragments.download.listener.OnItemClickListener;
import fragments.download.model.Dlfile;
import fragments.download.model.Download;
import fragments.tool.Util;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 2015/7/8.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class DlMainViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<Dlfile> mDownloads;
    private OnItemClickListener<Dlfile> mListener;

    public DlMainViewAdapter() {
        this.mDownloads = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener<Dlfile> listener) {
        this.mListener = listener;
    }

    public void setData(Download appInfos) {
        this.mDownloads.clear();
        this.mDownloads.addAll(appInfos.getDlfile());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dl_item_main, parent, false);
        return new AppViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindData((AppViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return mDownloads.size();
    }

    private void bindData(AppViewHolder holder, final int position) {
        Ion.with(holder.itemView.getContext())
                .load(mDownloads.get(position).getCover())
                .withBitmap()
                .placeholder(R.drawable.empty_dl)
                .intoImageView(holder.ivIcon);
        holder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, mDownloads.get(position));
                }
            }
        });
        holder.coverTitle.setText(mDownloads.get(position).getTitle());
        holder.coverTitle.setTypeface(Util.GetSelfTypeFace(holder.itemView.getContext(), 5));
    }

    public static final class AppViewHolder extends RecyclerView.ViewHolder {
        public final ImageView ivIcon;
        public final TextView coverTitle;

        public AppViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            coverTitle = (TextView) itemView.findViewById(R.id.coverTitle);
        }
    }
}
