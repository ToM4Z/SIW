<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="user" class="model.Utente" scope="session" />

<html>
<head lang="it">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script>

function AccettaRichiestaIscrizione(fullname,email,gruppo,canale){
	$.ajax({
		type: "GET",
		url: "gestisciGruppo",
		data: {"esito": "y", "group":gruppo, "channel": canale, "utente": email},
		success: function(data){
			$("#acceptUser"+fullname).remove();
			if (isEmpty($('#listUserRequest')))
				$('#listUserRequest').append("Non ci sono altre richieste");
		}
	});
}
function RifiutaRichiestaIscrizione(fullname,email,gruppo,canale){
	$.ajax({
		type: "GET",
		url: "gestisciGruppo",
		data: {"esito": "n", "group":gruppo, "channel": canale, "utente": email},
		success: function(data){
			$("#acceptUser"+fullname).remove();
			if (isEmpty($('#listUserRequest')))
				$('#listUserRequest').append("Non ci sono altre richieste");
		}
	});
}
function isEmpty( el ){
    return !$.trim(el.html())
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
			<div id="homePost" style="margin-top:60px; text-align: center;">
				<h2>Lista iscrizioni al gruppo ${gruppo.nome}</h2>
				
				<c:if test = "${vuoto == true}">
					<h4>Non ci sono altre richieste</h4>
				</c:if>
				<br>
				<div id="listUserRequest">
				<c:forEach var="utente" items="${utentiInAttesa}">
					<h4 id="acceptUser${utente.nome}${utente.cognome}">${utente.nome} ${utente.cognome} (${utente.username}) : 
						<a href ='javascript:AccettaRichiestaIscrizione("${utente.nome}${utente.cognome}","${utente.email}","${gruppo.nome}","${gruppo.canale.nome}")'>Accetta</a> / 
						<a href ='javascript:RifiutaRichiestaIscrizione("${utente.nome}${utente.cognome}","${utente.email}","${gruppo.nome}","${gruppo.canale.nome}")'>Rifiuta</a></h4>
				</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>