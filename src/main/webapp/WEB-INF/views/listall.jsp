<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Players Directory</title>

<!-- Bootstrap 4 -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<!-- Bootstrap 4 Close -->
</head>
<body>
	<h1 align="center">Players On Sale</h1>
	<br>
	<div align="center">
		<a href="homepage">Home</a> <a href="create">Create Player</a>&nbsp;&nbsp;
		<a href="myteam">My Team</a>&nbsp;&nbsp; <a href="trade">Trade
			Details</a>&nbsp;&nbsp; <a href="listall">Players On Sale</a>&nbsp;&nbsp;
		<a href="logout">Log Out</a>&nbsp;&nbsp; <br> <br> <b
			style="color: green;">Funds Available : </b> ${requestingUser.balance }
		crores &nbsp;&nbsp;&nbsp; <b style="color: green;">Team Strength :
		</b> ${requestingUser.numPlayers - requestingUser.sold} <br> <br>
		<b style="color: aqua;">${message }</b><br> <br>
		<div>
			<c:choose>
				<c:when test="${tradePlayers.isEmpty() }">
				No Players were listed for Trade
				</c:when>
				<c:otherwise>
					<div class="container-fluid">
						<div class="row">
							<c:forEach var="temp" items="${tradePlayers}">
								<div class="col-xs-6 col-md-3 col-xl-2">
									<div class="card"
										style="padding-top: 15px; padding-bottom: 15px;">
										<img class="card-img-top" src="${temp.playerImage }"
											alt="Image of ${temp.playerImage}" height="150" width="100">
										<div class="card-body" align="center">
											 <h5>${temp.playerName }</h5><br> 
											<b>Age</b> : ${temp.playerAge }<br>
											<b>Asking Price</b> : ${temp.playerPrice }<br> 
											<b>Team</b> : ${temp.playerTeam }<br> 
											<b>Owner</b> : ${temp.playerOwner }<br><br>
											<form action="/buy" method="post">
												<input type="number" name="offeringPrice"
													required="required" style="width: 120px;" min="1" max="15"
													placeholder="Your Offer ?"><br><br>
												<input type="hidden" value="${temp.playerName }"
													name="playerName"> <input type="hidden"
													value="${requestingUser.teamName }" name="reqTeamName">
												<input type="hidden" value="${requestingUser.balance }"
													name="reqTeamBalance"> <input type="submit"
													value="Buy ${temp.playerName }">
											</form>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>

					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>
<%-- <c:forEach var="temp" items="${tradePlayers }">
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
					</c:forEach> --%>