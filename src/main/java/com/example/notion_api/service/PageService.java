package com.example.notion_api.service;

import com.example.notion_api.dto.page.PageDTO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface PageService {

    /** 계정 생성시 기본적으로 제공하는 페이지 생성 */
    List<PageDTO> createDefaultTemplate(String userId, String pageType);

    /** 페이지 생성 */
    PageDTO createPage(String userId, String pageType) throws IOException;

    /** 모든 페이지 목록 가져오기 */
    List<String> getPageTitleList(String userId);

    /** 목록에서 선택한 페이지 렌더링 */
    PageDTO getPage(PageDTO localPageDTO) throws IOException;

    /** 마지막 업데이트된 시간 체크 */
    LocalDateTime getLastUpdatedTIme(String userId);

    /** 페이지 업데이트 */
    List<PageDTO> updatePages(String userId, List<PageDTO> pageDTOs);

    /** 페이지 삭제 */
    void deletePage(String userId, PageDTO pageDTO);
}
