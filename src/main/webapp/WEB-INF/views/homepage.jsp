<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%-- <%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
String blobKey = (String)request.getParameter("blob-key");
%> 
 --%>     
<!DOCTYPE html >
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
<div align="center">
<h1 >PLAYERS BIDDING</h1>
<br>
<a href="create" >Create Player</a>&nbsp;&nbsp;
<a href="myteam">My Team</a>&nbsp;&nbsp;
<a href="trade">Trade Details</a>&nbsp;&nbsp;
<a href="listall">Players On Sale</a>&nbsp;&nbsp;
<a href="logout">Log Out</a>&nbsp;&nbsp;
<br>
<h3>Team Name : ${User.teamName } </h3>
<h3>Team Director : ${User.userName }</h3>
<h3>Money in Purse : ${User.balance } crores</h3>
<h3>No.of Players Bought : ${User.numPlayers }</h3>
<h3>No.of Players Sold   : ${User.sold }</h3>
<h3>Profit Made out of Trade : ${User.profit } crores</h3>
<img height="200" width="200" alt="${User.teamName }" src="${User.image }">
</div>
</body>
</html>