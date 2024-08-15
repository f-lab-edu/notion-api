package com.example.notion_api.service;

import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.enumpage.PageType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PageServiceImpl implements PageService{
    @Override
    public void createDefaultTemplate(String usreId) {

    }

    @Override
    public PageDTO createPage(String userId, String pageType, LocalDateTime localDateTime) {

        return null;
    }

    @Override
    public List<String> getPageList(String userId) {
        return null;
    }

    @Override
    public PageDTO getPage(String userId, String title) {
        return null;
    }

    @Override
    public LocalDateTime getLastUpdatedDate(String userId) {
        return null;
    }

    @Override
    public List<PageDTO> updatePages(String userId, List<PageDTO> pageDTOS) {
        return null;
    }

    @Override
    public void deletePage(String userId, PageDTO pageDTO) {

    }
}
