package com.dagurasu.libraryapi.api.service.imp;

import com.dagurasu.libraryapi.api.model.entity.Loan;
import com.dagurasu.libraryapi.api.model.repository.LoanRepository;
import com.dagurasu.libraryapi.api.service.LoanService;

public class LoanServiceImpl implements LoanService {

	private LoanRepository repository;

	public LoanServiceImpl(LoanRepository repository) {
		this.repository = repository;
		
	}
	
	@Override
	public Loan save(Loan loan) {
		return repository.save(loan);
	}

}
