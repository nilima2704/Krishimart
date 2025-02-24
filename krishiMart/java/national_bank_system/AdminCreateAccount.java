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
import javax.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminCreateAccount extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String id = req.getParameter("id") ;
		String name = req.getParameter("name") ;
		String email = req.getParameter("email") ;
		String password = req.getParameter("password") ;
		
//		HttpSession hs = req.getSession() ;
//		hs.setAttribute("id", id);
//		hs.setAttribute("name", name);
//		hs.setAttribute("email", email);
//		hs.setAttribute("password", password);
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root", "MySQL@9566") ;
			PreparedStatement ps = con.prepareStatement("insert into admin(id,name,email,password) values(?,?,?,?)") ;
			ps.setInt(1, Integer.parseInt(id)) ;
			ps.setString(2, name) ;
			ps.setString(3, email) ;
			ps.setString(4, password) ;
			
			ps.execute() ;
			
			PrintWriter pw = resp.getWriter() ;
			pw.println("<h1> Your account is created..! </h1>") ;
			
			RequestDispatcher rd = req.getRequestDispatcher("adminlogin.html") ;
			rd.forward(req, resp) ;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
