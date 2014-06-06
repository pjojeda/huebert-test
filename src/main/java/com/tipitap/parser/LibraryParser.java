package com.tipitap.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONTokener;

import com.tipitap.dto.BookDto;
import com.tipitap.dto.CategoryDto;
import com.tipitap.dto.LibraryDto;
import com.tipitap.dto.TagDto;

public class LibraryParser{

	protected LibraryDto config(JSONObject json) throws JSONException, java.text.ParseException {
		LibraryDto library = new LibraryDto();
		JSONObject libraryItem = json.getJSONObject(ParserStatics.LIBRARY);
		getLibraryItem(libraryItem, library);

		List<BookDto> bookList = new ArrayList<BookDto>();
		JSONArray jsonBookList = json.getJSONArray(ParserStatics.BOOKS);
		for (int i = 0; i < jsonBookList.length(); i++) {
			bookList.add(getBookItem(jsonBookList.getJSONObject(i)));
		}

		library.setBooks(bookList);
		return library;
	}
	
	public static LibraryDto config(String jsonString){
		LibraryDto library = new LibraryDto();
		JSONObject libraryItem;
		try {
			JSONObject json = (JSONObject) new JSONTokener(jsonString).nextValue();
			libraryItem = json.getJSONObject(ParserStatics.LIBRARY);
			getLibraryItem(libraryItem, library);
			List<BookDto> bookList = new ArrayList<BookDto>();
			JSONArray jsonBookList = json.getJSONArray(ParserStatics.BOOKS);
			for (int i = 0; i < jsonBookList.length(); i++) {
				bookList.add(getBookItem(jsonBookList.getJSONObject(i)));
			}
			library.setBooks(bookList);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return library;
	}
	
	private static BookDto getBookItem(JSONObject itemResult) throws JSONException {

		JSONObject jsonItemResult = itemResult.getJSONObject(ParserStatics.BOOK);

		String description = jsonItemResult.getString(ParserStatics.DESCRIPTION);
		String author = jsonItemResult.getString(ParserStatics.AUTHOR);
		boolean freebie = jsonItemResult.getBoolean(ParserStatics.FREEBIE);
		int numberOfPages = jsonItemResult.getInt(ParserStatics.NUMBER_OF_PAGES);
		String isbn = jsonItemResult.getString(ParserStatics.ISBN);
		String title = jsonItemResult.getString(ParserStatics.TITLE);

		String gender = jsonItemResult.getString(ParserStatics.GENDER);
		String age = jsonItemResult.getString(ParserStatics.AGE);

		List<TagDto> tagList = new ArrayList<TagDto>();
		JSONArray jsonTagList = jsonItemResult.getJSONArray(ParserStatics.TAGS);
		for (int i = 0; i < jsonTagList.length(); i++) {
			tagList.add(getTagItem(jsonTagList.getJSONObject(i)));
		}
		List<CategoryDto> catList = new ArrayList<CategoryDto>();
		JSONArray jsonCatList = jsonItemResult.getJSONArray(ParserStatics.CATEGORIES);
		for (int i = 0; i < jsonCatList.length(); i++) {
			catList.add(getCategoryItem(jsonCatList.getJSONObject(i)));
		}
		
		boolean mostPopular = jsonItemResult.getBoolean(ParserStatics.MOST_POPULAR);
		boolean newRelease = jsonItemResult.getBoolean(ParserStatics.NEW_RELEASE);
		BookDto book = new BookDto(isbn, author, numberOfPages, title, description, gender, age, freebie, tagList, catList);
		book.setMostPopular(mostPopular);
		book.setNewRelease(newRelease);
		return book;
	}
	
	private static BookDto getBook(JSONObject jsonItemResult) throws JSONException {

		String description = jsonItemResult.getString(ParserStatics.DESCRIPTION);
		String author = jsonItemResult.getString(ParserStatics.AUTHOR);
		boolean freebie = jsonItemResult.getBoolean(ParserStatics.FREEBIE);
		int numberOfPages = jsonItemResult.getInt(ParserStatics.NUMBER_OF_PAGES);
		String isbn = jsonItemResult.getString(ParserStatics.ISBN);
		String title = jsonItemResult.getString(ParserStatics.TITLE);

		String gender = jsonItemResult.getString(ParserStatics.GENDER);
		String age = jsonItemResult.getString(ParserStatics.AGE);

		List<TagDto> tagList = new ArrayList<TagDto>();
		JSONArray jsonTagList = jsonItemResult.getJSONArray(ParserStatics.TAGS);
		for (int i = 0; i < jsonTagList.length(); i++) {
			tagList.add(getTagItem(jsonTagList.getJSONObject(i)));
		}
		List<CategoryDto> catList = new ArrayList<CategoryDto>();
		return new BookDto(isbn, author, numberOfPages, title, description, gender, age, freebie, tagList, catList);
	}

	private static LibraryDto getLibraryItem(JSONObject libraryItem, LibraryDto library) throws JSONException, java.text.ParseException {
		int libraryVersion = libraryItem.getInt(ParserStatics.LIBRARY_VERSION);
//		String updated = libraryItem.getString(ParserStatics.LIBRARY_UPDATED);
//		Date updatedDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(updated);
		library.setVersion(libraryVersion);
//		library.setLastSync(updatedDate);
		return library;
	}

	private static TagDto getTagItem(JSONObject tagItem) throws JSONException {
		String tagName = tagItem.getString(ParserStatics.TAG_NAME);
		TagDto tag = new TagDto();
		tag.setName(tagName);
		return tag;
	}

	private static CategoryDto getCategoryItem(JSONObject tagItem) throws JSONException {
		String catName = tagItem.getString(ParserStatics.CAT_NAME);
		CategoryDto category = new CategoryDto();
		category.setName(catName);
		return category;
	}
	
	public static List<CategoryDto>getCategoriesSorted(String jsonString)throws JSONException{
		JSONObject json = (JSONObject) new JSONTokener(jsonString).nextValue();
		JSONArray categoriesArray = json.getJSONArray("CategoryOrder");
		List<CategoryDto>categories = new ArrayList<CategoryDto>();
		for(int index = 0; index < categoriesArray.length(); index ++){
			CategoryDto category = new CategoryDto();
			String name = categoriesArray.getString(index);
			category.setName(name);
			categories.add(category);
		}
		return categories;
	}
	
	public static List<CategoryDto> getCategories(String json){
		List<CategoryDto>categories = new ArrayList<CategoryDto>();
		try {
			JSONArray jsonCategories = (JSONArray) new JSONTokener(json).nextValue();
			int size = jsonCategories.length();
			for(int index = 0; index < size; index++){
				JSONObject jsonCategory = jsonCategories.getJSONObject(index);
				CategoryDto category = getCategoryItem(jsonCategory);
				JSONArray jsonBooks = jsonCategory.getJSONArray(ParserStatics.BOOKS);
				List<BookDto>books = new ArrayList<BookDto>();
				for(int indexBook = 0; indexBook < jsonBooks.length(); indexBook++){
					JSONObject jsonBook = jsonBooks.getJSONObject(indexBook);
					BookDto book = getBook(jsonBook);
					books.add(book);
				}
				
				//removing repeated books
				Set<BookDto> hs = new HashSet<BookDto>();
				hs.addAll(books);
				books.clear();
				books.addAll(hs);
				
				category.setBooks(books);
				categories.add(category);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return categories;
	}
	
	public static String parseLibraryToJson(LibraryDto lib)throws Exception{
		JSONObject jsonLibrary = new JSONObject();
		JSONObject jsonServer = new JSONObject();
		jsonServer.put(ParserStatics.LIBRARY_VERSION, lib.getVersion());
		jsonLibrary.put(ParserStatics.LIBRARY, jsonServer);
		JSONArray arrayBooks = new JSONArray();
		for(BookDto book: lib.getBooks()){
			JSONObject jsonBook = new JSONObject();
			jsonBook.put(ParserStatics.BOOK, parseBookToJson(book));
			arrayBooks.put(jsonBook);
		}
		jsonLibrary.put(ParserStatics.BOOKS, arrayBooks);
		JSONArray arrayCategories = new JSONArray();
		for(CategoryDto category: lib.getCategoriesSorted()){
			arrayCategories.put(category.getName());
		}
		jsonLibrary.put("CategoryOrder", arrayCategories);
		return jsonLibrary.toString();
	}
	
	private static JSONObject parseBookToJson(BookDto book){
		JSONObject jsonBook = new JSONObject();
		try {
			jsonBook.put(ParserStatics.ISBN, book.getIsbn());
			jsonBook.put(ParserStatics.DESCRIPTION, book.getDescription());
			JSONArray arrayTag = new JSONArray();
			for(TagDto tag: book.getTags()){
				JSONObject jsonTag = parseTagToJson(tag);
				arrayTag.put(jsonTag);
			}
			jsonBook.put(ParserStatics.TAGS, arrayTag);
			jsonBook.put(ParserStatics.MOST_POPULAR, book.isMostPopular());
			jsonBook.put(ParserStatics.NEW_RELEASE, book.isNewRelease());
			JSONArray arrayCategories = new JSONArray();
			for(CategoryDto category: book.getCategories()){
				JSONObject jsonCategory = parseCategoryToJson(category);
				arrayCategories.put(jsonCategory);
			}
			jsonBook.put(ParserStatics.CATEGORIES, arrayCategories);
			jsonBook.put(ParserStatics.AUTHOR, book.getAuthor());
			jsonBook.put(ParserStatics.NUMBER_OF_PAGES, book.getNumberOfPages());
			jsonBook.put(ParserStatics.TITLE, book.getTitle());
			jsonBook.put(ParserStatics.GENDER, book.getGender());
			jsonBook.put(ParserStatics.AGE, book.getAge());
			jsonBook.put(ParserStatics.FREEBIE, book.isFreebie());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonBook;
	}
	
	private static JSONObject parseTagToJson(TagDto tag){
		JSONObject jsonTag = new JSONObject();
		try {
			jsonTag.put(ParserStatics.TAG_NAME, tag.getName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonTag;
	}
	
	private static JSONObject parseCategoryToJson(CategoryDto category){
		JSONObject jsonCategory = new JSONObject();
		try {
			jsonCategory.put(ParserStatics.CAT_NAME, category.getName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonCategory;
	}
	
}
