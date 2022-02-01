package com.dagurasu.libraryapi.api.service;

import java.util.Optional;

import com.dagurasu.libraryapi.api.model.entity.Loan;

public interface LoanService {

	Loan save(Loan loan);

	Optional<Loan> getById(Long id);

	Loan update(Loan loan);

}
