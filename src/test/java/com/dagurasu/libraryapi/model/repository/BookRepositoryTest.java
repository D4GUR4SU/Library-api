package com.dagurasu.libraryapi.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dagurasu.libraryapi.api.model.repository.BookRepository;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	BookRepository repository;

	@Test
	@DisplayName("Deve retornar verdadeiro quando existir um livro com o isbn informado.")
	public void returnTrueWithIsbnExists() {

		String isbn = "123";

		boolean exists = repository.existsByIsbn(isbn);

		assertThat(exists).isTrue();
	}
}
