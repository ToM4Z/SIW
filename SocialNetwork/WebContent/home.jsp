<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="post" class="model.Post" scope="request" />

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
	<c:if test="${empty user.nome}">
		<c:redirect url="login.html"/>
	</c:if>
	<jsp:include page="LoosyNetBar.jsp" />
	
	<div id = "homePost">
		<c:forEach var="post" items="${posts}">
			<h4><a href = utente?to=${post.creatore.email}> ${post.creatore.username}</a> > <a href = canale?to=${post.canale.nome}>${post.canale.nome}</a> / <a href = gruppo?to=${post.gruppo.nome}&at=${post.canale.nome}>${post.gruppo.nome}</a></h4>
			<p>${post.contenuto}</p>
			<small><small>${post.dataCreazione}</small></small>
		</c:forEach>
	</div>
	
	<jsp:include page="barraCanali.jsp" flush="true"/>
	
	
</body>
</html>
