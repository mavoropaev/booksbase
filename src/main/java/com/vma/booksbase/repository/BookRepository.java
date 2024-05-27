package com.vma.booksbase.repository;

import com.vma.booksbase.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
