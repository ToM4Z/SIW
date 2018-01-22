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
				<c:if test = "${vuoto == true}">
					<h4>Non ci sono altri utenti in attesa</h4>
				</c:if>
				
				<c:forEach var="utente" items="${utentiInAttesa}">
					<h4>${utente.nome} ${utente.cognome} Lo vuoi aggiungere al gruppo ${gruppo.nome}? 
						<a href = gestisciGruppo?esito=y&group=${gruppo.nome}&channel=${gruppo.canale.nome}&utente=${utente.email}>Sì</a> / 
						<a href = gestisciGruppo?esito=n&group=${gruppo.nome}&channel=${gruppo.canale.nome}&utente=${utente.email}>No</a></h4>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>