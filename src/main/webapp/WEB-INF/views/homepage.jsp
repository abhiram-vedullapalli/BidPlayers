<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
<div align="center">
<h1 >PLAYERS BIDDING</h1>
<br>
<a href="create" >Create Player</a>&nbsp;&nbsp;
<a href="update">Update Player</a>&nbsp;&nbsp;
<a href="myteam">My Team</a>&nbsp;&nbsp;
<a href="listall">Players Directory</a>&nbsp;&nbsp;
<a href="logout">Log Out</a>&nbsp;&nbsp;
<br>
<h3>Team Name : ${TeamName } </h3>
<h3>Team Director : ${UserName }</h3>
<h3>Money in Purse : ${Balance } crores</h3>
<h3>Players Bought : ${NumPlayers }</h3>
</div>
</body>
</html>