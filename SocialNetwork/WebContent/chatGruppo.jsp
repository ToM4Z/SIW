<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head lang="it">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1,  height=device-height">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/message.css">
<link rel="stylesheet" href="css/loader.css">

<script>
function sendMessage(){
	$.ajax({
			type:"POST",
			url:"sendMessage",
			datatype: "json",
			data: {gruppo : $("#nomeGruppo").text(), canale : $("#nomeCanale").text(), testo : $("#messaggio").val()},
			success: function(data){
				if(data == "error"){
					console.log("error");
				}
			}
	});
	$("#messaggio").val("");
}

var querying = false;
var numberMessageSession;
function loadMessaggi(){
	console.log("first "+$("#nomeGruppo").text()+" "+$("#nomeCanale").text());
	$.ajax({
		type:"POST",
		url:"receiveMessage",
		data: {first: "1", gruppo : $("#nomeGruppo").text(), canale : $("#nomeCanale").text()},
		success: function(data){
			if(data != "[]" && data != "error"){
				var json = JSON.parse(data);
				var liste = json.messaggi;
				numberMessageSession = json.numberMessageSession;
				console.log("session "+numberMessageSession);
				$("div#loaderChat.loader").remove();
				appendMessages(liste);
			}else if(data == "error"){
				stopChat();
			}
			getMessaggi();
		}
	});
}

function appendMessages(messaggi){
	messaggi.forEach(function(item,index){
		$("#listchat").append(
		"<div class=\"mexContainer "+(item.right? "right":"left")+"\">"
		  + "<div class=\"row\">"
		  + "<img src=\"images/users/"+item.email+".jpg\" alt=\"Avatar\" onerror=\"this.src='images/users/unknown.jpg';\" onclick='showImageModal(this.src);'"+(item.right? "class=\"right\"":"")+">"
		  + "<span id=\"Name\""+(item.right? "class=\"right\"":"")+"><a href=\"javascript:showUser('"+item.email+"')\">"+item.username+"</a></span>"
		  + "<span class=\"time-"+(item.right? "left":"right")+"\">"+item.data+"</span>"	  
		  + "</div><hr><p style=\"float:"+(item.right? "right":"left")+"\">"+item.contenuto+"</p>"
		  + "</div>");
	});
	$("#chat").animate({scrollTop:$("#chat").get(0).scrollHeight}, 'slow');
}

var intervalMex;
var ajaxMex;
function getMessaggi(){
	intervalMex = setInterval(function(){
		if(!querying){
			querying = true;
			console.log($("#nomeGruppo").text()+$("#nomeCanale").text()+numberMessageSession);
			ajaxMex = $.ajax({
				type:"POST",
				url:"receiveMessage",
				datatype: "json",
				data: {first: "0", attrSession : $("#nomeGruppo").text()+$("#nomeCanale").text()+numberMessageSession},
				success: function(data){
					console.log("getMessaggi: "+data);
					if(data != "[]" && data != "error"){
						var liste = JSON.parse(data);
						appendMessages(liste);
					}else if(data == "error"){
						console.log("error");
					}
					querying=false;
				},
				error: function(jqXHR, textStatus, errorThrown) {
					querying=false;
				}
			});
		}
	}, 250);
}

function stopChat(){	
	clearInterval(intervalMex);
}

function onbeforeunloadChat(){
	stopChat();
	if(ajaxMex != undefined)
		ajaxMex.abort();
	console.log("closing");
    $.ajax({
    	type: "GET",
		url:"receiveMessage",
		data: {"attrSession": $("#nomeGruppo").text()+$("#nomeCanale").text()+numberMessageSession}
    });
}
</script>
</head>
<body>
	<form action="javascript:onbeforeunloadChat()" style="display:none">
		<input type="submit" class="onbeforeunload">
	</form>
		
	<h1 id="nomeGruppo" style="display: none">${gruppo.nome}</h1>
	<h1 id="nomeCanale" style="display: none">${gruppo.canale.nome}</h1>

	<div id="chatGruppo" class="panel panel-default"
		style="position: fixed; width: 25%; height: 100%; right: 0%; margin-top: 40px; margin-bottom: -20px;">
		<div class="panel-heading" style="text-align: center">
			<h3>Chat ${gruppo.nome}</h3>
		</div>
		<div class="panel-body" style="padding-left:0px;">
			<c:if test="${gruppo.nome != null}">
				<script type="text/javascript">
					loadMessaggi();
				</script>

				<div id="chat"
					style="position: relative; margin-top: -15px; margin-right: -15px; height: 72%; overflow-y: scroll; overflow-x: hidden;">
					<ul id="listchat"
						style="margin-left: auto; margin-right: auto;">
					</ul>
					<div class="loader" id="loaderChat"></div>
				</div>
			</c:if>
			<c:if test="${gruppo.nome == null}">
				<h2 style="text-align: center">Apri un gruppo per visualizzare la chat</h2>
			</c:if>

			<div id="writeMex" style="position: fixed; bottom: 5px; width: 30%;">
				<hr>
				<form action="javascript:sendMessage()">
					<c:if test="${not empty gruppo.nome}">
						<input id="messaggio" type="text" class="form-control"
							placeholder="Inserisci testo" autocomplete="off"
							style="float: left; margin-left: 8px; margin-top:3px; width: 70%"
							name="messaggio">

						<span> 
							<input type="submit" class="btn btn-info btn-bg"
								style="left: 5px; width: 32px; height: 32px; top:5px;" value=""> 
							<span class="glyphicon glyphicon-send"
								style="position: relative; left: -28px; top: 5px"></span>
						</span>
					</c:if>
				</form>
			</div>
		</div>
	</div>
</body>
</html>