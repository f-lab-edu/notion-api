package com.example.notion_api.service;

import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.dto.page.PageMultipartDTO;
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
import java.util.UUID;

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
                    .title("no-title")
                    .lastUpdated(LocalDateTime.now())
                    .contents(null)
                    .build();
            String key = uuid + "_"+pageDTO.getTitle();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String jsonString = objectMapper.writeValueAsString(pageDTO);
            s3Service.writeObjectContent("user/"+userId+"/"+key, jsonString);
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
        UUID uuid = UUID.randomUUID();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = objectMapper.writeValueAsString(pageDTO);
        String title = pageDTO.getTitle();
        s3Service.writeObjectContent("user/"+userId+"/"+uuid+"_"+title,jsonString);

    }

    public PageMultipartUrlDTO uploadPageMultipartFile(String userId, PageMultipartDTO pageMultipartDTO) throws IOException {
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
            UUID uuid = UUID.randomUUID();
            iconKeyName = "user/"+userId+"/icon/"+uuid+"_"+pageMultipartDTO.getIcon().getOriginalFilename();
            s3Service.uploadFileWithKeyname(pageMultipartDTO.getIcon(),iconKeyName);

            // 저장된 오브젝트(MultipartFile)를 Url로 다운로드
            pageMultipartUrlDTO.setIconUrl(s3Service.downloadFileAsURL(iconKeyName));
        }
        // /userId/background/ 하위 경로에 'uuid_[배경 이미지 이름]'으로 저장
        if (pageMultipartDTO.getBackgroundImage() != null){
            UUID uuid = UUID.randomUUID();
            backgroundImageKeyName = "user/"+userId+"/background/"+uuid+"_"+pageMultipartDTO.getBackgroundImage().getOriginalFilename();
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
                    UUID uuid = UUID.randomUUID();
                    String contentKeyName = "user/"+userId+"/content";
                    String fileName = uuid+"_"+contentFile.getOriginalFilename();
                    s3Service.uploadFileByPathFileName(contentFile, fileName, contentKeyName);
                    pageMultipartUrlDTO.getContentUrlList().add(s3Service.downloadFileAsURL(contentKeyName+"/"+fileName));
                }
            }
        }

        return pageMultipartUrlDTO;
    }



    public Boolean isServerPageLastUpdated(PageDTO pageDTO){
        return null;
    }
}
