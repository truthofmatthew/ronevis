package fragments.download.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import activities.AppDetailActivity;
import fragments.db.dl.FilesDBManager;
import fragments.db.dl.FilesInfo;
import fragments.download.adapter.DlFilesListAdapter;
import fragments.download.fragment.DlFilesListFragment;
import fragments.tool.RegexFileFilter;
import fragments.tool.Util;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 9/4/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class PackageDelete extends
        AsyncTask<Integer, Integer, Void> {
    private static FilesDBManager filesDBManager;
    public Context context;
    public FilesInfo filesInfo;
    public int position;
    public DlFilesListAdapter.AppViewHolder holder = null;
    public DlFilesListFragment sourceObjectType = null;
    public Activity activity = null;
    File PackageDir;
    List<File> filesAll = new ArrayList<>();

    public PackageDelete(Context mcontext, FilesInfo mfilesInfo, int mposition) {
        context = mcontext;
        filesInfo = mfilesInfo;
        position = mposition;
        activity = AppDetailActivity.appDetailInstance();
        sourceObjectType = (DlFilesListFragment) AppDetailActivity.appDetailInstance().getSupportFragmentManager().findFragmentByTag(filesInfo.getType());
        if (sourceObjectType != null) {
            holder = sourceObjectType.getViewHolder(position);
        } else {
            holder = null;
        }
    }

    @Override
    protected void onPreExecute() {
        if (filesDBManager == null) {
            filesDBManager = FilesDBManager.getInstance(context);
        }
        if (!filesDBManager.exists(filesInfo.getSubcat())) {
            cancleBarcodeWorker();
            Toast.makeText(context, Util.Persian(R.string.dfFileDeleted), Toast.LENGTH_SHORT).show();
        }
        PackageDir = ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathFolder));
    }

    public void cancleBarcodeWorker() {
        try {
            cancel(true);
        } catch (Exception ex) {
        }
    }

    @Override
    protected void onCancelled() {
    }

    public void displayDirectoryContents(File dir) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    filesAll.addAll(Arrays.asList(listFilesMatching(file, filesInfo.getSubcat() + "_*\\d*")));
                    displayDirectoryContents(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File[] listFilesMatching(File root, String regex) {
        if (!root.isDirectory()) {
            throw new IllegalArgumentException(root + " is no directory.");
        }
        return root.listFiles(new RegexFileFilter(regex));
    }

    @Override
    protected Void doInBackground(Integer... param) {
        displayDirectoryContents(PackageDir);
        int fileCount = filesAll.size() - 1;
        if (fileCount != 0) {
            for (int i = 0; i <= fileCount; i++) {
                if (filesAll.get(i).exists()) {
                    filesAll.get(i).delete();
                    int progress = 100 * i / fileCount;
                    publishProgress(0, progress);
                }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(final Integer... values) {
        sourceObjectType = (DlFilesListFragment) AppDetailActivity.appDetailInstance().getSupportFragmentManager().findFragmentByTag(filesInfo.getType());
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sourceObjectType != null) {
                        holder = sourceObjectType.getViewHolder(position);
                        if (sourceObjectType.isCurrentListViewItemVisible(position)) {
                            if (holder.getTag().toString().equals(filesInfo.getType())) {
                                holder.progressBar.setProgress(values[1]);
                                holder.tvDownloadPerSize.setText(String.format("%s%%", String.valueOf(values[1])));
                                holder.tvInfo.setText(ApplicationLoader.appInstance().getString(R.string.STATUS_DELETEING));
//                                holder.tvDownload.setTextColor(Util.getColorWrapper(R.color.md_yellow_700));
                                holder.tvDownload.setText(ApplicationLoader.appInstance().getString(R.string.Icon_delete_sweep));
                                holder.tvDownload.setEnabled(false);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        sourceObjectType = (DlFilesListFragment) AppDetailActivity.appDetailInstance().getSupportFragmentManager().findFragmentByTag(filesInfo.getType());
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sourceObjectType != null) {
                        holder = sourceObjectType.getViewHolder(position);
                        if (sourceObjectType.isCurrentListViewItemVisible(position)) {
                            if (holder != null) {
                                if (holder.getTag().toString().equals(filesInfo.getType())) {
                                    holder.tvInfo.setText(ApplicationLoader.appInstance().getString(R.string.dfFileDeleted));
                                    holder.progressBar.setProgress(0);
                                    holder.tvDownload.setText(ApplicationLoader.appInstance().getString(R.string.Icon_download));
                                    sourceObjectType.mAdapter.refreshBlockOverlay(position);
                                    holder.tvDownload.setEnabled(true);
                                }
                            }
                        }
                    }
                }
            });
        }
        filesDBManager.delete(filesInfo.getSubcat());
        filesInfo.setStatus(FilesInfo.STATUS_NOT_DOWNLOAD);
    }
}