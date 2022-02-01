package com.dagurasu.libraryapi.api.service.imp;

import java.util.Optional;

import com.dagurasu.libraryapi.api.model.entity.Loan;
import com.dagurasu.libraryapi.api.model.repository.LoanRepository;
import com.dagurasu.libraryapi.api.service.LoanService;
import com.dagurasu.libraryapi.exception.BusinessException;

public class LoanServiceImpl implements LoanService {

	private LoanRepository repository;

	public LoanServiceImpl(LoanRepository repository) {
		this.repository = repository;
		
	}
	
	@Override
	public Loan save(Loan loan) {
		if(repository.existsByBookAndNotReturned(loan.getBook())) {
			throw new BusinessException("Book already loaned");
		}
		return repository.save(loan);
	}

	@Override
	public Optional<Loan> getById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Loan update(Loan loan) {
		return repository.save(loan);
	}

}
