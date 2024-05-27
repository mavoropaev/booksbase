package com.vma.booksbase.service;

import com.vma.booksbase.exceptions.UserNotFoundException;
import com.vma.booksbase.model.User;
import com.vma.booksbase.repository.UserRepository;
import com.vma.booksbase.service.UserService;
import com.vma.booksbase.service.dto.UserDTO;
import com.vma.booksbase.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.modelToDto(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.dtoToModel(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.modelToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    userMapper.updateEntityFromDto(userDTO, user);
                    if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                    }
                    User updatedUser = userRepository.save(user);
                    return userMapper.modelToDto(updatedUser);
                })
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
