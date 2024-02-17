package multithreaddownload.architecture;

import multithreaddownload.DownloadException;

/**
 * Created by mt.karimi on 2015/7/22.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public interface DownloadTask extends Runnable {
    void cancel();

    void pause();

    boolean isDownloading();

    boolean isComplete();

    boolean isPaused();

    boolean isCanceled();

    boolean isFailed();

    @Override
    void run();

    interface OnDownloadListener {
        void onDownloadConnecting();

        void onDownloadProgress(long finished, long length);

        void onDownloadCompleted();

        void onDownloadPaused();

        void onDownloadCanceled();

        void onDownloadFailed(DownloadException de);
    }
}
