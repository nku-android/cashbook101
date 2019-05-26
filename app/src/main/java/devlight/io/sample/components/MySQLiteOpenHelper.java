package devlight.io.sample.components;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

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

    private MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tb_todo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title Text not null," +
                "alert_time datetime," +
                "is_done bool default 0" +
                ");");

        ContentValues cv = new ContentValues();
        cv.put("title", "提醒1");
        cv.put("is_done", true);
        db.insert("tb_todo", null, cv);

        cv.put("title", "提醒2");
        cv.put("is_done", false);
        db.insert("tb_todo", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
