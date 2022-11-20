package com.example.funpark.util;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Classe afin de convertir les date en long pour stocket dasn la base de donnée Room
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long dateLong) {

        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }
}
