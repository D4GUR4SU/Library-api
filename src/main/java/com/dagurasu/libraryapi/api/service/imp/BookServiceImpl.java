package com.dagurasu.libraryapi.api.service.imp;

import org.springframework.stereotype.Service;

import com.dagurasu.libraryapi.api.entity.Book;
import com.dagurasu.libraryapi.api.model.repository.BookRepository;
import com.dagurasu.libraryapi.api.service.BookService;
import com.dagurasu.libraryapi.exception.BusinessException;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository repository;

	public BookServiceImpl(BookRepository repository) {
		this.repository = repository;
	}

	@Override
	public Book save(Book book) {
		if (repository.existsByIsbn(book.getIsbn())) {
			throw new BusinessException("Isbn já cadastrado.");
		}
		return repository.save(book);
	}

}
