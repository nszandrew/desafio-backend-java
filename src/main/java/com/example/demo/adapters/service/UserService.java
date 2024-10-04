package com.example.demo.adapters.service;

import com.example.demo.entity.dto.UserRequestDTO;
import com.example.demo.entity.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userDTO);
    UserResponseDTO updateUser(UserRequestDTO userDTO, Long id);
    UserResponseDTO getUserById(Long id);
    void deleteUserById(Long id);
}
