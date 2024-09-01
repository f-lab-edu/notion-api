package com.example.notion_api.dto.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponseDTO {

    private PageDTO pageDTO;

    @JsonProperty("page_multipart_url")
    private PageMultipartUrlDTO pageMultipartUrlDTO;
}
