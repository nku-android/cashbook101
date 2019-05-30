package devlight.io.sample;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager notificationManager = null;
    private static final int NOTIFICATION_FLAG = 3;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification.Builder(context).setTicker("提醒").build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(NOTIFICATION_FLAG, notification);


    }
}
