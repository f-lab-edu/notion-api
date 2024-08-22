package com.example.notion_api.dto.teamspace;

import com.example.notion_api.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TeamWorkspaceDTO {
    private Long teamspaceId;
    private String masterId;
    private String title;
    private String icon;
    private String comment;
    private String authority;
    private List<MemberDTO> memberDTOs;
}

