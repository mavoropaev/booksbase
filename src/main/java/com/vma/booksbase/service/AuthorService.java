package com.vma.booksbase.service;

import com.vma.booksbase.service.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> getAllAuthors();
    AuthorDTO getAuthorById(Long id);
    AuthorDTO createAuthor(AuthorDTO authorDTO);
    AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO);
    void deleteAuthor(Long id);
}
