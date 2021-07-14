package com.online.boot.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.book.store.controller.OnlineBookStoreController;
import com.online.book.store.model.Book;
import com.online.book.store.model.Books;
import com.online.book.store.repository.OnlineBookStoreRepository;
import com.online.book.store.repository.impl.OnlineBookStoreRepositoryImpl;
import com.online.book.store.service.OnlineBookStoreService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OnlineBookStoreApplicationTests {

	static Logger log = Logger.getLogger(OnlineBookStoreApplicationTests.class.getName());

	@InjectMocks
	private OnlineBookStoreController onlineBookStoreController;

	@Mock
	private OnlineBookStoreService onlineBookStoreService;

	@Mock
	private OnlineBookStoreRepository onlineBookStoreRepository;

	@Mock
	private JdbcTemplate jdbcTemplate;

	@Mock
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper=new ObjectMapper();

	@Mock
	private OnlineBookStoreRepositoryImpl onlineBookStoreRepositoryImpl;

	@Test
	public void testGetAllBooks() throws Exception {
		mockMvc.perform(get("/online/bookStore/getBooks"))
		.andDo(print())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

		final List<Book> listOfBooks=new ArrayList<Book>();
		final Book book1=new Book(1,"Telugu","Gurajada",3434.34);
		final Book book2=new Book(2,"English","Gurajada",4343.32);
		final Book book3=new Book(3,"Hindi","Gurajada",4323.12);
		listOfBooks.add(book1);
		listOfBooks.add(book2);
		listOfBooks.add(book3);
		when(onlineBookStoreService.getAllBooks()).thenReturn(listOfBooks);
		final List<Book> books=onlineBookStoreController.getAllBooks();
		assertNotNull(books);
		assertEquals(3, books.size());
		assertEquals(1, books.get(0).getBook_id());
		assertEquals("Gurajada", books.get(0).getBook_author());
		assertEquals("Telugu", books.get(0).getBook_name());
		verify(onlineBookStoreService, times(1)).getAllBooks();
	}

	@Test
	public void testGetBook() throws Exception {
		final Book book=new Book(1,"Telugu","Gurajada",3434.34);
		when(onlineBookStoreService.getBookById(1)).thenReturn(book);
		final Book result=onlineBookStoreController.getBookById(1);
		assertNotNull(result);
		assertEquals(1, result.getBook_id());
		assertEquals("Gurajada", book.getBook_author());
		assertEquals("Telugu", book.getBook_name());
	}

	@Test
	public void testInvalidRestUrl() throws Exception {
		mockMvc.perform(get("/online/bookStore/saveBook"))
		.andDo(print())
		.andExpect(status().isNotFound());
	}

	@Test
	public void testAddBook() throws Exception {
		final Random rand = new Random();
		final Book book=new Book(rand.nextInt(99999),"Telugu","Gurajada",3434.34);

		mockMvc.perform(post("/online/bookStore/addBook").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(book))).andExpect(status().isOk());

		when(onlineBookStoreService.saveBook(book)).thenReturn(book);
		when(onlineBookStoreRepository.saveBook(book)).thenReturn(book);
		final Book result=onlineBookStoreController.addBook(book);
		assertNotNull(result);
		assertEquals("Gurajada", book.getBook_author());
		assertEquals("Telugu", book.getBook_name());
	}

	@Test
	public void testUpdateBook() throws Exception {
		final Book book=new Book(1,"Telugu","Gurajada",3434.34);

		mockMvc.perform(put("/online/bookStore/updateBook").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(book))).andExpect(status().isOk());

		when(onlineBookStoreService.updateBook(book)).thenReturn(book);
		when(onlineBookStoreRepository.updateBook(book)).thenReturn(book);
		final Book result=onlineBookStoreController.updateBook(book);
		assertNotNull(result);
		assertEquals("Gurajada", book.getBook_author());
		assertEquals("Telugu", book.getBook_name());
	}

	@Test
	public void testDeleteBook() throws Exception {
		final Book books=new Book(1,"Telugu","Gurajada",3434.34);

		mockMvc.perform(delete("/online/bookStore/deleteBook").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(books))).andExpect(status().isOk());

		final Book book=new Book();
		book.setBook_id(1);
		book.setBook_name("Telugu");
		book.setBook_author("Shakesphere");
		book.setBook_price(1212.32);
		when(onlineBookStoreService.deleteBook(book)).thenReturn(book);
		when(onlineBookStoreRepository.deleteBook(book)).thenReturn(book);
		book.toString();
		final Book result=onlineBookStoreController.deleteBook(book);
		assertNotNull(result);
		assertEquals("Shakesphere", book.getBook_author());
		assertEquals("Telugu", book.getBook_name());
	}

	@Test
	public void testAddBooksWithInvalidRequest() throws Exception {
		final Random rand = new Random();
		final Books books=new Books();
		final List<Book> listOfBooks=new ArrayList<Book>();
		final Book book1=new Book(rand.nextInt(99999),"Telugu","Gurajada",3434.34);
		final Book book2=new Book(rand.nextInt(99999),"English","Gurajada",4343.32);
		final Book book3=new Book(rand.nextInt(99999),"Hindi","Gurajada",4323.12);
		listOfBooks.add(book1);
		listOfBooks.add(book2);
		listOfBooks.add(book3);
		books.setBooks(listOfBooks);

		mockMvc.perform(post("/online/bookStore/addBooks").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(listOfBooks))).andExpect(status().is4xxClientError());

		when(onlineBookStoreService.saveBooks(books)).thenReturn(listOfBooks);
		final List<Book> result=onlineBookStoreController.addBooks(books);
		assertNotNull(result);
		assertEquals(3, result.size());
		verify(onlineBookStoreService, times(1)).saveBooks(books);
	}

	@Test
	public void testAddBooks() throws Exception {
		final Random rand = new Random();
		final Books books=new Books();
		final List<Book> listOfBooks=new ArrayList<Book>();
		final Book book1=new Book(rand.nextInt(99999),"Telugu","Gurajada",3434.34);
		final Book book2=new Book(rand.nextInt(99999),"English","Gurajada",4343.32);
		final Book book3=new Book(rand.nextInt(99999),"Hindi","Gurajada",4323.12);
		listOfBooks.add(book1);
		listOfBooks.add(book2);
		listOfBooks.add(book3);
		books.setBooks(listOfBooks);

		mockMvc.perform(post("/online/bookStore/addBooks").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(books))).andExpect(status().isOk());

		when(onlineBookStoreService.saveBooks(books)).thenReturn(listOfBooks);
		final List<Book> result=onlineBookStoreController.addBooks(books);
		assertNotNull(result);
		assertEquals(3, result.size());
		verify(onlineBookStoreService, times(1)).saveBooks(books);
	}

	@Test
	public void testUpdateBooks() throws Exception {
		final Random rand = new Random();
		final Books books=new Books();
		final List<Book> listOfBooks=new ArrayList<Book>();
		final Book book1=new Book(rand.nextInt(99999),"Telugu","Gurajada",3434.34);
		final Book book2=new Book(rand.nextInt(99999),"English","Gurajada",4343.32);
		final Book book3=new Book(rand.nextInt(99999),"Hindi","Gurajada",4323.12);
		listOfBooks.add(book1);
		listOfBooks.add(book2);
		listOfBooks.add(book3);
		books.setBooks(listOfBooks);

		mockMvc.perform(put("/online/bookStore/updateBooks").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(books))).andExpect(status().isOk());

		when(onlineBookStoreService.updateBooks(books)).thenReturn(listOfBooks);
		final List<Book> result=onlineBookStoreController.updateBooks(books);
		assertNotNull(result);
		assertEquals(3, result.size());
		verify(onlineBookStoreService, times(1)).updateBooks(books);
	}


	@Test
	public void testDeleteBooks() throws Exception {
		final Random rand = new Random();
		final Books books=new Books();
		final List<Book> listOfBooks=new ArrayList<Book>();
		final Book book1=new Book(rand.nextInt(99999),"Telugu","Gurajada",3434.34);
		final Book book2=new Book(rand.nextInt(99999),"English","Gurajada",4343.32);
		final Book book3=new Book(rand.nextInt(99999),"Hindi","Gurajada",4323.12);
		listOfBooks.add(book1);
		listOfBooks.add(book2);
		listOfBooks.add(book3);
		books.setBooks(listOfBooks);

		mockMvc.perform(delete("/online/bookStore/deleteBooks").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(books))).andExpect(status().isOk());

		when(onlineBookStoreService.deleteBooks(books)).thenReturn(listOfBooks);
		final List<Book> result=onlineBookStoreController.deleteBooks(books);
		assertNotNull(result);
		assertEquals(3, result.size());
		verify(onlineBookStoreService, times(1)).deleteBooks(books);
	}

	@Test
	public void testSearchBook() throws Exception {
		final Random rand = new Random();
		final List<Book> listOfBooks=new ArrayList<Book>();
		final Book book1=new Book(rand.nextInt(99999),"Telugu","Gurajada",3434.34);
		final Book book2=new Book(rand.nextInt(99999),"English","Gurajada",4343.32);
		final Book book3=new Book(rand.nextInt(99999),"Hindi","Gurajada",4323.12);
		listOfBooks.add(book1);
		listOfBooks.add(book2);
		listOfBooks.add(book3);

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("bookId", "1");
		requestParams.add("bookName", "Telugu");
		requestParams.add("bookAuthor", "Gurajada");

		mockMvc.perform(get("/online/bookStore/searchBook").params(requestParams)).andExpect(status().isOk());
		
		when(onlineBookStoreService.searchBook(1,"Telugu","Gurajada")).thenReturn(listOfBooks);
		final List<Book> result=onlineBookStoreController.searchBook(1,"Telugu","Gurajada");
		assertNotNull(result);
		assertEquals(3, result.size());
		verify(onlineBookStoreService, times(1)).searchBook(1,"Telugu","Gurajada");
	}

	@Test
	void contextLoads() {
	}

}
