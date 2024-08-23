package com.example.notion_api.service;

import com.example.notion_api.dto.pages.CreatePageDTO;
import com.example.notion_api.dto.pages.GetPageListDTO;
import com.example.notion_api.dto.pages.PageDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface PageService {

    CreatePageDTO createPage(String userId, String pageType) throws IOException;

    GetPageListDTO getPageTitleList(String userId);

    PageDTO getPage(String userId,PageDTO pageDTO);

    List<PageDTO> getUpdatedPageByLogin(String userId, List<PageDTO> pageDTOs);

    List<PageDTO> getUpdatedPageByTimer(String userId, List<PageDTO> pageDTOs);

    String deletePage(String userId, String pageId);

    List<PageDTO> createTemplatePages(String userId);

    List<PageDTO> getPagesByVersion(String userId, String pageId);
}
