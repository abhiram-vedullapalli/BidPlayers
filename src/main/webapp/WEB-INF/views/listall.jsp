<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List All</title>
</head>
<body>
	<h1 align="center">Players On Sale</h1>
	<br>
	<div align="center">
		<a href="homepage">Home</a> <a href="create">Create Player</a>&nbsp;&nbsp;
		<a href="update">Update Player</a>&nbsp;&nbsp; <a href="myteam">My
			Team</a>&nbsp;&nbsp; <a href="trade">Trade Details</a>&nbsp;&nbsp; <a
			href="listall">Players On Sale</a>&nbsp;&nbsp; <a href="logout">Log
			Out</a>&nbsp;&nbsp; <br>
		<br> <b style="color: green;">Funds Available : </b> ${Balance }
		crores &nbsp;&nbsp;&nbsp; <b style="color: green;">Team Strength :
		</b> ${NumPlayers } <br>
		<br> <b style="color: aqua;">${message }</b><br>
		<br>
		<div>
			<c:choose>
				<c:when test="${tradePlayers.isEmpty() }">
				No Players were listed for Trade
				</c:when>
				<c:otherwise>
					<c:forEach var="temp" items="${tradePlayers }">
						${temp.allDetails() }<br>
						<form action="/buy" method="post">
							<input type="number" name="offeringPrice" required="required" style="width: 225px;"
								min="1" max="15" placeholder="How much are you willing to Offer ?">
							<input type="hidden" value="${temp.playerName }"
								name="playerName"> <input type="hidden"
								value="${TeamName }" name="reqTeamName"> <input
								type="hidden" value="${Balance }" name="reqTeamBalance">
							<input type="submit" value="Buy ${temp.playerName }">
						</form>
						<br>
						<br>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>