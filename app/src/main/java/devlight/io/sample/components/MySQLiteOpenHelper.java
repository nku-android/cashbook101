package devlight.io.sample.components;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.Date;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    @SuppressWarnings("FieldCanBeLocal")
    private static int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "TODO.db";

    private static MySQLiteOpenHelper instance;

    public static MySQLiteOpenHelper getInstance(Context context) {
        if (null == instance) {
            synchronized (MySQLiteOpenHelper.class) {
                if (null == instance) {
                    instance = new MySQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
                }
            }
        }
        return instance;
    }

    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tb_todo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title Text not null," +
                "alert_time INTEGER," +
                "content Text,"+
                "clock Text,"+
                "importance INTEGER default 0,"+
                "alert_time Text," +
                "is_done bool default 0" +
                ");");

        ContentValues cv = new ContentValues();
        cv.put("title", "提醒1");
        cv.put("is_done", true);
        cv.put("alert_time", System.currentTimeMillis() + 3600);
        db.insert("tb_todo", null, cv);

        cv.put("title", "提醒2");
        cv.put("is_done", false);
        cv.put("alert_time", System.currentTimeMillis());
        db.insert("tb_todo", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tb_todo");
        onCreate(db);
    }

}
