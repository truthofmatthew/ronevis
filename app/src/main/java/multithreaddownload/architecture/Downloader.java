package multithreaddownload.architecture;

/**
 * Created by mt.karimi on 2015/10/29.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public interface Downloader {
    boolean isRunning();

    void start();

    void pause();

    void cancel();

    void onDestroy();

    interface OnDownloaderDestroyedListener {
        void onDestroyed(String key, Downloader downloader);
    }
}
