package com.example.notion_api.dto.pages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RequestUpdatePageListDTO {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("page_dtos")
    private List<PageDTO> pageDTOs;
}
