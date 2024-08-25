package com.example.notion_api.dto.pages;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yy.MM.dd HH:mm:ss")
    private LocalDateTime updatedDate;
    private String content;
}
