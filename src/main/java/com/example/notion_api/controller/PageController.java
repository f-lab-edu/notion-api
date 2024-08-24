package com.example.notion_api.controller;

import com.example.notion_api.api.Api;
import com.example.notion_api.dto.pages.*;
import com.example.notion_api.service.PageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Api<CreatePageDTO>> createPage(
        @RequestParam("user_id") String userId,
        @RequestParam("page_type") String pageType
    ) throws IOException {
        CreatePageDTO createPageDTO = pageService.createPage(userId, pageType);
        Api<CreatePageDTO> response = Api.<CreatePageDTO>builder()
                .resultCode("200")
                .resultMessage("페이지 생성 성공")
                .data(createPageDTO)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 페이지 제목 목록 가져오기
     * */
    @GetMapping("/page/titles")
    public ResponseEntity<Api<PageIdTitleListDTO>> getPagesList(
        @RequestParam("user_id") String userId
    ){
        PageIdTitleListDTO pageIdTitleListDTO =  pageService.getPageTitleList(userId);
        Api<PageIdTitleListDTO> response = Api.<PageIdTitleListDTO>builder()
                .resultCode("200")
                .resultMessage("페이지 제목 리스트 불러오기 성공")
                .data(pageIdTitleListDTO)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 페이지 가져오기(페이지 1개)
     * */
    @PostMapping("/page/view")
    public ResponseEntity<Api<PageDTO>> getPage(
            @RequestBody RequestUpdatePageDTO request
    ) throws IOException {
        String userId = request.getUserId();
        PageDTO pageDTO = request.getPageDTO();

        PageDTO resultPageDTO = pageService.getPage(userId, pageDTO);
        Api<PageDTO> response = Api.<PageDTO>builder()
                .resultCode("200")
                .resultMessage("페이지 업데이트 및 가져오기 성공")
                .data(resultPageDTO)
                .build();
        return ResponseEntity.ok(response);

    }

    /**
     * 로그인 시 페이지 업데이트
     * */
    @PutMapping("/pages/event-login")
    public ResponseEntity<Api<List<PageDTO>>> pageUpdateWhenLogin(
            @RequestBody RequestUpdatePageListDTO request
    ) throws IOException {
        return updatePages(request, "로그인 이벤트");
    }

    /**
     * 특정 시간마다 페이지 업데이트
     * */
    @PutMapping("/pages/event-timeout")
    public ResponseEntity<Api<List<PageDTO>>> pageUpdateWhenTimeout(
            @RequestBody RequestUpdatePageListDTO request
    ) throws IOException {
        return updatePages(request, "타임아웃 이벤트");
    }

    private ResponseEntity<Api<List<PageDTO>>> updatePages(
            RequestUpdatePageListDTO request,
            String eventType
    ) throws IOException {
        String userId = request.getUserId();
        List<PageDTO> pageDTOs = request.getPageDTOs();

        // 페이지 업데이트 및 가져오기
        List<PageDTO> updatedPages = pageService.getUpdatedPage(userId, pageDTOs);

        // 응답 작성
        Api<List<PageDTO>> response = Api.<List<PageDTO>>builder()
                .resultCode("200")
                .resultMessage(eventType + " - 페이지 업데이트 및 가져오기 성공")
                .data(updatedPages)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 페이지 삭제
     * */
    @DeleteMapping("/page")
    public ResponseEntity<Api<Void>> pageDelete(
            @RequestBody RequestDeletePageDTO request
    ){
        String userId = request.getUserId();
        String pageId = request.getPageId();
        pageService.deletePage(userId, pageId);

        Api<Void> response = Api.<Void>builder()
                .resultCode("200")
                .resultMessage("페이지 삭제 성공")
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 기본 템플릿 페이지 생성
     * */
    @PostMapping("/pages/template")
    public ResponseEntity<Api<List<PageDTO>>> createTemplate(
        @RequestParam("user_id") String userId
    ) throws IOException {
        List<PageDTO> createdPages = pageService.createTemplatePages(userId);
        Api<List<PageDTO>> response = Api.<List<PageDTO>>builder()
                .resultCode("200")
                .resultMessage("템플릿 페이지 생성 성공")
                .data(createdPages)
                .build();
        return ResponseEntity.ok(response);
    }
}
