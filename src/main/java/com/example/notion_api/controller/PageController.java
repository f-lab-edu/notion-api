package com.example.notion_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api")
public class PageController {

    /**
     * page 생성 요청 처리
     * */
    @PostMapping("/defaultpage/{userId}")
    public String createDefaultPage(
        @PathVariable String userId
    ){
        return "";
    }

    @PostMapping("/todo/{userId}")
    public String createTodoPage(
            @PathVariable String userId
    ){
        return "";
    }

    @PostMapping("/weekplan/{userId}")
    public String createWeekplanPage(
            @PathVariable String userId
    ){
        return "";
    }

    @PostMapping("/diary/{userId}")
    public String createDiaryPage(
            @PathVariable String userId
    ){
        return "";
    }

    @PostMapping("/table/{userId}")
    public String createTablePage(
            @PathVariable String userId
    ){
        return "";
    }

    @PostMapping("/board/{userId}")
    public String createBoardPage(
            @PathVariable String userId
    ){
        return "";
    }

    @PostMapping("/list/{userId}")
    public String createListPage(
            @PathVariable String userId
    ){
        return "";
    }

    @PostMapping("/timeline/{userId}")
    public String createTimelinePage(
            @PathVariable String userId
    ){
        return "";
    }

    @PostMapping("/calender/{userId}")
    public String createCalenderPage(
            @PathVariable String userId
    ){
        return "";
    }

    @PostMapping("/gallery/{userId}")
    public String createGalleryPage(
            @PathVariable String userId
    ){
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
