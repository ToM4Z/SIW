package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.Gruppo;
import model.Messaggio;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;
import persistence.dao.MessaggioDao;

@WebServlet(value="/receiveMessage")
public class ReceiveMessage extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		resp.setCharacterEncoding("UTF-8");
		
		if(utente == null) {
			resp.getWriter().write("error");
			return;
		}
        MessaggioDao messaggioDao = DatabaseManager.getInstance().getDaoFactory().getMessaggioDAO();
		
		if(Boolean.valueOf(req.getParameter("first"))){
	        GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();

			String gruppo = req.getParameter("gruppo");
			String canale = req.getParameter("canale");

			Gruppo group = gruppoDao.findByPrimaryKey(gruppo,canale);
			
			Messaggio lastMex = new Messaggio();
			lastMex.setGruppo(group);
			
			List<Messaggio> mex = messaggioDao.getOther50(lastMex);
			if(mex.size()==0)
				session.setAttribute("lastMessage", lastMex);
			else
				session.setAttribute("lastMessage", mex.get(mex.size()-1));
			resp.getWriter().write(new Gson().toJson(createListMex(utente,mex)));
			
		}else {
			Messaggio lastMex = (Messaggio) session.getAttribute("lastMessage");
			List<Messaggio> mex;
			if(lastMex.getId() != null)
				mex = messaggioDao.getAfter(lastMex);
			else
				mex = messaggioDao.getOther50(lastMex);
			
			if(mex.size() != 0)
				session.setAttribute("lastMessage", mex.get(mex.size()-1));
			
			resp.getWriter().write(new Gson().toJson(createListMex(utente,mex)));
		}
	}
	
	private List<String> createListMex(Utente me,List<Messaggio> mex){
		List<String> json = new LinkedList<>();
		Calendar today = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		for(int i=0;i<mex.size();++i) {
			Messaggio m = mex.get(i);	
			calendar.setTime(m.getData());
			boolean right = m.getMittente().getEmail().equals(me.getEmail()) ? true : false;
			int day = calendar.get(Calendar.DATE);
			int month = calendar.get(Calendar.MONTH)+1;
			int year = calendar.get(Calendar.YEAR);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int min = calendar.get(Calendar.MINUTE);
			String data = "";
			if(!(year == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)))
				data = day+"/"+ (month<10 ? "0"+month : month) +"/"+ year +" ";			
			data += (hour<10 ? "0"+hour : hour) +":"+ (min<10 ? "0"+min : min);
			String email = m.getMittente().getEmail();
			String username = m.getMittente().getUsername();
			
			json.add("<div class=\"mexContainer "+(right? "right":"left")+"\">"
			  + "<div class=\"row\">"
			  + "<img src=\"images/users/"+email+".jpg\" alt=\"Avatar\" onerror=\"this.src='images/users/unknown.jpg';\""+(right? "class=\"right\"":"")+">"
			  + "<span id=\"Name\""+(right? "class=\"right\"":"")+"><a href=\"javascript:showUser('"+email+"')\">"+username+"</a></span>"
			  + "<span class=\"time-"+(right? "left":"right")+"\">"+data+"</span>"	  
			  + "</div><hr><p style=\"float:"+(right? "right":"left")+"\">"+m.getContenuto()+"</p>"
			  + "</div>");
		}
		return json;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("lastMessage");
	}
}
