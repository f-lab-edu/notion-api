package com.example.notion_api.dto.page;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class PageDTO {

    private String title;

    private String icon;

    private String coverImage;

    private LocalDateTime updatedDate;

    private String content;

}
