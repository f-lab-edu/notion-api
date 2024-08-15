package com.example.notion_api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("M월 d일 , a h:mm");

    public static String formatDateTime(LocalDateTime dateTime){
        return dateTime.format(FORMATTER);
    }
}
