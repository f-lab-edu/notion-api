package com.example.notion_api.dto.pages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreatePageDTO {
    private String userId;
    private Long paegId;
    private String content;
}
