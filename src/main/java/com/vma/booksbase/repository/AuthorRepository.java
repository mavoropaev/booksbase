package com.vma.booksbase.repository;

import com.vma.booksbase.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
