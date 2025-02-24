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
<title>Edit Your Profile</title>
<link rel="stylesheet" href="EditProfile.css">
</head>
<body>
	<%
		Connection connection = ProductDao.getConnection() ;
		PreparedStatement preparedStatement = connection.prepareStatement("select * from user where email=? and password=?") ;
		preparedStatement.setString(1, (String) session.getAttribute("email")) ;
		preparedStatement.setString(2, (String) session.getAttribute("password")) ;
		ResultSet resultSet = preparedStatement.executeQuery() ;
		while(resultSet.next())
		{
	%>
	
	
	<div class="main-div">
        <h2>Edit Your Profile: </h2>
        <form action="update" method="post"> 
            <label for=""> Enter First Name:</label>
            <input class="input-style" type="text" name="firstName" value="<%=resultSet.getString("firstName")%>"> <br><br>
            <label for="">Enter Last Name: </label>
            <input class="input-style" type="text" name="lastName" value="<%=resultSet.getString("lastName")%>""> <br><br>
            <label for=""> Enter Mobile Number:</label>
            <input class="input-style" type="tel" name="phone" value="<%=resultSet.getLong("phone")%>"> <br><br>
            <label for=""> Enter Email: </label>
            <input class="input-style" type="email" name="email" value="<%=resultSet.getString("email")%>"> <br><br>
            <label for="">Enter Address : </label>
            <input class="input-style" type="text" name="address" value="<%=resultSet.getString("address")%>"> <br><br>
            <input class="button-style" type="submit" value="Update">  
            <input class="button-style" type="reset" value="Cancel"> 
        </form>
    </div>
    
	<% } %>
</body>
</html>