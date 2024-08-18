package com.example.notion_api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("M월 d일 , a h:mm");

    public static String formatDateTime(LocalDateTime dateTime){
        return dateTime.format(FORMATTER);
    }

    private static LocalDateTime parseDate(String date) throws DateTimeParseException {
        return LocalDateTime.parse(date, FORMATTER);
    }

    public static String getMoreRecentDate(String date1, String date2){
        LocalDateTime dateTime1 = parseDate(date1);
        LocalDateTime dateTime2 = parseDate(date2);

        LocalDateTime moreRecentDate = dateTime1.isAfter(dateTime2) ? dateTime1 : dateTime2;
        return FORMATTER.format(moreRecentDate);
    }
}
