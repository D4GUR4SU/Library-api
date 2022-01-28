package com.dagurasu.libraryapi.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dagurasu.libraryapi.api.entity.Book;
import com.dagurasu.libraryapi.api.model.repository.BookRepository;
import com.dagurasu.libraryapi.api.service.imp.BookServiceImpl;
import com.dagurasu.libraryapi.exception.BusinessException;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BookServiceTest {

	BookService service;

	@MockBean
	BookRepository repository;

	@BeforeEach
	public void init() {
		this.service = new BookServiceImpl(repository);
	}

	@Test
	@DisplayName("Deve salvar um livro.")
	public void saveBookTest() {

		Book book = createValidBook();
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
		Mockito.when(repository.save(book))
				.thenReturn(Book.builder().id(1l).isbn("123").title("As Aventuras").author("Fulano").build());

		Book savedBook = service.save(book);

		assertThat(savedBook.getId()).isNotNull();
		assertThat(savedBook.getIsbn()).isEqualTo("123");
		assertThat(savedBook.getTitle()).isEqualTo("As Aventuras");
		assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
	}

	@Test
	@DisplayName("Deve lançar erro de neǵocio ao tentar salvar um livro com isbn dupolicado.")
	public void shouldNotSaveBookWithDuplicatedISBN() {
		
		Book book = createValidBook();
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);
		
		Throwable exception = Assertions.catchThrowable(() -> service.save(book));
		assertThat(exception)
				.isInstanceOf(BusinessException.class)
				.hasMessage("Isbn já cadastrado.");
		
		Mockito.verify(repository, Mockito.never()).save(book);
		
	}

	private Book createValidBook() {
		return Book.builder().isbn("123").author("Fulano").title("As Aventuras").build();
	}

}
