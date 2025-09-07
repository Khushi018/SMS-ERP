package com.aristiec.schoolmanagementsystem.service.user;

import com.aristiec.schoolmanagementsystem.dto.LoginRequest;
import com.aristiec.schoolmanagementsystem.modal.user.User;
import com.aristiec.schoolmanagementsystem.modal.user.UserDTO;

import java.util.List;


public interface UserService {
    User createUser(UserDTO user);
    String loginUser(LoginRequest user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    void blockUser(Long id);
}