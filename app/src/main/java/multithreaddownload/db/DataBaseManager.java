package multithreaddownload.db;

import android.content.Context;

import java.util.List;

/**
 * Created by mt.karimi on 15-4-19.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class DataBaseManager {
    private static DataBaseManager sDataBaseManager;
    private final ThreadInfoDao mThreadInfoDao;

    private DataBaseManager(Context context) {
        mThreadInfoDao = new ThreadInfoDao(context);
    }

    public static DataBaseManager getInstance(Context context) {
        if (sDataBaseManager == null) {
            sDataBaseManager = new DataBaseManager(context);
        }
        return sDataBaseManager;
    }

    public synchronized void insert(ThreadInfo threadInfo) {
        mThreadInfoDao.insert(threadInfo);
    }

    public synchronized void delete(String tag) {
        mThreadInfoDao.delete(tag);
    }

    public synchronized void update(String tag, int threadId, long finished) {
        mThreadInfoDao.update(tag, threadId, finished);
    }

    public List<ThreadInfo> getThreadInfos(String tag) {
        return mThreadInfoDao.getThreadInfos(tag);
    }

    public List<ThreadInfo> getThreadInfos() {
        return mThreadInfoDao.getThreadInfos();
    }

    public boolean exists(String tag, int threadId) {
        return mThreadInfoDao.exists(tag, threadId);
    }
}
