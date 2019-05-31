package devlight.io.sample.components;

import android.os.Parcel;
import android.os.Parcelable;

public class TodoItem {
    public static String TABLE_NAME = "tb_todo";
    public static String ID = "id";
    public static String TITLE = "title";
    public static String IS_DONE = "is_done";
    public static String CLOCK = "clock";
    public static String ALERT_TIME = "alert_time";
    public static String IMPORTANCE = "importance";
    public static String CONTENT = "content";

    private TodoItem() {
    }

}
