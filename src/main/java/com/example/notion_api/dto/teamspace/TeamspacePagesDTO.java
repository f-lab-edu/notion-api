package com.example.notion_api.dto.teamspace;

import com.example.notion_api.dto.pages.PageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamspacePagesDTO {
    private Long teamspaceId;
    private List<PageDTO> pageDTOs;
}
