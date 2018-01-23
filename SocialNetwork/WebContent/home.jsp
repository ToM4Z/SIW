<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="post" class="model.Post" scope="request" />

<html>
<head lang="it">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="js/eliminaPost.js"></script>
<script src="js/ResizeScreen.js"></script>
<link rel="stylesheet" href="css/ResizeScreen.css">
<script src="js/post.js"></script>
<link rel="stylesheet" href="css/post.css">
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
			
			<div id="switchPanel">
				<span class="glyphicon glyphicon-chevron-left" style="font-size:2em;" onclick="javascript:shiftLeft()"></span>
				<span class="glyphicon glyphicon-chevron-right" style="font-size:2em;left:500%;" onclick="javascript:shiftRight()"></span>
			</div>
				<c:forEach var = "post" items = "${posts}">
				<div id = post_${post.id}> 
				  <div class="row">
				  <div class="post">
				    <div class="post-header">
				    <div class="post-header-left">
				        <a href = "javascript:showUser('${post.creatore.email}')">${post.creatore.username}</a> > 
				        <a href="javascript:showChannel('${post.canale.nome}')">${post.canale.nome}</a>
						/ <a href="gruppo?group=${post.gruppo.nome}&channel=${post.canale.nome}">${post.gruppo.nome}</a>
					
				    </div>
				    <div class="post-header-right">
				      <c:if test = "${post.creatore.email == user.email}">
				       <a onclick="javascript:modificaPost(${post.id})"><span class="glyphicon glyphicon-pencil"></span></a>
				      </c:if>
				      <c:if test = "${post.creatore.email == user.email || admin==true}">
				       <a onclick="javascript:eliminaPosts(${post.id})"><span class="glyphicon glyphicon-trash"></span></a>
				      </c:if>
				    </div>

				    <div class="post-body${post.id}">
				      <hr class="post-hr">
				      <p class="contenuto">${post.contenuto}</p>
				    </div>
				    <div class="post-footer">
				      <p class="date" id="${post.id}"><small>${post.dataCreazione}</small></p>
				      <hr class="post-hr">
				      <div style="display:inline" class="row">
				         <span class="glyphicon glyphicon-thumbs-up" style="font-size:1.5em;padding-left:10%;padding-right:20%">10</span>
				        <span class="glyphicon glyphicon-thumbs-down" style="font-size:1.5em;padding-right:10%">10</span>
				      </div>
				      <hr class="post-hr">
				        <a href = "commenti?idPost=${post.id}" style="font-size:1em">mostra commenti</a>
				        <div style="display:inline">
				      <form action = "javascript:addCommento(${post.id})">
				        <input id="commento${post.id}" type="text" class="form-control"
											placeholder="Commenta"
											name = "commento${post.id}">
				        <button type="button" class="btn btn-default" style="position:absolute;right:7;bottom:16px;height:33px"><span class="glyphicon glyphicon-send" style="font-size:1.2em;margin:-2px"></span></button>
				      </form>
				    </div>
				    </div>
				  </div>
				</div>
				</div>
				</div>		
				</c:forEach>
			
			
			
			<!-- 
				<c:forEach var="post" items="${posts}">
					<h4>
						<a href="javascript:showUser('${post.creatore.email}')">
							${post.creatore.username}</a> > <a href="javascript:showChannel('${post.canale.nome}')">${post.canale.nome}</a>
						/ <a href=gruppo?group=${post.gruppo.nome}&channel=${post.canale.nome}>${post.gruppo.nome}</a>
					</h4>
		
					<c:if test = "${post.creatore.email == user.email}">
						<h6 onclick = "seiSicuro(${post.id})">Elimina post</h6>
					</c:if>
					
					<p>${post.contenuto}</p>
					<small><small>${post.dataCreazione}</small></small>
				</c:forEach>-->
			</div>
		</div>
	</div>
</body>
</html>
