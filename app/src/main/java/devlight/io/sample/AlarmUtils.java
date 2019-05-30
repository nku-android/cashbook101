package devlight.io.sample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public final class AlarmUtils {


    public static void setAlerm(Context context, String action, int id, long timeInMillions, int alarmType) {
        Intent intent = new Intent();
        intent.putExtra("alert_time", timeInMillions);
        intent.setAction(action);

        PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, 0);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(alarmType, -1, sender);

    }
}
