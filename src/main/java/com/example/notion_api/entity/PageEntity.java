package com.example.notion_api.entity;

import com.example.notion_api.annotations.PageVersionDateTimeFormat;

import java.time.LocalDateTime;

public class PageEntity {

    private String userId;

    private String title;

    @PageVersionDateTimeFormat(pattern = "M월 d일 , a h:mm")
    private LocalDateTime updatedDate;

    private String content;
}
