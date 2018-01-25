package controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Canale;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;


public class EliminaCanale extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nomeCanale = req.getParameter("channel");
		CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		
		Canale canale = canaleDao.findByPrimaryKey(nomeCanale);
		
		File file = new File(new File(getServletContext().getResource("/images/channels").getPath()).getAbsolutePath()+"\\"+canale.getNome()+".jpg");
		if(file.exists())
			file.delete();
		
		canaleDao.delete(canale);
		
		resp.sendRedirect("home");

	}
}
