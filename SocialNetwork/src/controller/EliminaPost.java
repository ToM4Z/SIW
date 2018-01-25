package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Post;
import persistence.DatabaseManager;
import persistence.dao.PostDao;


public class EliminaPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       	
	private class Elimina{		
		private String idPost;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		Elimina e = new Gson().fromJson(br.readLine(), Elimina.class);
		
		Long idPost = Long.parseLong(e.idPost);
		PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
		
		Post post = postDao.findByPrimaryKey(idPost);
		
		File file = new File(new File(getServletContext().getResource("/images/posts").getPath()).getAbsolutePath()+"\\"+post.getId()+".jpg");
		if(file.exists())
			file.delete();
		
		postDao.delete(post);
		
		resp.getWriter().write("true");
	}

}
