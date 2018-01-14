<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="canale" class="model.Canale" scope="request" />
<jsp:useBean id="gruppo" class="model.Gruppo" scope="request" />
<jsp:useBean id="post" class="model.Post" scope="request" />

<html>
<head>
<head lang="it">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>LoosyNet</title>

		<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
		<script src="js/jquery-3.2.1.min.js"></script>
		<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
		<script src="js/eliminaPost.js"></script>
	</head>
</head>

<script>
function creaPost(){
	
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var json = JSON.stringify({"gruppo": gruppo,"canale" : canale, "contenuto": $("#contenuto").val()});
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
</script>

<script>

function seiSicuroGruppo(){
	
	var gruppo = $("#nomeGruppo").text();
	var canale = $("#nomeCanale").text();
	
	if (confirm("Sei sicuro di voler cancellare il gruppo?") == true) {
	    
		document.location.href = "eliminaGruppo?group="+gruppo+"&channel="+canale;
		
	} else {
	    
		//do nothing
	} 
}


</script>

<script>

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

<body style="overflow-x:hidden">
	<c:if test="${empty user.nome}">
		<c:redirect url="login.html" />
	</c:if>
	<jsp:include page="LoosyNetBar.jsp" />
	<div class="row">
		<div class="col-bg-6 brd">
			<jsp:include page="barraCanali.jsp" />
		</div>
		<div class="col-bg-6 brd">
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
				<input id = "contenuto" type="text" name = "contenuto">
				<input type = "submit" value = "Pubblica">
				
				</form>
	
				<c:forEach var = "post" items = "${gruppo.post}">
					<h3><a href = utente?to=${post.creatore.email}> ${post.creatore.username}</a></h3>
					<c:if test = "${post.creatore.email == user.email}">
						<h6 onclick="javascript:seiSicuro(${post.id})">Elimina post</h6>
					</c:if>
					<p>${post.contenuto}</p>
					<small><small>${post.dataCreazione}</small></small>
				</c:forEach>
			</div>
		</div>
		<div class="col-bg-6 brd" style="margin-top:25px">
			<jsp:include page="chatGruppo.jsp" />
		</div>
	</div>
	 
	
</body>
</html>