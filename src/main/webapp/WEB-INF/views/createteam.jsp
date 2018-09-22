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
<title>Create Team</title>
</head>
<body>
<div align="center">
<h1>CREATE TEAM</h1>


<form action="<%= blobstoreService.createUploadUrl("/register") %>" method="post" enctype="multipart/form-data">
<label>Enter Your User Name</label><br>
<input type="text" name="UserName" required="required"><br><br>
<label>Enter Your Team Name</label><br>
<input type="text" name="TeamName" required="required"><br><br>
<label>Upload Team Logo</label><br>
<input type="file" name="myFile" required="required"><br><br>
<input type="submit" value="Create Team">
</form>

</div>
</body>
</html>