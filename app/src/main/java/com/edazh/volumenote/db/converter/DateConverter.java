package com.edazh.volumenote.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
