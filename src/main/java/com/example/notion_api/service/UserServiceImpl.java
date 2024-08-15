package com.example.notion_api.service;

import com.example.notion_api.dto.user.UserDTO;

import java.util.List;

public class UserServiceImpl implements UserService{
    @Override
    public boolean existsUser(String userId) {
        return false;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO getUser(String userId) {
        return null;
    }

    @Override
    public List<UserDTO> getUserList() {
        return null;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public UserDTO addShareUser(String email) {
        return null;
    }
}
