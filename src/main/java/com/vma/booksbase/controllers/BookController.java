package com.vma.booksbase.controllers;

import com.vma.booksbase.service.BookService;
import com.vma.booksbase.service.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public List<BookDTO> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){
        return ResponseEntity.ok().body(bookService.getBookById(id));
    }

    @PostMapping("/book")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) throws URISyntaxException {
        BookDTO result = bookService.createBook(bookDTO);
        return ResponseEntity.created(new URI("/books")).body(result);
    }

    @PutMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        return ResponseEntity.ok().body(bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findbooks")
    public List<BookDTO> findBooksByAuthorName(@RequestParam String text) {
        return bookService.findByAuthorNameContaining(text);
    }

}
