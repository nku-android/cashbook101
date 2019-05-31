package devlight.io.sample.components;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Build;
import android.widget.Toast;

import devlight.io.sample.R;

public class AlarmReceiver extends BroadcastReceiver {
    private final String TAG = getClass().getName();
    private NotificationManager notificationManager = null;

    @TargetApi(Build.VERSION_CODES.P)
    @Override
    public void onReceive(Context context, Intent intent) {

        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(
                context.getPackageName(), "todo_channel", NotificationManager.IMPORTANCE_HIGH);
        this.notificationManager.createNotificationChannel(notificationChannel);

        int id = intent.getIntExtra("id", -1);
        if (id == -1) {
            return;
        }
        ContentValues cv = getTodo(context, id);
        if (cv.getAsBoolean("is_done")) {
            return;
        }

        Notification notification = new Notification.Builder(context)
                .setChannelId(context.getPackageName())
                .setContentTitle(cv.getAsString(TodoItem.TITLE))
                .setContentText(TodoItem.CONTENT)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.ic_button_on)
                .build();
        this.notificationManager.notify(id, notification);
//        Toast.makeText(context, "send notification", Toast.LENGTH_SHORT).show();

    }

    private ContentValues getTodo(Context context, Integer id) {
        MySQLiteOpenHelper dbHelper = MySQLiteOpenHelper.getInstance(context);

        String sql = "SELECT * FROM tb_todo where id==?";

        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql, new String[]{String.valueOf(id)});

        ContentValues cv = new ContentValues();
        while (cursor.moveToNext()) {
            DatabaseUtils.cursorStringToContentValues(cursor, "title", cv);
            DatabaseUtils.cursorLongToContentValues(cursor, "alert_time", cv);
            DatabaseUtils.cursorIntToContentValues(cursor, "id", cv);
            DatabaseUtils.cursorIntToContentValues(cursor, "is_done", cv);
        }
        return cv;
    }
}
