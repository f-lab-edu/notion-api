package com.example.notion_api.service;

import com.example.notion_api.dto.user.UserDTO;

public interface UserService {

    /** 회원가입 중 아이디 존재 여부 */
    boolean existsUser(String userId);

    /** 회원가입 성공 */
    UserDTO createUser(UserDTO userDTO);

    /** 회원정보 조회 */
    UserDTO findByUserId(String userId);

    /** 회원정보 변경 */
    UserDTO updateUser(UserDTO userDTO);

    /** 계정 삭제 */
    void deleteUser(String userId);
}
