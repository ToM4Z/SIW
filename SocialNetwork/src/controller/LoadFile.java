package controller;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(value="/loadFile")
public class LoadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private boolean isMultipart;
	private String filePath;
	private File file;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		
		String finalPath = "",finalName = "";
		if(req.getParameter("request").equals("user")) {
			finalPath = "/images/users";
			finalName = req.getParameter("user")+".jpg";
		}else
			if(req.getParameter("request").equals("channel")) {
				finalPath = "/images/channels";
				finalName = req.getParameter("channel")+".jpg";
			}else 
				if(req.getParameter("request").equals("post")) {
					finalPath = "/images/posts";
					finalName = req.getParameter("post")+".jpg";
				}
		
		File f = new File(getServletContext().getResource(finalPath).getPath());
		filePath = f.getAbsolutePath()+"\\";
		
		isMultipart = ServletFileUpload.isMultipartContent(req);

		if (!isMultipart) {
			resp.getWriter().write("error");
			System.out.println("error Multipart");
			return;
		}
		
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

		try {
			List<FileItem> fileItems = upload.parseRequest(req);
			for (FileItem item : fileItems)
			    if (!item.isFormField()) {
			        file = new File(filePath + finalName);
					item.write(file);
					System.out.println("File loaded as "+finalName);
			    }
			
			resp.getWriter().write(finalName);
		} catch (Exception ex) {
			resp.getWriter().write("error");
			System.out.println(ex);
			return;
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
	}
}
