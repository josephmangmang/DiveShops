package com.divetym.dive.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kali_root on 4/24/2017.
 */

public class DateUtils {

    public static String formatServerDate(Date date) {
        // server sample time 2017-02-20 23:59:59
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String formatServerDateTime(Date date) {
        // server sample time 2017-02-20 23:59:59
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String formatDisplayDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd yyyy hh:mm a");
        return simpleDateFormat.format(date);
    }
}
