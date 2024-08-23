package com.example.notion_api.util;

import com.example.notion_api.markdown.MarkdownTemplate;

public class PageUtil {

    public static String getPageContent(String pageType){
        String content;
        switch (pageType) {
            case "board":
                content = MarkdownTemplate.MARKDOWN_TEMPLATE_BOARD;
                break;
            case "calender":
                content = MarkdownTemplate.MARKDOWN_TEMPLATE_CALENDER;
                break;
            case "default":
                content = MarkdownTemplate.MARKDOWN_TEMPLATE_DEFAULT;
                break;
            case "diary":
                content = MarkdownTemplate.MARKDOWN_TEMPLATE_DIARY;
                break;
            case "list":
                content = MarkdownTemplate.MARKDOWN_TEMPLATE_LIST;
                break;
            case "table":
                content = MarkdownTemplate.MARKDOWN_TEMPLATE_TABLE;
                break;
            case "timeline":
                content = MarkdownTemplate.MARKDOWN_TEMPLATE_TIMELINE;
                break;
            case "todo":
                content = MarkdownTemplate.MARKDOWN_TEMPLATE_TODO;
                break;
            case "weekplan":
                content = MarkdownTemplate.MARKDOWN_TEMPLATE_WEEKPLAN;
                break;
            default:
                content = "";  // 기본값 설정 (필요에 따라 수정)
                break;
        }
        return content;

    }
}
