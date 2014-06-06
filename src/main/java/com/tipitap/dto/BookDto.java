package com.tipitap.dto;

import java.io.Serializable;
import java.util.List;

public class BookDto implements Serializable {

	public static final String AGE_RANGE_0 = "0-3";
	public static final String AGE_RANGE_1 = "4-7";
	public static final String AGE_RANGE_2 = "8-12";

	private static final long serialVersionUID = 1247105773826895573L;
	private String isbn;
	private String title;
	private String author;
	private int numberOfPages;
	private String description;
	private boolean freebie;
	private boolean mostPopular;
	private boolean newRelease;

	private String age; // filter
	private String gender; // not a filter anymore

	private List<TagDto> tags; // "name": "jungle"
	private List<CategoryDto> categories; // "name": "Dinosaurs!"

	public BookDto(String isbn, String author, int numberOfPages, String title,
			String description, String gender, String age, boolean freebie,
			List<TagDto> tags, List<CategoryDto> categories) {
		this.isbn = isbn;
		this.description = description;
		this.author = author;
		this.numberOfPages = numberOfPages;
		this.title = title;
		this.tags = tags;
		this.categories = categories;
		this.gender = gender;
		this.age = age;
		this.freebie = freebie;
	}

	public BookDto() {
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFreebie() {
		return freebie;
	}

	public void setFreebie(boolean freebie) {
		this.freebie = freebie;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public List<TagDto> getTags() {
		return tags;
	}

	public void setTags(List<TagDto> tags) {
		this.tags = tags;
	}

	public List<CategoryDto> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDto> categories) {
		this.categories = categories;
	}

	/**
	 * @return the mostPopular
	 */
	public boolean isMostPopular() {
		return mostPopular;
	}

	/**
	 * @param mostPopular the mostPopular to set
	 */
	public void setMostPopular(boolean mostPopular) {
		this.mostPopular = mostPopular;
	}

	/**
	 * @return the newRelease
	 */
	public boolean isNewRelease() {
		return newRelease;
	}

	/**
	 * @param newRelease the newRelease to set
	 */
	public void setNewRelease(boolean newRelease) {
		this.newRelease = newRelease;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDto other = (BookDto) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}

}
