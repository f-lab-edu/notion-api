package com.example.notion_api.entity;

import java.time.LocalDateTime;

//@Entity
public class PageEntity {


    private String userId;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

//    @Column(nullable = false)
    private String title;

//    @Column(nullable = false)
    private LocalDateTime updatedDate;

    private String content;
}
