package devlight.io.sample.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import devlight.io.sample.components.AlarmReceiver;

public final class AlarmUtils {


    public static void setAlarm(Context context, int id, long timeInMillions, int alarmType) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alert_time", timeInMillions);
        intent.setAction("TIMER_ACTION");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(alarmType, timeInMillions, pendingIntent);

    }
}
