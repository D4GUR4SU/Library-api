package com.dagurasu.libraryapi.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dagurasu.libraryapi.api.model.entity.Book;
import com.dagurasu.libraryapi.api.model.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

	boolean existsByBookAndNotReturned(Book book);

}
