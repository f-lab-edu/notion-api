package com.example.notion_api.dao;

import com.example.notion_api.dto.page.PageDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface PageDAO {

    /** 특정 사용자에게 기본 템플릿 페이지 생성 */
    List<PageDTO> createDefaultTemplate(String pageType, String userId, String formattedTime);

    /** 페이지 생성 */
    PageDTO createPage(String userId, String pageType, String formattedTime);

    /** 모든 페이지 목록 가져오기 */
    List<String> getPageList(String userId);

    /** 목록에서 선택한 페이지 렌더링 */
    PageDTO getPage(String userId, String title);

    /** 마지막 업데이트된 시간 체크 */
    LocalDateTime getLastUpdatedTIme(String userId);

    /** 페이지 업데이트 */
    List<PageDTO> updatePages(String userId, List<PageDTO> pageDTOs);

    /** 페이지 삭제 */
    void deletePage(String userId, PageDTO pageDTO);
}
