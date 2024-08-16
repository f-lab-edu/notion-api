package com.example.notion_api.controller;

import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.service.PageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PageController.class)
public class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PageServiceImpl pageService;


    private PageDTO pageDTO1;
    private PageDTO pageDTO2;
    private String userId;
    private String pageType;
    private String title;
    private String content;
    private List<PageDTO> pageDTOList;

    @BeforeEach
    void setUp() {
        userId = "admin";
        pageType = "default";
        title = "페이지 제목";
        content = "<n-title font=\"bold\",sort=\"left\",hint=\"제목없음\",text=\"\"/>\n" +
                    "<n-icon path = \"...\">\n" +
                    "<n-cover path = \"...\">";

        pageDTO1 = new PageDTO();
        pageDTO1.setTitle(title);
        pageDTO1.setIcon("아이콘.png");
        pageDTO1.setCoverImage("커버 이미지.png");
        pageDTO1.setUpdatedDate(LocalDateTime.now());
        pageDTO1.setContent(content);

        pageDTO2 = new PageDTO();
        pageDTO2.setTitle(title);
        pageDTO2.setIcon("아이콘.png");
        pageDTO2.setCoverImage("커버 이미지.png");
        pageDTO2.setUpdatedDate(LocalDateTime.now());
        pageDTO2.setContent(content);

        pageDTOList = Arrays.asList(pageDTO1, pageDTO2);
    }

    @Test
    void createDefaultPageTest() throws Exception {
        when(pageService.createPage(userId, pageType)).thenReturn(pageDTO1);

        mockMvc.perform(post("/api/newpage")
                        .param("userId", userId)
                        .param("pageType", pageType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 상태 코드 검사 추가
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(pageDTO1.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.icon").value(pageDTO1.getIcon()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coverImage").value(pageDTO1.getCoverImage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(pageDTO1.getContent()));
    }

    @Test
    void getPageTest() throws Exception {
        when(pageService.getPage(userId, title)).thenReturn(pageDTO1);

        mockMvc.perform(get("/api/page")
                        .param("userId", userId)
                        .param("title", title)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(pageDTO1.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.icon").value(pageDTO1.getIcon()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coverImage").value(pageDTO1.getCoverImage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(pageDTO1.getContent()));
    }

    @Test
    void deletePageTest() throws Exception {
        doNothing().when(pageService).deletePage(userId, pageDTO1);

        // Perform the DELETE request
        mockMvc.perform(delete("/api/delete-page/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pageDTO1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("페이지가 정상적으로 삭제되었습니다."));
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new AssertionError("JSON 변환 중 오류 발생", e);
        }
    }
}
