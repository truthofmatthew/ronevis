package fragments.db;
/**
 * Created by mt.karimi on 12/12/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fragments.db.dl.FilesInfo;

public class Setting_DBAdapter {
    private static final String TABLE_NAME = FilesInfo.class.getSimpleName();
    private Setting_DBHelper databaseHelper;
    private SQLiteDatabase database;

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (_id integer primary key autoincrement, id text, type text, maincat text, subcat text, title text, name text, name_fa text, image text, url text)");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + TABLE_NAME);
    }

    public Setting_DBAdapter open() throws Exception {
        databaseHelper = new Setting_DBHelper();
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public boolean insertIntoTable() {
        try {
            database.execSQL("insert into casecategory values('5','v2');");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cursor getData() {
        return database.rawQuery("select _id,category_name from casecategory;", null);
    }

    public void close() throws Exception {
        database.close();
    }

    public void insert(FilesInfo info) {
        database.execSQL("insert into "
                        + TABLE_NAME
                        + "(id, type, maincat, subcat, title, name, name_fa, image, url) values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{info.getId(), info.getType(), info.getMaincat(), info.getSubcat(), info.getTitle(), info.getName(), info.getName_fa(), info.getImage(), info.getUrl()});
    }

    public void delete(String subcat) {
        database.execSQL("delete from "
                        + TABLE_NAME
                        + " where subcat = ?",
                new Object[]{subcat});
    }

    public boolean exists(String subcat) {
        Cursor cursor = database.rawQuery("select * from "
                        + TABLE_NAME
                        + " where subcat = ?",
                new String[]{subcat});
        boolean isExists = cursor.moveToNext();
        cursor.close();
        return isExists;
    }
}
