package com.example.notion_api.dto.teamworkspace;


import com.example.notion_api.enums.PermissionLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestCreateTeamspaceDTO {
    @JsonProperty("host_id")
    private String hostId;
    private String title;
    private String comment;
    @JsonProperty("permission_level")
    private PermissionLevel permissionLevel;
}
