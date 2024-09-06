package com.example.notion_api.service;

import com.example.notion_api.dao.AwsS3DAOImpl;
import com.example.notion_api.dto.pages.CreatePageDTO;
import com.example.notion_api.dto.pages.GetPageListDTO;
import com.example.notion_api.dto.pages.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PageServiceImpl implements PageService{

    private final AwsS3DAOImpl awsS3DAO;

    @Autowired
    public PageServiceImpl(AwsS3DAOImpl awsS3DAO) {
        this.awsS3DAO = awsS3DAO;
    }

    @Override
    public CreatePageDTO createPage(String userId, String pageType) throws IOException {
        awsS3DAO.updateStringAsFile(userId,"test","test");
        return null;
    }

    @Override
    public GetPageListDTO getPageTitleList(String userId) {
        return null;
    }

    @Override
    public PageDTO getPage(String userId, PageDTO pageDTO) {
        return null;
    }

    @Override
    public List<PageDTO> getUpdatedPageByLogin(String userId, List<PageDTO> pageDTOs) {
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
    public List<PageDTO> createTemplatePages(String userId) {
        return List.of();
    }

    @Override
    public List<PageDTO> getPagesByVersion(String userId, String pageId) {
        return List.of();
    }
}
