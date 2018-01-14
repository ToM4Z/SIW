<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head lang="it">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1,  height=device-height">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<style>
hr { 
    display: block;
    margin-top: 0.5em;
    margin-bottom: 0.5em;
    width: 100%;
    border-style: inset;
    border-width: 1px;
} 
</style>
<script>
function getMessaggi(){
	
}
</script>
</head>
<body>
	<div style="display:none">
	<form action="javascript:getMessaggi()">
		<input type="submit" class="onload">
	</form>
	</div>

	<div class="panel panel-default"
		style="position: fixed; width: 25%; height: 100%; right: 0%; margin-top: 40px; margin-bottom: -20px">
		<div class="panel-heading" style="text-align: center">
			<h3>Chat ${gruppo.nome}</h3>
		</div>
		<div class="panel-body">
			<c:if test="${gruppo.nome != null}">


				<c:forEach var="messaggio" items="${gruppo.chat}">
					<p>
						${messaggio.mittente} : ${messaggio.contenuto} <small>${messaggio.data}</small>
					</p>
				</c:forEach>
			</c:if>
			<c:if test="${gruppo.nome == null}">
				<h2 style="text-align: center">Apri un gruppo per visualizzare la chat</h2>
			</c:if>
			<div style="position: fixed; bottom: 10px; width: 30%">		
			<hr>	
				<form action="#">
					<input type="text" class="form-control" placeholder="Inserisci testo" style="float:left; margin-left:-7px; width: 70%">
					<a href="submit" class="btn btn-info btn-bg" style="height:32px">
	          			<span class="glyphicon glyphicon-send" style="right:2px;top:2px"></span>
	        		</a>
        		</form>
			</div>
		</div>
	</div>
</body>
</html>