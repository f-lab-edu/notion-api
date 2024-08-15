package com.example.notion_api.controller;

import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.enumpage.PageType;
import com.example.notion_api.service.PageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PageController {


    private final PageServiceImpl pageService;

    /* 팔드를 final로 선언하고, 생성자에 @Autowired로 주입을 하는 이유는
    *  테스트를 고려함. */
    @Autowired
    public PageController(PageServiceImpl pageService) {
        this.pageService = pageService;
    }

    /**
     * page 생성 요청 처리
     * */
    @PostMapping("/defaultpage/{userId}")
    public String createDefaultPage(
            @PathVariable String userId
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        PageDTO pageDTO = pageService.createPage(userId, "default", localDateTime);

        /* how?

           생성한 PageDTO의 title, icon, coverImage를 타임리프 화면으로 렌더링하여 클라이언트에 전송하는 작업.
           + content(markdown)을 적절한 html, css를 적용하여 화면에 표시하는 처리.
        */

        return "";
    }

    @PostMapping("/todo/{userId}")
    public String createTodoPage(
            @PathVariable String userId
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        PageDTO pageDTO = pageService.createPage(userId, "todo", localDateTime);
        return "";
    }

    @PostMapping("/weekplan/{userId}")
    public String createWeekplanPage(
            @PathVariable String userId
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        PageDTO pageDTO = pageService.createPage(userId, "weekplan", localDateTime);
        return "";
    }

    @PostMapping("/diary/{userId}")
    public String createDiaryPage(
            @PathVariable String userId
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        PageDTO pageDTO = pageService.createPage(userId, "diary", localDateTime);
        return "";
    }

    @PostMapping("/table/{userId}")
    public String createTablePage(
            @PathVariable String userId
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        PageDTO pageDTO = pageService.createPage(userId, "table", localDateTime);
        return "";
    }

    @PostMapping("/board/{userId}")
    public String createBoardPage(
            @PathVariable String userId
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        PageDTO pageDTO = pageService.createPage(userId, "board", localDateTime);
        return "";
    }

    @PostMapping("/list/{userId}")
    public String createListPage(
            @PathVariable String userId
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        PageDTO pageDTO = pageService.createPage(userId, "list", localDateTime);
        return "";
    }

    @PostMapping("/timeline/{userId}")
    public String createTimelinePage(
            @PathVariable String userId
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        PageDTO pageDTO = pageService.createPage(userId, "timeline", localDateTime);
        return "";
    }

    @PostMapping("/calender/{userId}")
    public String createCalenderPage(
            @PathVariable String userId
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        PageDTO pageDTO = pageService.createPage(userId, "calender", localDateTime);
        return "";
    }

    @PostMapping("/gallery/{userId}")
    public String createGalleryPage(
            @PathVariable String userId
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        PageDTO pageDTO = pageService.createPage(userId, "gallery", localDateTime);
        return "";
    }



    /**
     * 페이지 템플릿 반환(특정 페이지 선택시)
     * */
    @GetMapping("/page")
    public String getPage(
        @RequestParam String userId,
        @RequestParam String title
    ){
        PageDTO pageDTO = pageService.getPage(userId,title);

        // 로컬과 서버의 페이지 버전 비교 및 버전에 따른 페이지 렌더링.

        return "";
    }

    /**
     * 페이지 데이터 업데이트
     * (로컬과 서버의 업데이트 시간 비교)
     * */
    @PutMapping("/update-page/{userId}")
    public String updatePage(
            @PathVariable String userId,
            @RequestBody Map<String, PageDTO> updatePageMap
    ){
        /*
        *  1. 로컬과 서버의 최종 업데이트된 시간 비교
        *
        *  2. 로컬이 늦은 시간인 경우 업데이트 시간 변경 / 페이지 업데이트 작업
        *
        *  3. 로컬이 빠른 시간인 경우, 클라이언트에 동기화 에러 알림.
        *     다른 로컬의 클라이언트에 접속하여 최종 업데이트 시간 요청!?
        * */
        return "";
    }

    /**
     * 페이지 삭제 api가 계정 삭제 api와 겹치지 않도록 주의
     * */
    @DeleteMapping("/delete-page/{userId}")
    public String deletePage(
            @PathVariable String userId,
            @RequestBody PageDTO pageDto
    ){
        /*
        *  db에서 페이지 삭제
        * */
        return "";
    }

}
