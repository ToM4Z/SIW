<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />

<script>

function aggiungiAdmin(x){
	
	  alert(x);
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var azione = "aggiungi";
	  var json = JSON.stringify({"nomeGruppo": gruppo,"nomeCanale" : canale, "user" : x, "azione": azione});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","gestioneAdmin", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("hai nominato admin "+x);
	  		}else{
	  			
	      }
	  }
	  xhr.send(json);
}

function rimuoviAdmin(x){
	  
	  alert(x);
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var azione = "rimuovi";
	  var json = JSON.stringify({"nomeGruppo": gruppo,"nomeCanale" : canale, "user" : x, "azione": azione});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","gestioneAdmin", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("hai rimosso admin "+x);
	  		}else{
	  			
	      }
	  }
	  xhr.send(json);
}

</script>

<html>
<head lang="it">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>LoosyNet</title>

		<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
		<script src="js/jquery-3.2.1.min.js"></script>
		<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
		
	</head>
</head>
<body>
	
	<h2>Stai gestendo gli admin del gruppo: </h2>
	<h1 id="nomeGruppo">${gruppo}</h1>
	<h1 id="nomeCanale" style="display:none">${canale}</h1>
	<h1></h1>
	<c:forEach var = "riga" items = "${righe}">
	
		${riga}
	
	</c:forEach>
	
</body>
</html>