<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="canale" class="model.Canale" scope="request" />
<jsp:useBean id="gruppo" class="model.Gruppo" scope="request" />
<jsp:useBean id="post" class="model.Post" scope="request" />

<html>
<head lang="it">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>LoosyNet</title>

		<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
		<script src="js/jquery-3.2.1.min.js"></script>
		<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
		<script src="js/eliminaPost.js"></script>

<script>
function creaPost(){
	
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var json = JSON.stringify({"gruppo": gruppo,"canale" : canale, "contenuto": $("#contenuto").val()});
	  //alert($("#contenuto").val());
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","creaPost", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  window.location.replace("gruppo?group="+gruppo+"&channel="+canale);
	  		}else{
	      }
	  }
	  xhr.send(json);
}


function addCommento(idPost){
	
	  alert("addando");
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var json = JSON.stringify({"gruppo": gruppo,"canale" : canale, "idPost":idPost, "commento": $("#"+idPost).val()});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","commento", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("commento aggiunto")
	  		}else{
	      }
	  }
	  xhr.send(json);
}

function seiSicuroGruppo(){
	
	var gruppo = $("#nomeGruppo").text();
	var canale = $("#nomeCanale").text();
	
	if (confirm("Sei sicuro di voler cancellare il gruppo?") == true) {
	    
		document.location.href = "eliminaGruppo?group="+gruppo+"&channel="+canale;
		
	} else {
	    
		//do nothing
	} 
}

function inviaNotificaRichiesta(){
	
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var tipo = "richiestaIscrizione";
	  var json = JSON.stringify({"nomeGruppo": gruppo,"nomeCanale" : canale, "tipo" : tipo});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","inviaNotifica", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("richiesta inviata");
	  		}else{
	  			
	      }
	  }
	  xhr.send(json);
	  
}

</script>
<style>
.post{
	background-color: #E8E8E8;
	color: #555;
	border: .1em solid;
	border-color: #C0C0C0;
	border-radius: 10px;
	font-family: Tahoma, Geneva, Arial, sans-serif;
	font-size: 1.1em;
	padding: 10px;
	margin: 20px;
	cursor: default;
	position: relative;
  	left:  calc(40vw - 5%);
    width: calc(40vw - 10%);
}
.post-header{
  padding-left: 10px;
	cursor: default;
  display: inline;
}
.post-header-left{
  float:left;
}
.post-header a{
  font-family: Tahoma, Geneva, Arial, sans-serif;
	font-size: 1.1em;
  color:#555;
}
.post-header-right{
  float: right;
}
.post-body{
  padding-top: 10px;
}
.post-footer{
    margin-bottom: -30px;
}
.contenuto{
  font-size: 1.2em;
}
.post-hr{
	border-color: #D2D2D2;
	margin-top:4px; 
	margin-bottom:5px; 
}
@media screen and (max-width : 767px) {
	#barraCanali, #chatGruppo{
		display:none;
		float:none !important;
		width:100% !important;
	}
	#writeMex{
		width: 100% !important;
		bottom: 10px;
	}
	#switchPanel{
		display:block !important;
	}
	.post{
		left:10%;
		width:70%;
	}
}

