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
			<div id="homePost" style="margin-top:60px; text-align: center;">
				<c:if test="${not empty utente.nome}">	
					<h3><strong>Nome:</strong> ${utente.nome}</h3>
					<h3><strong>Cognome:</strong> ${utente.cognome}</h3>
					<h3><strong>Username:</strong> ${utente.username}</h3>
					<h3><strong>Email:</strong> ${utente.email}</h3>
					<h3><strong>Data di nascita:</strong> ${utente.dataDiNascita}</h3>	
					<h3><strong>Data di iscrizione:</strong> ${utente.dataIscrizione}</h3>	
				</c:if>
				<c:if test="${empty utente.nome}">	
					<h3><strong>Nome:</strong> ${user.nome}</h3>
					<h3><strong>Cognome:</strong> ${user.cognome}</h3>
					<h3><strong>Username:</strong> ${user.username}</h3>
					<h3><strong>Email:</strong> ${user.email}</h3>
					<h3><strong>Data di nascita:</strong> ${user.dataDiNascita}</h3>	
					<h3><strong>Data di iscrizione:</strong> ${user.dataIscrizione}</h3>	
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>