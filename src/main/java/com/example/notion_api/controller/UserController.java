package com.example.notion_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {

    /**
     * 회원가입
     * */
    @PostMapping("/auth/signup")
    public void signUp(

    ){

    }

    /**
     * 로그인
     * */
    @PostMapping("/auth/login")
    public void login(

    ){

    }

    /**
     * 로그아웃
     * */
    @GetMapping("/auth/logout")
    public void logout(

    ){

    }

    /**
     * 회원정보 수정
     * */
    @PostMapping("/auth/info")
    public void reviseUserInfo(

    ){

    }

    /**
     * 회원 탈퇴
     * */
    @DeleteMapping("/auth/withdraw")
    public void withdrawUser(

    ){

    }
}

