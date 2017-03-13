package com.codepath.flickster.util;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DateUtil {
    public static CharSequence getRelativeTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        long time = 0;
        try {
            time = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long now = System.currentTimeMillis();

        CharSequence ago =
                DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
        return ago;
    }

    public static String parseReleaseDate(String releaseDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat= new SimpleDateFormat("MMM dd, yyyy");
        Date inputDate = null;
        try {
            inputDate = inputFormat.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(inputDate);
    }
}
