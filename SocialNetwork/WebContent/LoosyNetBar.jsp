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
.notify{
margin-left:10px;
}
</style>

<script>
function search(){
		$.ajax({
			type: "POST",
			url: "search",
			datatype: "json",
			data: JSON.stringify({"search": $("#search").val()}),
			success: function(data){
				var data = JSON.parse(data);
		        if(data == "")
		        	$("#listaCanaliTrovati").append($("<li style=\"margin-left:10px;\">Nessun canale trovato</li>"));
		        else
		        	$("#listaCanaliTrovati").append(data);
		        
				if($("#listaCanaliTrovati").css("display") == "none"){
		    		$("#listaCanaliTrovati").slideDown();
		    		$("#listaCanaliTrovati").show();
		    	}
			}
		});
}

function collapseButton(){
	if($("#myNavbar").css("display")=="none"){
		$("#myNavbar").slideDown();
	}else{
		$("#myNavbar").slideUp();
	}
}
var timerNotifiche;
function getNotifiche(){
	timerNotifiche = setInterval(function(){
		$.ajax({
			type:"POST",
			url:"notifiche",
			success: function(data){
				if(data != "[]"){
					var liste = JSON.parse(data);
					$("#emptylist").remove();
					$("#numNotifiche").text(liste.length);
					$("#listaNotifiche").append(liste);
					$("#listaNotifiche").find('li').addClass('notify');
				}
			}
		});
	},1000);
}

function showNotify(){
	if($("#listaNotifiche").css("display") == "none"){
		$("#listaNotifiche").slideDown();
		$("#listaNotifiche").show();
	}else
		HideNotify();
}
function HideNotify(){
	$("#listaNotifiche").hide();
}
function clearlistSearch(){
	if($("#listaCanaliTrovati").css("display") == "block"){
		$("#listaCanaliTrovati").hide();
		$("#listaCanaliTrovati").find("li").remove();
	}	  
}
var mouse_in_listSearch = false;
var mouse_in_listNotify = false;
var mouse_in_linkNotify = false;

function onLoadLoosyNetBar(){
	getNotifiche();
	$("#linkShowNotify").focus(function(){
		$(this).blur();
	});
	 $('#listaCanaliTrovati').hover(function(){ 
		 	mouse_in_listSearch=true; 
	    }, function(){ 
	    	mouse_in_listSearch=false; 
	  });
	 $('#listaNotifiche').hover(function(){ 
		 	mouse_in_listNotify=true; 
	    }, function(){ 
	    	mouse_in_listNotify=false; 
	  });
	 $('#linkShowNotify').hover(function(){ 
		 	mouse_in_linkNotify=true; 
	    }, function(){ 
	    	mouse_in_linkNotify=false; 
	  });

    $("body").mouseup(function(){ 
        if(! mouse_in_listSearch)
        	clearlistSearch();
        if(! mouse_in_listNotify && !mouse_in_linkNotify)
        	HideNotify();
    });    
}
function stopLoosyNetBar(){
	clearInterval(timerNotifiche);
}
function onbeforeunloadLoosyNetBar(){
	stopLoosyNetBar();
	alert("stop");
	 $.ajax({
	    	type: "GET",
			url:"notifiche"
	    });
}
</script>

<body>
	<form action="javascript:onbeforeunloadLoosyNetBar()" style="display:none">
		<input type="submit" class="onbeforeunload">
	</form>
	<form action="javascript:onLoadLoosyNetBar()" style="display:none">
		<input type="submit" class="onload">
	</form>
	
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
						<div class="dropdown">
						<form class="navbar-form " action="javascript:search()">
							<div class="input-group" style="margin-top: 2px; margin-right:-30px;">
								<input type="text" id="search" autocomplete="off" class="form-control" onblur="javascript:clearlistSearch()" onkeyup="javascript:clearlistSearch()" placeholder="Search">
								<div class="input-group-btn" id="buttonSearch">
									<button class="btn btn-default" type="submit" onclick="javascript:clearlistSearch()">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</div>
							</div>
						</form>
						<ul id="listaCanaliTrovati" class="dropdown-menu" style="left:15px;">
							<li id="emptylist" class="notify">Non ci sono notifiche</li>
						</ul>
						</div>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="home"><span class="glyphicon glyphicon-home" id="icons"></span>
							Home</a></li>
					<li><a href="utente"><span class="glyphicon glyphicon-user"  id="icons"></span>
							${user.username}</a></li>
					<li class="dropdown">
						<a href="javascript:showNotify()" class="dropdown-toggle" id="linkShowNotify">					
							<span id="numNotifiche" class="badge badge-notify" style="background:red"></span>
							<span class="glyphicon glyphicon-globe" id="icons"></span>
								Notifiche
							<span class="caret"></span>
						</a>
						<ul id="listaNotifiche" class="dropdown-menu"></ul>
						</li>
					<li><a href="logout"><span class="glyphicon glyphicon-log-out" id="icons"></span>
							Esci</a></li>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>
