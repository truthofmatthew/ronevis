package fragments.download.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import fragments.db.dl.FilesDBManager;
import fragments.db.dl.FilesInfo;
import fragments.download.listener.OnItemClickListener;
import fragments.tool.Util;
import fragments.views.TextIcon;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

public class DlFilesListAdapter extends RecyclerSwipeAdapter<DlFilesListAdapter.AppViewHolder> {
    private List<FilesInfo> mFilesInfos;
    private OnItemClickListener<FilesInfo> mListener;
    private FilesDBManager filesDBManager;
    private String typeTag;

    public DlFilesListAdapter() {
    }

    public static DlFilesListAdapter newInstance() {
        return new DlFilesListAdapter();
    }

    public void setAdapter(FilesDBManager DBManager, String typeTag) {
        this.mFilesInfos = new ArrayList<>();
        this.filesDBManager = DBManager;
        this.typeTag = typeTag;
    }

    public void setOnItemClickListener(OnItemClickListener<FilesInfo> listener) {
        this.mListener = listener;
    }

    public void setData(List<FilesInfo> filesInfo) {
        this.mFilesInfos.clear();
        this.mFilesInfos.addAll(filesInfo);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dl_item_list, parent, false);
//        AppViewHolder holder = new AppViewHolder(itemView);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        return holder;
        return new AppViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        bindData(holder, position);
    }

    @Override
    public int getItemCount() {
        return mFilesInfos.size();
    }

    private void bindData(final AppViewHolder holder, final int position) {
        final FilesInfo filesInfo = mFilesInfos.get(position);
//        mItemManger.bindView( holder.swipeLayout, position);
        holder.setTag(typeTag);
        holder.tvName.setText(filesInfo.getTitle());
        holder.tvDownload.setText(ApplicationLoader.appInstance().getString(R.string.STATUS_B_NOT_DOWNLOAD));
        Ion.with(holder.itemView.getContext())
                .load(filesInfo.getImage())
                .withBitmap()
                .placeholder(R.drawable.empty_dl)
                .intoImageView(holder.ivIcon);
//        Ion.with(MainActivity.mainInstance()).load(filesInfo.getImage()).withBitmap().asBitmap()
//                .setCallback(new FutureCallback<Bitmap>() {
//                    @Override
//                    public void onCompleted(Exception e, Bitmap result) {
//
//                        Palette.from(result).generate(new Palette.PaletteAsyncListener() {
//                            public void onGenerated(Palette palette) {
//                                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
//                                if (vibrantSwatch != null) {
//                                    holder.infoLayout.setBackgroundColor(vibrantSwatch.getRgb());
//                                    holder.tvInfo.setTextColor(vibrantSwatch.getTitleTextColor());
//                                    holder.tvName.setTextColor(vibrantSwatch.getBodyTextColor());
//                                    holder.tvName.setBackgroundColor(vibrantSwatch.getRgb());
//                                    holder.tvDownload.setBackgroundColor(vibrantSwatch.getRgb());
//                                }
//                            }
//                        });
//
//                        holder.ivIcon .setImageBitmap(result);
//
//                    }
//                });
        holder.tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, filesInfo, typeTag);
                }
            }
        });
        holder.tvName.setTypeface(Util.GetSelfTypeFace(holder.itemView.getContext(), 5));
        holder.tvDownloadPerSize.setTypeface(Util.GetSelfTypeFace(holder.itemView.getContext(), 5));
        holder.tvInfo.setTypeface(Util.GetSelfTypeFace(holder.itemView.getContext(), 5));
        holder.tvInfo.setText(filesInfo.getStatusText());
        holder.tvDownloadPerSize.setText(filesInfo.getDownloadPerSize());
        holder.progressBar.setProgress(filesInfo.getProgress());
        holder.tvDownload.setText(filesInfo.getButtonText());
        if (filesDBManager.exists(filesInfo.getSubcat())) {
            holder.tvInfo.setText(ApplicationLoader.appInstance().getString(R.string.STATUS_INSTALLED));
            holder.progressBar.setProgress(100);
//            holder.tvDownload.setEnabled(false);
            holder.tvDownload.setText(ApplicationLoader.appInstance().getString(R.string.Icon_delete));
            holder.tvDownloadPerSize.setText("");
//            holder.tvDownload.setTextColor(Util.getColorWrapper(R.color.md_deep_orange_700));
        } else {
            holder.tvDownloadPerSize.setText("");
//            holder.tvDownload.setTextColor(Util.getColorWrapper(text_secondary_light));
            holder.progressBar.setProgress(0);
        }
    }

    public void fileDBInsert(FilesInfo info) {
        if (!filesDBManager.exists(info.getSubcat())) {
            filesDBManager.insert(info);
        }
    }

    public void changeItem(int position) {
        notifyItemRangeChanged(position, getItemCount());
    }

    public void refreshBlockOverlay(int position) {
        notifyItemChanged(position);
    }

    public static final class AppViewHolder extends RecyclerView.ViewHolder {
        public final ImageView ivIcon;
        public final TextView tvName;
        public final TextIcon tvDownload;
        public final TextView tvDownloadPerSize;
        public final TextView tvInfo;
        public final ProgressBar progressBar;
        Object mTag = null;

        public AppViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDownload = (TextIcon) itemView.findViewById(R.id.tvDownload);
            tvDownloadPerSize = (TextView) itemView.findViewById(R.id.tvDownloadPerSize);
            tvInfo = (TextView) itemView.findViewById(R.id.tvInfo);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

        public Object getTag() {
            return mTag;
        }

        public void setTag(final Object tag) {
            mTag = tag;
        }
    }
}