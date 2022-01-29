package com.dagurasu.libraryapi.api.service;

import java.util.Optional;

import com.dagurasu.libraryapi.api.entity.Book;

public interface BookService {

	Book save(Book book);

	Optional<Book> getById(Long id);

	void delete(Book book);

}
