<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="user" class="model.Utente" scope="session" />

<html>
<head lang="it">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
	
	<c:if test = "${vuoto == true}">
		<h4>Non ci sono altri utenti in attesa</h4>
	</c:if>
	
	<c:forEach var="utente" items="${utentiInAttesa}">
		
		<h4>${utente.nome} ${utente.cognome} Lo vuoi aggiungere al gruppo ${gruppo.nome}? 
			<a href = gestisciGruppo?esito=y&group=${gruppo.nome}&channel=${gruppo.canale.nome}&utente=${utente.email}>Sì</a> / 
			<a href = gestisciGruppo?esito=n&group=${gruppo.nome}&channel=${gruppo.canale.nome}&utente=${utente.email}>No</a></h4>
		
	</c:forEach>

</body>
</html>