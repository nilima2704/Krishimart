package national_bank_system;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class CustomerLogout extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		HttpSession hs = req.getSession() ;
		hs.removeAttribute("customeraccountnum");
		hs.removeAttribute("pincode");
		
		RequestDispatcher rd = req.getRequestDispatcher("customerlogin.html") ;
		rd.include(req, resp) ;
	}
}
