package com.example.notion_api.service;

import com.example.notion_api.dao.PageDAO;
import com.example.notion_api.dto.page.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class PageServiceImpl implements PageService{

    private final PageDAO pageDAO;

    @Autowired
    public PageServiceImpl(PageDAO pageDAO) {
        this.pageDAO = pageDAO;
    }

    @Override
    public List<PageDTO> createDefaultTemplate(String pageType, String userId) {
        LocalDateTime pageCreatedTime = LocalDateTime.now();
        List<PageDTO> pageDTOList = pageDAO.createDefaultTemplate(pageType, userId, pageCreatedTime);
        return pageDTOList;
    }

    @Override
    public PageDTO createPage(String userId, String pageType) {
        LocalDateTime pageCreatedTime = LocalDateTime.now();
        PageDTO pageDTO = pageDAO.createPage(userId,pageType, pageCreatedTime);
        return pageDTO;
    }

    @Override
    public List<String> getPageList(String userId) {
        List<String> pageList = pageDAO.getPageList(userId);
        return pageList;
    }

    @Override
    public PageDTO getPage(String userId, String title) {
        PageDTO pageDTO = pageDAO.getPage(userId, title);
        return pageDTO;
    }

    @Override
    public LocalDateTime getLastUpdatedTIme(String userId) {
        LocalDateTime lastUpdatedTime = pageDAO.getLastUpdatedTIme(userId);
        return lastUpdatedTime;
    }

    @Override
    public List<PageDTO> updatePages(String userId, List<PageDTO> pageDTOs) {
        List<PageDTO> updatedPages = pageDAO.updatePages(userId, pageDTOs);
        return updatedPages;
    }

    @Override
    public void deletePage(String userId, PageDTO pageDTO) {
        pageDAO.deletePage(userId,pageDTO);
    }
}
