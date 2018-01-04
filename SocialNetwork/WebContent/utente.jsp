<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />

<html>
<head>
<head lang="it">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>LoosyNet</title>

		<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
		<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
		<script src="js/jquery-3.2.1.min.js"></script>
	</head>
</head>
<body>
	<c:if test="${sessionScope.user.nome == null}">
		<%
			response.sendRedirect("index.jsp");
		%>
	</c:if>
	<c:if test="${sessionScope.user != null}">
		<h5>${sessionScope.user.nome}</h5>
	</c:if>
	
	<h3><strong>Nome:</strong> ${utente.nome}</h3>
	<h3><strong>Cognome:</strong> ${utente.cognome}</h3>
	<h3><strong>Username:</strong> ${utente.username}</h3>
	<h3><strong>Email:</strong> ${utente.email}</h3>
	<h3><strong>Data di nascita:</strong> ${utente.dataDiNascita}</h3>
	
	
</body>
</html>