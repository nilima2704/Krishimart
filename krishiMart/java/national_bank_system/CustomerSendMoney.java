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

@WebServlet("/customersendmoney")
public class CustomerSendMoney extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		HttpSession hs = req.getSession() ;
		long youraccount = (Long) hs.getAttribute("customeraccountnum") ;
		String pincode = req.getParameter("pincode") ;
		String sAccount = req.getParameter("Snumber") ;
		String amount = req.getParameter("amount") ;
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root", "MySQL@9566") ;
			PreparedStatement ps = con.prepareStatement("select * from customer where accountnumber=? and pincode=?") ;
			ps.setLong(1, youraccount) ;
			ps.setInt(2, Integer.parseInt(pincode)) ;
			
			ResultSet rs = ps.executeQuery() ;
			
			if (rs.next())
			{
				double amount1 = Double.parseDouble(amount) ;
				double dbAmount = rs.getDouble(5) ;
				double total ;
				
				if (dbAmount > amount1 )
				{
					total = dbAmount-amount1 ;
				
				PreparedStatement ps1 = con.prepareStatement("update customer set balance=? where accountnumber=?") ;
				ps1.setDouble(1, total) ;
				ps1.setLong(2, youraccount) ;
				
				ps1.executeUpdate() ;
				
				PreparedStatement ps2 = con.prepareStatement("select * from customer where accountnumber=?") ;
				ps2.setLong(1, Long.parseLong(sAccount)) ;
				
				ResultSet rs1 = ps2.executeQuery() ;
				
				if (rs1.next())
				{
					double amount2 = Double.parseDouble(amount) ;
					double dbBalance = rs1.getDouble(5) ;
					double total1 = dbBalance+amount2 ;
					
					PreparedStatement ps3 = con.prepareStatement("update customer set balance=? where accountnumber=?") ;
					ps3.setDouble(1, total1) ;
					ps3.setLong(2, Long.parseLong(sAccount)) ;
					
					ps3.executeUpdate() ;
					
					PrintWriter pw = resp.getWriter() ;
					pw.println("<h1 style=color:red>Money Transferred successfully..!! </h1>") ;
					
					RequestDispatcher rd = req.getRequestDispatcher("customerSendMoney.jsp") ;
					rd.include(req, resp) ;
				}
				else
				{

					PrintWriter pw = resp.getWriter() ;
					pw.println("<h1 style=color:red>Invalid Account Number - Please enter the correct Account Number. </h1>") ;
					
					RequestDispatcher rd = req.getRequestDispatcher("customerSendMoney.jsp") ;
					rd.include(req, resp) ;
				}
				
				}
				else
				{
					PrintWriter pw = resp.getWriter() ;
					pw.println("<h1 style=color:red>Insufficient Balance </h1>") ;
					
					RequestDispatcher rd = req.getRequestDispatcher("customerSendMoney.jsp") ;
					rd.include(req, resp) ;
				}
			}
			else
			{
				PrintWriter pw = resp.getWriter() ;
				pw.println("<h1 style=color:red>Invalid Credentials...!! </h1>") ;
				
				RequestDispatcher rd = req.getRequestDispatcher("customerSendMoney.jsp") ;
				rd.include(req, resp) ;
				
			}
		} catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
