package com.example.notion_api.entity;

import com.example.notion_api.annotations.PageVersionDateTimeFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UserEntity {
    private long id;

    @NotBlank
    @Size(min = 5, max = 20)
    private String userId;

    @NotBlank
    @Size(min = 8)
    private String password;

    @PageVersionDateTimeFormat(pattern = "M월 d일 , a h:mm")
    private LocalDateTime lastPageUpdated;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String birth;

    @Pattern(regexp = "^\\d{10,15}$")
    private String phoneNumber;

    @Email
    private String email;
}
