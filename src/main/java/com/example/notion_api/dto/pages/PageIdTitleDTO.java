package com.example.notion_api.dto.pages;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageIdTitleDTO {
    private String pageId;
    private String title;
}
