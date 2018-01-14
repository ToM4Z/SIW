<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="user" class="model.Utente" scope="session" />

<html>
<head lang="it">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<style>
#search {
	margin-left: 10px;
	height: 28px;
	min-height: 28px;
	max-height: 28px;
}
</style>

<script>
function collapseButton(){
	if($("#myNavbar").css("display")=="none"){
		$("#myNavbar").slideDown();
	}else{
		$("#myNavbar").slideUp();
	}
}
</script>

<body>
	<nav class="navbar navbar-default navbar-static-top navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
					  aria-expanded="false" onclick="javascript:collapseButton()">
					<span class="sr-only">Toggle navigation</span>
        			<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="home" style="margin-left:3%">LoosyNet</a>
			</div>
			
			<div class="collapse navbar-collapse" id="myNavbar"	style="overflow-x: hidden">

				<ul class="nav navbar-nav navbar-left">
					<li>
						<form class="navbar-form " action="#">
							<div class="input-group" style="margin-top: 2px; margin-right:-30px;">
								<input type="text" id="search" class="form-control"	placeholder="Search">
								<div class="input-group-btn" style="left: -20px !important;">
									<button class="btn btn-default" type="submit">
										<i class="glyphicon glyphicon-search"></i>
									</button>
								</div>
							</div>
						</form>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="home"><span class="glyphicon glyphicon-home"></span>
							Home</a></li>
					<li><a href="utente"><span class="glyphicon glyphicon-user"></span>
							${user.nome}</a></li>
					<li><a href="notifiche"><span class="glyphicon glyphicon-cog"></span>
							Notifiche</a></li>
					<li><a href="logout"><span class="glyphicon glyphicon-log-out"></span>
							Esci</a></li>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>