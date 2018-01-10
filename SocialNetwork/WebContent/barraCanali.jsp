<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="canale" class="model.Canale" scope="request" />
<jsp:useBean id="gruppo" class="model.Gruppo" scope="request" />

<html>
	<head lang="it">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>LoosyNet</title>

		<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
		<script src="js/jquery-3.2.1.min.js"></script>
		<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
		<script src ="js/barraCanali.js"></script>
</head>
<body onload = "javascript:getCanali()">
	
	<h4><a href = "creaCanale">Crea Canale</a></h4>
	<h3>I tuoi canali</h3>	
	<ul id = "listaCanali">
		<ul id = "listaGruppi">
		
		</ul>
	</ul>
</body>
</html>