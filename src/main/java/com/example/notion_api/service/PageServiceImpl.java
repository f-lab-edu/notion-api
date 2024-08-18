package com.example.notion_api.service;

import com.example.notion_api.dao.AwsS3DAOImpl;
import com.example.notion_api.dao.PageDAO;
import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PageServiceImpl implements PageService{

    @Value("${aws.s3.credentials.bucket}")
    private String bucketName;

    private final AwsS3DAOImpl awsS3DAO;
    private final PageDAO pageDAO;

    @Autowired
    public PageServiceImpl(AwsS3DAOImpl awsS3DAO, PageDAO pageDAO) {
        this.awsS3DAO = awsS3DAO;
        this.pageDAO = pageDAO;
    }

    @Override
    public List<PageDTO> createDefaultTemplate(String userId, String pageType) {
        LocalDateTime pageCreatedTime = LocalDateTime.now();
        String formattedTime = DateTimeUtil.formatDateTime(pageCreatedTime);
        List<PageDTO> pageDTOList = pageDAO.createDefaultTemplate(userId, pageType, formattedTime);
        return pageDTOList;
    }

    @Override
    public PageDTO createPage(String userId, String pageType) throws IOException {
        LocalDateTime pageCreatedTime = LocalDateTime.now();
        String formattedTime = DateTimeUtil.formatDateTime(pageCreatedTime);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setUserId(userId);
        pageDTO.setTitle("no-title");
        pageDTO.setIcon("no-icon");
        pageDTO.setCoverImage("no-image");
        pageDTO.setUpdatedDate(formattedTime);
        bucketName = userId+"_no-title_"+formattedTime;

        String keyName;
        if (pageType.equals("default")){
            keyName = "DefaultPageConfig/DefaultPage";
            pageDTO.setContent(awsS3DAO.downloadFileAsString(bucketName,keyName));
        }else if (pageType.equals("to-do")){
            keyName = "DefaultPageConfig/TodoPage";
            pageDTO.setContent(awsS3DAO.downloadFileAsString(bucketName,keyName));
        }else if (pageType.equals("weekplan")){
            keyName = "DefaultPageConfig/WeekplanPage";
            pageDTO.setContent(awsS3DAO.downloadFileAsString(bucketName,keyName));
        }else if (pageType.equals("diary")){
            keyName = "DefaultPageConfig/DiaryPage";
            pageDTO.setContent(awsS3DAO.downloadFileAsString(bucketName,keyName));
        }else if (pageType.equals("table")){
            keyName = "DefaultPageConfig/TablePage";
            pageDTO.setContent(awsS3DAO.downloadFileAsString(bucketName,keyName));
        }else if (pageType.equals("board")){
            keyName = "DefaultPageConfig/BoardPage";
            pageDTO.setContent(awsS3DAO.downloadFileAsString(bucketName,keyName));
        }else if (pageType.equals("list")){
            keyName = "DefaultPageConfig/ListPage";
            pageDTO.setContent(awsS3DAO.downloadFileAsString(bucketName,keyName));
        }else if (pageType.equals("timeline")){
            keyName = "DefaultPageConfig/TimelinePage";
            pageDTO.setContent(awsS3DAO.downloadFileAsString(bucketName,keyName));
        }else if (pageType.equals("calender")){
            keyName = "DefaultPageConfig/CalenderPage";
            pageDTO.setContent(awsS3DAO.downloadFileAsString(bucketName,keyName));
        }else if (pageType.equals("Gallery")){
            keyName = "DefaultPageConfig/GalleryPage";
            pageDTO.setContent(awsS3DAO.downloadFileAsString(bucketName,keyName));
        }

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
