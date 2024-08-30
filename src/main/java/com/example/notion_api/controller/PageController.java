package com.example.notion_api.controller;


import com.example.notion_api.api.Api;
import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/api")
public class PageController {

    private final PageService pageService;

    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @PostMapping("/pages")
    public ResponseEntity<String> createPage(
            @RequestParam("user_id") String userId,
            @RequestParam("page_type") String pageType
    ) throws IOException {
        PageDTO pageDTO = pageService.createPage(userId, pageType);

        // Create a success response
//        Api<PageDTO> response = Api.<PageDTO>builder()
//                .resultCode("200")
//                .resultMessage("페이지가 생성되었습니다.")
//                .data(pageDTO)
//                .build();

        return ResponseEntity.ok("페이지 생성");
    }
}
