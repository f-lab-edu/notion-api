package com.example.notion_api.controller;


import com.example.notion_api.dto.user.LoginDTO;
import com.example.notion_api.dto.user.UserDTO;
import com.example.notion_api.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 폼
     * */
    @GetMapping("/register")
    public String showRegistrationForm(
        Model model
    ){
        model.addAttribute("user",new UserDTO());
        /*
        *  회원가입 폼을 보여줌.
        * */
        return "user/register";
    }

    /**
     * 회원가입 처리
     * */
    @PostMapping("/register")
    public String registerUser(){
        /*
        *  1. 회원가입 성공시 로그인 페이지로 이동.
        *
        *  2. 회원가입 실패시 에러 원인 알림.
        *     받은 회원가입 정보 데이터와 함께 회원가입 페이지 렌더링.
        * */
        return "";
    }

    /**
     * 회원정보 수정 폼
     * */
    @GetMapping("/update-user/{userId}")
    public String showUpdateUserForm(
            @PathVariable String userId
    ){
        /*
        *  회원정보 수정 페이지를 렌더링.
        * */
        return "";
    }

    /**
     * 회원정보 수정된 폼
     * */
    @GetMapping("/updated-user/{userId}")
    public String showUpdatedUserForm(
            @PathVariable String userId
    ){
        /*
        *  회원정보 수정 완료된 페이지를 렌더링.
        * */
        return "";
    }

    /**
     * 회원정보 변경
     * 비번, 생일, 이메일, 휴대폰 번호 등..
     * */
    @PutMapping("/update-user/{userId}")
    public String updateUser(
            @PathVariable String userId
    ){
        /*
         *  1. 회원정보 수정 시 수정된 정보를 보여주는 페이지 렌더링
         *
         *  2. 회원정보 수정 실패시 실패 메세지 알림
         * */
        return "";
    }

    /**
     * 계정 삭제
     * */
    @DeleteMapping("/delete-user/{userId}")
    public String deleteUser(
            @PathVariable String userId
    ){
        /*
         * 계정 삭제에 대한 컨트롤러는 오직
         * id만 필요로 함. 다른 Dto 객체가 필요없음.
         * */
        return "";
    }




    /**
     * 로그인 폼
     * */
    @GetMapping("/login")
    public String showLoginForm(){
        return "";
    }

    /**
     * 로그인 처리
     * */
    @PostMapping("/login")
    public String login(
            @RequestBody LoginDTO loginDto
    ){
        return "";
    }

    /**
     * 로그아웃 처리
     * */
    @PostMapping("/logout")
    public String logout(

    ){
        /*
        * 로그아웃에 대한 컨트롤러는 오직
        * id만 필요로 함. 다른 Dto 객체가 필요없음.
        * */
        return "";
    }
}
