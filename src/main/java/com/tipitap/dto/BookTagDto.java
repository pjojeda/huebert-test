package com.tipitap.dto;

import java.io.Serializable;

public class BookTagDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private long tagID;
	private String isbn;

	public BookTagDto() {
	}

	public BookTagDto(String isbn, long tagID) {
		this.tagID = tagID;
		this.isbn  = isbn;
	}

	public long getTagID() {
		return tagID;
	}

	public void setTagID(long catID) {
		this.tagID = catID;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}