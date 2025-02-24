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

@WebServlet("/customerUpdateProfile")
public class CustomerUpdateProfile extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		HttpSession hs = req.getSession() ;
		long accountnumber = (Long) hs.getAttribute("customeraccountnum") ;
		String name = req.getParameter("name") ;
//		String phone = req.getParameter("phone") ;
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root", "MySQL@9566") ;
			PreparedStatement ps = con.prepareStatement("select * from customer where accountnumber=?") ;
			ps.setLong(1, accountnumber) ;
			
			ResultSet rs = ps.executeQuery() ;
			
			if (rs.next())
			{
				PreparedStatement ps1 = con.prepareStatement("update customer set name=? where accountnumber=?") ;
				ps1.setString(1, name) ;
				ps1.setLong(2, accountnumber) ;
				
				ps1.executeUpdate() ;
				
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=color:red>Profile is updated successfully..! </h1>") ;
				
				RequestDispatcher rd = req.getRequestDispatcher("customerupdateprofile.html") ;
				rd.include(req, resp) ;
			}
			else
			{

				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=color:red>Account is not found - invalid id </h1>") ;
				
				RequestDispatcher rd = req.getRequestDispatcher("customerupdateprofile.html") ;
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
