package com.vma.booksbase.service;

import com.vma.booksbase.exceptions.AuthorNotFoundException;
import com.vma.booksbase.model.Author;
import com.vma.booksbase.repository.AuthorRepository;
import com.vma.booksbase.service.dto.AuthorDTO;
import com.vma.booksbase.service.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::modelToDto)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id " + id));
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.dtoToModel(authorDTO);
        author = authorRepository.save(author);
        return authorMapper.modelToDto(author);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO) {
        return authorRepository.findById(id)
                .map(author -> {
                    author.setName(updatedAuthorDTO.getName());
                    author = authorRepository.save(author);
                    return authorMapper.modelToDto(author);
                })
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id " + id));
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
