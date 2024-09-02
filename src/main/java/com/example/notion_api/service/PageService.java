package com.example.notion_api.service;

import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.dto.page.PageMultipartDTO;
import com.example.notion_api.dto.page.PageMultipartNameDTO;
import com.example.notion_api.dto.page.PageMultipartUrlDTO;
import com.example.notion_api.exception.InvalidPageTypeException;
import com.example.notion_api.s3service.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PageService {

    private final S3Service s3Service;

    public PageService(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    public PageDTO createPage(String userId, String pageType) throws JsonProcessingException {
        if (pageType.equals("default")){
            UUID uuid = UUID.randomUUID();
            PageDTO pageDTO = PageDTO.builder()
                    .pageId(uuid.toString())
                    .title("no-title")
                    .lastUpdated(LocalDateTime.now())
                    .contents(null)
                    .build();
            String key = uuid + "_"+pageDTO.getTitle();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String jsonString = objectMapper.writeValueAsString(pageDTO);
            s3Service.writeObjectContent("user/"+userId+"/page/"+key, jsonString);
            return pageDTO;
        }else {
            throw new InvalidPageTypeException(pageType);
        }
    }

    /**
     * 페이지 업로드의 시나리오
     *
     * TODO : 1. 무조건적으로 페이지를 업데이트 하는 경우
     *           (버전 비교를 하지 않아도 됨.(진짜 괜찮을까? 어디가 변경되었는지 체크하는게 필요하지 않을까?)
     *            페이지를 변경하였을 때 사용됨.
     *            그러나 로컬의 페이지의 데이터가 정상적이지 않은 경우,
     *            서버의 데이터에 추가된 내용 수정 후 돌려줘야 함.)
     *        2. 페이지 버전을 비교하여 업데이트 하는 경우
     *           (버전 비교가 필요함. 로그인 시에나 페이지 수정없이
     *            페이지를 클릭하여 불러올 때, 이러한 작업이 필요함.)
     * */
    public void uploadPageTemplate(String userId, PageDTO pageDTO) throws IOException {

        // 페이지의 전체적인 포맷을 나타내는 json 데이터를 문자열 객체로 변환하여 하나의 파일로 저장.
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = objectMapper.writeValueAsString(pageDTO);
        String title = pageDTO.getTitle();
        s3Service.writeObjectContent("user/"+userId+"/page/"+pageDTO.getPageId()+"_"+title,jsonString);
    }

    public PageMultipartUrlDTO uploadPageMultipartFile(String userId, String id, PageMultipartDTO pageMultipartDTO) throws IOException {
        /**
         *  multipartFile 객체가 null 이면 아무 작업도 하지 않음.
         *  파일을 읽어올 때에만 null 인지 확인하고 null이 아니면 파일을 처리함.
         * */
        // /userId/icon/ 하위 경로에 'uuid_[아이콘 이름]'으로 저장
        String iconKeyName = "";
        String backgroundImageKeyName = "";
        PageMultipartUrlDTO pageMultipartUrlDTO = new PageMultipartUrlDTO();
        if (pageMultipartDTO.getIcon() != null){
            // 경로에 오브젝트 저장

            iconKeyName = "user/"+userId+"/icon/"+id+"_"+pageMultipartDTO.getIcon().getOriginalFilename();
            s3Service.uploadFileWithKeyname(pageMultipartDTO.getIcon(),iconKeyName);

            // 저장된 오브젝트(MultipartFile)를 Url로 다운로드
            pageMultipartUrlDTO.setIconUrl(s3Service.downloadFileAsURL(iconKeyName));
        }
        // /userId/background/ 하위 경로에 'uuid_[배경 이미지 이름]'으로 저장
        if (pageMultipartDTO.getBackgroundImage() != null){
            backgroundImageKeyName = "user/"+userId+"/background/"+id+"_"+pageMultipartDTO.getBackgroundImage().getOriginalFilename();
            s3Service.uploadFileWithKeyname(pageMultipartDTO.getBackgroundImage(),backgroundImageKeyName);

            pageMultipartUrlDTO.setBackgroundImageUrl(s3Service.downloadFileAsURL(backgroundImageKeyName));
        }
        // 페이지의 본문에 해당하는 부분 중, 이미지, 오디오, 동영상 파일에 해당하는 부분을 저장함.
        // 경로는 userId/content/ 하위 경로에 'uuid_[파일 이름]'으로 저장.
        if (pageMultipartDTO.getContentFileList() != null) {
            if (pageMultipartUrlDTO.getContentUrlList() == null) {
                pageMultipartUrlDTO.setContentUrlList(new ArrayList<>());
            }

            for (MultipartFile contentFile : pageMultipartDTO.getContentFileList()) {
                if (contentFile != null) {
                    String contentKeyName = "user/"+userId+"/content";
                    String fileName = id+"_"+contentFile.getOriginalFilename();
                    s3Service.uploadFileByPathFileName(contentFile, fileName, contentKeyName);
                    pageMultipartUrlDTO.getContentUrlList().add(s3Service.downloadFileAsURL(contentKeyName+"/"+fileName));
                }
            }
        }
        return pageMultipartUrlDTO;
    }

    public List<String> getAllPageList(String userId){
        String filePath = "user/"+userId+"/page/";
        List<String> pageList = s3Service.getObjectListByPath(filePath);
        List<String> pageListResult = new ArrayList<>();
        for (String page : pageList) {
            String clearPathAndUUID = page.split("_")[1];
            pageListResult.add(clearPathAndUUID);
        }
        return pageListResult;
    }

    public Boolean isServerPageLastUpdated(String userId, PageDTO pageDTO){
        String title = pageDTO.getTitle();
        LocalDateTime lastUpdated = pageDTO.getLastUpdated();
        LocalDateTime serverFileLastUpdated = s3Service.getSingleLatestModificationDateByName("user/"+userId+"/page",title);
        // 서버의 마지막 수정 시간이 클라이언트의 마지막 업데이트 시간보다 최신인지 확인
        if (serverFileLastUpdated == null) {
            // 서버에서 오브젝트를 찾을 수 없는 경우, 클라이언트의 마지막 업데이트 시간이 더 최신이라고 가정
            return false;
        }
        return serverFileLastUpdated.isAfter(lastUpdated);
    }

    public PageMultipartUrlDTO getPageMultipartFile(String userId, PageMultipartNameDTO pageMultipartNameDTO) throws IOException {
        String backgroundPath = "user/" + userId + "/background/";
        String contentPath = "user/" + userId + "/content/";
        String iconPath = "user/" + userId + "/icon/";

        // 각 경로에서 파일 이름에 해당하는 파일의 URL 가져오기
        Map<String, String> backgroundImageUrls = s3Service.getFileUrlByNameContains(backgroundPath, pageMultipartNameDTO.getBackgroundImageName());
        Map<String, String> contentUrls = s3Service.getFileUrlByNameContainsList(contentPath, pageMultipartNameDTO.getContentNameList());
        Map<String, String> iconUrls = s3Service.getFileUrlByNameContains(iconPath, pageMultipartNameDTO.getIconName());

        // 결과를 PageMultipartUrlDTO로 변환
        return PageMultipartUrlDTO.builder()
                .backgroundImageUrl(backgroundImageUrls.isEmpty() ? null : backgroundImageUrls.values().iterator().next()) // Assuming only one background image
                .contentUrlList(new ArrayList<>(contentUrls.values())) // Assuming multiple content images
                .iconUrl(iconUrls.isEmpty() ? null : iconUrls.values().iterator().next()) // Assuming only one icon image
                .build();
    }

    public String getPageAsJsonString(String userId, String title) throws IOException {
        return s3Service.readObjectContentFromPath("user/"+userId+"/page",title);
    }

    public void deletePage(String userId, String pageId){
        String[] paths = {
                "user/"+userId+"/page/",
                "user/"+userId+"/icon",
                "user/"+userId+"/content/",
                "user/"+userId+"/background/"
        };
        for (String path : paths){
            s3Service.deleteObjects(path, pageId);
        }
    }
}
