package com.example.notion_api.dto.teamspace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TeamspaceAddMemberDTO {
    private Long teamspaceId;
    private String uesrId;
    private String memberId;
}
