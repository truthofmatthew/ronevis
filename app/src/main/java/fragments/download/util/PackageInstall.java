package fragments.download.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.crash.FirebaseCrash;

import org.acra.sender.AcraLSender;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import activities.AppDetailActivity;
import fragments.db.dl.FilesDBManager;
import fragments.db.dl.FilesInfo;
import fragments.download.adapter.DlFilesListAdapter;
import fragments.download.fragment.DlFilesListFragment;
import fragments.tool.Util;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 9/4/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class PackageInstall extends
        AsyncTask<Integer, Integer, Void> {
    private static FilesDBManager filesDBManager;
    public File file;
    public File destination;
    public Context context;
    public FilesInfo filesInfo;
    public int position;
    public String TypeTag;
    public DlFilesListAdapter.AppViewHolder holder = null;
    public DlFilesListFragment sourceObjectType = null;
    public Activity activity = null;

    public PackageInstall(File mfile, File mdestination, Context mcontext, FilesInfo mfilesInfo, int mposition) throws IOException {
        file = mfile;
        destination = mdestination;
        context = mcontext;
        filesInfo = mfilesInfo;
        position = mposition;
        TypeTag = filesInfo.getType();
        activity = AppDetailActivity.appDetailInstance();
        sourceObjectType = (DlFilesListFragment) AppDetailActivity.appDetailInstance().getSupportFragmentManager().findFragmentByTag(TypeTag);
        if (sourceObjectType != null) {
            holder = sourceObjectType.getViewHolder(position);
        } else {
            holder = null;
        }
    }

    @Override
    protected void onPreExecute() {
        if (!FileUtils.isMemorySizeAvailableAndroid(file.length(), true)) {
            cancleBarcodeWorker();
        }
        if (filesDBManager == null) {
            filesDBManager = FilesDBManager.getInstance(context);
        }
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

    @Override
    protected Void doInBackground(Integer... param) {
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(file));
            String workingDir = destination.getAbsolutePath() + '/';
            byte buffer[] = new byte[1024 * 8];
            int bytesRead;
            ZipEntry entry;
            int ff = 0;
            while ((entry = zin.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    File dir = new File(workingDir, entry.getName());
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                } else {
                    FileOutputStream fos = new FileOutputStream(workingDir + entry.getName());
                    BufferedOutputStream bufout = new BufferedOutputStream(fos, buffer.length);
                    while ((bytesRead = zin.read(buffer, 0, buffer.length)) != -1) {
                        bufout.write(buffer, 0, bytesRead);
                    }
                    zin.closeEntry();
                    bufout.flush();
                    bufout.close();
                    fos.flush();
                    fos.close();
                }
                ff += entry.getCompressedSize();
                final int percent = Math.round((float) ff / file.length() * 100f);
                publishProgress(0, percent);
            }
            zin.close();
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception ignored) {
            FirebaseCrash.report(ignored);
            try {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (sourceObjectType.isCurrentListViewItemVisible(position)) {
                                if (holder != null) {
                                    if (holder.getTag().toString().equals(TypeTag)) {
                                        holder.tvInfo.setText(ApplicationLoader.appInstance().getString(R.string.STATUS_INSTALL_ERROR));
                                        holder.tvDownloadPerSize.setText(String.format("%s%%", String.valueOf(0)));
                                        holder.progressBar.setProgress(0);
                                        holder.tvDownload.setEnabled(true);
                                        holder.tvDownload.setTextColor(Util.getColorWrapper(R.color.text_primary_dark));
                                    }
                                }
                            }
                        }
                    });
                }
            } catch (Exception ignored1) {
                AcraLSender acraLSender1 = new AcraLSender();
                acraLSender1.TrySend(ApplicationLoader.appInstance(), ignored, "UnzipUtility_133");
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(final Integer... values) {
        sourceObjectType = (DlFilesListFragment) AppDetailActivity.appDetailInstance().getSupportFragmentManager().findFragmentByTag(TypeTag);
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sourceObjectType != null) {
                        holder = sourceObjectType.getViewHolder(position);
                        if (sourceObjectType.isCurrentListViewItemVisible(position)) {
                            if (holder.getTag().toString().equals(TypeTag)) {
                                holder.progressBar.setProgress(values[1]);
                                holder.tvDownloadPerSize.setText(String.format("%s%%", String.valueOf(values[1])));
                                holder.tvInfo.setText(ApplicationLoader.appInstance().getString(R.string.STATUS_INSTALLING));
                                holder.tvDownload.setText(ApplicationLoader.appInstance().getString(R.string.Icon_file_import));
//                                holder.tvDownload.setTextColor(Util.getColorWrapper(R.color.md_light_blue_700));
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
        sourceObjectType = (DlFilesListFragment) AppDetailActivity.appDetailInstance().getSupportFragmentManager().findFragmentByTag(TypeTag);
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sourceObjectType != null) {
                        holder = sourceObjectType.getViewHolder(position);
                        if (sourceObjectType.isCurrentListViewItemVisible(position)) {
                            if (holder != null) {
                                if (holder.getTag().toString().equals(TypeTag)) {
                                    holder.tvInfo.setText(ApplicationLoader.appInstance().getString(R.string.STATUS_INSTALLED));
                                    holder.tvDownloadPerSize.setText(String.format("%s%%", String.valueOf(100)));
                                    holder.progressBar.setProgress(100);
                                    filesInfo.setStatus(FilesInfo.STATUS_INSTALLED);
                                    holder.tvDownload.setText(ApplicationLoader.appInstance().getString(R.string.Icon_delete));
                                    sourceObjectType.mAdapter.refreshBlockOverlay(position);
                                    holder.tvDownload.setEnabled(true);
                                }
                            }
                        }
                    }
                }
            });
        }
        filesInfo.setStatus(FilesInfo.STATUS_INSTALLED);
        if (!filesDBManager.exists(filesInfo.getSubcat())) {
            filesDBManager.insert(filesInfo);
        }
    }
}