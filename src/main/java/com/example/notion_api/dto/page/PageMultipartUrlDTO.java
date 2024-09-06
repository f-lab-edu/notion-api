package com.example.notion_api.dto.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageMultipartUrlDTO {

    @JsonProperty("icon_url")
    private String iconUrl;
    @JsonProperty("background_image")
    private String backgroundImageUrl;
    @JsonProperty("content_url_list")
    private List<String> contentUrlList;
}
