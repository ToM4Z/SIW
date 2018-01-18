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
.mex {
	background-color:#e3f7fc;
	color:#555;
  border:.1em solid;
	border-color: #8ed9f6;
  border-radius:10px;
  font-family:Tahoma,Geneva,Arial,sans-serif;
  font-size:1.1em;
  padding:10px 10px 10px 10px;
  margin:10px;
  cursor: default;
}
.mex-left{
  float:left;
  margin-right: 100px;
  margin-left: -40px;
}
.mex-right{
  float:right;
  margin-left: 100px;
}
.mex-header {
    font-family:Tahoma,Geneva,Arial,sans-serif;
    font-size:1.0em;
    font-weight: bold;
    cursor: default;
    position:relative;
}
.mex-header-right {
    float:right;
}
.mex-header-left {
    float:left;
}
.mex-footer{
    font-family:Tahoma,Geneva,Arial,sans-serif;
    font-size:0.7em;
    font-weight: bold;
    cursor: default;
    position:relative;
    margin-top:20px;
}
.mex-footer-right {
    float:right;
}
.mex-footer-left {
    float:left;
}
</style>
<script>

function sendMessage(){
	
	$.ajax({
			type:"POST",
			url:"sendMessage",
			datatype: "json",
			data: {gruppo : $("#nomeGruppo").text(), canale : $("#nomeCanale").text(), testo : $("#messaggio").val()},
			success: function(data){
				if(data == "error"){
					alert("error");
				}
			}
	});
	$("#messaggio").val("");
}

var continua;
function loadMessaggi(){
	$.ajax({
		type:"POST",
		url:"receiveMessage",
		datatype: "json",
		data: {first: 'true', gruppo : $("#nomeGruppo").text(), canale : $("#nomeCanale").text()},
		success: function(data){
			if(data != "empty" && data != "error"){
				var liste = JSON.parse(data);
				$("#listchat").append(liste);
				$("#chat").animate({scrollTop:$("#chat").get(0).scrollHeight}, 'slow');
				getMessaggi();
			}else if(data == "error"){
				alert("error");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert("lost connection"); 
			stop();
		}
	});
}

function getMessaggi(){
	
	continua = setInterval(function(){
		$.ajax({
			type:"POST",
			url:"receiveMessage",
			datatype: "json",
			data: {first: 'false', gruppo : $("#nomeGruppo").text(), canale : $("#nomeCanale").text()},
			success: function(data){
				if(data != "empty" && data != "error"){
					var liste = JSON.parse(data);
					$("#chat").append(liste);
					$("#chat").animate({scrollTop:$("#chat").get(0).scrollHeight}, 'slow');
				}else if(data == "error"){
					alert("error");
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				stop();
			}
		});
	}, 1000);
}

window.onbeforeunload = function(event) {
    stop();
    $.ajax({
    	type: "GET",
		url:"receiveMessage"
    });
}

function stop(){	
	clearInterval(continua);
}

</script>
</head>
<body>
	
	
	<h1 id="nomeGruppo" style="display:none">${gruppo.nome}</h1>
	<h1 id="nomeCanale" style="display:none">${gruppo.canale.nome}</h1>

	<div class="panel panel-default"
		style="position: fixed; width: 25%; height: 100%; right: 0%; margin-top: 40px; margin-bottom: -20px">
		<div class="panel-heading" style="text-align: center">
			<h3>Chat ${gruppo.nome}</h3>
		</div>
		<div class="panel-body">
			<c:if test="${gruppo.nome != null}">

				<script type="text/javascript">
					loadMessaggi();
				</script>

			<div id="chat" style="position: relative; margin-top:-15px; margin-right:-15px; 
							height:72% ;overflow-y:scroll; overflow-x:hidden;">
				<ul id="listchat" style="list-style-type: none;
						margin-left:auto; 
						margin-right:auto;">
				
				</ul>
			</div>
					
			</c:if>
			<c:if test="${gruppo.nome == null}">
				<h2 style="text-align: center">Apri un gruppo per visualizzare
					la chat</h2>
										
			</c:if>
			<div style="position: fixed; bottom: 10px; width: 30%;">
				<hr>
				<form action="javascript:sendMessage()">
					<c:if test="${not empty gruppo.nome}">
					<input id="messaggio" type="text" class="form-control"
						placeholder="Inserisci testo"
						style="float: left; margin-left: -7px; width: 70%"
						name="messaggio"> 
					<span>
					<input type="submit" class="btn btn-info btn-bg" style="left:5px;width:32px;height: 32px" value=""> 
					<span class="glyphicon glyphicon-send" style="position:relative; left: -28px; top: 2px"></span>
					</span>
					</c:if>
				</form>
			</div>
		</div>
	</div>
</body>
</html>