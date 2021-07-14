package com.online.book.store.constants;

public class OnlineBookStoreConstants {
	
	public static String GET_BOOK_BY_ID ="select * from books where book_id=?";
	
	public static String GET_BOOKS ="select * from books";
	
	public static String SAVE_BOOK ="insert into books(book_id,book_name,book_author,book_price) values(:book_id,:book_name,:book_author,:book_price)";
	
	public static String UPDATE_BOOK ="update books set book_name=:book_name,book_author=:book_author,book_price=:book_price where book_id=:book_id";
	
	public static String DELETE_BOOK ="delete from books where book_id=:book_id";

}
