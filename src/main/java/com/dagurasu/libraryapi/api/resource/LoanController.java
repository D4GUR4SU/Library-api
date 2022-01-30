package com.dagurasu.libraryapi.api.resource;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dagurasu.libraryapi.api.dto.LoanDTO;
import com.dagurasu.libraryapi.api.model.entity.Book;
import com.dagurasu.libraryapi.api.model.entity.Loan;
import com.dagurasu.libraryapi.api.service.BookService;
import com.dagurasu.libraryapi.api.service.LoanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

	private final LoanService service;
	private final BookService bookService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Long create(@RequestBody LoanDTO dto) {

		Book book = bookService.getBookByIsbn(dto.getIsbn()).get();
		Loan entity = Loan.builder().book(book).customer(dto.getCustomer()).loanDate(LocalDate.now()).build();

		entity = service.save(entity);

		return entity.getId();
	}
}
