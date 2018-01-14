<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="user" class="model.Utente" scope="session" />

<html>
<head lang="it">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, height=device-height">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/tree.css">
<script>
function getCanali(){
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	        var liste = JSON.parse(this.responseText);
	        var cont = 0;
	        var canale;
	        $.each(liste, function(i,lista){
	        	cont = 0;
	        	$.each(lista, function(i, stringhe){
	        		if (cont === 0){
	        			canale = stringhe;
	        			$("#listaCanali").append("<ul class=\"treeview\" id=\""+canale+"\">" +
	        	                "<li><a href=\"canale?channel="+canale+"\">"+canale+"</a>"+
	                        	"<ul>");
	        		}else{
	        			$("#listaCanali").find("ul#"+canale).find("ul").append("<li>" +
	        									"<a class=\"nav-link\" href=\"gruppo?group="+stringhe+"&channel="+canale+"\">"
	        									+stringhe+"</a></li>");
	        		}
	        		cont++;
	        	});
	        	$("#listaCanali").append("</ul></li></ul>");
	        });
	    }
	};
	xmlhttp.open("GET", "barraCanali", true);
	xmlhttp.send();	
}
</script>
</head>

<body>
	<div style="display:none">
	<form action="javascript:getCanali()">
		<input type="submit" class="onload">
	</form>
	</div>
	<div class="panel panel-default"
		style="position:fixed; float: left; width: 25%; height: 100%; margin-top: 40px; margin-bottom: -20px">
		
		<div class="panel-heading" style="text-align: center"><h3>Canali</h3></div>
		
		<div class="panel-body" id="listaCanali"><h4><a href = "creaCanale">Crea Canale</a></h4></div>
	</div>

</body>
</html>