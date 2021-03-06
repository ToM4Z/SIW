<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="user" class="model.Utente" scope="session" />

<html>
<head lang="it">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, height=device-height">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/tree.css">
<link rel="stylesheet" href="css/loader.css">
<style>
img.avatarChannel{
    float: left;
    width: 100%;
    max-width: 45px;
    min-height: 45px;
    max-height:45px;
    margin-right: 0px;
    margin-left: 0px;
    border-radius: 50%;
}
</style>
<script>
function getCanali(){
	$.ajax({
		type:"GET",
		url:"barraCanali",
		success: function(data) {
    		$("div#loaderChannels.loader").remove();
	        var liste = JSON.parse(data);
	        var cont = 0;
	        var canale;
	        $.each(liste, function(i,lista){
	        	cont = 0;
	        	$.each(lista, function(i, stringhe){
	        		if (cont === 0){
	        			canale = stringhe;
	        			$("#listaCanali").append("<img src=\"images/channels/"+canale+".jpg\" class=\"avatarChannel\" alt=\"Avatar\" onclick='showImageModal(this.src);' onerror='this.src=\"images/channels/unknown.jpg\";'>"
	        	                +"<ul class=\"treeview\" id=\""+canale+"\">"
	        	               + "<li><a href=\"javascript:showChannel('"+canale+"')\">"+canale+"</a><ul>");
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
	});
}

function addChannel(name){
	$("#listaCanali").append("<img src=\"images/channels/"+name+".jpg\" class=\"avatarChannel\" alt=\"Avatar\" onclick='showImageModal(this.src);' onerror='this.src=\"images/channels/unknown.jpg\";'>"
            +"<ul class=\"treeview\" id=\""+name+"\">"
            +"<li><a href=\"javascript:showChannel('"+name+"')\">"+name+"</a>"
        	+"<ul><li><a class=\"nav-link\" href=\"gruppo?group=home&channel="+name+"\">"
			+"home</a></li></ul></ul>");
}
</script>
</head>

<body>
	<div style="display:none">
	<form action="javascript:getCanali()">
		<input type="submit" class="onload">
	</form>
	</div>
	
	<jsp:include page="createChannel.jsp" />
	<jsp:include page="showChannel.jsp" />
	
	<div id="barraCanali" class="panel panel-default"
		style="position:fixed; float: left; width: 25%; height: 100%; margin-top: 40px; margin-bottom: -20px;">
		
		<div class="panel-heading" style="text-align: center"><h3>Canali</h3></div>
		
		<div class="panel-body" id="listaCanali" style="height: 83%; overflow-y: auto;">
			<h4><a href = "javascript:showCreateChannelModal()" style="color:black;">
				<span class="glyphicon glyphicon-plus-sign" style="font-size:1.5em;"></span>Crea Canale</a>
			</h4>
			
			<div class="loader" id="loaderChannels"></div>
		</div>
	</div>

</body>
</html>