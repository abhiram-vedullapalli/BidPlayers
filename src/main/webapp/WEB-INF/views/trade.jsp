<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trade Details</title>
</head>
<body>
	<h1 align="center">${Team}</h1>
	<br>
	<div align="center">
		<a href="homepage">Home</a>&nbsp;&nbsp; <a href="create">Create
			Player</a>&nbsp;&nbsp; <a href="myteam">My Team</a>&nbsp;&nbsp; <a href="trade">Trade
			Details</a>&nbsp;&nbsp; <a href="listall">Players On Sale</a>&nbsp;&nbsp;
		<a href="logout">Log Out</a>&nbsp;&nbsp; <br>
	</div>
	<br>
	<br>
	<h3 align="center" style="color: red;">${message }</h3>
	<div align="center"
		style=" max-width: 40%; position: absolute; left: 10%">
		<h3>Requests I Made</h3>
		<c:choose>
			<c:when test="${requestsIMade.isEmpty() }">
			You didn't place any buy requests
			</c:when>
			<c:otherwise>
				<c:forEach var="temp" items="${requestsIMade}">
					${temp.myRequests() }<br>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>

	<div align="center"
		style=" right: 0%; left: 60%; position: relative; width: 30%">
		<h3 >Requests to Me</h3>

		<c:choose>
			<c:when test="${requestsToMe.isEmpty() }">
				No buy requests where made for your players
			</c:when>
			<c:otherwise>
				<c:forEach var="temp" items="${requestsToMe}">
					<div >${temp }</div><br>
					<div >
						<form action="/accept" method="post">
						<input type="hidden" value="${temp.playerName }" name="playerName">
						<input type="hidden" value="${temp.reqTeamName }" name="reqTeamName">
						<input type="hidden" value="${temp.offeringPrice }" name="offeringPrice">
						<input type="submit" value="Accept"><br><br>
						</form>
						<form action="/reject" method="post">
						<input type="hidden" value="${temp.playerName }" name="playerName">
						<input type="hidden" value="${temp.reqTeamName }" name="reqTeamName">
						<input type="submit" value="Reject"><br><br>
						</form>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>