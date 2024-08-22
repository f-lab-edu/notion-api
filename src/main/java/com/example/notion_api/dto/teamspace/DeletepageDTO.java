package com.example.notion_api.dto.teamspace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeletepageDTO {
    private String userId;
    private Long teamspaceId;
    private Long pageId;
}
