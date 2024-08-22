package com.example.notion_api.service;

import com.example.notion_api.dto.pages.CreatePageDTO;
import com.example.notion_api.dto.pages.GetPageListDTO;
import com.example.notion_api.dto.pages.PageDTO;

import java.util.List;

public class PageServiceImpl implements PageService{

    @Override
    public CreatePageDTO createPage(String uesrId, String pageType) {
        return null;
    }

    @Override
    public GetPageListDTO getPageTilteList(String userId) {
        return null;
    }

    @Override
    public PageDTO getPage(String userId, PageDTO pageDTO) {
        return null;
    }

    @Override
    public List<PageDTO> getUpdatedPageByLogin(String uesrId, List<PageDTO> pageDTOs) {
        return List.of();
    }

    @Override
    public List<PageDTO> getUpdatedPageByTimer(String userId, List<PageDTO> pageDTOs) {
        return List.of();
    }

    @Override
    public String deletePage(String userId, String pageId) {
        return "";
    }

    @Override
    public List<PageDTO> createTemplatePages(String uesrId) {
        return List.of();
    }

    @Override
    public List<PageDTO> getPagesByVersion(String userId, String pageId) {
        return List.of();
    }
}
