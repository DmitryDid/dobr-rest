package Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static final Long NOW = new Date().getTime();
    public static final Long HOUR = 3600000L;
    public static final Long DAY = 86400000L;

    public static String getCurrentDate() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    public static String getDate(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String getDate(Long date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(date));
    }
}
