package com.example.notion_api.controller;

import com.example.notion_api.api.Api;
import com.example.notion_api.dto.teamworkspace.RequestCreateTeamspaceDTO;
import com.example.notion_api.dto.teamworkspace.ResponseCreateTeamspaceDTO;
import com.example.notion_api.service.TeamspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api")
public class TeamspaceController {

    private final TeamspaceService teamspaceService;

    public TeamspaceController(TeamspaceService teamspaceService) {
        this.teamspaceService = teamspaceService;
    }

    @PostMapping("/teamspace")
    public ResponseEntity<Api<ResponseCreateTeamspaceDTO>> createTeamSpace(
            @RequestPart("request_create_teamspace_dto") RequestCreateTeamspaceDTO requestCreateTeamspaceDTO,
            @RequestPart("icon_file") MultipartFile iconFile
    ) throws IOException {
        ResponseCreateTeamspaceDTO responseCreateTeamspaceDTO = teamspaceService.createTeamspace(requestCreateTeamspaceDTO,iconFile);
        Api<ResponseCreateTeamspaceDTO> response = Api.<ResponseCreateTeamspaceDTO>builder()
                .resultCode("200")
                .resultMessage("팀 스페이스가 생성되었습니다.")
                .data(responseCreateTeamspaceDTO)
                .build();
        return ResponseEntity.ok(response);
    }
}
