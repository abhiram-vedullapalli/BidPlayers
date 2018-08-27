<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Back</title>
</head>
<body>
<div align="center">
<h1 align="center">PLAYERS BIDDING</h1>
<br>
<a href="create" >Create Player</a>&nbsp;&nbsp;
<a href="update">Update Player</a>&nbsp;&nbsp;
<a href="myteam">My Team</a>&nbsp;&nbsp;
<a href="trade">Trade Details</a>&nbsp;&nbsp;
<a href="listall">Players On Sale</a>&nbsp;&nbsp;
<a href="logout">Log Out</a>&nbsp;&nbsp;
<br>
<h3>Team Name : ${TeamName } </h3>
<h3>Team Director : ${UserName }</h3>
<h3>Money in Purse : ${Balance } crores</h3>
<h3>Players Bought : ${NumPlayers } </h3>
<h3>No.of Players Sold   : ${Sold }</h3>
<h3>Profit Made out of Trade : ${Profit }</h3>
</div>
</body>
</html>