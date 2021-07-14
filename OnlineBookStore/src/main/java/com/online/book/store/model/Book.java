package com.online.book.store.model;

public class Book {
	
	public int book_id;
	public String book_name,book_author;
	public double book_price;

	public Book(final int book_id, final String book_name,final String book_author,final double book_price) {
		this.book_id=book_id;
		this.book_name=book_name;
		this.book_author=book_author;
		this.book_price=book_price;
	}

	public Book() {
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getBook_author() {
		return book_author;
	}

	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}

	public double getBook_price() {
		return book_price;
	}

	public void setBook_price(double book_price) {
		this.book_price = book_price;
	}

	@Override
	public String toString() {
		return "Book [book_id=" + book_id + ", book_name=" + book_name + ", book_author=" + book_author
				+ ", book_price=" + book_price + "]";
	}

}
