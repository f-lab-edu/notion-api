package com.example.notion_api.formatter;

import com.example.notion_api.annotations.PageVersionDateTimeFormat;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PageVersionDateTimeFormatter implements Formatter<LocalDateTime> {

    private final DateTimeFormatter formatter;

    public PageVersionDateTimeFormatter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text, formatter);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return formatter.format(object);
    }
}
