package com.example.notion_api.service;


import com.example.notion_api.dto.teamworkspace.RequestCreateTeamspaceDTO;
import com.example.notion_api.dto.teamworkspace.ResponseCreateTeamspaceDTO;
import com.example.notion_api.dto.teamworkspace.TeamspaceDTO;
import com.example.notion_api.s3service.S3Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TeamspaceService {

    private final S3Service s3Service;

    private final PageService pageService;

    public TeamspaceService(S3Service s3Service, PageService pageService) {
        this.s3Service = s3Service;
        this.pageService = pageService;
    }

    public ResponseCreateTeamspaceDTO createTeamspace(RequestCreateTeamspaceDTO requestCreateTeamspaceDTO, MultipartFile iconFile) throws IOException {
        UUID uuid = UUID.randomUUID();
        TeamspaceDTO teamspaceDTO = new TeamspaceDTO().builder()
                .teamspaceId(uuid.toString())
                .hostId(requestCreateTeamspaceDTO.getHostId())
                .title(requestCreateTeamspaceDTO.getTitle())
                .comment(requestCreateTeamspaceDTO.getComment())
                .lastUpdated(LocalDateTime.now())
                .permissionLevel(requestCreateTeamspaceDTO.getPermissionLevel())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = objectMapper.writeValueAsString(teamspaceDTO);
        s3Service.writeObjectContent("teamspace/"+uuid+"/"+requestCreateTeamspaceDTO.getHostId()+"/setting/json_setting",jsonString);
        s3Service.uploadFileByPath(iconFile,"teamspace/"+uuid+"/"+requestCreateTeamspaceDTO.getHostId()+"/setting");
        String iconUrl = s3Service.downloadFileAsURL("teamspace/"+uuid+"/"+requestCreateTeamspaceDTO.getHostId()+"/setting/"+iconFile.getOriginalFilename());

        ResponseCreateTeamspaceDTO responseCreateTeamspaceDTO = new ResponseCreateTeamspaceDTO().builder()
                .teamspaceId(uuid.toString())
                .hostId(teamspaceDTO.getHostId())
                .title(teamspaceDTO.getTitle())
                .iconUrl(iconUrl)
                .comment(teamspaceDTO.getComment())
                .lastUpdated(teamspaceDTO.getLastUpdated())
                .permissionLevel(teamspaceDTO.getPermissionLevel())
                .build();

        return responseCreateTeamspaceDTO;
    }
}
