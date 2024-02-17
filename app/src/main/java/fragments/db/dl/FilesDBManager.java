package fragments.db.dl;

import android.content.Context;

import java.util.List;

/**
 * Created by mt.karimi on 15-4-19.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class FilesDBManager {
    private static FilesDBManager sDataBaseManager;
    private FilesInfoDao mThreadInfoDao;

    public FilesDBManager(Context context) {
        mThreadInfoDao = new FilesInfoDao(context);
    }

    public static FilesDBManager getInstance(Context context) {
        if (sDataBaseManager == null) {
            sDataBaseManager = new FilesDBManager(context);
        }
        return sDataBaseManager;
    }

    public synchronized void insert(FilesInfo threadInfo) {
        mThreadInfoDao.insert(threadInfo);
    }

    public synchronized void delete(String subcat) {
        mThreadInfoDao.delete(subcat);
    }
//    public synchronized void update(String tag, int threadId, long finished) {
//        mThreadInfoDao.update(tag, threadId, finished);
//    }

    public FilesInfo getThreadInfos(String subcat) {
        return mThreadInfoDao.getThreadInfos(subcat);
    }

    public List<FilesInfo> getTypeInfos(String type) {
        return mThreadInfoDao.getTypeInfos(type);
    }

    public boolean exists(String subcat) {
        return mThreadInfoDao.exists(subcat);
    }
}
