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

@WebServlet("/successed")
public class CustomerLogin extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String accountnumber = req.getParameter("number") ;
		String pincode = req.getParameter("Pincode") ;
		
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root", "MySQL@9566") ;
			PreparedStatement ps = con.prepareStatement("select * from customer where accountnumber=? and pincode=?") ;
			ps.setLong(1, Long.parseLong(accountnumber)) ;
			ps.setInt(2, Integer.parseInt(pincode)) ;
			
			ResultSet rs = ps.executeQuery() ;
			
			if (rs.next())
			{
				HttpSession hs = req.getSession() ;
				hs.setAttribute("customeraccountnum",rs.getLong(3)) ;
				hs.setAttribute("pincode", rs.getInt(4)) ;
				
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=text-align:center; color:red;>Welcome</h1>") ;
				
				RequestDispatcher rd = req.getRequestDispatcher("customerMENU.html") ;
				rd.include(req, resp) ;
			}
			else
			{
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=text-align:center; color:red; >Wrong Credentials...!!! Try again...!</h1>") ;
				
				RequestDispatcher rd = req.getRequestDispatcher("customerlogin.html") ;
				rd.include(req, resp) ;
			}
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
