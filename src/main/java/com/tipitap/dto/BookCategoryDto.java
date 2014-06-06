package com.tipitap.dto;

import java.io.Serializable;

public class BookCategoryDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private long catID;
	private String isbn;

	public BookCategoryDto() {
	}

	public BookCategoryDto(String isbn, long catID) {
		this.catID = catID;
		this.isbn  = isbn;
	}

	public long getCatID() {
		return catID;
	}

	public void setCatID(long catID) {
		this.catID = catID;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}