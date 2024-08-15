package com.example.notion_api.service;

import com.example.notion_api.dto.user.UserDTO;

import java.util.List;

public interface UserService {

    /** 회원가입 중 아이디 존재 여부 체크 */
    boolean existsUser(String userId);

    /** 회원가입 성공 */
    UserDTO createUser(UserDTO userDTO);

    /** 회원정보 조회 */
    UserDTO getUser(String userId);

    /** 모든 계정 정보 가져오기 */
    List<UserDTO> getUserList();

    /** 회원정보 변경 */
    UserDTO updateUser(UserDTO userDTO);

    /** 계정 삭제 */
    void deleteUser(String userId);

    /** 페이지 공유 대상 추가 */
    UserDTO addShareUser(String email);
}
