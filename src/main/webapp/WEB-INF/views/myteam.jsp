<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Team</title>
</head>
<body>
<h1 align="center">${TeamName }</h1>
<br>
<div align="center">
<a href="homepage">Home</a>&nbsp;&nbsp;
<a href="create" >Create Player</a>&nbsp;&nbsp;
<a href="update">Update Player</a>&nbsp;&nbsp;
<a href="myteam">My Team</a>&nbsp;&nbsp;
<a href="trade">Trade Details</a>&nbsp;&nbsp;
<a href="listall">Players On Sale</a>&nbsp;&nbsp;
<a href="logout">Log Out</a>&nbsp;&nbsp;
<br>
</div>

<div align="center">
<br>
<p style="color: green;">${updelMessage}</p>
<br>
<c:choose>
<c:when test="${myteam.isEmpty() }">
<h1>No one joined your Squad yet</h1>
</c:when>
<c:otherwise>
<c:forEach var="temp" items = "${myteam }">
	${temp }<br>
	<form action="/changevalues" method="post">
	<input type="hidden"  value="${temp.playerName }" name="playerName">
	<input type="submit" value="Update Details of ${temp.playerName }"></form><br>
	<form action="/delete" method="post">
	<input type="hidden"  value="${temp.playerName }" name="playerName">
	<input type="submit" value="Delete ${temp.playerName }"></form><br>
</c:forEach>
</c:otherwise>
</c:choose>
</div>
</body>
</html>