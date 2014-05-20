package com.tipitap.services;

import java.io.IOException;
import java.io.InputStream;

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
			String path = servletContext.getRealPath("/");
			path = path + "WEB-INF/json/";
			String json = libraryServiceImpl.getBooks(path + version + ".txt");
			return Response.ok(json).build();
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
		
		String uploadedFileLocation = path + fileDisposition.getFileName();
		 
		try{
			libraryServiceImpl.writeToFile(file, uploadedFileLocation);
			return Response.status(Status.OK).entity("save").build();
		}catch(IOException ex){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("save").build();
		}
		
	}
	
}
