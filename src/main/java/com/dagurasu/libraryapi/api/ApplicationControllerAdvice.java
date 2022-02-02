package com.dagurasu.libraryapi.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.dagurasu.libraryapi.api.exception.ApiErrors;
import com.dagurasu.libraryapi.exception.BusinessException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiErrors handleValidationException(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		return new ApiErrors(bindingResult);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public ApiErrors handleBusinessException(BusinessException ex) {
		return new ApiErrors(ex);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity handleResdponseStatusException(ResponseStatusException ex) {

		return new ResponseEntity(new ApiErrors(ex), ex.getStatus());
	}

}
