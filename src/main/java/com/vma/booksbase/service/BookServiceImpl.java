package com.vma.booksbase.service;

import com.vma.booksbase.exceptions.AuthorNotFoundException;
import com.vma.booksbase.exceptions.BookNotFoundException;
import com.vma.booksbase.model.Author;
import com.vma.booksbase.model.Book;
import com.vma.booksbase.repository.AuthorRepository;
import com.vma.booksbase.repository.BookRepository;
import com.vma.booksbase.service.mapper.BookMapper;
import com.vma.booksbase.service.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final EntityManager entityManager;


    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::modelToDto)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.dtoToModel(bookDTO);
        Author author = authorRepository.findById(bookDTO.getAuthor().getId())
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id " + bookDTO.getAuthor().getId()));
        book.setAuthor(author);
        book = bookRepository.save(book);
        return bookMapper.modelToDto(book);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO updateBookDTO) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updateBookDTO.getTitle());
                    Author author = authorRepository.findById(updateBookDTO.getAuthor().getId())
                            .orElseThrow(() -> new AuthorNotFoundException("Author not found with id " + updateBookDTO.getAuthor().getId()));
                    book.setAuthor(author);
                    book = bookRepository.save(book);
                    return bookMapper.modelToDto(book);
                })
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> findByAuthorNameContaining(String text) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.author.name LIKE :text", Book.class);
        query.setParameter("text", "%" + text + "%");
        return query.getResultList().stream()
                .map(bookMapper::modelToDto)
                .collect(Collectors.toList());
    }
}
