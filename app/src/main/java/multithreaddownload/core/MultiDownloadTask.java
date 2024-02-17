package multithreaddownload.core;
/**
 * Created by mt.karimi on 2015/7/20.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import multithreaddownload.DownloadInfo;
import multithreaddownload.db.DataBaseManager;
import multithreaddownload.db.ThreadInfo;

/**
 * download thread
 */
public class MultiDownloadTask extends DownloadTaskImpl {
    private DataBaseManager mDBManager;

    public MultiDownloadTask(DownloadInfo downloadInfo, ThreadInfo threadInfo, DataBaseManager dbManager, OnDownloadListener listener) {
        super(downloadInfo, threadInfo, listener);
        this.mDBManager = dbManager;
    }

    @Override
    protected void insertIntoDB(ThreadInfo info) {
        if (!mDBManager.exists(info.getTag(), info.getId())) {
            mDBManager.insert(info);
        }
    }

    @Override
    protected int getResponseCode() {
        return HttpURLConnection.HTTP_PARTIAL;
    }

    @Override
    protected void updateDB(ThreadInfo info) {
        mDBManager.update(info.getTag(), info.getId(), info.getFinished());
    }

    @Override
    protected Map<String, String> getHttpHeaders(ThreadInfo info) {
        Map<String, String> headers = new HashMap<>();
        long start = info.getStart() + info.getFinished();
        long end = info.getEnd();
        headers.put("Range", "bytes=" + start + '-' + end);
        return headers;
    }

    @Override
    protected RandomAccessFile getFile(File dir, String name, long offset) throws IOException {
        File file = new File(dir, name);
        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
        raf.seek(offset);
        return raf;
    }

    @Override
    protected String getTag() {
        return this.getClass().getSimpleName();
    }
}