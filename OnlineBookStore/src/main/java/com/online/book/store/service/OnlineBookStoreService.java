package com.online.book.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.book.store.model.Book;
import com.online.book.store.model.Books;
import com.online.book.store.repository.OnlineBookStoreRepository;

@Service
public class OnlineBookStoreService {
	
	@Autowired
	private OnlineBookStoreRepository onlineBookStoreRepository;
	
	public Book getBookById(final int bookId) {
		return onlineBookStoreRepository.getBookById(bookId);
	}
	
	public List<Book> getAllBooks(){
		return onlineBookStoreRepository.getAllBooks();
	}
	
	public Book saveBook(final Book book) {
		return onlineBookStoreRepository.saveBook(book);
	}
	
	public Book updateBook(final Book book) {
		return onlineBookStoreRepository.updateBook(book);
	}
	
	public Book deleteBook(final Book book) {
		return onlineBookStoreRepository.deleteBook(book);
	}
	
	public List<Book> saveBooks(final Books books) {
		return onlineBookStoreRepository.saveBooks(books.getBooks());
	}
	
	public List<Book> updateBooks(final Books books) {
		return onlineBookStoreRepository.updateBooks(books.getBooks());
	}
	
	public List<Book> deleteBooks(final Books books) {
		return onlineBookStoreRepository.deleteBooks(books.getBooks());
	}

	public List<Book> searchBook(final int bookId,final String bookName,final  String bookAuthor) {
		return onlineBookStoreRepository.searchBook(bookId,bookName,bookAuthor);
	}

}
