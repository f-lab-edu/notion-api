package com.example.notion_api.dao;

import com.example.notion_api.dto.page.PageDTO;

import java.time.LocalDateTime;
import java.util.List;

public class PageDAOImpl implements PageDAO{


    @Override
    public List<PageDTO> createDefaultTemplate(String userId, String pageType, String formattedTime) {
        return null;
    }

    @Override
    public PageDTO createPage(String userId, String pageType, String formattedTime) {
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
    public LocalDateTime getLastUpdatedTIme(String userId) {
        return null;
    }

    @Override
    public List<PageDTO> updatePages(String userId, List<PageDTO> pageDTOs) {
        return null;
    }

    @Override
    public void deletePage(String userId, PageDTO pageDTO) {

    }
}
