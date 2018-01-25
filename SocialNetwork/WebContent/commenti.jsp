<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="user" class="model.Utente" scope="session" />

<html>
<head lang="it">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="js/post.js"></script>
<script>
function deleteCommento(idCommento){
	
	if (confirm("Sei sicuro di voler cancellare il post?") == true) {
		  var idPost = $("#idPost").text();
		  var elimina = "true";
		  var json = JSON.stringify({"idCommento": idCommento, "elimina" : elimina});
		  alert(json);
		  var xhr = new XMLHttpRequest();
		  xhr.open("post","eliminaCommenti", true);
		  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
		  xhr.setRequestHeader("connection","close");
		  xhr.setRequestHeader("Content-Type", "application/json");
		  
		  $("#commento_"+idCommento).remove();
		  
		  xhr.send(json);
	}
};

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
	<c:if test="${empty user.username}">
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
				<h1 id="idPost" style="display:none">${post.id}</h1>
				<h2>${post.creatore.username}</h2>
				<p>${post.contenuto}</p>
				<h1 id="nomeCanale" style="display:none">${post.canale.nome}</h1>
				<h1 id="nomeGruppo" style="display:none">${post.gruppo.nome}</h1>
				<form action = "javascript:addCommento('${post.id}')">
					<input id="commento${post.id}" autocomplete="off" type="text" name="commento" >
					<input type = "submit" value = "Commenta">
				</form>
				
				<small>commenti:</small>
				<ul id = "listaCommenti">
				<c:forEach var="commento" items="${commenti}">
				
					<div id=commento_${commento.id}>
					<li>
						<h4>${commento.creatore.username}</h4>
						<c:if test = "${commento.creatore.email == user.email || post.creatore.email == user.email}">
							<small onclick = "javascript:deleteCommento(${commento.id})">Elimina commento</small>
						</c:if>
						<h4>${commento.contenuto}</h4>
						</li>
					</div>
				
				</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>