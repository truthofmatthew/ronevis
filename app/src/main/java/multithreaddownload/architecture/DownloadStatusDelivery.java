package multithreaddownload.architecture;

/**
 * Created by mt.karimi on 2015/7/15.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public interface DownloadStatusDelivery {
    void post(DownloadStatus status);
}
