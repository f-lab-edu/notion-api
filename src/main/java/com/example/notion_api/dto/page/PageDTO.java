package com.example.notion_api.dto.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {

    private String title;
    private String icon;
    @JsonProperty("background_image")
    private String backgroundImage;
    private MultipartFile iconFile;
    private MultipartFile backgroundFile;
    private LocalDateTime lastUpdated;
    private List<ContentDTO> contents;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContentDTO {
        private String type;
        private String text;
        @JsonProperty("file_name")
        private String fileName;
        private MultipartFile multipartFile;
    }
}
