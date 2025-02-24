package krishiMart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import krishiMart.dto.User;

public class UserDao 
{

	public Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/krishi_mart", "root",
				"MySQL@9566");
		return connection;
	}
	
	// Register method for both
	
	public int register(User user) throws ClassNotFoundException, SQLException
	{
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into user(firstName,lastName,phone,address,role,email,password) values(?,?,?,?,?,?,?)");
		preparedStatement.setString(1, user.getFirstName());
		preparedStatement.setString(2, user.getLastName());
		preparedStatement.setLong(3, user.getPhone());
		preparedStatement.setString(4, user.getAddress());
		preparedStatement.setString(5, user.getRole());
		preparedStatement.setString(6, user.getEmail());
		preparedStatement.setString(7, user.getPassword());

		int count = preparedStatement.executeUpdate();
		connection.close();
		return count;
	}
	
	// Login method for both
	
	public User login(String email) throws ClassNotFoundException, SQLException 
	{
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("select * from user where email=?");
		preparedStatement.setString(1, email);

		ResultSet resultSet = preparedStatement.executeQuery();
		User user = new User();
		if (resultSet.next()) {
			user.setPassword(resultSet.getString("password"));
			user.setRole(resultSet.getString("role"));
			connection.close();
			return user ;
		}
		connection.close();
		return null;
	}
	
	public int changePassword(String email, String password) throws SQLException, ClassNotFoundException 
	{
		Connection connection = getConnection() ;
		PreparedStatement preparedStatement = connection.prepareStatement("select * from user where email = ?") ;
		preparedStatement.setString(1, email);
		ResultSet resultSet = preparedStatement.executeQuery() ;
		if (resultSet != null)
		{
			PreparedStatement preparedStatement1 = connection.prepareStatement("update user set password=? where email=?") ;
			preparedStatement1.setString(1, password) ;
			preparedStatement1.setString(2, email) ;
			int count = preparedStatement1.executeUpdate() ;
			connection.close();
			return count ;
		}
		else
		{
			connection.close();
			return 0 ;
		}
		
		
		
		
		
		
		
	}
	
	public int updateUser(User user, String email) throws ClassNotFoundException, SQLException 
	{	
		Connection connection = getConnection() ;
		PreparedStatement preparedStatement = connection.prepareStatement("update user set firstName=? , lastName=? , phone=? , email=? , address=? where email =?") ;
		preparedStatement.setString(1, user.getFirstName());
		preparedStatement.setString(2, user.getLastName());
		preparedStatement.setLong(3, user.getPhone());
		preparedStatement.setString(4, user.getEmail());
		preparedStatement.setString(5, user.getAddress());
		preparedStatement.setString(6, email );
		
		int count = preparedStatement.executeUpdate() ;
		connection.close();
		return count ;
	}
}
