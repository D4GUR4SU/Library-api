package com.dagurasu.libraryapi.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dagurasu.libraryapi.api.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	boolean existsByIsbn(String isbn);

}
