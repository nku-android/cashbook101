package devlight.io.sample;

import java.util.Calendar;

public class MyDateUtils {
    public static long getTodayTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }
}
