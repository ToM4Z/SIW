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
				<form action = "javascript:creaPost()">
				<input id = "contenuto" type="text" name = "contenuto">
				<input type = "submit" value = "Pubblica">
				
				</form>
	
				<c:forEach var = "post" items = "${gruppo.post}">
					<h3><a href = utente?to=${post.creatore.email}> ${post.creatore.username}</a></h3>
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