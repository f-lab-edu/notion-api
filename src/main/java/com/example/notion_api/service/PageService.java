package com.example.notion_api.service;

import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.s3service.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PageService {

    private final S3Service s3Service;

    public PageService(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    public PageDTO createPage(String userId, String pageType) throws JsonProcessingException {

        UUID uuid = UUID.randomUUID();
        PageDTO pageDTO = new PageDTO().builder()
                .title("no-title")
                .icon("no-icon")
                .backgroundImage("no-background-image")
                .iconFile(null)
                .backgroundFile(null)
                .lastUpdated(LocalDateTime.now())
                .contents(null)
                .build();
        String key = uuid + pageDTO.getTitle();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = objectMapper.writeValueAsString(pageDTO);
        s3Service.writeObjectContent(key, jsonString);
        return pageDTO;
    }
}
