package com.dagurasu.libraryapi.model.repository;

import static com.dagurasu.libraryapi.model.repository.BookRepositoryTest.createNewBook;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dagurasu.libraryapi.api.model.entity.Book;
import com.dagurasu.libraryapi.api.model.entity.Loan;
import com.dagurasu.libraryapi.api.model.repository.LoanRepository;


@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class LoanRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private LoanRepository repository;

	@Test
	@DisplayName("Deve verificar se existe empréstimo não devolvido para o livro ")
	public void existsBookByAndNotReturnedTest() {
		
		Book book = createNewBook("123");
		entityManager.persist(book); 
		
		Loan loan = Loan.builder()
				.book(book)
				.customer("Fulano")
				.loanDate(LocalDate.now())
				.build();
		entityManager.persist(loan);
		
		boolean exists = repository.existsByBookAndNotReturned(book);
		
		assertThat(exists).isTrue();
	}

}
