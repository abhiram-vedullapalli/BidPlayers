<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%

BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

%>

    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Player</title>
</head>
<body>
<div align="center">
<h1>Create Player for Team : ${TeamName}</h1>
<br>
<div align="center">
<a href="homepage">Home</a>&nbsp;&nbsp;
<a href="create" >Create Player</a>&nbsp;&nbsp;
<a href="myteam">My Team</a>&nbsp;&nbsp;
<a href="trade">Trade Details</a>&nbsp;&nbsp;
<a href="listall">Players On Sale</a>&nbsp;&nbsp;
<a href="logout">Log Out</a>&nbsp;&nbsp;
<br>
</div>
<br>
<form action="<%= blobstoreService.createUploadUrl("/create") %>" method="post" enctype="multipart/form-data">
Enter Name of Player : <input type="text" required="required" name="playerName"><br><br>
Enter Age of Player : <input type="number" name="playerAge"  min="16" max="40" required="required"><br><br>
Upload Picture of Player : <input type="file" name="myFile" required="required"><br><br>

<input type="submit" value="Create">
</form>
<p style="color: green;">${message }</p>
</div>
</body>
</html>