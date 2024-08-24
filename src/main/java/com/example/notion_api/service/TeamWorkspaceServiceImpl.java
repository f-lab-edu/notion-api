package com.example.notion_api.service;

import com.example.notion_api.dto.teamspace.*;

public class TeamWorkspaceServiceImpl implements TeamWorkspaceService{
    @Override
    public TeamWorkspaceDTO createTeamWorkspace(TeamWorkspaceDTO teamWorkspaceDTO) {
        return null;
    }

    @Override
    public TeamWorkspacePagesDTO createPageInTeamWorkspace(Long teamspaceId, String pageType) {
        return null;
    }

    @Override
    public TeamWorkspacePagesDTO updatePagesInTeamWorkspace(String userId, TeamWorkspacePagesDTO teamWorkspacePagesDTO) {
        return null;
    }

    @Override
    public String deletePagesInTeamWorkspace(TeamWorkspaceDeletepageDTO teamWorkspaceDeletepageDTO) {
        return "";
    }

    @Override
    public TeamWorkspacePagesDTO createTemplateInTeamWorkspace(Long teamspaceId) {
        return null;
    }

    @Override
    public String addMember(TeamWorkspaceAddMemberDTO teamWorkspaceAddMemberDTO) {
        return "";
    }

    @Override
    public String setMemberAuthority(TeamWorkspaceAuthorityDTO teamWorkspaceAuthorityDTO) {
        return "";
    }

    @Override
    public String deleteTeamWorkspace(Long teamspaceId, String uesrId) {
        return "";
    }

    @Override
    public TeamWorkspaceListDTO getTeamWorkspaceList(String userId) {
        return null;
    }
}
