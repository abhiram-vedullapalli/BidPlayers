<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trade Details</title>
</head>
<body>
	<h1 align="center">${TeamName }</h1>
	<br>
	<div align="center">
		<a href="homepage">Home</a>&nbsp;&nbsp; <a href="create">Create
			Player</a>&nbsp;&nbsp; <a href="update">Update Player</a>&nbsp;&nbsp; <a
			href="myteam">My Team</a>&nbsp;&nbsp; <a href="trade">Trade
			Details</a>&nbsp;&nbsp; <a href="listall">Players On Sale</a>&nbsp;&nbsp;
		<a href="logout">Log Out</a>&nbsp;&nbsp; <br>
	</div>
	<br>
	<br>
	<h3 align="center" style="color: red;">${message }</h3>
	<div
		style="background-color: yellow; max-width: 40%; position: absolute; left: 10%">
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

	<div
		style="background-color: aqua; right: 0%; left: 60%; position: relative; width: 30%">
		<h3 align="center">Requests to Me</h3>

		<c:choose>
			<c:when test="${requestsToMe.isEmpty() }">
				No buy requests where made for your players
			</c:when>
			<c:otherwise>
				<c:forEach var="temp" items="${requestsToMe}">
					<div align="center">${temp }</div>
					<div style="display: inline-block;">
						<form action="/accept" method="post">
						<input type="hidden" value="${temp.playerName }" name="playerName">
						<input type="hidden" value="${temp.reqTeamName }" name="reqTeamName">
						<input type="hidden" value="${temp.offeringPrice }" name="offeringPrice">
						<input type="submit" value="Accept">
						</form>
						<form action="/reject" method="post">
						<input type="hidden" value="${temp.playerName }" name="playerName">
						<input type="hidden" value="${temp.reqTeamName }" name="reqTeamName">
						<input type="submit" value="Reject">
						</form>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>