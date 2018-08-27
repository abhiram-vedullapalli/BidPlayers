<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>
</head>
<body>
<h1 align="center">Log In </h1>
<div align="center">

<form action="/homepage" method="post">

Enter User Name : <input type="text" required="required" name="userName" >
<br><br>
Enter PassWord : <input type="text" name="passWord"  required="required"><br><br>
<input type="submit" value="Login">

</form>
</div>
<%-- <form:form action="homepage" method="post" >
Enter Username : <form:input path="userName" autocomplete="off"/>
<br><br>
Enter Password :  <form:input path="passWord" autocomplete="off" />
<br><br>
<input type="submit" value="LogIn">

</form:form></div> --%>
</body>
</html>