package com.online.book.store.repository;

import java.util.List;

import com.online.book.store.model.Book;

public interface OnlineBookStoreRepository {
	
	public Book getBookById(final int bookId);
	
	public List<Book> getAllBooks();
	
	public Book saveBook(final Book book);
	
	public Book updateBook(final Book book);
	
	public Book deleteBook(final Book book);
	
	public List<Book> saveBooks(final List<Book> book);
	
	public List<Book> updateBooks(final List<Book> book);
	
	public List<Book> deleteBooks(final List<Book> book);

	public List<Book> searchBook(final int bookId,final  String bookName,final  String bookAuthor);

}
