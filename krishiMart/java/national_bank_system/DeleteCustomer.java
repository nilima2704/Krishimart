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

@WebServlet("/deletecustomer")
public class DeleteCustomer extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String accountnumber = req.getParameter("number") ;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root", "MySQL@9566");
			PreparedStatement ps1 = con.prepareStatement("select * from customer where accountnumber=?") ;
			ps1.setLong(1, Long.parseLong(accountnumber)) ;
			
			ResultSet rs = ps1.executeQuery() ;
			
			PreparedStatement ps = con.prepareStatement("delete from customer where accountnumber=?") ;
			ps.setLong(1, Long.parseLong(accountnumber)) ;
			
			if (rs.next())
			{
				ps.executeUpdate() ;
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=text-align:center>Mention Customer is deleted </h1>");
				
				RequestDispatcher rd = req.getRequestDispatcher("deletecustomer.html") ;
				rd.include(req, resp) ;
			}
			else
			{
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=text-align:center> Customer not found - Invlid Account Number </h1>");
				
				RequestDispatcher rd = req.getRequestDispatcher("deletecustomer.html") ;
				rd.include(req, resp) ;
			}
			
			
		} catch (ClassNotFoundException e) 
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
