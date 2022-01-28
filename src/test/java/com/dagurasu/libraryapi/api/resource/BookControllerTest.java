package com.dagurasu.libraryapi.api.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dagurasu.libraryapi.api.dto.BookDTO;
import com.dagurasu.libraryapi.api.entity.Book;
import com.dagurasu.libraryapi.api.service.BookService;
import com.dagurasu.libraryapi.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BookControllerTest {

	static String BOOK_API = "/api/books";

	@Autowired
	MockMvc mvc;

	@MockBean
	BookService service;

	@Test
	@DisplayName("Deve criar um livro com sucesso.")
	public void createBookTest() throws Exception {

		BookDTO dto = createNewBook();

		Book savedBook = Book.builder()
				.id(10l)
				.author("Douglas")
				.title("O Mestre do Bug")
				.isbn("001")
				.build();

		BDDMockito.given(service.save(Mockito.any(Book.class)))
					.willReturn(savedBook);
		
		String json = new ObjectMapper().writeValueAsString(dto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);

		mvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("title").value(dto.getTitle()))
				.andExpect(MockMvcResultMatchers.jsonPath("author").value(dto.getAuthor()))
				.andExpect(MockMvcResultMatchers.jsonPath("isbn").value(dto.getIsbn()));

	}

	@Test
	@DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação do livro.")
	public void createInvalidBookTest() throws Exception {

		String json = new ObjectMapper().writeValueAsString(new BookDTO());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);

		mvc.perform(request)
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(3)));
	}

	@Test
	@DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn já utilizado por outro.")
	public void createBookWithDuplicatedIsbn() throws Exception {

		BookDTO dto = createNewBook();
		String json = new ObjectMapper().writeValueAsString(dto);
		String errorMsg = "Isbn já cadastrado.";
		
		BDDMockito.given(service.save(Mockito.any(Book.class)))
					.willThrow(new BusinessException(errorMsg));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);

		mvc.perform(request)
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("errors[0]").value(errorMsg));

	}

	private BookDTO createNewBook() {
		return BookDTO.builder()
				.author("Douglas")
				.title("O Mestre do Bug")
				.isbn("001")
				.build();
	}
}
