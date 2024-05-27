package com.vma.booksbase.service.mapper;

import com.vma.booksbase.model.Book;
import com.vma.booksbase.service.dto.BookDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book dtoToModel(BookDTO bookDto);
    BookDTO modelToDto(Book book);
    List<BookDTO> toListDto(List<Book> books);
}
