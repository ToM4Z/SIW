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

function aggiungiMembro(x){
	
	var canale = $("#NomeCanale").text();
	var gruppo = $("#NomeGruppo").text();
	var azione = "aggiungi";
	
	$.ajax({
		type: "POST",
		url: "gestioneMembri",
		datatype: "json",
		data: JSON.stringify({"nomeCanale" : canale, "nomeGruppo" : gruppo, "user" : x, "azione": azione}),
		success: function(data){
			var user = JSON.parse(data);
		    $("#mem"+user+" a").replaceWith("<a href = javascript:rimuoviMembro('"+x+"')> Rimuovi membro</a>");
	    }
	});
}

function rimuoviMembro(x){
	 
	  //alert(x);
	  var canale = $("#NomeCanale").text();
	  var gruppo = $("#NomeGruppo").text();
	  var azione = "rimuovi";
	  //alert(gruppo);
	  $.ajax({
			type: "POST",
			url: "gestioneMembri",
			datatype: "json",
			data: JSON.stringify({"nomeCanale" : canale, "nomeGruppo" : gruppo, "user" : x, "azione": azione}),
			success: function(data){
				var user = JSON.parse(data);
		        $("#mem"+user+" a").replaceWith("<a href = javascript:aggiungiMembro('"+x+"')> Aggiungi membro</a>");
	    	}
		});
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
			<div id="homePost" style="margin-top: 60px; text-align: center;">

				<h2>Gestione membri del gruppo:</h2>
				<h1 id="NomeGruppo">${nomegruppo}</h1>
				<h1 id="NomeCanale" style="display:none">${nomecanale}</h1>
				<c:forEach var="riga" items="${righe}">
	
					${riga}
	
				</c:forEach>

			</div>
		</div>
	</div>
</body>
</html>