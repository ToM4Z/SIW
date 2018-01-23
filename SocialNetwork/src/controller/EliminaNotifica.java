package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Notifica;
import persistence.DatabaseManager;
import persistence.dao.NotificaDao;

public class EliminaNotifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
	private class IdNotifica{
		
		private String idNotifica;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		//System.out.println(linea);
        IdNotifica id = new Gson().fromJson(linea, IdNotifica.class);
        
        NotificaDao notificaDao = DatabaseManager.getInstance().getDaoFactory().getNotificaDAO(); 
        Notifica n = notificaDao.findByPrimaryKey(Long.parseLong(id.idNotifica));
        notificaDao.delete(n);
	}

}
