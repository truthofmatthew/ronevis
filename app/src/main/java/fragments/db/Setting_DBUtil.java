package fragments.db;
/**
 * Created by mt.karimi on 12/12/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Setting_DBUtil {
    public static void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    public static void closeDataBase(SQLiteDatabase database) {
        if (database != null) {
            database.close();
        }
    }
}
