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
import krishiMart.dto.User;

@WebServlet("/update")
public class UpdateProfileController extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String firstName = req.getParameter("firstName") ;
		String lastName = req.getParameter("lastName") ;
		long phone = Long.parseLong(req.getParameter("phone")) ;
		String email = req.getParameter("email") ;
		String address = req.getParameter("address") ;
		
		HttpSession session = req.getSession() ;
		String dbEmail = (String) session.getAttribute("email") ;
		
		
		User user = new User() ;
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAddress(address);
		
		UserDao crud = new UserDao() ;
		
		try {
			int count = crud.updateUser(user, dbEmail) ;
			if (count != 0)
			{
				session.setAttribute("email", email);
				PrintWriter printWriter = resp.getWriter() ;
				printWriter.println("<h1 style=text-align:center; color:red;> Profile Updated Successfully...! </h1>") ;
			
				RequestDispatcher dispatcher = req.getRequestDispatcher("BuyerProfile.jsp") ;
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
}
