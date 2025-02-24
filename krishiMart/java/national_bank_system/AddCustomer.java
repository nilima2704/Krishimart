package national_bank_system;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addcustomer")
public class AddCustomer extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String id = req.getParameter("id") ;
		String name = req.getParameter("name") ;
		String pincode = req.getParameter("pincode") ;
		long accountnumber = (long) (Math.random()*1000000000) ;
		double balance = 500.00 ;
		
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root", "MySQL@9566") ;
			PreparedStatement ps = con.prepareStatement("insert into customer(id, name, accountnumber, pincode, balance) values(?,?,?,?,?)") ;
			ps.setInt(1, Integer.parseInt(id)) ;
			ps.setString(2, name) ;
			ps.setLong(3, accountnumber) ;
			ps.setInt(4, Integer.parseInt(pincode)) ;
			ps.setDouble(5, balance) ;
			
			ps.execute() ;
			
			PrintWriter pw = resp.getWriter() ;
			pw.println("<h1 style=text-align:center>Customer is added Successfully...!!! </h1>") ;
			
			RequestDispatcher rd = req.getRequestDispatcher("addcustomer.html") ;
			rd.include(req, resp) ;
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
