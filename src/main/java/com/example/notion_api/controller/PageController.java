package com.example.notion_api.controller;


import com.example.notion_api.api.Api;
import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.dto.page.PageMultipartDTO;
import com.example.notion_api.dto.page.PageMultipartUrlDTO;
import com.example.notion_api.dto.page.UploadResponseDTO;
import com.example.notion_api.exception.CustomJsonMappingException;
import com.example.notion_api.exception.UserNotFoundException;
import com.example.notion_api.repository.UserRepository;
import com.example.notion_api.service.PageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<Api<UploadResponseDTO>> uploadPage(
            @RequestParam("user_id")String userId,
            @RequestParam("json") String json,
            @RequestPart("icon_file") MultipartFile iconFile,
            @RequestPart("background_file") MultipartFile backgroundFile,
            @RequestPart("file_name") List<MultipartFile> multipartFileList
    ) {

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            PageDTO pageDTO = objectMapper.readValue(json, PageDTO.class);
            PageMultipartDTO pageMultipartDTO = new PageMultipartDTO().builder()
                                                    .icon(iconFile)
                                                    .backgroundImage(backgroundFile)
                                                    .contentFileList(multipartFileList)
                                                    .build();

            pageService.uploadPageTemplate(userId,pageDTO);
            PageMultipartUrlDTO pageMultipartUrlDTO = pageService.uploadPageMultipartFile(userId, pageMultipartDTO);
            UploadResponseDTO uploadResponseDTO = new UploadResponseDTO().builder()
                    .pageDTO(pageDTO)
                    .pageMultipartUrlDTO(pageMultipartUrlDTO)
                    .build();
            Api<UploadResponseDTO> response = Api.<UploadResponseDTO>builder()
                    .resultCode("200")
                    .resultMessage("페이지 업로드를 성공하였습니다.")
                    .data(uploadResponseDTO)
                    .build();

            return ResponseEntity.ok(response);
        }catch (CustomJsonMappingException | JsonParseException e ){
            throw new CustomJsonMappingException("JSON 매핑 오류: 입력된 JSON 형식이 잘못되었습니다. 입력 데이터를 확인하세요.", e);
        } catch (IOException e) {
            throw new CustomJsonMappingException("JSON 처리 중 입출력 오류가 발생했습니다.", e);
        }

    }

}
