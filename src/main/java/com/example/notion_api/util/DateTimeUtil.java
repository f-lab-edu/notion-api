package com.example.notion_api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm:ss");

    public static String formatLocalDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
    public static LocalDateTime StringToLocalDateTime(String dateTime){
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        return localDateTime;
    }
}
