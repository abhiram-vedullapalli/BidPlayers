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
Update Age of Player : <input type="number" name="playerAge" value="${playerAge }"  min="16" max="40" ><br><br>
Current Price of ${playerName} is ${playerPrice }<br>
<br>
Set Selling Price :	<input type="number" name="playerPrice"  min="1" max="15"  ><br><br>
<input type="submit" value="Update">
</form>
<ul style="color: green;">
<li style="color: navy;">Set Selling Price for Player , If You want to list him for Trade</li>
<li>Player won't agree for decreasing his Price by current Team</li>
<li>Increase Player's Price to make him Happy or to Earn Profits for your Club if someone bids for this Player</li>
</ul>
</div>
</body>
</html>