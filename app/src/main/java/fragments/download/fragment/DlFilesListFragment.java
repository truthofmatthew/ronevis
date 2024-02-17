package fragments.download.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fragments.animation.LoadingFace;
import fragments.db.dl.FilesDBManager;
import fragments.db.dl.FilesInfo;
import fragments.download.adapter.DlFilesListAdapter;
import fragments.download.listener.OnItemClickListener;
import fragments.download.model.Dlfile;
import fragments.download.service.DownloadService;
import fragments.download.util.PackageDelete;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;
import multithreaddownload.DownloadInfo;
import multithreaddownload.DownloadManager;

public class DlFilesListFragment extends Fragment implements OnItemClickListener<FilesInfo>/*, OnBackPressedListener*/ {
    static RecyclerView recyclerView;
    private final File mDownloadDir = new File(ApplicationLoader.appInstance().getCacheDir(), "Download");
    private final DecimalFormat DF = new DecimalFormat("0.00");
    public DlFilesListAdapter mAdapter;
    LoadingFace mLoadingFace;
    List<FilesInfo> mFilesInfos;
    String myTag;
    FilesInfo filesInfo;
    PackageDelete packageDelete;
    String mfragmenttag;
    private FilesDBManager filesDBManager;
    private Dlfile mDlfile;
    private DownloadReceiver mReceiver;

    public DlFilesListFragment() {
        recyclerView = null;
        filesDBManager = null;
        mLoadingFace = null;
        mFilesInfos = null;
        mAdapter = null;
        mDlfile = null;
        mReceiver = null;
        myTag = null;
    }

