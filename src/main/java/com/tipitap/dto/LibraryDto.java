package com.tipitap.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibraryDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private int version;
	private List<BookDto> books = new ArrayList<BookDto>();
	private Date lastSync;
	private List<CategoryDto>categoriesSorted = new ArrayList<CategoryDto>();

	public List<BookDto> getBooks() {
		return books;
	}

	public void setBooks(List<BookDto> books) {
		this.books = books;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	

	public Date getLastSync() {
		return lastSync;
	}
	

	public void setLastSync(Date lastSync) {
		this.lastSync = lastSync;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the categoriesSorted
	 */
	public List<CategoryDto> getCategoriesSorted() {
		return categoriesSorted;
	}

	/**
	 * @param categoriesSorted the categoriesSorted to set
	 */
	public void setCategoriesSorted(List<CategoryDto> categoriesSorted) {
		this.categoriesSorted = categoriesSorted;
	}
}
