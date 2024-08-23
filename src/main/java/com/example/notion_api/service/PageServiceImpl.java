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
import java.util.*;

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
        String datetime = DateTimeUtil.formatLocalDateTimeToString(LocalDateTime.now());

        String fileName = getFileName(pageId,userId,title,icon,coverImage,datetime);

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
            String fileName = getFileName(pageDTO.getPageId(),userId,pageDTO.getTitle(),
                                            pageDTO.getIcon(),pageDTO.getCoverImage(),
                                            pageDTO.getUpdatedDate());
            awsS3DAO.updateStringAsFile(userId,fileName,pageDTO.getContent());
            return pageDTO;
        }
    }

    /**
     * 로그인 시 모든 페이지의 업데이트 날짜를 비교하여, 서버와 로컬의 페이지를 업데이트시킨다.
     * 특정 시간마다 페이지들의 업데이트를 동일하게 진행한다.
     * (1)로그인 시, (2)특정 시간마다 페이지 업데이트 진행에 대한 서비스를 하나의 서비스로 처리한다.
     * 로그인 또는 특정 시간을 구분하는 서비스 로직을 UserService에서 처리한다.
     * */
    // TODO : 리팩토링 하기.
    @Override
    public List<PageDTO> getUpdatedPage(String userId, List<PageDTO> pageDTOs) throws IOException {
        List<String> s3StorageFileNames = awsS3DAO.getFileNames(userId,userId);

        // 로컬의 페이지의 날짜값을 담아옴
        Map<Long, LocalDateTime> localUpdateDatesMap = new HashMap<>();
        for (PageDTO pageDTO : pageDTOs){
            localUpdateDatesMap.put(pageDTO.getPageId(),pageDTO.getUpdatedDate());
        }
        // s3 저장소의 페이지의 날짜값을 담아옴
        Map<Long, LocalDateTime> s3UpdatedDatesMap = new HashMap<>();
        for (String fileName : s3StorageFileNames){
            String[] parts = fileName.split("_");
            Long pageId = Long.parseLong(parts[0]);
            LocalDateTime s3UpdatedDate = DateTimeUtil.StringToLocalDateTime(parts[5]);
            s3UpdatedDatesMap.put(pageId, s3UpdatedDate);
        }
        // 로컬과 s3의 업데이트 날짜 비교후 최신의 파일을 담아 클라이언트에 전송할 페이지 리스트
        List<PageDTO> resultPages = new ArrayList<>();

        for (PageDTO pageDTO : pageDTOs){
            // 로컬과 s3의 map에서 최종 업데이트 시간 읽어오기
            Long pageId = pageDTO.getPageId();
            LocalDateTime localDateTime = localUpdateDatesMap.get(pageId);
            LocalDateTime s3UpdatedDate = s3UpdatedDatesMap.get(pageId);
            // TODO : 로컬(s3 저장소)에만 파일이 존재하는 경우에 대한 업데이트 처리 기준?
            // 아래는 일단 두 저장소에 모두 파일이 존재한다고 가정.
            if (s3UpdatedDate.isAfter(localDateTime)){
                // s3의 파일이 최신인 경우, 클라이언트에 업데이트시킬 페이지 추가
                String prefix = new StringBuilder()
                        .append(pageDTO.getPageId()).append("_")
                        .append(userId).toString();
                S3FileDTO s3FileDTO = awsS3DAO.getFileNameAndContent(userId,prefix);
                String[] tempS3FileNameParts = s3FileDTO.getFileName().split("_");
                PageDTO tempPageDTO = new PageDTO(
                        pageId,
                        tempS3FileNameParts[2],
                        tempS3FileNameParts[3],
                        tempS3FileNameParts[4],
                        DateTimeUtil.StringToLocalDateTime(tempS3FileNameParts[5]),
                        s3FileDTO.getContent()
                );
                resultPages.add(tempPageDTO);
            }else {
                // 로컬의 파일이 최신인 경우, s3에 업데이트시킬 페이지 추가.
                // 클라이언트가 최신인 경우에는, 다시 페이지를 보내줄 필요가 없음.
                /* TODO : s3의 저장소의 파일을 모두 업데이트한 후 페이지를 반환하는 것은
                *         클라이언트 측에서 응답이 느릴 수 있으므로 빠른 응답을 위한 방법 고려해보기.
                * */
                String fileName = getFileName(pageId, userId, pageDTO.getTitle(),
                                                pageDTO.getIcon(),pageDTO.getCoverImage(),
                                                localDateTime);
                awsS3DAO.updateStringAsFile(userId,fileName,pageDTO.getContent());
            }
        }
        return resultPages;
    }

    @Override
    public void deletePage(String userId, String pageId) {
        awsS3DAO.deleteFile(userId,pageId);
    }

    @Override
    public List<PageDTO> createTemplatePages(String userId) {
        return List.of();
    }

    @Override
    public List<PageDTO> getPagesByVersion(String userId, String pageId) {
        return List.of();
    }

    private String getFileName(Object pageId, String userId, String title,
                               String icon, String coverImage, Object updateDate){
        return new StringBuilder()
                        .append(pageId).append("_")
                        .append(userId).append("_")
                        .append(title).append("_")
                        .append(icon).append("_")
                        .append(coverImage).append("_")
                        .append(updateDate).toString();
    }
}
