package com.dagurasu.libraryapi.api.service;

import static org.assertj.core.api.Assertions.assertThat;

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
	@DisplayName("Deve salvar um livro")
	public void saveBookTest() {
		
		Book book = Book.builder().isbn("123").author("Fulano").title("As Aventuras").build();
		
		Mockito.when(repository.save(book))
				.thenReturn(Book.builder().id(1l).isbn("123").title("As Aventuras").author("Fulano").build());

		Book savedBook = service.save(book);

		assertThat(savedBook.getId()).isNotNull();
		assertThat(savedBook.getIsbn()).isEqualTo("123");
		assertThat(savedBook.getTitle()).isEqualTo("As Aventuras");
		assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
	}

}
