package com.example.notion_api.controller;

import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.formatter.PageVersionDateTimeFormatter;
import com.example.notion_api.service.PageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<PageDTO> createPage(
        @RequestParam String userId,
        @RequestParam String pageType
    ) throws IOException {
        /* pageType : default, to-do, weekplan, diary, table, board,
        *             list, timeline, calender, gallery
        * */
        PageDTO pageDTO = pageService.createPage(userId, pageType);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/page/list")
    public ResponseEntity<List<String>> getPageList(
        @RequestParam String userId
    ){
        List<String> pageList = pageService.getPageTitleList(userId);
        return ResponseEntity.ok(pageList);
    }


    /**
     * 페이지 템플릿 반환(특정 페이지 선택시)
     * */
    @PostMapping("/page")
    public ResponseEntity<PageDTO> getPage(
            @RequestBody PageDTO pageDTO
    ) throws IOException {
        PageDTO checkedPageDTO = pageService.getPage(pageDTO);
        return ResponseEntity.ok(checkedPageDTO);
    }

    /**
     * 페이지 데이터 업데이트
     * (로컬과 서버의 업데이트 시간 비교)
     * */
    @PutMapping("/update-pages")
    @ResponseBody
    public ResponseEntity<List<PageDTO>> updatePage(
            @RequestBody List<PageDTO> pageDTOs
    ) throws IOException {
        List<PageDTO> updatedPages = pageService.updatePages(
                                                pageDTOs.get(0).getUserId(),pageDTOs);

        return ResponseEntity.ok(updatedPages);
    }

    /**
     * 페이지 삭제
     * */
    @PostMapping("/delete-page")
    @ResponseBody
    public ResponseEntity<String> deletePage(
            @RequestParam String userId,
            @RequestParam String pageId
    ){
        pageService.deletePage(userId, pageId);
        return ResponseEntity.ok("페이지가 정상적으로 삭제되었습니다.");
    }
}
