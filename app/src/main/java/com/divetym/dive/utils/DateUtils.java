package com.divetym.dive.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.divetym.dive.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by kali_root on 4/24/2017.
 */

public class DateUtils extends android.text.format.DateUtils {

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

    private static boolean isWithin(final long millis, final long span, final TimeUnit unit) {
        return System.currentTimeMillis() - millis <= unit.toMillis(span);
    }

    private static boolean isYesterday(final long when) {
        return DateUtils.isToday(when + TimeUnit.DAYS.toMillis(1));
    }

    private static int convertDelta(final long millis, TimeUnit to) {
        return (int) to.convert(System.currentTimeMillis() - millis, TimeUnit.MILLISECONDS);
    }

    private static String getFormattedDateTime(long time, String template, Locale locale) {
        final String localizedPattern = getLocalizedPattern(template, locale);
        return new SimpleDateFormat(localizedPattern, locale).format(new Date(time));
    }

    public static String getBriefRelativeTimeSpanString(final Context c, final Locale locale, final long timestamp) {
        if (isWithin(timestamp, 1, TimeUnit.MINUTES)) {
            return c.getString(R.string.DateUtils_now);
        } else if (isWithin(timestamp, 1, TimeUnit.HOURS)) {
            int mins = convertDelta(timestamp, TimeUnit.MINUTES);
            return c.getResources().getString(R.string.DateUtils_minutes_ago, mins);
        } else if (isWithin(timestamp, 1, TimeUnit.DAYS)) {
            int hours = convertDelta(timestamp, TimeUnit.HOURS);
            return c.getResources().getQuantityString(R.plurals.hours_ago, hours, hours);
        } else if (isWithin(timestamp, 6, TimeUnit.DAYS)) {
            return getFormattedDateTime(timestamp, "EEE", locale);
        } else if (isWithin(timestamp, 365, TimeUnit.DAYS)) {
            return getFormattedDateTime(timestamp, "MMM d", locale);
        } else {
            return getFormattedDateTime(timestamp, "MMM d, yyyy", locale);
        }
    }

    public static String getExtendedRelativeTimeSpanString(final Context c, final Locale locale, final long timestamp) {
        if (isWithin(timestamp, 1, TimeUnit.MINUTES)) {
            return c.getString(R.string.DateUtils_now);
        } else if (isWithin(timestamp, 1, TimeUnit.HOURS)) {
            int mins = (int) TimeUnit.MINUTES.convert(System.currentTimeMillis() - timestamp, TimeUnit.MILLISECONDS);
            return c.getResources().getString(R.string.DateUtils_minutes_ago, mins);
        } else {
            StringBuilder format = new StringBuilder();
            if (isWithin(timestamp, 6, TimeUnit.DAYS)) format.append("EEE ");
            else if (isWithin(timestamp, 365, TimeUnit.DAYS)) format.append("MMM d, ");
            else format.append("MMM d, yyyy, ");

            if (DateFormat.is24HourFormat(c)) format.append("HH:mm");
            else format.append("hh:mm a");

            return getFormattedDateTime(timestamp, format.toString(), locale);
        }
    }

    public static SimpleDateFormat getDetailedDateFormatter(Context context, Locale locale) {
        String dateFormatPattern;

        if (DateFormat.is24HourFormat(context)) {
            dateFormatPattern = getLocalizedPattern("MMM d, yyyy HH:mm:ss zzz", locale);
        } else {
            dateFormatPattern = getLocalizedPattern("MMM d, yyyy hh:mm:ss a zzz", locale);
        }

        return new SimpleDateFormat(dateFormatPattern, locale);
    }

    public static String getRelativeDate(@NonNull Context context,
                                         @NonNull Locale locale,
                                         long timestamp) {
        if (isToday(timestamp)) {
            return context.getString(R.string.DateUtils_today);
        } else if (isYesterday(timestamp)) {
            return context.getString(R.string.DateUtils_yesterday);
        } else {
            return getFormattedDateTime(timestamp, "EEE, MMM d, yyyy", locale);
        }
    }

    private static String getLocalizedPattern(String template, Locale locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return DateFormat.getBestDateTimePattern(locale, template);
        } else {
            return new SimpleDateFormat(template, locale).toLocalizedPattern();
        }
    }

    public static String getDayInMonth(long timestamp, Locale locale) {
        return getFormattedDateTime(timestamp, "dd", locale);
    }

    public static String getDayNameInWeek(long timestamp, Locale locale) {
        return getFormattedDateTime(timestamp, "EEE", locale);
    }

    public static String getMonthInYearWithYear(long timestamp, Locale locale, boolean showYear) {
        return showYear ? getFormattedDateTime(timestamp, "MMM''yy", locale)
                : getFormattedDateTime(timestamp, "MMM", locale);
    }

    public static boolean isThisYear(Calendar calendar) {
        return Calendar.getInstance().get(Calendar.YEAR) == calendar.get(Calendar.YEAR);
    }
}
