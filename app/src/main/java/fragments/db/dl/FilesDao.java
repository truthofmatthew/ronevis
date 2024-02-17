package fragments.db.dl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mt.karimi on 15-4-19.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public abstract class FilesDao<T> {
    private FilesDBHelper mHelper;

    public FilesDao(Context context) {
        mHelper = new FilesDBHelper(context);
    }

    protected SQLiteDatabase getWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    protected SQLiteDatabase getReadableDatabase() {
        return mHelper.getReadableDatabase();
    }

    public void close() {
        mHelper.close();
    }
}
