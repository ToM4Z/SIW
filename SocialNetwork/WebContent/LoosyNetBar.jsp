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

<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="home.jsp">LoosyNet</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar"
				style="overflow-x: hidden">

				<ul class="nav navbar-nav">
					<li>
						<form class="navbar-form navbar-left" action="#">
							<div class="input-group" style="margin-top: 2px">
								<input type="text" id="search" class="form-control"
									placeholder="Search">
								<div class="input-group-btn" style="left: -20px !important">
									<button class="btn btn-default" type="submit">
										<i class="glyphicon glyphicon-search"></i>
									</button>
								</div>
							</div>
						</form>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="home.jsp"><span class="glyphicon glyphicon-home"></span>
							Home</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-user"></span>
							${user.nome}</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-cog"></span>
							Impostazioni</a></li>
					<li><a href="logout"><span class="glyphicon glyphicon-log-out"></span>
							Esci</a></li>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>