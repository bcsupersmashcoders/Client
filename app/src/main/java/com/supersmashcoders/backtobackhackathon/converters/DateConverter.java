package com.supersmashcoders.backtobackhackathon.converters;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public enum DateFormat {
        // "2015-04-12T20:20:50.520Z",
        ISO_FORMAT(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")),
        DATE_FORMAT(new SimpleDateFormat("yyyy-MM-dd"));

        SimpleDateFormat dateFormat;

        DateFormat(SimpleDateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public SimpleDateFormat getDateFormat() {
            return dateFormat;
        }
    }

    public static Date toDate(String dateStr, DateFormat format) {
        Date date = null;
        try {
            date = format.getDateFormat().parse(dateStr);
        } catch (ParseException e) {
            Log.e("DATE ERROR", "Failed to parse date" ,e);
        }
        return date;
    }

    public static String toDisplayString(Date date) {
        return DateFormat.DATE_FORMAT.getDateFormat().format(date);
    }
}
