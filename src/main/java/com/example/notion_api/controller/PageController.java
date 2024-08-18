package com.example.notion_api.controller;

import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.formatter.PageVersionDateTimeFormatter;
import com.example.notion_api.service.PageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class PageController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new PageVersionDateTimeFormatter("dd.MM.yy HH:mm:ss"));
    }

    @Autowired
    PageServiceImpl pageService;
    /**
     * page 생성 요청 처리
     * */
    @PostMapping("/new-page")
    public PageDTO createDefaultPage(
        @RequestParam String userId,
        @RequestParam String pageType
    ){
        /* pageType : default, to-do, weekplan, diary, table, board,
        *             list, timeline, calender, gallery
        * */
        PageDTO pageDTO = pageService.createPage(userId, pageType);
        return pageDTO;
    }


    /**
     * 페이지 템플릿 반환(특정 페이지 선택시)
     * */
    @GetMapping("/page")
    public PageDTO getPage(
            @RequestParam String userId,
            @RequestParam String title
    ){
        PageDTO pageDTO = pageService.getPage(userId, title);
        return pageDTO;
    }

    /**
     * 페이지 데이터 업데이트
     * (로컬과 서버의 업데이트 시간 비교)
     * */
    @PutMapping("/update-page/{userId}")
    @ResponseBody
    public ResponseEntity<List<PageDTO>> updatePage(
            @PathVariable String userId,
            @RequestBody List<PageDTO> pageDTOs
    ){
        List<PageDTO> updatedPages = pageService.updatePages(userId,pageDTOs);

        return ResponseEntity.ok(updatedPages);
    }

    /**
     * 페이지 삭제
     * */
    @DeleteMapping("/delete-page/{userId}")
    @ResponseBody
    public ResponseEntity<String> deletePage(
            @PathVariable String userId,
            @RequestBody PageDTO pageDTO
    ){
        pageService.deletePage(userId,pageDTO);
        return ResponseEntity.ok("페이지가 정상적으로 삭제되었습니다.");
    }
}
