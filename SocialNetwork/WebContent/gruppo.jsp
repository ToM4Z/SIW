<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="canale" class="model.Canale" scope="request" />
<jsp:useBean id="gruppo" class="model.Gruppo" scope="request" />
<jsp:useBean id="post" class="model.Post" scope="request" />

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
	<c:if test="${sessionScope.user != null}">
		<h5>${sessionScope.user.nome}</h5>
	</c:if>
	
	<h1>${gruppo.nome}</h1>
	
	<c:forEach var = "post" items = "${gruppo.post}">
		<h3><a href = utente?to=${post.creatore.email}> ${post.creatore.username}</a></h3>
		<p>${post.contenuto}</p>
		<small><small>${post.dataCreazione}</small></small>
	</c:forEach>
	
	 <jsp:include page="chatGruppo.jsp" flush="true"/>
	 
	
</body>
</html>