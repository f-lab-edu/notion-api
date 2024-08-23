package com.example.notion_api.service;

import com.example.notion_api.dao.AwsS3DAOImpl;
import com.example.notion_api.dto.pages.CreatePageDTO;
import com.example.notion_api.dto.pages.PageIdTitleDTO;
import com.example.notion_api.dto.pages.PageIdTitleListDTO;
import com.example.notion_api.dto.pages.PageDTO;
import com.example.notion_api.dto.s3.S3FileDTO;
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

    /**
     * 로컬과 s3 파일의 최신 업데이트 날짜를 비교하고, 최신업데이트 날짜에 대한 페이지 파일을 기준으로
     * 업데이트 및 클라이언트에 전송 작업을 한다.
     * */
    @Override
    public PageDTO getPage(String userId, PageDTO pageDTO) throws IOException {
        String prefix = new StringBuilder()
                                .append(pageDTO.getPageId()).append("_")
                                .append(userId).toString();
        S3FileDTO s3FileDTO = awsS3DAO.getFileNameAndContent(userId,prefix);
        String[] fileNameParts = s3FileDTO.getFileName().split("_");
        LocalDateTime s3UpdatedDate = LocalDateTime.parse(fileNameParts[5]);

        if (s3UpdatedDate.isAfter(pageDTO.getUpdatedDate())){
            return new PageDTO(
                    Long.parseLong(fileNameParts[0]),
                    fileNameParts[2],
                    fileNameParts[3],
                    fileNameParts[4],
                    s3UpdatedDate,
                    s3FileDTO.getContent()
            );
        }else {
            String fileName = new StringBuilder().append(pageDTO.getPageId()).append("_")
                    .append(userId).append("_")
                    .append(pageDTO.getTitle()).append("_")
                    .append(pageDTO.getIcon()).append("_")
                    .append(pageDTO.getCoverImage()).append("_")
                    .append(pageDTO.getUpdatedDate()).append("_")
                    .toString();
            awsS3DAO.updateStringAsFile(userId,fileName,pageDTO.getContent());
            return pageDTO;
        }
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
