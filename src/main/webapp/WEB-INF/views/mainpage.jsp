
<%@ page language="java" session="false" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html >
<html>
<head>
<title>Welcome</title>

<!-- Google Sing in  -->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id" content="1097688824452-sahp286og0evom6coidb82hid5tmvate.apps.googleusercontent.com">

<!-- End of Google Sign In -->

<!-- ViewPort and BootStrap4 styling -->
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<!-- End of Bootstrap4  -->

</head>
<body style="background-color: #0FB9F4;" >
<%-- <h1 align="center" style="color: red;"> WELCOME TO PLAYERS </h1>
<br>
<h2 align="center">Create Your Own Team , Bid for Players , Buy Players for your Team</h2>
<br><br>
<div align="center" style="max-width: 40%;position:absolute;left: 10%">
<form:form  action="register" modelAttribute="user" method="POST">
<h4>REGISTER HERE</h4>
Enter User Name : <form:input path="userName" required="required"  title="Enter Lower case letters only" />
<p style="color: red;">${message }</p>
Enter Password  : <form:password path="passWord" required="required"/>
<br><br>
Create Your Team : <form:input path="teamName" required="required"  title="Enter alphabets only" placeholder="Enter Team Name"/>
<br><br>
<input type="submit" value="Register">
</form:form>
</div>
<div align="center" style=" right: 0%;left: 60%;position:relative;width: 30%">
<h4>Already Registered ? Log In Here</h4>
<form:form action="homepage" modelAttribute="user" method="post">
Enter User Name : <form:input path="userName" required="required"  title="Enter Lower case letters only" />
<br><br>
Enter Password  : <form:password path="passWord" required="required"/>
<p style="color: red;">${message2 }</p>
<input type="submit" value="Login">
</form:form>
</div> --%>

<div style="min-height: 10vh;">	
<div class="container-fluid text-white" >
	<header style="padding:10px;">
		<h1 class="display-4"><b>Bid Players</b></h1>
	</header>
</div>
</div>


<!-- Div for Registration and quote -->
<div class="container-fluid " style="margin-top:10vh;margin-bottom: 10vh;">
	<div class="row text-white justify-content-center">
		<div class="col-lg-8 col-lg-offset-3" align="center">
			<h3 >Sign Up Here</h3>
			<form action="emailsignin" method="post">
			<label>Enter Your Email : </label>
			<input type="email" name="email" required="required" ><br><br>
			<label>Enter Password : </label>
			<input type="password" name="password" required="required"><br><br>
			<div>
			<input type="submit" class="btn btn-outline-light" value="Sign In" name="Sign In"><br><br>
			<div class="g-signin2" align="center" data-width="200"  data-onsuccess="onSignIn"></div></div>
			</form>
		</div>
		
	
	</div>
	
	
	
</div>

<div class="container-fluid mt-md-5">
	<div class="row text-white">
		<div class="col-12 text-sm-center p-2">
			<blockquote class="blockquote text-center ">
  				<p class="mb-0"><b>"buy , bid and trade players ; Earn Huge amounts Profits / Succumb to loses"</b> </p>
			</blockquote>

		</div>
	</div>

</div>

<!-- Signin the User -->
<script type="text/javascript" src="scripts/googlesignin.js"></script>

<!-- BootStrap4 corresponding JS , Ajax and JQuery functions and libraries -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>