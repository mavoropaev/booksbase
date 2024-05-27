package com.vma.booksbase.service;

import com.vma.booksbase.service.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO updatedUserDTO);
    void deleteUser(Long id);
}
