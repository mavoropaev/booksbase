package com.vma.booksbase.service.mapper;

import com.vma.booksbase.model.Author;
import com.vma.booksbase.service.dto.AuthorDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author dtoToModel(AuthorDTO authorDto);
    AuthorDTO modelToDto(Author author);
    List<AuthorDTO> toListDto(List<Author> authors);

}
