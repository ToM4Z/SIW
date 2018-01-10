<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="canale" class="model.Canale" scope="request" />

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
<body>
	<c:if test="${sessionScope.user.nome == null}">
		<%
			response.sendRedirect("index.jsp");
		%>
	</c:if>
	
	<jsp:include page="LoosyNetBar.jsp" />
	<jsp:include page="barraCanali.jsp" />
	
	
	<c:if test="${sessionScope.user != null}">
		<h2>${sessionScope.user.nome}</h2>
	</c:if>
	<div id="homePost" style="margin-top:60px; text-align: center;">
		<h1>${canale.nome}</h1>
		<h3>Descizione: ${canale.descrizione}</h3>
		<h4><a href = creaGruppo?channel=${canale.nome}>Crea Gruppo</a></h4>
		<div id = gruppi>
		<h3>Gruppi:</h3>
		<c:forEach var = "gruppo" items = "${canale.gruppi}">
			<h4><a href = gruppo?group=${gruppo.nome}&channel=${canale.nome} >${gruppo.nome}</a></h4>
			<small><small>${gruppo.data_creazione}</small></small>
		
		</c:forEach>
		</div>
	</div>
	
</body>
</html>