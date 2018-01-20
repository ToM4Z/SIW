<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="canale" class="model.Canale" scope="request" />


<html>
<head lang="it">
<meta charset="utf-8">
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
	<c:if test="${empty user.nome}">
		<c:redirect url="login.html" />
	</c:if>
	<c:if test="${not empty canale.nome}">
	 	<c:redirect url="canale?channel=${canale.nome}"/>
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
				<c:if test="${canale.nome == null}">
					<h1>Crea nuovo canale</h1>
				 </c:if>
				
				<c:if test ="${creazione == false}">
					<h3>Canale già esistente, prova un'altro nome</h3>
				</c:if>
				 
				 <form method="post" action="creaCanale">
		          <br>
		          <label for="descrizione">Nome:</label> <input name="nome" autocomplete="off" type="text" class="form-control" style="position:relative; left:30%; width:30%"/>
		          <br>
		          <label for="descrizione">Descrizione:</label> <input name="descrizione" autocomplete="off" type="text" class="form-control" style="position:relative; left:30%; width:30%"/>
		          <br>
		          <input name="inviaDati" type="submit" value="creaCanale"  class="btn btn-success"/>
				</form>
			</div>
		</div>
	</div>
</body>
</html>