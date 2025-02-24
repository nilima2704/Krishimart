<%@page import="krishiMart.dao.ProductDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Profile</title>
<link rel="stylesheet" href="Profile.css">
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
		PreparedStatement preparedStatement = connection.prepareStatement("select * from user where email=? and password=?") ;
		preparedStatement.setString(1,(String) session.getAttribute("email")) ;
		preparedStatement.setString(2, (String) session.getAttribute("password")) ;
		
		ResultSet resultSet = preparedStatement.executeQuery() ;
		
		while(resultSet.next()){
			
		
	%>
	
	<div class="main-div">

        <div class="img-div">

        </div>

        <div class="profile-div">
            <h1> My Profile </h1>
            <p> <span> Name: </span> <%=resultSet.getString("firstName")+ " " +resultSet.getString("lastName")%> </p>
            <p> <span> Email: </span> <%=resultSet.getString("email") %> </p>
            <p> <span> Mobile Number: </span> <%=resultSet.getLong("phone") %> </p>
            <p> <span> Place: </span> <%=resultSet.getString("address") %> </p>
        </div>
        
        <%} %>

    </div>

    <div class="btn-div">
        <div class="btn-style-div">
            <button type="button" class="btn"> <a href="EditProfile.jsp"> Edit Profile </a> </button>
            <button type="button" class="btn"> <a href="ChangePassword.html"> Change Password </a> </button> <br><br>
        </div>
        <div class="btn-style-div">
            <button type="button" class="btn"> <a href="Post.html"> Add Post </a> </button>
            <button type="button" class="btn"> <a href=""> Delete Post </a> </button>
        </div>
    </div>
	
	<% connection.close() ; %>

</body>
</html>