package devlight.io.sample.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MyDateUtils {

    public static final long MillisInDay = 86400000;

    public static final long MillisInHour = 3600000;

    public static final long MillisInMinute = 60000;

    public static long getTodayTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getDayTimestamp(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month, day);
        return calendar.getTimeInMillis();
    }

    public static String formatDate(String pattern, long timeInMillis) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(timeInMillis));
    }

    public static String formatDate(String pattern, int year, int monthOfYear, int dayOfMonth) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
    }

    public static long parseDate(String pattern, String text, int parsePosition) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).parse(text, new ParsePosition(parsePosition)).getTime();
    }

    public static long parseDate(String pattern, String text) {
        return parseDate(pattern, text, 0);
    }
}
