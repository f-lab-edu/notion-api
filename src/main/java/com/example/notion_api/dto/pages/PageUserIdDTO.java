package com.example.notion_api.dto.pages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageUserIdDTO {
    private String userId;
    private List<PageDTO> pageDTOs;
}
