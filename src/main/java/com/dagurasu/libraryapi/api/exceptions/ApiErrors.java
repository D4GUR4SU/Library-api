package com.dagurasu.libraryapi.api.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;

public class ApiErrors {

	private List<String> errors;

	public ApiErrors(BindingResult result) {
		this.errors = new ArrayList<>();
		result.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));
	}
	
	public List<String> getErrors() {
		return errors;
	}
}
