package krishiMart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import krishiMart.dao.ProductDao;
import krishiMart.dao.UserDao;
import krishiMart.dto.Product;

@WebServlet("/addPost")
public class ProductController extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String name = req.getParameter("name") ;
		int quantity = Integer.parseInt(req.getParameter("quantity")) ;
		double price = Double.parseDouble(req.getParameter("price")) ;
		String description = req.getParameter("description") ;
		
		Product product = new Product() ;
		product.setName(name);
		product.setQuantity(quantity);
		product.setPrice(price);
		product.setDescription(description);
		
//		UserDao crudUserCRUD = new UserDao() ;
//		crudUserCRUD.addPost(product);
		
		ProductDao dao = new ProductDao() ;
		try {
			int count = dao.post(product) ;
			if (count != 0)
			{
				PrintWriter printWriter = resp.getWriter() ;
				printWriter.println("<h1 style=text-align:center; color:red> Post Added Successfully </h1>");
				
				RequestDispatcher dispatcher = req.getRequestDispatcher("FarmerHomePage.jsp") ;
				dispatcher.include(req, resp) ;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
