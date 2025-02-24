<%@page import="krishiMart.dao.ProductDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Farmer Home Page</title>
<link rel="stylesheet" href="FarmerHomePage.css">
</head>
<body>
		 <div class="navDiv">
        <ul>
            <li> <img class="img" alt="error" src="logo1.png"> </li>
            <li> <a href="FarmerHomePage.jsp"> Home </a>  </li>
            <li> <a href="Post.html"> Post </a>  </li>
            <li> <a href="Profile.jsp"> Profile </a>  </li>
            <li> <a href="Maintain.html"> About </a>  </li>
            <li> <a href="Maintain.html"> Contact </a>  </li>
            <li> <a href="logout"><img class="img-logout" alt="error" src="switch.png"></a> </li>
        </ul>
    </div>
		
		<%
			Connection connection = ProductDao.getConnection() ;
			PreparedStatement preparedStatement = connection.prepareStatement("select * from product") ;
			ResultSet resultSet = preparedStatement.executeQuery() ;
			
				while(resultSet.next())
				{
			%>
					<div class="t-div">
					
					<p> Name: <%=resultSet.getString("name") %> </p> 
					<p> Quantity: <%=resultSet.getInt("quantity")%> </p> 
					<p> Price: <%=resultSet.getDouble("price")%> </p> 
					<p> Description: <%=resultSet.getString("description") %> </p>
					</div>
			<% 	} %>
			
			
</body>
</html>