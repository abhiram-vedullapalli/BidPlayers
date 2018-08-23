<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body >
<h1 align="center" style="color: red;"> WELCOME TO PLAYERS </h1>
<br>
<h2 align="center">Create Your Own Team , Bid for Players , Buy Players for your Team</h2>
<br><br>
<div align="center" style="max-width: 40%;position:absolute;left: 10%">
<form:form  action="register" modelAttribute="user" method="POST">
<h4>REGISTER HERE</h4>
Enter User Name : <form:input path="userName" required="required"  title="Enter Lower case letters only" />
<p style="color: red;">${message }</p>
Enter Password  : <form:password path="passWord" required="required"/>
<br><br>
Create Your Team : <form:input path="teamName" required="required"  title="Enter alphabets only" placeholder="Enter Team Name"/>
<br><br>
<input type="submit" value="Register">
</form:form>
</div>
<div align="center" style=" right: 0%;left: 60%;position:relative;width: 30%">
<h4>Already Registered ? Log In Here</h4>
<form:form action="homepage" modelAttribute="user" method="post">
Enter User Name : <form:input path="userName" required="required"  title="Enter Lower case letters only" />
<br><br>
Enter Password  : <form:password path="passWord" required="required"/>
<p style="color: red;">${message2 }</p>
<input type="submit" value="Login">
</form:form>
</div>
</body>
</html>