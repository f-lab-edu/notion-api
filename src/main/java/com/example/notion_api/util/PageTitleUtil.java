package com.example.notion_api.util;

import java.util.ArrayList;
import java.util.List;

public class PageTitleUtil {
    public static List<String> extractTitles(List<String> keys) {
        List<String> titles = new ArrayList<>();

        for (String key : keys) {
            String[] parts = key.split("_");

            if (parts.length > 1) {
                titles.add(parts[1]);
            }
        }
        return titles;
    }
}
