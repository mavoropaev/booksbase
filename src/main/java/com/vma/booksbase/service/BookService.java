package com.vma.booksbase.service;


import com.vma.booksbase.service.dto.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO createBook(BookDTO bookDto);
    BookDTO updateBook(Long id, BookDTO updateBookDTO);
    void deleteBook(Long id);
    List<BookDTO> findByAuthorNameContaining(String text);

}