#switchPanel{
	position:fixed;
	top:60px;
	left:10px;
	display:none;
}
</style>
<script>
var page = 2;
function shiftLeft(){
	if(page===2){
		$("#homePost").hide();
		$("#barraCanali").show();
		--page;
	}else if(page===3){
		$("#chatGruppo").hide();
		$("#homePost").show();
		--page;
	}
}
function shiftRight(){
	if(page===1){
		$("#barraCanali").hide();
		$("#homePost").show();
		++page;
	}else if(page===2){
		$("#homePost").hide();
		$("#chatGruppo").show();
		++page;
	}
}
function load(){
	$("input.onload").each(function(){
		$(this).trigger("click");
	});
};
function unload(){
	$("input.onbeforeunload").each(function(){
		$(this).trigger("click");
	});
}
</script>
</head>
<body onload="javascript:load();" onbeforeunload="javascript:unload()" style="overflow-x:hidden">
	<c:if test="${empty user.nome}">
		<c:redirect url="login.html" />
	</c:if>
	<jsp:include page="LoosyNetBar.jsp" />
	<div class="row">
		<div class="col-bg-6 brd">
			<jsp:include page="barraCanali.jsp" />
		</div>		
		<div class="col-bg-6 brd">
			<jsp:include page="chatGruppo.jsp" />
		</div>
		<div class="col-bg-6 brd">
			<div id="switchPanel">
					<span class="glyphicon glyphicon-chevron-left" style="font-size:2em;" onclick="javascript:shiftLeft()"></span>
					<span class="glyphicon glyphicon-chevron-right" style="font-size:2em;left:500%;" onclick="javascript:shiftRight()"></span>
				</div>
			<div id="homePost" style="margin-top:60px; text-align: center;">
				
				<h1 id="nomeGruppo">${gruppo.nome}</h1>
				<h1 id="nomeCanale" style="display:none">${gruppo.canale.nome}</h1>
				
				<c:if test = "${admin == true }">
					<h5 onclick="seiSicuroGruppo()">Elimina gruppo</h5>
					<h5><a href = utentiInAttesa?group=${gruppo.nome}&channel=${gruppo.canale.nome}>Visualizza utenti in attesa</a></h5>
					<h5><a href = gestioneAdmin?group=${gruppo.nome}&channel=${gruppo.canale.nome}>Gestisci admin</a></h5>
				</c:if>

				<c:choose>
					<c:when test="${iscritto == true }">
						<h4>
							<a href=gestisciGruppo?channel=${gruppo.canale.nome}&group=${gruppo.nome}&esito=cancellazione>Cancellati dal gruppo</a>
						</h4>

					</c:when>
					<c:when test="${iscritto == false }">
						<h4 onclick = "javascript:inviaNotificaRichiesta()">
							Richiedi iscrizione al gruppo  
						</h4>
					</c:when>
				</c:choose>

				<form action = "javascript:creaPost()">
					<input id="contenuto" autocomplete="off" type="text" name="contenuto" >
					<input type = "submit" value = "Pubblica">
				</form>
	
				<c:forEach var = "post" items = "${gruppo.post}">
				  <div class="row">
				  <div class="post">
				    <div class="post-header">
				    <div class="post-header-left">
				        <a href = "utente?to=${post.creatore.email}">${post.creatore.username}</a>
				    </div>
				    <div class="post-header-right">
				      <c:if test = "${post.creatore.email == user.email}">
				        <a onclick="javascript:seiSicuro(${post.id})"><span class="glyphicon glyphicon-pencil"></span></a>
				      </c:if>
				      <c:if test = "${post.creatore.email == user.email || admin==true}">
				        <a onclick="javascript:seiSicuro(${post.id})"><span class="glyphicon glyphicon-trash"></span></a>
				      </c:if>
				    </div>
				    <div class="post-body">
				      <hr class="post-hr">
				      <p class="contenuto">${post.contenuto}</p>
				    </div>
				    <div class="post-footer">
				      <p class="date"><small>${post.dataCreazione}</small></p>
				      <hr class="post-hr">
				      <div style="display:inline" class="row">
				         <span class="glyphicon glyphicon-thumbs-up" style="font-size:1.5em;padding-left:10%;padding-right:20%">10</span>
				        <span class="glyphicon glyphicon-thumbs-down" style="font-size:1.5em;padding-right:10%">10</span>
				      </div>
				      <hr class="post-hr">
				        <a href = "commenti?idPost=${post.id}" style="font-size:1em">mostra commenti</a>
				        <div style="display:inline">
				      <form action = "javascript:addCommento(${post.id})">
				        <input id="${post.id }" type="text" class="form-control"
											placeholder="Commenta"
											name = "${post.id }">
				        <button type="button" class="btn btn-default" style="position:absolute;right:7;bottom:16px;height:33px"><span class="glyphicon glyphicon-send" style="font-size:1.2em;margin:-2px"></span></button>
				      </form>
				    </div>
				    </div>
				  </div>
				</div>
				</div>			
				</c:forEach>	
			</div>
		</div>
	</div>	
</body>
</html>