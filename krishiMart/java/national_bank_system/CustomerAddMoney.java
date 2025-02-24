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

@WebServlet("/customeraddmoney")
public class CustomerAddMoney extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		HttpSession hs = req.getSession() ;
		long accountnumber = (Long) hs.getAttribute("customeraccountnum") ;
		String pincode = req.getParameter("pincode") ;
		String amount = req.getParameter("amount") ;
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root", "MySQL@9566") ;
			PreparedStatement ps = con.prepareStatement("select * from customer where accountnumber=? and pincode=?") ;
			ps.setLong(1, accountnumber) ;
			ps.setInt(2, Integer.parseInt(pincode)) ;
			
			ResultSet rs = ps.executeQuery() ;
			
			if (rs.next())
			{
				double amount1 = Double.parseDouble(amount) ;
				double dbBalance = rs.getDouble(5) ;
				//System.out.println(dbBalance);
				double total= dbBalance + amount1 ;
				
				PreparedStatement ps1 = con.prepareStatement("update customer set balance=? where accountnumber=? and pincode=? ") ;
				ps1.setDouble(1, total) ;
				ps1.setLong(2, accountnumber) ;
				ps1.setInt(3, Integer.parseInt(pincode)) ;
				
				ps1.executeUpdate() ;
				
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=color:red>Amount is added successfully..!!</h1>") ;
				
				RequestDispatcher rd = req.getRequestDispatcher("customerAddMoney.jsp") ;
				rd.include(req, resp) ;
			}
			else
			{
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=color:red>Invalid credentials </h1>") ;
				
				RequestDispatcher rd = req.getRequestDispatcher("customerAddMoney.jsp") ;
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
