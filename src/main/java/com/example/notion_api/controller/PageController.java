package com.example.notion_api.controller;


import com.example.notion_api.api.Api;
import com.example.notion_api.dto.page.*;
import com.example.notion_api.exception.CustomJsonMappingException;
import com.example.notion_api.exception.UserNotFoundException;
import com.example.notion_api.repository.UserRepository;
import com.example.notion_api.service.PageService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class PageController {

    private final PageService pageService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    public PageController(PageService pageService, UserRepository userRepository) {
        this.pageService = pageService;
        this.userRepository = userRepository;
    }

    @PostMapping("/page")
    public ResponseEntity<Api<PageDTO>> createPage(
            @RequestParam("user_id") String userId,
            @RequestParam("page_type") String pageType
    ) throws IOException {
        /**
         *  TODO : userId 존재하는지, pageType 일치하는지 확인하는 로직
         * */
        Api<PageDTO> response = null;

        if (!userRepository.existsByUsername(userId)){
            throw new UserNotFoundException("일치하는 아이디가 존재하지 않습니다.");
        }

        PageDTO pageDTO = pageService.createPage(userId, pageType);
        response = Api.<PageDTO>builder()
                .resultCode("200")
                .resultMessage("페이지가 생성되었습니다.")
                .data(pageDTO)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/page/upload")
    public ResponseEntity<Api<PageResponseDTO>> uploadPage(
            @RequestParam("user_id")String userId,
            @RequestParam("json") String json,
            @RequestPart("icon_file") MultipartFile iconFile,
            @RequestPart("background_file") MultipartFile backgroundFile,
            @RequestPart("file_name") List<MultipartFile> multipartFileList
    ){
        try{
            // json 데이터의 PageDTO 맵핑
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            PageDTO pageDTO = objectMapper.readValue(json, PageDTO.class);
            // MultipartFile(아이콘, 배경이미지, 컨텐츠)의 데이터 맵핑
            PageMultipartDTO pageMultipartDTO = new PageMultipartDTO().builder()
                                                    .icon(iconFile)
                                                    .backgroundImage(backgroundFile)
                                                    .contentFileList(multipartFileList)
                                                    .build();

            pageService.uploadPageTemplate(userId,pageDTO);
            PageMultipartUrlDTO pageMultipartUrlDTO = pageService.uploadPageMultipartFile(userId, pageMultipartDTO);
            PageResponseDTO pageResponseDTO = new PageResponseDTO().builder()
                    .pageDTO(pageDTO)
                    .pageMultipartUrlDTO(pageMultipartUrlDTO)
                    .build();
            Api<PageResponseDTO> response = Api.<PageResponseDTO>builder()
                    .resultCode("200")
                    .resultMessage("페이지 업로드를 성공하였습니다.")
                    .data(pageResponseDTO)
                    .build();

            return ResponseEntity.ok(response);
        }catch (JsonParseException e) {
            logger.error("JSON 파싱 오류: JSON 형식이 잘못되었습니다. JSON 데이터: {}", json, e);
            throw new CustomJsonMappingException("JSON 파싱 오류: JSON 형식이 잘못되었습니다. 입력 데이터를 확인하세요.", e);
        } catch (JsonMappingException e) {
            logger.error("JSON 매핑 오류: JSON 구조가 PageDTO와 맞지 않습니다. JSON 데이터: {}", json, e);
            throw new CustomJsonMappingException("JSON 매핑 오류: JSON 구조가 PageDTO와 맞지 않습니다. 입력 데이터를 확인하세요.", e);
        } catch (IOException e) {
            logger.error("입출력 오류: JSON 처리 중 문제가 발생했습니다. 파일 이름: icon_file={}, background_file={}, multipart_files={}",
                    iconFile.getOriginalFilename(), backgroundFile.getOriginalFilename(),
                    multipartFileList.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.toList()), e);
            throw new CustomJsonMappingException("입출력 오류: JSON 처리 중 문제가 발생했습니다.", e);
        }
    }

    @GetMapping("/page/main")
    public ResponseEntity<Api<List<String>>> getMainPage(
        @RequestParam("user_id")String userId
    ){
        /**
         *  메인 페이지 가져올 때.
         *  페이지 목록 가져오기
         *  마지막으로 띄어져 있던 페이지 버전 비교 및 페이지 가져오기
         *
         */
        List<String> allPageList = pageService.getAllPageList(userId);
        Api<List<String>> response = Api.<List<String>>builder()
                .resultCode("200")
                .resultMessage("페이지 업로드를 성공하였습니다.")
                .data(allPageList)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/page/search")
    public ResponseEntity<Api<PageResponseDTO>> getPage(
            @RequestParam("user_id")String userId,
            @RequestParam("json") String json,
            @RequestPart("icon_file") MultipartFile iconFile,
            @RequestPart("background_file") MultipartFile backgroundFile,
            @RequestPart("file_name") List<MultipartFile> multipartFileList
    ){

        try {
            // json 데이터의 PageDTO 맵핑
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            PageDTO pageDTO = objectMapper.readValue(json, PageDTO.class);

            List<String> contentFileNames = multipartFileList.stream()
                    .map(MultipartFile::getOriginalFilename)
                    .collect(Collectors.toList());

            // MultipartFile(아이콘, 배경이미지, 컨텐츠)의 데이터 맵핑
            PageMultipartNameDTO pageMultipartNameDTO = new PageMultipartNameDTO().builder()
                    .iconName(iconFile.getOriginalFilename())
                    .backgroundImageName(backgroundFile.getOriginalFilename())
                    .contentNameList(contentFileNames)
                    .build();

            // 로컬과 서버의 페이지의 최종 업데이트 시간 비교
            if (pageService.isServerPageLastUpdated(userId, pageDTO)){
                // 서버의 페이지가 최신인 경우

                // 서버의 json 페이지와 아이콘, 배경이미지, 컨텐츠의 MultipartFile의 Url 읽어오기
                PageMultipartUrlDTO multipartUrlDTO = pageService.getPageMultipartFile(userId, pageMultipartNameDTO);
                PageDTO resPageDTO = objectMapper.readValue(pageService.getPageAsJsonString(userId,pageDTO.getTitle()),PageDTO.class);
                PageResponseDTO pageResponseDTO = new PageResponseDTO().builder()
                        .pageDTO(resPageDTO)
                        .pageMultipartUrlDTO(multipartUrlDTO)
                        .build();

                Api<PageResponseDTO> response = Api.<PageResponseDTO>builder()
                        .resultCode("200")
                        .resultMessage("서버에 저장된 페이지를 반환합니다.")
                        .data(pageResponseDTO)
                        .build();

                return ResponseEntity.ok(response);
            }else {
                // 로컬의 페이지가 최신인 경우
                PageMultipartDTO pageMultipartDTO = new PageMultipartDTO().builder()
                        .icon(iconFile)
                        .backgroundImage(backgroundFile)
                        .contentFileList(multipartFileList)
                        .build();

                pageService.uploadPageTemplate(userId,pageDTO);
                pageService.uploadPageMultipartFile(userId,pageMultipartDTO);

                pageService.uploadPageTemplate(userId,pageDTO);
                PageMultipartUrlDTO pageMultipartUrlDTO = pageService.uploadPageMultipartFile(userId, pageMultipartDTO);
                PageResponseDTO pageResponseDTO = new PageResponseDTO().builder()
                        .pageDTO(pageDTO)
                        .pageMultipartUrlDTO(pageMultipartUrlDTO)
                        .build();
                Api<PageResponseDTO> response = Api.<PageResponseDTO>builder()
                        .resultCode("200")
                        .resultMessage("로컬의 페이지를 서버에 업데이트 시킵니다.")
                        .data(pageResponseDTO)
                        .build();

                return ResponseEntity.ok(response);
            }


        }catch (CustomJsonMappingException | JsonParseException e ){
            throw new CustomJsonMappingException("JSON 매핑 오류: 입력된 JSON 형식이 잘못되었습니다. 입력 데이터를 확인하세요.", e);
        } catch (IOException e) {
            throw new CustomJsonMappingException("JSON 처리 중 입출력 오류가 발생했습니다.", e);
        }
    }

}
