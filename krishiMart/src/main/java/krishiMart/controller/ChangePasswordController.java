package krishiMart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import krishiMart.dao.UserDao;

@WebServlet("/changePassword")
public class ChangePasswordController extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String email = req.getParameter("email") ;
		String password1 = req.getParameter("password1") ;
		String password2 = req.getParameter("password2") ;
		
		
		if (password1.equals(password2)) 
		{
			UserDao crud = new UserDao() ;
			try {
				int count = crud.changePassword(email, password1) ;
				if (count != 0) 
				{
					HttpSession session = req.getSession() ;
					session.setAttribute("password", password1);
					
					PrintWriter printWriter = resp.getWriter() ;
					printWriter.println("<h1>Password Updated..!</h1>") ;
					
					RequestDispatcher dispatcher = req.getRequestDispatcher("Profile.jsp") ;
					dispatcher.include(req, resp) ;
				}
				else
				{
					PrintWriter printWriter = resp.getWriter() ;
					printWriter.println("<h1>Account is not Registered. </h1>") ;
					
					RequestDispatcher dispatcher = req.getRequestDispatcher("ChangePassword.html") ;
					dispatcher.include(req, resp) ;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			PrintWriter printWriter = resp.getWriter() ;
			printWriter.println("<h1>Password Not Matched..!</h1>") ;
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("ChangePassword.html") ;
			dispatcher.include(req, resp) ;
		}
		
	}
}
