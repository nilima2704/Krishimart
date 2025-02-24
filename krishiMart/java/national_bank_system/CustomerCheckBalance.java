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

@WebServlet("/checkaccountbalance")
public class CustomerCheckBalance extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession hs = req.getSession();
		Object accountnumber =  hs.getAttribute("customeraccountnum");
		Object pincode =  hs.getAttribute("pincode");
		
		if((accountnumber!= null) ) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/national_bank", "root",
					"MySQL@9566");
			PreparedStatement ps = con
					.prepareStatement("select balance from customer where accountnumber=? and pincode=?");
			ps.setLong(1, (Long)accountnumber);
			ps.setInt(2, (Integer)pincode);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				PrintWriter pw = resp.getWriter();
				pw.println("<h1 style=color:black>Your Balance is: </h1>" + rs.getDouble(1));

				RequestDispatcher rd = req.getRequestDispatcher("customercheckbalance.html");
				rd.include(req, resp);
			} else {
				PrintWriter pw = resp.getWriter();
				pw.println("<h1 style=color:#7c0a02>No customer found - Invalid Account Number</h1>");

				RequestDispatcher rd = req.getRequestDispatcher("customercheckbalance.html");
				rd.include(req, resp);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		else {
			resp.getWriter().println("<h1> please login fist </h1>");
			req.getRequestDispatcher("customerlogin.html").include(req, resp) ;
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
