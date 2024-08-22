package com.example.notion_api.service;

import com.example.notion_api.dto.teamspace.*;

import java.util.List;

public interface TeamWorkspaceService {

    TeamWorkspaceDTO createTeamWorkspace(TeamWorkspaceDTO teamWorkspaceDTO);

    TeamWorkspacePagesDTO createPageInTeamWorkspace(Long teamspaceId, String pageType);

    TeamWorkspacePagesDTO updatePagesInTeamWorkspace(String userId, TeamWorkspacePagesDTO teamWorkspacePagesDTO);

    String deletePagesInTeamWorkspace(DeletepageDTO deletepageDTO);

    TeamWorkspacePagesDTO createTemplateInTeamWorkspace(Long teamspaceId);

    String addMember(TeamWorkspaceAddMemberDTO teamWorkspaceAddMemberDTO);

    String setMemberAuthority(TeamWorkspaceAuthorityDTO teamWorkspaceAuthorityDTO);

    String deleteTeamWorkspace(Long teamspaceId, String uesrId);

    TeamWorkspaceListDTO getTeamWorkspaceList(String userId);
}
