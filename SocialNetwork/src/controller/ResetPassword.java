package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;

public class ResetPassword extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private class EmailPassword{
		private String email,password;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        EmailPassword empas = new Gson().fromJson(br.readLine(), EmailPassword.class);
        
        UtenteDao utentedao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
        Utente user = new Utente();
        user.setEmail(empas.email);
		utentedao.setPassword(user, empas.password);
	}
}
