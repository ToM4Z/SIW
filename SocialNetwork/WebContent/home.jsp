<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<script src="js/eliminaPost.js"></script>
<script>
function load(){
	$("input.onload").each(function(){
		$(this).trigger("click");
	});
};
</script>
</head>
<body onload="javascript:load()" style="overflow-x:hidden">
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
				<c:forEach var="post" items="${posts}">
					<h4>
						<a href=utente?to=${post.creatore.email}>
							${post.creatore.username}</a> > <a href=canale?channel=${post.canale.nome}>${post.canale.nome}</a>
						/ <a href=gruppo?group=${post.gruppo.nome}&channel=${post.canale.nome}>${post.gruppo.nome}</a>
					</h4>
		
					<c:if test = "${post.creatore.email == user.email}">
						<h6 onclick = "seiSicuro(${post.id})">Elimina post</h6>
					</c:if>
					
					<p>${post.contenuto}</p>
					<small><small>${post.dataCreazione}</small></small>
				</c:forEach>
			<!--  <form action="upload" method="post" enctype="multipart/form-data">
    			<input type="text" name="description" />
    			<input type="file" name="file" />
    			 <input type="submit" />
			</form>
			<img src="/upload">-->
			</div>
		</div>
		
		
	</div>
</body>
</html>
