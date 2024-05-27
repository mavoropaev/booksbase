package com.vma.booksbase.service.mapper;

import com.vma.booksbase.model.User;
import com.vma.booksbase.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToModel(UserDTO userDto);
    UserDTO modelToDto(User user);
    void updateEntityFromDto(UserDTO userDTO, @MappingTarget User user);
    List<UserDTO> toListDto(List<User> users);
}
