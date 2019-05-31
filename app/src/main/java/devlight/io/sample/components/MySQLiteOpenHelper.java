package devlight.io.sample.components;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

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
                "content Text," +
                "clock Long," +
                "importance String default '无', " +
                "alert_time Long," +
                "is_done bool default 0" +
                ");");

        ContentValues cv1 = new ContentValues();
        cv1.put(TodoItem.TITLE, "提醒1");
        cv1.put(TodoItem.IS_DONE, true);
        cv1.put(TodoItem.IMPORTANCE, "无");
        cv1.put(TodoItem.CLOCK, -1);
        cv1.put(TodoItem.CONTENT, " ");
        cv1.put(TodoItem.ALERT_TIME, System.currentTimeMillis());
        db.insert(TodoItem.TABLE_NAME, null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put(TodoItem.TITLE, "提醒2");
        cv2.put(TodoItem.IS_DONE, false);
        cv2.put(TodoItem.IMPORTANCE, "无");
        cv2.put(TodoItem.CLOCK, System.currentTimeMillis() + 5000);
        cv2.put(TodoItem.CONTENT, " ");
        cv2.put(TodoItem.ALERT_TIME, System.currentTimeMillis());
        db.insert(TodoItem.TABLE_NAME, null, cv2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tb_todo");
        onCreate(db);
    }

}
