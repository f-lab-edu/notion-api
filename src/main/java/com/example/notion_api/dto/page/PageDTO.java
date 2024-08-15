package com.example.notion_api.dto.page;

import com.example.notion_api.annotations.PageVersionDateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageDTO {

    private String title;

    private String icon;

    private String coverImage;

    @PageVersionDateTimeFormat(pattern = "M월 d일 , a h:mm")
    private LocalDateTime updatedDate;

    private String content;

}
