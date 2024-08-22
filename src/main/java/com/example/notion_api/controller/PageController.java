package com.example.notion_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class PageController {

    /**
     * 페이지 생성
     * */
    @PostMapping("/page")
    public void createPage(

    ){

    }

    /**
     * 페이지 제목 목록 가져오기
     * */
    @GetMapping("/page/titles")
    public void getPagesList(

    ){

    }

    /**
     * 페이지 가져오기(페이지 1개)
     * */
    @PostMapping("/page/view")
    public void getPage(

    ){

    }

    /**
     * 로그인 시 페이지 업데이트
     * */
    @PutMapping("/pages/event-login")
    public void pageUpdateWhenLogin(

    ){

    }

    /**
     * 특정 시간마다 페이지 업데이트
     * */
    @PutMapping("/pages/event-timeout")
    public void pageUpdateWhenTimeout(

    ){

    }

    /**
     * 페이지 삭제
     * */
    @DeleteMapping("/page")
    public void pageDelete(

    ){

    }

    /**
     * 기본 템플릿 페이지 생성
     * */
    @PostMapping("/pages/template")
    public void createTemplate(

    ){

    }

    /**
     * 버전별 페이지 가져오기
     * */
    @GetMapping("/pages/version-check")
    public void getPagesWithVersions(

    ){

    }
}
