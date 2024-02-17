package fragments.db.dl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FilesInfoDao extends FilesDao<FilesInfo> {
    private static final String TABLE_NAME = FilesInfo.class.getSimpleName();

    public FilesInfoDao(Context context) {
        super(context);
    }

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (_id integer primary key autoincrement, id text, type text, maincat text, subcat text, title text, name text, name_fa text, image text, url text);");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + TABLE_NAME);
    }

    public void insert(FilesInfo info) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into "
                        + TABLE_NAME
                        + "(id, type, maincat, subcat, title, name, name_fa, image, url) values(?, ?, ?, ?, ?, ?, ?, ?, ?);",
                new Object[]{info.getId(), info.getType(), info.getMaincat(), info.getSubcat(), info.getTitle(), info.getName(), info.getName_fa(), info.getImage(), info.getUrl()});
    }

    public void delete(String subcat) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "
                        + TABLE_NAME
                        + " where subcat = ?",
                new Object[]{subcat});
    }

    public FilesInfo getThreadInfos(String subcat) {
        FilesInfo info;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "
                        + TABLE_NAME
                        + " where subcat = ?",
                new String[]{subcat});
        while (cursor.moveToNext()) {
            info = new FilesInfo();
            info.setId(cursor.getString(cursor.getColumnIndex("id")));
            info.setType(cursor.getString(cursor.getColumnIndex("type")));
            info.setMaincat(cursor.getString(cursor.getColumnIndex("maincat")));
            info.setSubcat(cursor.getString(cursor.getColumnIndex("subcat")));
            info.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            info.setName(cursor.getString(cursor.getColumnIndex("name")));
            info.setName_fa(cursor.getString(cursor.getColumnIndex("name_fa")));
            info.setImage(cursor.getString(cursor.getColumnIndex("image")));
            info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            return info;
        }
        cursor.close();
        return null;
    }

    public List<FilesInfo> getTypeInfos(String type) {
        List<FilesInfo> OutInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "
                        + TABLE_NAME
                        + " where type = ?",
                new String[]{type});
        while (cursor.moveToNext()) {
            FilesInfo info = new FilesInfo();
            info.setId(cursor.getString(cursor.getColumnIndex("id")));
            info.setType(cursor.getString(cursor.getColumnIndex("type")));
            info.setMaincat(cursor.getString(cursor.getColumnIndex("maincat")));
            info.setSubcat(cursor.getString(cursor.getColumnIndex("subcat")));
            info.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            info.setName(cursor.getString(cursor.getColumnIndex("name")));
            info.setName_fa(cursor.getString(cursor.getColumnIndex("name_fa")));
            info.setImage(cursor.getString(cursor.getColumnIndex("image")));
            info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            OutInfo.add(info);
        }
        cursor.close();
        return OutInfo;
    }

    public boolean exists(String subcat) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "
                        + TABLE_NAME
                        + " where subcat = ?",
                new String[]{subcat});
        boolean isExists = cursor.moveToNext();
        cursor.close();
        return isExists;
    }
}
