package com.example.notion_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class TeamWorkspaceController {

    /**
     * 팀 워크스페이스 생성
     * */
    @PostMapping("/workspace")
    public void createWorkspace(

    ){

    }

    /**
     * 팀 워크스페이스에서 페이지 생성
     * */
    @PostMapping("/workspace/page")
    public void createPageInWorkspace(

    ){

    }

    /**
     * 팀 워크스페이스에서 페이지 업데이트
     * */
    @PutMapping("/workspace/pages")
    public void pageUpdateInWorkspace(

    ){

    }

    /**
     * 팀 워크스페이스에서 페이지 삭제
     * */
    @DeleteMapping("/workspace/pages")
    public void pageDeleteInWorkspace(

    ){

    }

    /**
     * 팀 워크스페이스 기본 템플릿 생성
     * */
    @PostMapping("/workspace/template")
    public void createTemplateInWorkspace(

    ){

    }

    /**
     * 팀 워크스페이스의 멤버 추가
     * */
    @PostMapping("/workspace/members")
    public void addMembers(

    ){

    }

    /**
     * 멤버 권한 설정
     * */
    @PostMapping("/workspace/authority")
    public void memberAuthority(

    ){

    }

    /**
     * 팀 워크스페이스 삭제
     * */
    @DeleteMapping("/workspace")
    public void deleteWorkspace(

    ){

    }

    /**
     * 팀 워크스페이스 목록 가져오기
     * */
    @GetMapping("/workspace/list")
    public void getWorkspaceList(

    ){

    }

}
