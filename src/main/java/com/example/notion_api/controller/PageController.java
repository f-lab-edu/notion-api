package com.example.notion_api.controller;

import com.example.notion_api.dto.pages.RequestDeletePageDTO;
import com.example.notion_api.dto.pages.PageDTO;
import com.example.notion_api.dto.pages.RequestUpdatePageDTO;
import com.example.notion_api.dto.pages.RequestUpdatePageListDTO;
import com.example.notion_api.service.PageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api")
public class PageController {

    private final PageServiceImpl pageService;

    @Autowired
    public PageController(PageServiceImpl pageService) {
        this.pageService = pageService;
    }

    /*
     * TODO : 인터셉터, exception handler 작성하기.
     *        response 작성하기.
     * */

    /**
     * 페이지 생성
     * */
    @PostMapping("/page")
    public void createPage(
        @RequestParam("user_id") String userId,
        @RequestParam("page_type") String pageType
    ) throws IOException {

        pageService.createPage(userId, pageType);
    }

    /**
     * 페이지 제목 목록 가져오기
     * */
    @GetMapping("/page/titles")
    public void getPagesList(
        @RequestParam("user_id") String userId
    ){
        pageService.getPageTitleList(userId);
    }

    /**
     * 페이지 가져오기(페이지 1개)
     * */
    @PostMapping("/page/view")
    public void getPage(
            @RequestBody RequestUpdatePageDTO request
    ) throws IOException {
        String userId = request.getUserId();
        PageDTO pageDTOs = request.getPageDTO();

        pageService.getPage(userId, pageDTOs);
    }

    /**
     * 로그인 시 페이지 업데이트
     * */
    @PutMapping("/pages/event-login")
    public void pageUpdateWhenLogin(
            @RequestBody RequestUpdatePageListDTO request
    ) throws IOException {
        String userId = request.getUserId();
        List<PageDTO> pageDTOs = request.getPageDTOs();

        pageService.getUpdatedPage(userId, pageDTOs);
    }

    /**
     * 특정 시간마다 페이지 업데이트
     * */
    @PutMapping("/pages/event-timeout")
    public void pageUpdateWhenTimeout(
            @RequestBody RequestUpdatePageListDTO request
    ) throws IOException {
        String userId = request.getUserId();
        List<PageDTO> pageDTOs = request.getPageDTOs();

        pageService.getUpdatedPage(userId, pageDTOs);
    }

    /**
     * 페이지 삭제
     * */
    @DeleteMapping("/page")
    public void pageDelete(
            @RequestBody RequestDeletePageDTO request
    ){
        String userId = request.getUserId();
        String pageId = request.getPageId();
        pageService.deletePage(userId, pageId);
    }

    /**
     * 기본 템플릿 페이지 생성
     * */
    @PostMapping("/pages/template")
    public void createTemplate(
        @RequestParam("user_id") String uesrId
    ) throws IOException {
        pageService.createTemplatePages(uesrId);
    }
}
