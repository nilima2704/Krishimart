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

import krishiMart.dao.UserDao;
import krishiMart.dto.User;

@WebServlet("/register")
public class UserController extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String firstName = req.getParameter("firstName") ;
		String lastName = req.getParameter("lastName") ;
		Long phone = Long.parseLong(req.getParameter("phone")) ;
		String address = req.getParameter("address") ;
		String role = req.getParameter("role") ;
		String email = req.getParameter("email") ;
		String password1 = req.getParameter("password1") ;
		String password2 = req.getParameter("password2") ;
		
		
		User user = new User() ;
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPhone(phone);
		user.setAddress(address);
		user.setRole(role);
		user.setEmail(email);
		user.setPassword(password1);
		
		
		UserDao crud = new UserDao() ;
		
		
			
			PrintWriter printWriter = resp.getWriter() ;
			RequestDispatcher dispatcher ;
			
			
			try {
				if (password1.equals(password2)) 
				{
					int result = crud.register(user);
					
					if (result != 0) 
					{
						
						printWriter.print("<h1 style=text-align:center; color:red;> Register Successfully </h1> <p style=text-align:center; color:red;> Now you can Log in your Account </p>");
						
						 dispatcher = req.getRequestDispatcher("Login.html") ;
						 dispatcher.include(req, resp);
					}
				}
				else
				{
					printWriter.print("<h1 style=text-align:center; color:red;> Password Not Matched </h1> <p style=text-align:center; color:red;> Please enter same password </p>");
					
					 dispatcher = req.getRequestDispatcher("FarmerRegistration.html") ;
					 dispatcher.include(req, resp);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}
}

