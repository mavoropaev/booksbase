package com.vma.booksbase.controllers;

import com.vma.booksbase.service.AuthorService;
import com.vma.booksbase.service.dto.AuthorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public List<AuthorDTO> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthorDTO> getAuthorBuId(@PathVariable Long id){

        return ResponseEntity.ok().body(authorService.getAuthorById(id));
    }

    @PostMapping("/author")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) throws URISyntaxException {
        AuthorDTO result = authorService.createAuthor(authorDTO);
        return ResponseEntity.created(new URI("/authors")).body(result);
    }

    @PutMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO){
        return ResponseEntity.ok().body(authorService.updateAuthor(id, authorDTO));
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }

}
