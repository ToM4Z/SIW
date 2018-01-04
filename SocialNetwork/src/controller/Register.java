package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//controllo se l'email è già registrata nel db
		if(checkEmail(req.getParameter("email")))
			resp.getWriter().write("true");
		else
			resp.getWriter().write("false");
	}
	
	private boolean checkEmail(String s) {
		UtenteDao utentedao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
		return utentedao.findByPrimaryKey(s) != null;
	}
	
	private class Credenziali{
		private String email,nome,cognome,datadinascita,nickname,password;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        Credenziali cred = new Gson().fromJson(br.readLine(), Credenziali.class);
        
		if(!checkEmail(cred.email)){
			Utente user = new Utente();
			user.setEmail(cred.email);
			user.setNome(cred.nome);
			user.setCognome(cred.cognome);
			user.setUsername(cred.nickname);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				user.setDataDiNascita(format.parse(cred.datadinascita));
			} catch (ParseException e) {
				user.setDataDiNascita(null);
			}
			user.setDataIscrizione(Calendar.getInstance().getTime());
	
			UtenteDao utentedao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
			utentedao.save(user);
			utentedao.setPassword(user,cred.password);
			
			System.out.println("registrazione completata");
		}
	}
}