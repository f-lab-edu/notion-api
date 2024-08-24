package com.example.notion_api.dto.pages;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RequestUpdatePageDTO {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("page_dto")
    private PageDTO pageDTO;
}
