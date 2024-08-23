package com.example.notion_api.service;

import com.example.notion_api.dao.AwsS3DAOImpl;
import com.example.notion_api.dto.pages.CreatePageDTO;
import com.example.notion_api.dto.pages.PageIdTitleDTO;
import com.example.notion_api.dto.pages.PageIdTitleListDTO;
import com.example.notion_api.dto.pages.PageDTO;
import com.example.notion_api.util.DateTimeUtil;
import com.example.notion_api.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PageServiceImpl implements PageService{

    private final AwsS3DAOImpl awsS3DAO;

    @Autowired
    public PageServiceImpl(AwsS3DAOImpl awsS3DAO) {
        this.awsS3DAO = awsS3DAO;
    }

    @Override
    public CreatePageDTO createPage(String userId, String pageType) throws IOException {
        Long pageId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        String title = "no-title";
        String icon = "no-icon";
        String coverImage = "no-coverImage";
        String datetime = DateTimeUtil.formatLocalDateTime(LocalDateTime.now());

        String fileName = new StringBuilder()
                .append(pageId).append("_")
                .append(userId).append("_")
                .append(title).append("_")
                .append(icon).append("_")
                .append(coverImage).append("_")
                .append(datetime)
                .toString();

        String content = PageUtil.getPageContent(pageType);

        awsS3DAO.uploadStringAsFile(userId,fileName,content);
        CreatePageDTO createPageDTO = new CreatePageDTO(pageId, content);
        return createPageDTO;
    }

    @Override
    public PageIdTitleListDTO getPageTitleList(String userId) {
        List<String> fileNames = awsS3DAO.getFileNames(userId,userId);
        List<PageIdTitleDTO> pageIdTitleDTOList = new ArrayList<>();
        for (String fileName : fileNames){
            String[] parts = fileName.split("_");
            String pageId = parts[0];
            String title = parts[2];
            PageIdTitleDTO pageIdTitleDTO = new PageIdTitleDTO(pageId,title);
            pageIdTitleDTOList.add(pageIdTitleDTO);
        }
        return new PageIdTitleListDTO(pageIdTitleDTOList);
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
