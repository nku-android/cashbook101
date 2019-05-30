package devlight.io.sample.components;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
        NotificationChannel notificationChannel = new NotificationChannel(context.getPackageName(), "todo_channel", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
        this.notificationManager.createNotificationChannel(notificationChannel);

        Notification notification = new Notification.Builder(context)
                .setChannelId("1")
                .setContentTitle("title")
                .setContentText("content")
                .setSmallIcon(R.drawable.ic_button_on)
                .build();
        this.notificationManager.notify(1, notification);
        Toast.makeText(context, "send notification", Toast.LENGTH_SHORT).show();

    }
}
