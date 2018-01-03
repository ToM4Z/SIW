<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="post" class="model.Post" scope="request" />

<html>
	<head lang="it">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Social Network</title>

		<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
		<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
		<script src="js/jquery-3.2.1.min.js"></script>
	</head>
<body>
	
	<c:if test="${sessionScope.user.nome == null}">
		<% response.sendRedirect("index.jsp"); %>
	</c:if>
	<c:if test="${sessionScope.user != null}">
 		<h1>Ciao ${sessionScope.user.nome}</h1>
	</c:if>

	
	<div id = "homePost">
		<c:forEach var="post" items="${posts}">
			<h4>${post.creatore.username} > ${post.canale.nome} / ${post.gruppo.nome}</h4>
			<p>${post.contenuto}</p>
			<small><small>${post.dataCreazione}</small></small>
		</c:forEach>
	</div>
	
	<div id = "canali">
		<h3>I tuoi canali</h3>
		<c:forEach var="nome" items="${canali}">
			<h5>${nome}</h5>
		</c:forEach>
	
	</div>
	
</body>
</html>
