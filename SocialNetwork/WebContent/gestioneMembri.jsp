<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />

<script>

function aggiungiMembro(x){
	
	  
	  var canale = $("#nomeCanale").text();
	  var gruppo = $("#nomeGruppo").text();
	  var azione = "aggiungi";
	  var json = JSON.stringify({"nomeCanale" : canale, "nomeGruppo" : gruppo, "user" : x, "azione": azione});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","gestioneMembri", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("hai aggiunto ai membri "+x);
	  		}else{
	  			
	      }
	  }
	  xhr.send(json);
}

function rimuoviMembro(x){
	  
	  alert(x);
	  var canale = $("#nomeCanale").text();
	  var gruppo = $("#nomeGruppo").text();
	  var azione = "rimuovi";
	  var json = JSON.stringify({"nomeCanale" : canale, "nomeGruppo" : gruppo, "user" : x, "azione": azione});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","gestioneMembri", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("hai rimpsso dai membri "+x);
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
<script>
	function load() {
		$("input.onload").each(function() {
			$(this).trigger("click");
		});
	};
</script>
</head>
<body onload="javascript:load()" style="overflow-x: hidden">
	<c:if test="${empty user.nome}">
		<c:redirect url="login.html" />
	</c:if>
	<jsp:include page="LoosyNetBar.jsp" />
	<div class="row">
		<div class="col-bg-6 brd">
			<jsp:include page="barraCanali.jsp" />
		</div>
		
		</div>
		<div class="col-bg-6 brd">
			<div id="homePost" style="margin-top: 60px; text-align: center;">

				<h2>Stai gestendo i membri del gruppo:</h2>
				<h1 id="nomeGruppo">${gruppo}</h1>
				<h1 id="nomeCanale" style="display:none">${canale}</h1>
				<c:forEach var="riga" items="${righe}">
	
					${riga}
	
				</c:forEach>

			</div>
		
	</div>
	
</body>
</html>