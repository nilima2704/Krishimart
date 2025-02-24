package krishiMart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import krishiMart.dao.UserDao;
import krishiMart.dto.User;

@WebServlet("/login")
public class LoginController extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String email = req.getParameter("email") ;
		String password = req.getParameter("password") ;
		
		HttpSession session = req.getSession() ;
		session.setAttribute("email", email);
		session.setAttribute("password", password);
		
		
		PrintWriter printWriter = resp.getWriter() ;
		RequestDispatcher dispatcher ;
		
		UserDao crud =new UserDao() ;
		
		User user ;
		try 
		{
			user = crud.login(email) ;
			
			if (user != null) 
			{
				if (user.getPassword().equalsIgnoreCase(password)) 
				{
					if (user.getRole().equalsIgnoreCase("FARMER"))
					{
						printWriter.print("<h1>Login Successfully</h1>") ;
						
						 dispatcher = req.getRequestDispatcher("FarmerHomePage.jsp") ;
						 dispatcher.include(req, resp) ;
					}
					else if (user.getRole().equalsIgnoreCase("BUYER"))
					{
						printWriter.print("<h1>Login Successfully</h1>") ;
						
						 dispatcher = req.getRequestDispatcher("BuyerHomePage.jsp") ;
						 dispatcher.include(req, resp) ;
					}
				}
				
				
				else
				{
					printWriter.print("<h1>Invalid Credentials</h1>") ;
					
					dispatcher = req.getRequestDispatcher("Login.html") ;
					dispatcher.include(req, resp) ;
				}
				
				
			}
			else
			{
				printWriter.print("<h1>Invalid Credentials</h1>") ;
				
				dispatcher = req.getRequestDispatcher("Login.html") ;
				dispatcher.include(req, resp) ;
			}
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
