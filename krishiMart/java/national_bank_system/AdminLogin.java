package national_bank_system;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class AdminLogin extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String email = req.getParameter("EmailId") ;
		String password = req.getParameter("PassWord") ;
		
		
		HttpSession hs = req.getSession() ;
//		String name = (String) hs.getAttribute("name") ;
		hs.setAttribute("email", email);
		String email1 = (String) hs.getAttribute("email") ;
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root", "MySQL@9566") ;
			PreparedStatement ps = con.prepareStatement("select * from admin where email=? and password=?") ;
			
			
			ps.setString(1, email) ;
			ps.setString(2, password) ;
			
			ResultSet rs = ps.executeQuery() ;
			
			if(rs.next())
			{
				PrintWriter pw = resp.getWriter() ;
				pw.println("Your email: " + email1 );
				resp.setContentType("text/html") ;
				
				RequestDispatcher rd = req.getRequestDispatcher("adminmenu.html") ;
				rd.include(req, resp) ;
				
			}
			else
			{
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=color:red> Invalid Creadentials...! </h1>" );
				
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin.html") ;
				rd.include(req, resp) ;
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
