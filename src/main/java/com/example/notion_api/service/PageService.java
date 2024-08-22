package com.example.notion_api.service;

import com.example.notion_api.dto.pages.CreatePageDTO;
import com.example.notion_api.dto.pages.GetPageListDTO;
import com.example.notion_api.dto.pages.PageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageService {

    CreatePageDTO createPage(String uesrId, String pageType);

    GetPageListDTO getPageTilteList(String userId);

    PageDTO getPage(String userId,PageDTO pageDTO);

    List<PageDTO> getUpdatedPageByLogin(String uesrId, List<PageDTO> pageDTOs);

    List<PageDTO> getUpdatedPageByTimer(String userId, List<PageDTO> pageDTOs);

    String deletePage(String userId, String pageId);

    List<PageDTO> createTemplatePages(String uesrId);

    List<PageDTO> getPagesByVersion(String userId, String pageId);
}