    public static DlFilesListFragment newInstance(Dlfile filesInfo) {
        DlFilesListFragment fragment = new DlFilesListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("EXTRA_PACKINFO", filesInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static boolean isCurrentListViewItemVisible(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = layoutManager.findFirstVisibleItemPosition();
        int last = layoutManager.findLastVisibleItemPosition();
        return first <= position && position <= last;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTag = this.getTag();
        mAdapter = null;
        mDlfile = null;
        filesDBManager = null;
        Bundle bundle = getArguments();
        mDlfile = (Dlfile) bundle.getSerializable("EXTRA_PACKINFO");
        filesDBManager = FilesDBManager.getInstance(getContext());
        mAdapter = DlFilesListAdapter.newInstance();
        mAdapter.setAdapter(filesDBManager, mDlfile.getType());
        mAdapter.setOnItemClickListener(this);
        if (!mDownloadDir.exists()) {
            mDownloadDir.mkdir();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = null;
        mLoadingFace = null;
        filesInfo = null;
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLoadingFace = (LoadingFace) view.findViewById(R.id.lv_circularSmile);
        mLoadingFace.setVisibility(View.VISIBLE);
        mLoadingFace.startAnim();
        mFilesInfos = new ArrayList<>();
        for (int i = 0; i < mDlfile.getPacks().size(); i++) {
            DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(mDlfile.getPacks().get(i).getUrl());
            filesInfo = new FilesInfo(
                    mDlfile.getPacks().get(i).getId(),
                    mDlfile.getPacks().get(i).getType(),
                    mDlfile.getPacks().get(i).getMaincat(),
                    mDlfile.getPacks().get(i).getSubcat(),
                    mDlfile.getPacks().get(i).getTitle(),
                    mDlfile.getPacks().get(i).getName(),
                    mDlfile.getPacks().get(i).getName_fa(),
                    mDlfile.getPacks().get(i).getImage(),
                    mDlfile.getPacks().get(i).getUrl());
            if (downloadInfo != null) {
                filesInfo.setProgress(downloadInfo.getProgress());
                filesInfo.setDownloadPerSize(getDownloadPerSize(downloadInfo.getFinished(), downloadInfo.getLength()));
                filesInfo.setStatus(FilesInfo.STATUS_PAUSED);
            }
            mFilesInfos.add(filesInfo);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setData(mFilesInfos);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        checkAdapterIsEmpty();
        List<FilesInfo> filesInfo = filesDBManager.getTypeInfos(myTag);
        return view;
    }

    private void checkAdapterIsEmpty() {
        if (mAdapter.getItemCount() == 0) {
            mLoadingFace.setVisibility(View.VISIBLE);
        } else {
            mLoadingFace.setVisibility(View.GONE);
            mLoadingFace.stopAnim();
        }
    }

    private String getDownloadPerSize(long finished, long total) {
        return DF.format((float) finished / (1024 * 1024)) + "M/" + DF.format((float) total / (1024 * 1024)) + "M";
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(View v, int position, FilesInfo filesInfo) {
    }

    @Override
    public void onItemClick(View v, int position, FilesInfo filesInfo, String fragmenttag) {
        switch (v.getId()) {
            case R.id.tvDownload:
                boolean isRunningInfo = DownloadManager.getInstance().isRunning(filesInfo.getUrl());
//                if (filesInfo.getStatus() == FilesInfo.STATUS_DOWNLOADING || filesInfo.getStatus() == FilesInfo.STATUS_CONNECTING && downloadInfo)  {
                if (isRunningInfo) {
                    pause(filesInfo.getUrl());
                } else if (filesInfo.getStatus() == FilesInfo.STATUS_INSTALLED) {
                    unInstall(filesInfo, position);
                } else {
                    if (filesDBManager.exists(filesInfo.getSubcat())) {
                        unInstall(filesInfo, position);
                    } else {
                        download(position, filesInfo.getUrl(), filesInfo);
                    }
                }
                break;
        }
    }

    private void pause(String tag) {
        DownloadService.intentPause(getActivity(), tag, myTag);
    }

    private void pauseAll() {
        DownloadService.intentPauseAll(getActivity());
    }

    private void unInstall(final FilesInfo filesInfo, int position) {
        packageDelete = new PackageDelete(getContext(), filesInfo, position);
        packageDelete.execute();
    }

    private void register() {
        mReceiver = new DownloadReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadService.ACTION_DOWNLOAD_BROAD_CAST);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, intentFilter);
    }

    private void unRegister() {
        if (mReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        register();
    }

    @Override
    public void onPause() {
        super.onPause();
        unRegister();
    }

    private void download(final int position, String tag, final FilesInfo filesInfo) {
        DownloadService.intentDownload(getActivity(), position, tag, myTag, filesInfo);
    }

    public DlFilesListAdapter.AppViewHolder getViewHolder(int position) {
        return (DlFilesListAdapter.AppViewHolder) recyclerView.findViewHolderForLayoutPosition(position);
    }

    class DownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action == null || !action.equals(DownloadService.ACTION_DOWNLOAD_BROAD_CAST)) {
                return;
            }
            final int position = intent.getIntExtra(DownloadService.EXTRA_POSITION, -1);
            final FilesInfo tmpInfo = (FilesInfo) intent.getSerializableExtra(DownloadService.EXTRA_APP_INFO);
            mfragmenttag = intent.getStringExtra(DownloadService.EXTRA_FRAGMENT_TAG);
            if (tmpInfo == null || position == -1) {
                return;
            }
            final FilesInfo mFilesInfo = mFilesInfos.get(position);
            final int status = tmpInfo.getStatus();
            final DlFilesListAdapter.AppViewHolder holder;
            switch (status) {
                case FilesInfo.STATUS_CONNECTING:
                    mFilesInfo.setStatus(FilesInfo.STATUS_CONNECTING);
                    if (isCurrentListViewItemVisible(position)) {
                        holder = getViewHolder(position);
                        if (holder.getTag().toString().equals(mfragmenttag)) {
                            holder.tvInfo.setText(mFilesInfo.getStatusText());
                            holder.tvDownload.setText(mFilesInfo.getButtonText());
                        }
                    }
                    break;
                case FilesInfo.STATUS_DOWNLOADING:
                    mFilesInfo.setStatus(FilesInfo.STATUS_DOWNLOADING);
                    mFilesInfo.setProgress(tmpInfo.getProgress());
                    mFilesInfo.setDownloadPerSize(tmpInfo.getDownloadPerSize());
                    if (isCurrentListViewItemVisible(position)) {
                        holder = getViewHolder(position);
                        if (holder.getTag() != null) {
                            if (holder.getTag().toString().equals(mfragmenttag)) {
                                holder.tvDownloadPerSize.setText(mFilesInfo.getDownloadPerSize());
                                holder.progressBar.setProgress(mFilesInfo.getProgress());
                                holder.tvInfo.setText(mFilesInfo.getStatusText());
                                holder.tvDownload.setText(mFilesInfo.getButtonText());
                            }
                        }
                    }
                    break;
                case FilesInfo.STATUS_COMPLETE:
                    mFilesInfo.setStatus(FilesInfo.STATUS_COMPLETE);
                    mFilesInfo.setProgress(tmpInfo.getProgress());
                    mFilesInfo.setDownloadPerSize(tmpInfo.getDownloadPerSize());
                    if (isCurrentListViewItemVisible(position)) {
                        holder = getViewHolder(position);
                        holder.tvDownloadPerSize.setText("");
                        if (holder.getTag() != null) {
                            if (holder.getTag().toString().equals(mfragmenttag)) {
                                holder.tvInfo.setText(mFilesInfo.getStatusText());
                                holder.tvDownload.setText(mFilesInfo.getButtonText());
                                holder.tvDownloadPerSize.setText(mFilesInfo.getDownloadPerSize());
                                holder.progressBar.setProgress(mFilesInfo.getProgress());
                            }
                        }
                    }
                    break;
                case FilesInfo.STATUS_PAUSED:
                    mFilesInfo.setStatus(FilesInfo.STATUS_PAUSED);
                    if (isCurrentListViewItemVisible(position)) {
                        holder = getViewHolder(position);
                        if (holder.getTag() != null) {
                            if (holder.getTag().toString().equals(mfragmenttag)) {
                                holder.tvInfo.setText(mFilesInfo.getStatusText());
                                holder.tvDownload.setText(mFilesInfo.getButtonText());
                            }
                        }
                    }
                    break;
                case FilesInfo.STATUS_NOT_DOWNLOAD:
                    mFilesInfo.setStatus(FilesInfo.STATUS_NOT_DOWNLOAD);
                    mFilesInfo.setProgress(tmpInfo.getProgress());
                    mFilesInfo.setDownloadPerSize(tmpInfo.getDownloadPerSize());
                    if (isCurrentListViewItemVisible(position)) {
                        holder = getViewHolder(position);
                        if (holder.getTag() != null) {
                            if (holder.getTag().toString().equals(mfragmenttag)) {
                                holder.tvInfo.setText(mFilesInfo.getStatusText());
                                holder.tvDownload.setText(mFilesInfo.getButtonText());
                                holder.progressBar.setProgress(mFilesInfo.getProgress());
                                holder.tvDownloadPerSize.setText(mFilesInfo.getDownloadPerSize());
                            }
                        }
                    }
                    break;
                case FilesInfo.STATUS_DOWNLOAD_ERROR:
                    mFilesInfo.setStatus(FilesInfo.STATUS_DOWNLOAD_ERROR);
                    mFilesInfo.setDownloadPerSize("");
                    if (isCurrentListViewItemVisible(position)) {
                        holder = getViewHolder(position);
                        if (holder.getTag() != null) {
                            if (holder.getTag().toString().equals(mfragmenttag)) {
                                holder.tvInfo.setText(mFilesInfo.getStatusText());
                                holder.tvDownloadPerSize.setText("");
                                holder.tvDownload.setText(mFilesInfo.getButtonText());
                            }
                        }
                    }
                    break;
                default:
            }
        }
    }
}