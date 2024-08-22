package com.example.notion_api.dto.pages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PageDTO {
    private Long pageId;
    private String title;
    private String icon;
    private String coverImage;
    private LocalDateTime updatedDate;
    private String content;
}
