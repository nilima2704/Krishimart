package krishiMart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import krishiMart.dto.Product;

public class ProductDao 
{
	public static Connection getConnection() throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.cj.jdbc.Driver") ;
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/krishi_mart", "root", "MySQL@9566") ;
		return connection ;
	}
	
	public int post(Product product) throws ClassNotFoundException, SQLException 
	{
		Connection connection = getConnection() ;
		PreparedStatement preparedStatement = connection.prepareStatement("insert into product(name, quantity, price, description) values(?,?,?,?)") ;
		preparedStatement.setString(1, product.getName());
		preparedStatement.setInt(2, product.getQuantity());
		preparedStatement.setDouble(3, product.getPrice());
		preparedStatement.setString(4, product.getDescription());
		
		int count = preparedStatement.executeUpdate() ;
		connection.close();
		return count ;
	}
}
