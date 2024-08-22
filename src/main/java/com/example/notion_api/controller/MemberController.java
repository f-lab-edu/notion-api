package com.example.notion_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class MemberController {

    /**
     * 멤버 추가
     * */
    @PostMapping("/members")
    public void addMember(

    ){

    }

    /**
     * 멤버 목록 가져오기
     * */
    @GetMapping("/members")
    public void getMemberList(

    ){

    }
}
