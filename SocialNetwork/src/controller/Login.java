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
import persistence.UtenteCredenziali;
import persistence.dao.UtenteDao;

public class Login extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private class Credenziali{
		private String email,password;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        Credenziali cred = new Gson().fromJson(br.readLine(), Credenziali.class);
                
        UtenteDao utentedao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
		UtenteCredenziali user = utentedao.findByPrimaryKeyCredential(cred.email);
		
		if(user!=null && cred.password.equals(user.getPassword())) {
				req.getSession().setAttribute("user", (Utente) user);
				resp.getWriter().write("true");
		}else 
			resp.getWriter().write("false");
	}
}
