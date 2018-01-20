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
  margin-top: 4px;
  width:  calc(40vw - 120px);
  margin-right: -9px;
	height: 28px;
}
#icons{
  position: relative;
  top:3px;
  font-size: 20px;
}
.badge-notify{
  position: absolute;
  font-size: 10px;
  margin-top: -8px;
  margin-left: 15px;
}
#buttonSearch{
  bottom:-2px;
  left: -30px !important;
}
@media screen and (max-width : 767px) {
  #search{
    margin-top: 4px;
  	margin-left: 10px;
    width:94%;
  	height: 28px;
  }
  #buttonSearch{
    bottom:-2px;
    left: -40px !important;
  }
}
</style>

<script>

function search(){
	
	  //alert($("#search").val());
	  var json = JSON.stringify({"search": $("#search").val()});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","search", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");

	  xhr.onreadystatechange = function () {
		    if (xhr.readyState === 4 && xhr.status === 200) {
		        var data = JSON.parse(xhr.responseText);
		        $("#listaRisultati").append(data);
		        
		    }
		};
		xhr.send(json);
		showResults();
}

function collapseButton(){
	if($("#myNavbar").css("display")=="none"){
		$("#myNavbar").slideDown();
	}else{
		$("#myNavbar").slideUp();
	}
}

function getNotifiche(){
	$.ajax({
		type:"GET",
		url:"notifiche",
		success: function(data){
			if(data!='0'){
				var liste = JSON.parse(data);
				$("#numNotifiche").append(liste.length);
				$("#listaNotifiche").append(liste);
			}
		}
	});
}

function showNotify(){
	if($("#listaNotifiche").css("display") == "none"){
		$("#listaNotifiche").slideDown();
		$("#listaNotifiche").show();
	}else{
		$("#listaNotifiche").slideUp();
		$("#listaNotifiche").css("display:none");
	}
}

function showResults(){
	if($("#listaRisultati").css("display") == "none"){
		$("#listaRisultati").slideDown();
		$("#listaRisultati").show();
	}else{
		$("#listaRisultati").slideUp();
		$("#listaRisultati").css("display:none");
	}
}



</script>

<body>
	<div style="display:none">
	<form action="javascript:getNotifiche()">
		<input type="submit" class="onload">
	</form>
	</div>
	
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
				<a class="navbar-brand" href="home" style="margin-top:4px;margin-left:3%">LoosyNet</a>
			</div>

			<div class="collapse navbar-collapse" id="myNavbar"	style="overflow-x: hidden">

				<ul class="nav navbar-nav navbar-left">
					<li>
						<form class="navbar-form " action="javascript:search()">
							<div class="input-group" style="margin-top: 2px; margin-right:-30px;">
								<input type="text" id="search" class="form-control"	placeholder="Search">
								<div class="input-group-btn" id="buttonSearch">
									<button class="btn btn-default" type="submit">
										<i class="glyphicon glyphicon-search"></i>
									</button>
								</div>
							</div>
						</form>
						<div id="listaRisultati" style="position:absolute; display:none">
								<ul></ul>
						  		</div>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="home"><span class="glyphicon glyphicon-home" id="icons"></span>
							Home</a></li>
					<li><a href="utente"><span class="glyphicon glyphicon-user"  id="icons"></span>
							${user.nome}</a></li>
					<li><a href="javascript:showNotify()">					
						<span id="numNotifiche" class="badge badge-notify" style="background:red"></span>
						<span class="glyphicon glyphicon-globe" id="icons"></span>
							Notifiche
							<span class="caret"></span></a>
						<div id="listaNotifiche" style="position:absolute; display:none">
						<ul></ul>
						  </div>
						</li>
					<li><a href="logout"><span class="glyphicon glyphicon-log-out" id="icons"></span>
							Esci</a></li>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>
