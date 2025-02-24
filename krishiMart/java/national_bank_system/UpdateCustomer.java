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

@WebServlet("/updatecustomer")
public class UpdateCustomer extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String id = req.getParameter("id") ;
		String name = req.getParameter("name") ;
		String pincode = req.getParameter("pincode") ;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root", "MySQL@9566") ;
			PreparedStatement ps1 = con.prepareStatement("select * from customer where id=?") ;
			ps1.setInt(1, Integer.parseInt(id)) ;
			
			ResultSet rs = ps1.executeQuery() ;
			
			if (rs.next()) 
			{
				PreparedStatement ps = con.prepareStatement("update customer set id=? , name=? , pincode=? where accountnumber=?") ;
				ps.setInt(1, Integer.parseInt(id)) ;
				ps.setString(2, name) ;
				ps.setInt(3, Integer.parseInt(pincode)) ;
				ps.setLong(4, rs.getLong(3)) ;
				
				ps.executeUpdate() ;
				
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=text-align:center>Updated successfully..</h1>") ;
				
				resp.setContentType("text/html") ;
				
				RequestDispatcher rd = req.getRequestDispatcher("updatecustomer.html") ;
				rd.include(req, resp) ;
				
			}
			else
			{
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=color:blue> Customer is not found - Invalid id</h1>") ;
				
				RequestDispatcher rd = req.getRequestDispatcher("updatecustomer.html") ;
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
