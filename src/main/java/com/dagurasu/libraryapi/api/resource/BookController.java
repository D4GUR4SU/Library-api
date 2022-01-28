package com.dagurasu.libraryapi.api.resource;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dagurasu.libraryapi.api.dto.BookDTO;
import com.dagurasu.libraryapi.api.entity.Book;
import com.dagurasu.libraryapi.api.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	public BookService service;
	private ModelMapper modelMapper;

	public BookController(BookService service, ModelMapper mapper) {
		this.service = service;
		this.modelMapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody BookDTO dto) {
		
		Book entity = modelMapper.map(dto, Book.class);
		
		entity = service.save(entity);
		
		return modelMapper.map(entity, BookDTO.class);
	}

}
