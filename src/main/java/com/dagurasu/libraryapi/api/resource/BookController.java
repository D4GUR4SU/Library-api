package com.dagurasu.libraryapi.api.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dagurasu.libraryapi.api.dto.BookDTO;
import com.dagurasu.libraryapi.api.dto.LoanDTO;
import com.dagurasu.libraryapi.api.model.entity.Book;
import com.dagurasu.libraryapi.api.model.entity.Loan;
import com.dagurasu.libraryapi.api.service.BookService;
import com.dagurasu.libraryapi.api.service.LoanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
//@Api("Book API")
@Slf4j
public class BookController {

	public final BookService service;
	private final ModelMapper modelMapper;
	private final LoanService loanService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	/*
	 * @ApiOperation("Create a book")
	 * 
	 * @ApiResponses({
	 * 
	 * @ApiResponse(code = 201, message = "Book successfully created!") })
	 */
	public BookDTO create(@RequestBody @Valid BookDTO dto) {
		log.info("Create a book for isbn: {} ", dto.getIsbn());
		Book entity = modelMapper.map(dto, Book.class);
		entity = service.save(entity);
		return modelMapper.map(entity, BookDTO.class);
	}

	@GetMapping("{id}")
	//@ApiOperation("Obtains a book details by id")
	public BookDTO get(@PathVariable Long id) {
		log.info("Obtain details for book id {} ", id);
		return service.getById(id).map(book -> modelMapper.map(book, BookDTO.class))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	/*
	 * @ApiOperation("Delete a book by id")
	 * 
	 * @ApiResponses({
	 * 
	 * @ApiResponse(code = 204, message = "Book successfully deleted!") })
	 */
	public void delete(@PathVariable Long id) {
		log.info("Delete book of id {} ", id);
		Book book = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		service.delete(book);
	}

	@PutMapping("{id}")
	/*
	 * @ApiOperation("Update a book")
	 * 
	 * @ApiResponses({
	 * 
	 * @ApiResponse(code = 201, message = "Book successfully created!") })
	 */
	public BookDTO update(@PathVariable Long id,@RequestBody @Valid BookDTO dto) {
		return service.getById(id).map(book -> {
			
			book.setAuthor(dto.getAuthor());
			book.setTitle(dto.getTitle());

			book = service.update(book);
			return modelMapper.map(book, BookDTO.class);

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping
	//@ApiOperation("Find book by params")
	public Page<BookDTO> find(BookDTO dto, Pageable pageRequest) {
		
		Book filter = modelMapper.map(dto, Book.class);
		Page<Book> result = service.find(filter, pageRequest);
		
		List<BookDTO> list = result.getContent()
			.stream()
			.map(entity -> modelMapper.map(entity, BookDTO.class))
			.collect(Collectors.toList());
		
		return new PageImpl<BookDTO>(list, pageRequest, result.getTotalElements());
		
	}
	
	@GetMapping("{id}/loans")
	public Page<LoanDTO> loansByBook(@PathVariable Long id, Pageable pageable) {
		
		Book book = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		Page<Loan> result = loanService.getLoansByBook(book, pageable);
		
		List<LoanDTO> list = result.getContent()
				.stream()
				.map(loan -> {
					Book loanBook = loan.getBook();
					BookDTO bookDTO = modelMapper.map(loanBook, BookDTO.class);
					LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
					loanDTO.setBook(bookDTO);
	
					return loanDTO;
		
				}).collect(Collectors.toList());
		
		return new PageImpl<>(list, pageable, result.getTotalElements());
		
	}
}
