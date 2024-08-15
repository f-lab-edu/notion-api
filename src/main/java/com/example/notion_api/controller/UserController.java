package com.example.notion_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class UserController {

    /**
     * 회원가입 폼
     * */
    @GetMapping("/register")
    public String showRegistraionForm(

    ){
        return "";
    }

    /**
     * 회원가입 처리
     * */
    @PostMapping("/register")
    public String registerUser(

    ){
        return "";
    }

    /**
     * 회원정보 수정 폼
     * */
    @GetMapping("/update-user/{userId}")
    public String showUpdateUserForm(
            @PathVariable String userId
    ){
        return "";
    }

    /**
     * 회원정보 수정된 폼
     * */
    @GetMapping("/updated-user/{userId}")
    public String showUpdatedUserForm(
            @PathVariable String userId
    ){
        return "";
    }

    /**
     * 회원정보 변경
     * */
    @PutMapping("/update-user/{userId}")
    public String updateUser(
            @PathVariable String userId
    ){
        return "";
    }

    /**
     * 계정 삭제
     * */
    @DeleteMapping("/delete-user/{userId}")
    public String deleteUser(
            @PathVariable String userId
    ){
        return "";
    }



    /**
     * 로그인 폼
     * */
    @GetMapping("/login")
    public String showLoginForm(

    ){
        return "";
    }

    /**
     * 로그인 처리
     * */
    @PostMapping("/login")
    public String login(

    ){
        return "";
    }

    /**
     * 로그아웃 처리
     * */
    @PostMapping("/logout")
    public String logout(

    ){
        return "";
    }
}
