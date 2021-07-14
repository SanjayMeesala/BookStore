package com.online.book.store.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.online.book.store.constants.OnlineBookStoreConstants;
import com.online.book.store.model.Book;
import com.online.book.store.repository.OnlineBookStoreRepository;

@Repository
public class OnlineBookStoreRepositoryImpl implements OnlineBookStoreRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Book getBookById(final int bookId) {
		return jdbcTemplate.queryForObject(OnlineBookStoreConstants.GET_BOOK_BY_ID, (rowSet,rowNumber) -> {
			return new Book(rowSet.getInt("book_id"),rowSet.getString("book_name"),rowSet.getString("book_author"),rowSet.getDouble("book_price"));
		},bookId);
	}

	@Override
	public List<Book> getAllBooks() {
		return jdbcTemplate.query(OnlineBookStoreConstants.GET_BOOKS, (rowSet,rowNumber) -> {
			return new Book(rowSet.getInt("book_id"),rowSet.getString("book_name"),rowSet.getString("book_author"),rowSet.getDouble("book_price"));
		});
	}

	@Override
	public Book saveBook(final Book book) {
		final SqlParameterSource params=new MapSqlParameterSource("book_id",book.getBook_id()).addValue("book_name", book.getBook_name())
				.addValue("book_author", book.getBook_author()).addValue("book_price", book.getBook_price());
		namedParameterJdbcTemplate.update(OnlineBookStoreConstants.SAVE_BOOK,params);
		return book;
	}

	@Override
	public Book updateBook(final Book book) {
		final SqlParameterSource params=new MapSqlParameterSource("book_id",book.getBook_id()).addValue("book_name", book.getBook_name())
				.addValue("book_author", book.getBook_author()).addValue("book_price", book.getBook_price());
		namedParameterJdbcTemplate.update(OnlineBookStoreConstants.UPDATE_BOOK,params);
		return book;
	}

	@Override
	public Book deleteBook(final Book book) {
		final SqlParameterSource params=new MapSqlParameterSource("book_id",book.getBook_id());
		namedParameterJdbcTemplate.update(OnlineBookStoreConstants.DELETE_BOOK,params);
		return book;
	}

	@Override
	public List<Book> saveBooks(final List<Book> books) {
		for(final Book book:books) {
			final SqlParameterSource params=new MapSqlParameterSource("book_id",book.getBook_id()).addValue("book_name", book.getBook_name())
					.addValue("book_author", book.getBook_author()).addValue("book_price", book.getBook_price());
			namedParameterJdbcTemplate.update(OnlineBookStoreConstants.SAVE_BOOK,params);
		}
		return books;
	}

	@Override
	public List<Book> updateBooks(final List<Book> books) {
		for(final Book book:books) {
			final SqlParameterSource params=new MapSqlParameterSource("book_id",book.getBook_id()).addValue("book_name", book.getBook_name())
					.addValue("book_author", book.getBook_author()).addValue("book_price", book.getBook_price());
			namedParameterJdbcTemplate.update(OnlineBookStoreConstants.UPDATE_BOOK,params);
		}
		return books;
	}

	@Override
	public List<Book> deleteBooks(final List<Book> books) {
		for(final Book book:books) {
			final SqlParameterSource params=new MapSqlParameterSource("book_id",book.getBook_id()).addValue("book_name", book.getBook_name());
			namedParameterJdbcTemplate.update(OnlineBookStoreConstants.DELETE_BOOK,params);
		}
		return books;
	}

	@Override
	public List<Book> searchBook(final int bookId,final String bookName,final String bookAuthor) {
		String query="";
		boolean isViewed=false;
		query=OnlineBookStoreConstants.GET_BOOKS+" where book_id="+bookId;
		isViewed=true;
		if(bookName!=null && !bookName.isEmpty()) {
			if(isViewed) {
				query=query+" and book_name like '%"+bookName+"%'";
			} else {
				query=OnlineBookStoreConstants.GET_BOOKS+" where book_name like '%"+bookName+"%'";
				isViewed=true;
			}
		}
		if(bookAuthor!=null && !bookAuthor.isEmpty()) {
			if(isViewed) {
				query=query+" and book_author like '%"+bookAuthor+"%'";
			} else {
				query=OnlineBookStoreConstants.GET_BOOKS+" where book_author like '%"+bookAuthor+"%'";
				isViewed=true;
			}
		}

		return jdbcTemplate.query(query, (rowSet,rowNumber) -> {
			return new Book(rowSet.getInt("book_id"),rowSet.getString("book_name"),rowSet.getString("book_author"),rowSet.getDouble("book_price"));
		});

	}
}
