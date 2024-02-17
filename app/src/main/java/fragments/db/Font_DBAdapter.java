package fragments.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.crash.FirebaseCrash;

import org.acra.sender.AcraLSender;

import mt.karimi.ronevis.ApplicationLoader;

public class Font_DBAdapter {
    //    private final TodayDB helper;
    private final Context ourcontext;
    private Font_DBhelper dbhelper;
    private SQLiteDatabase database;

    public Font_DBAdapter(Context c) {
        dbhelper = new Font_DBhelper(c);
        ourcontext = c;
    }

    public Font_DBAdapter open() throws SQLException {
        dbhelper = new Font_DBhelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbhelper.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(Font_DBhelper.TABLE_NAME, null, null);
    }

    public long insertData(String path, String cat) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(Font_DBhelper.FONTPATH, path);
        contentvalues.put(Font_DBhelper.FONTCAT, cat);
        return db.insert(Font_DBhelper.TABLE_NAME, null, contentvalues);
    }

    public long updateData(String path, boolean fav, String cat) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(Font_DBhelper.FONTPATH, path);
        contentvalues.put(Font_DBhelper.FONTCAT, cat);
        return db.update(Font_DBhelper.TABLE_NAME, contentvalues, "font_path = ?", new String[]{path});
    }

    public Cursor readData() {
        Cursor c = null;
        try {
            String[] allColumns = new String[]{
                    Font_DBhelper.DID,
                    Font_DBhelper.FONTPATH,
                    Font_DBhelper.FONTCAT,
            };
            //TODO: no such column: font_category (code 1): , while compiling: SELECT _id, font_path, font_category FROM font_favorite ORDER BY _id ASC
            c = database.query(Font_DBhelper.TABLE_NAME, allColumns, null, null, null, null, Font_DBhelper.DID + " ASC");
            if (c != null) {
                c.moveToFirst();
            }
        } catch (Exception ignored) {
            FirebaseCrash.report(ignored);

        }
        return c;
    }

    public boolean Exists(String searchItem) {
        boolean exists = false;
        try {
            String[] allColumns = new String[]{
                    Font_DBhelper.DID,
                    Font_DBhelper.FONTPATH,
                    Font_DBhelper.FONTCAT,
            };     //TODO: font_category (code 1): , while compiling: SELECT _id, fo
            Cursor c = database.query(Font_DBhelper.TABLE_NAME, allColumns, Font_DBhelper.FONTPATH + "='" + searchItem + "'",
                    null, null, null, null);
            exists = (c.getCount() > 0);
            c.close();
        } catch (Exception ignored) {
            FirebaseCrash.report(ignored);

        }
        return exists;
    }

    public void deleteBook(String removeid) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            db.delete(Font_DBhelper.TABLE_NAME, "font_path = ?", new String[]{removeid});
        } catch (Exception ignored) {
        } finally {
            db.close();
        }
    }
//    static class TodayDB extends SQLiteOpenHelper {
//        public static final String DID = "_id";
//        public static final String FONTPATH = "font_path";
//        public static final String FONTCAT = "font_category";
//        public static final String DATABASE_NAME = "ronevisfont.db";
//        public static final String TABLE_NAME = "font_favorite";
//        public static final int DATABASE_VERSION = 2;
//        public static final String CREATE_APPHOME_TABLE = "CREATE TABLE " + TABLE_NAME + "("
//                + DID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + FONTPATH + " VARCHAR,"
//                + FONTCAT + " VARCHAR, UNIQUE (font_path) );";
//        public static final String DROP_APPHOME_TABLE = "DROP TABLE " + TABLE_NAME;
//
//        public TodayDB(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            try {
//                db.execSQL(CREATE_APPHOME_TABLE);
//            } catch (SQLException ignored) {
//                AcraLSender acraLSender = new AcraLSender();
//                acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "Font_DBAdapter_128");
//            }
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            try {
//                db.execSQL(DROP_APPHOME_TABLE);
//                onCreate(db);
//            } catch (SQLException ignored) {
//                AcraLSender acraLSender = new AcraLSender();
//                acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "Font_DBAdapter_139");
//            }
//        }
//    }
}
