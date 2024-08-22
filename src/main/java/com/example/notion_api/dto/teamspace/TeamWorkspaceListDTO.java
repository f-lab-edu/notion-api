package com.example.notion_api.dto.teamspace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamWorkspaceListDTO {
    private List<Long> teamspaceId;
    private List<String> teamspaceTitle;
}
