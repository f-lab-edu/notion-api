package com.example.notion_api.dto.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageMultipartDTO {
    private MultipartFile icon;
    @JsonProperty("background_image")
    private MultipartFile backgroundImage;
    private List<MultipartFile> contentFileList;
}
