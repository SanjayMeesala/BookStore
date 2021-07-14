package com.online.book.store.controller;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.book.store.model.Book;
import com.online.book.store.model.Books;
import com.online.book.store.service.OnlineBookStoreService;

@RequestMapping("online/bookStore")
@RestController
public class OnlineBookStoreController {
	
	@Autowired
	private OnlineBookStoreService onlineBookStoreService;
	
	@GetMapping("/getBook/{bookId}")
	public Book getBookById(@PathVariable("bookId") final int bookId) {
		return onlineBookStoreService.getBookById(bookId);
	}
	
	@GetMapping("/getBooks")
	public List<Book> getAllBooks() {
		return onlineBookStoreService.getAllBooks();
	}
	
	@PostMapping("/addBook")
	public Book addBook(@RequestBody final Book book) {
		return onlineBookStoreService.saveBook(book);
	}
	
	@PutMapping("/updateBook")
	public Book updateBook(@RequestBody final Book book) {
		return onlineBookStoreService.updateBook(book);
	}
	
	@DeleteMapping("/deleteBook")
	public Book deleteBook(@RequestBody final Book book) {
		return onlineBookStoreService.deleteBook(book);
	}
	
	@PostMapping("/addBooks")
	public List<Book> addBooks(@RequestBody final Books books) {
		return onlineBookStoreService.saveBooks(books);
	}
	
	@PutMapping("/updateBooks")
	public List<Book> updateBooks(@RequestBody final Books books) {
		return onlineBookStoreService.updateBooks(books);
	}
	
	@DeleteMapping("/deleteBooks")
	public List<Book> deleteBooks(@RequestBody final Books books) {
		return onlineBookStoreService.deleteBooks(books);
	}
	
	@GetMapping("/searchBook")
	public List<Book> searchBook(@QueryParam("bookId") final int bookId,@QueryParam("bookName") final String bookName,
			@QueryParam("bookAuthor") final String bookAuthor) {
				return onlineBookStoreService.searchBook(bookId,bookName,bookAuthor);
	}

}
