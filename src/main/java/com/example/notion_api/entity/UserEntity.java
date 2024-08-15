package com.example.notion_api.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

//@Entity
public class UserEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(nullable = false)
    @NotBlank
    @Size(min = 5, max = 20)
    private String userId;

//    @Column(nullable = false)
    @NotBlank
    @Size(min = 8)
    private String password;

//    @Column(nullable = false)
    private LocalDateTime lastPageUpdated;

//    @Column(nullable = false)
    @NotBlank
    @Size(min = 2,max = 50)
    private String name;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String birth;

    @Pattern(regexp = "^\\d{10,15}$")
    private String phoneNumber;

    @Email
    private String email;
}
