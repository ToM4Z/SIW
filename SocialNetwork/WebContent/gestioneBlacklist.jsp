<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />



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

<script>

function aggiungiBlacklist(x){

	
	  var canale = $("#canale").text();
	  alert($("#canale").text());
	  var azione = "aggiungi";
	  var json = JSON.stringify({"nomeCanale" : canale, "user" : x, "azione": azione});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","gestioneBlacklist", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("hai aggiunto alla Blacklist "+x);
	  		}else{
	  			
	      }
	  }
	  xhr.send(json);
}

function rimuoviBlacklist(x){
	  
	  //alert(x);
	  var canale = $("#canale").text();
	  var azione = "rimuovi";
	  var json = JSON.stringify({"nomeCanale" : canale, "user" : x, "azione": azione});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","gestioneBlacklist", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("hai rimosso dalla Blacklist "+x);
	  		}else{
	  			
	      }
	  }
	  xhr.send(json);
}

</script>


<body onload="javascript:load()" style="overflow-x: hidden">
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
			<div id="homePost" style="margin-top: 60px; text-align: center;">

				<h2>Stai gestendo la Blacklist del canale:</h2>
				<h1>${canale}</h1>
				<h3 id="canale">${canale}</h3>
				<c:forEach var="riga" items="${righe}">

	

	
					${riga}
	
				</c:forEach>

			</div>
		</div>
	</div>
</body>
</html>