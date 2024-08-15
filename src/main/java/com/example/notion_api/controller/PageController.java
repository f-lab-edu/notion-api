package com.example.notion_api.controller;

import com.example.notion_api.service.PageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class PageController {

    @Autowired
    PageServiceImpl pageService;
    /**
     * page 생성 요청 처리
     * */
    @PostMapping("/newpage")
    public String createDefaultPage(
        @RequestParam String userId,
        @RequestParam String pageType
    ){
        /* pageType : to-do, weekplan, diary, table, board,
        *             list, timeline, calender, gallery
        * */
        pageService.createPage(userId, pageType);
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
        return "";
    }

    /**
     * 페이지 데이터 업데이트
     * (로컬과 서버의 업데이트 시간 비교)
     * */
    @PutMapping("/update-page/{userId}")
    public String updatePage(
            @PathVariable String userId
    ){
        return "";
    }

    /**
     * 페이지 삭제
     * */
    @DeleteMapping("/delete-page/{userId}")
    public String deletePage(
            @PathVariable String userId
    ){
        return "";
    }
}
