package fragments.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class Font_DBhelper extends SQLiteOpenHelper {
    public static String DID = "_id";
    public static String FONTPATH = "font_path";
    public static String FONTCAT = "font_category";
    public static String TABLE_NAME = "font_favorite";
    private static String DATABASE_NAME = "ronevisfont.db";
    private static int DATABASE_VERSION = 2;
    private static String CREATE_APPHOME_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + DID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FONTPATH + " VARCHAR,"
            + FONTCAT + " VARCHAR, UNIQUE (font_path) );";
    private static String DROP_APPHOME_TABLE = "DROP TABLE " + TABLE_NAME;

    public Font_DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_APPHOME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_APPHOME_TABLE);
        onCreate(db);
    }
}