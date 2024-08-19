package com.example.notion_api.service;

import com.example.notion_api.dao.AwsS3DAOImpl;
import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.util.DateTimeUtil;
import com.example.notion_api.util.PageTitleUtil;
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

    @Autowired
    public PageServiceImpl(AwsS3DAOImpl awsS3DAO) {
        this.awsS3DAO = awsS3DAO;
    }

    @Override
    public List<PageDTO> createDefaultTemplate(String userId, String pageType) {
        LocalDateTime pageCreatedTime = LocalDateTime.now();
        String formattedTime = DateTimeUtil.formatDateTime(pageCreatedTime);
        return null;
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
        // aws s3 서버에 페이지 객체 저장
        String storeKeyName = userId+"_no-title_no-icon_no-image_"+formattedTime;
        awsS3DAO.uploadStringAsFile(bucketName,userId+"/"+storeKeyName,pageDTO.getContent());

        return pageDTO;
    }

    @Override
    public List<String> getPageTitleList(String userId) {
        List<String> tempPageList = awsS3DAO.getListOfKeyName(bucketName,userId);
        List<String> pageTitleList = PageTitleUtil.extractTitles(tempPageList);
        return pageTitleList;
    }

    @Override
    public PageDTO getPage(PageDTO localPageDTO) {
        /** 페이지 버전 비교하여 로컬 또는 서버의 페이지 업데이트 결정. */


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
