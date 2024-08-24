package com.example.notion_api.service;

import com.example.notion_api.dto.pages.CreatePageDTO;
import com.example.notion_api.dto.pages.PageIdTitleListDTO;
import com.example.notion_api.dto.pages.PageDTO;

import java.io.IOException;
import java.util.List;

public interface PageService {

    CreatePageDTO createPage(String userId, String pageType) throws IOException;

    PageIdTitleListDTO getPageTitleList(String userId);

    PageDTO getPage(String userId,PageDTO pageDTO) throws IOException;

    List<PageDTO> getUpdatedPage(String userId, List<PageDTO> pageDTOs) throws IOException;

    void deletePage(String userId, String pageId);

    List<PageDTO> createTemplatePages(String userId) throws IOException;
}
