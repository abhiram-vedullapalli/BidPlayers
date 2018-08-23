<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Details</title>
</head>
<body>
<div align="center">
<h1>Update Details of ${playerName } </h1>
<br>
<div align="center">
<a href="homepage">Home</a>&nbsp;&nbsp;
<a href="create" >Create Player</a>&nbsp;&nbsp;
<a href="update">Update Player</a>&nbsp;&nbsp;
<a href="myteam">My Team</a>&nbsp;&nbsp;
<a href="listall">Players Directory</a>&nbsp;&nbsp;
<a href="logout">Log Out</a>&nbsp;&nbsp;
<br>
</div>
<br>
<form action="update" method="post">
Name of Player : <input type="text" required="required" readonly="readonly" value="${playerName}"  name="playerName"><br><br>
Current Age of ${playerName} is ${playerAge }<br><br>
Update Age of Player : <input type="text" name="playerAge" pattern="[0-9]{2}" min="16" max="40" required="required"><br><br>
Current Price of ${playerName} is ${playerPrice }<br>
<br>
Update Player Price :	<input type="text" name="playerPrice" pattern="[0-9]{,2}" min="1" max="15" required="required"><br><br>
<input type="submit" value="Update">
</form>

</div>
</body>
</html>