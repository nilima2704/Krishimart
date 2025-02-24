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

@WebServlet("/viewcustomer")
public class ViewCustomer extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String id = req.getParameter("Anumber") ;
		
		try 	
		{
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root", "MySQL@9566") ;
			PreparedStatement ps = con.prepareStatement("select * from customer where accountnumber=?") ;
			ps.setLong(1, Long.parseLong(id)) ;
			
			ResultSet rs = ps.executeQuery() ;
			
			if (rs.next())
			{
				PrintWriter pw = resp.getWriter() ;
//				pw.println("<h1>id: </h1>" + rs.getInt(1)) ;
//				pw.println("<h1>Name: </h1>" + rs.getString(2)) ;
//				pw.println("<h1>Account Number: </h1>" + rs.getLong(3)) ;
//				pw.println("<h1>Pincode: </h1>" + rs.getInt(4)) ;
//				pw.println("<h1>Balance: </h1>" + rs.getDouble(5)) ;
				pw.println("id: " + rs.getInt(1) + "<br>") ;
				pw.println("Name: " + rs.getString(2) + "<br>") ;
				pw.println("Account Number: " + rs.getLong(3) + "<br>") ;
				pw.println("Pincode: " + rs.getInt(4) + "<br>") ;
				pw.println("Balance: " + rs.getDouble(5) + "<br>") ;
				
				resp.setContentType("text/html") ;
				
			}
			else
			{
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=text-align:center;>No User found Invalid id </h1>") ;	
				
				RequestDispatcher rd = req.getRequestDispatcher("viewcustomer.html") ;
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
