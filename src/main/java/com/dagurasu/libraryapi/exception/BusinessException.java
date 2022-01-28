package com.dagurasu.libraryapi.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String s) {
		super(s);
	}
}
