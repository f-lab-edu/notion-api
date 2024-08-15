package com.example.notion_api.service;

import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.enumpage.PageType;

import java.time.LocalDateTime;
import java.util.List;

public interface PageService {

    /** 계정 생성시 기본적으로 제공하는 페이지 생성 */
    void createDefaultTemplate(String usreId);

    /** 페이지 생성 */
    PageDTO createPage(String userId, String pageType, LocalDateTime localDateTime);

    /** 모든 페이지 목록 가져오기 */
    List<String> getPageList(String userId);

    /** 목록에서 선택한 페이지 렌더링 */
    PageDTO getPage(String userId, String title);

    /** 마지막 업데이트된 시간 체크 */
    LocalDateTime getLastUpdatedDate(String userId);

    /** 페이지 업데이트 */
    List<PageDTO> updatePages(String userId, List<PageDTO> pageDTOS);

    /** 페이지 삭제 */
    void deletePage(String userId, PageDTO pageDTO);
}
