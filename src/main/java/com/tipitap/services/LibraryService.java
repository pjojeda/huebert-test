package com.tipitap.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.tipitap.dto.BookDto;
import com.tipitap.dto.LibraryDto;
import com.tipitap.parser.LibraryParser;
import com.tipitap.services.impl.LibraryServiceImpl;

@Path(value="/library")
public class LibraryService {
	
	@Autowired
	private LibraryServiceImpl libraryServiceImpl;
	@Context 
	ServletContext servletContext;
	
	@POST
	@Path(value="/getBooks")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getBooks(@QueryParam(value="version")int version){
		try{
			Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
			int currentVersion = Integer.parseInt(prefs.get("currentVersion", "0"));
			String path = servletContext.getRealPath("/");
			path = path + "WEB-INF/json/";
			String finalVersion = "{Books: [ ], CategoryOrder: [ ], Server: { updated: \"2014-05-29\", version: "
									+currentVersion +"}}";
			if(version != currentVersion){
				version++;
				String minLib = libraryServiceImpl.getBooks(path + version + ".txt");
				LibraryDto finalLibrary = LibraryParser.config(minLib);
				String json = "";
				for(int v = version + 1; v <=currentVersion; v++){
					json = libraryServiceImpl.getBooks(path + v + ".txt");
					LibraryDto lib = LibraryParser.config(json);
					for(BookDto book: lib.getBooks()){
						if(finalLibrary.getBooks().contains(book)){
							finalLibrary.getBooks().remove(book);
							finalLibrary.getBooks().add(book);
						}
					}
				}
				finalLibrary.setCategoriesSorted(LibraryParser.getCategoriesSorted(json));
				finalVersion = LibraryParser.parseLibraryToJson(finalLibrary);
			}
			return Response.ok(finalVersion).build();
		}catch(Exception ex){
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(
	    @DefaultValue("true") @FormDataParam("enabled") boolean enabled,
	    @FormDataParam("file") InputStream file,
	    @FormDataParam("file") FormDataContentDisposition fileDisposition) {
	 
		String path = servletContext.getRealPath("/");
		path = path + "WEB-INF/json/";
		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
		String currentVersion = fileDisposition.getFileName().substring(0, fileDisposition.getFileName().length()-4);
		prefs.put("currentVersion", currentVersion);
		String uploadedFileLocation = path + fileDisposition.getFileName();
		 
		try{
			libraryServiceImpl.writeToFile(file, uploadedFileLocation);
			return Response.status(Status.OK).entity("save").build();
		}catch(IOException ex){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("save").build();
		}
		
	}
	
}
