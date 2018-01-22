<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="canale" class="model.Canale" scope="request" />
<jsp:useBean id="gruppo" class="model.Gruppo" scope="request" />
<jsp:useBean id="post" class="model.Post" scope="request" />

<html>
<head lang="it">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>LoosyNet</title>

		<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
		<script src="js/jquery-3.2.1.min.js"></script>
		<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
		<script src="js/dateConverter.js"></script>


<script>
function creaPost(){
	
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var json = JSON.stringify({"gruppo": gruppo,"canale" : canale, "contenuto": $("#contenuto").val()});
	  //alert($("#contenuto").val());
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","creaPost", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  window.location.replace("gruppo?group="+gruppo+"&channel="+canale);
	  		}else{
	      }
	  }
	  xhr.send(json);
}


function addCommento(idPost){
	
	  alert($("#commento"+idPost).val());
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var json = JSON.stringify({"gruppo": gruppo,"canale" : canale, "idPost":idPost, "commento": $("#commento"+idPost).val()});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","commento", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("commento aggiunto")
		  	  inviaNotificaCommento(idPost);
	  		}else{
	      }
	  }
	  xhr.send(json);
}

function eliminaPosts(idPost){
	
	
	if (confirm("Sei sicuro di voler cancellare il post?") == true) {
	  var json = JSON.stringify({"idPost" : idPost});
	  //alert(idPost);
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","eliminaPost", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  	  
	  $("#post_"+idPost).remove();
	  
	  xhr.send(json);
	}
	

}

function seiSicuroGruppo(){
	
	var gruppo = $("#nomeGruppo").text();
	var canale = $("#nomeCanale").text();
	
	if (confirm("Sei sicuro di voler cancellare il gruppo?") == true) {
	    
		inviaNotificaEliminaGruppo();
		document.location.href = "eliminaGruppo?group="+gruppo+"&channel="+canale;
		
	} else {
	    
		//do nothing
	} 
}

function inviaNotificaEliminaGruppo(){
	
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var tipo = "eliminazioneGruppo";
	  var json = JSON.stringify({"nomeGruppo": gruppo,"nomeCanale" : canale, "tipo" : tipo, "idPost" : ""});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","inviaNotifica", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("notifica eliminazione inviata");
	  		}else{
	  			
	      }
	  }
	  xhr.send(json);
}

function inviaNotificaRichiesta(){
	
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var tipo = "richiestaIscrizione";
	  var json = JSON.stringify({"nomeGruppo": gruppo,"nomeCanale" : canale, "tipo" : tipo, "idPost" : ""});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","inviaNotifica", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("richiesta inviata");
	  		}else{
	  			
	      }
	  }
	  xhr.send(json);
	  
}


function inviaNotificaSegnalazione(idPost){
	
	  var tipo = "segnalazione";
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var json = JSON.stringify({"idPost": idPost, "tipo" : tipo, "nomeGruppo": gruppo, "nomeCanale" : canale});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","inviaNotifica", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("notifica segnalazione inviata");
	  		}else{
	  			
	      }
	  }
	  xhr.send(json);
}


function inviaNotificaCommento(idPost){
	
	  var tipo = "commento";
	  var json = JSON.stringify({"idPost": idPost, "tipo" : tipo, "nomeGruppo": "", "nomeCanale" : ""});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","inviaNotifica", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
		  	  alert("notifica commento inviata");
	  		}else{
	  			
	      }
	  }
	  xhr.send(json);
	  
}

function eseguiModifica(x){
	  
	  var mod =  $("#modifica").val();
	  var json = JSON.stringify({"idPost":x, "modifica": mod});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","modificaPost", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true"){
			  $("div.post-body"+x).replaceWith("<div class=\"post-body"+x+"\">"
					  +"<hr style=\"margin:-0.5px; margin-bottom:7px; border-color:black\">"
					  +"<p class=\"contenuto\">"+mod+"</p></div>");
	  		}else{
	      }
	  }
	  xhr.send(json);
}

function modificaPost(x){
	alert(x);
	$("div.post-body"+x).replaceWith("<div class=\"post-body"+x+"\">"
		+"<hr style=\"margin:-0.5px; margin-bottom:7px; border-color:black\">"
		+"<form action = \"javascript:eseguiModifica("+x+")\"><input id=\"modifica\" type=\"text\" name=\"modifica\" >"
		+"<input type = \"submit\" value = \"Modifica\"></form></div>");
}

function addLike(idPost, numLike, numDis){
	//alert("inizio");
	var tipo = "addLike";
	$.ajax({
		type: "POST",
		url: "reaction",
		datatype: "json",
		data: JSON.stringify({"idPost" : idPost, "tipo" : tipo}),
		success: function(data){
			
			var out = JSON.parse(data);
			
			$("#reaction"+idPost).replaceWith("<div id = reaction"+idPost+" style=\"display:inline\" class=\"row\">"
			    + "<a onclick=\"javascript:removeLike("+idPost+")\">"
        		+"<span id=\"like\" class=\"glyphicon glyphicon-thumbs-up\" style=\"font-size:1.5em;padding-left:10%;padding-right:20%;color:#36c906\">"+out.nLike+"</span>"
       			+"</a><a onclick=\"javascript:addDislike("+idPost+")\">"
        	+"<span id = \"dislike\" class=\"glyphicon glyphicon-thumbs-down\" style=\"font-size:1.5em;padding-right:10%\">"+out.nDislike+"</span></a></div>");
		}
	});
}

function addDislike(idPost, numLike, numDis){
	
	var tipo = "addDislike";
	$.ajax({
		type: "POST",
		url: "reaction",
		datatype: "json",
		data: JSON.stringify({"idPost" : idPost, "tipo" : tipo}),
		success: function(data){
			var out = JSON.parse(data);
			$("#reaction"+idPost).replaceWith("<div id = reaction"+idPost+" style=\"display:inline\" class=\"row\">"
				    + "<a onclick=\"javascript:addLike("+idPost+")\">"
	        		+"<span id=\"like\" class=\"glyphicon glyphicon-thumbs-up\" style=\"font-size:1.5em;padding-left:10%;padding-right:20%\">"+out.nLike+"</span>"
	       			+"</a><a onclick=\"javascript:removeDislike("+idPost+")\">"
	        	+"<span id = \"dislike\" class=\"glyphicon glyphicon-thumbs-down\" style=\"font-size:1.5em;padding-right:10%;color:#ed0707\">"+out.nDislike+"</span></a></div>");
	    	}
	});
}

function removeLike(idPost, numLike, numDis){
	
	var tipo = "removeLike";
	$.ajax({
		type: "POST",
		url: "reaction",
		datatype: "json",
		data: JSON.stringify({"idPost" : idPost, "tipo" : tipo}),
		success: function(data){
			var out = JSON.parse(data);
			$("#reaction"+idPost).replaceWith("<div id = reaction"+idPost+" style=\"display:inline\" class=\"row\">"
			    + "<a onclick=\"javascript:addLike("+idPost+")\">"
        		+"<span id=\"like\" class=\"glyphicon glyphicon-thumbs-up\" style=\"font-size:1.5em;padding-left:10%;padding-right:20%\">"+out.nLike+"</span>"
       			+"</a><a onclick=\"javascript:addDislike("+idPost+")\">"
        	+"<span id = \"dislike\" class=\"glyphicon glyphicon-thumbs-down\" style=\"font-size:1.5em;padding-right:10%\">"+out.nDislike+"</span></a></div>");
		}
	});
}

function removeDislike(idPost, numLike, numDis){
	
	var tipo = "removeDislike";
	$.ajax({
		type: "POST",
		url: "reaction",
		datatype: "json",
		data: JSON.stringify({"idPost" : idPost, "tipo" : tipo}),
		success: function(data){
			var out = JSON.parse(data);
			$("#reaction"+idPost).replaceWith("<div id = reaction"+idPost+" style=\"display:inline\" class=\"row\">"
			    + "<a onclick=\"javascript:addLike("+idPost+")\">"
        		+"<span id=\"like\" class=\"glyphicon glyphicon-thumbs-up\" style=\"font-size:1.5em;padding-left:10%;padding-right:20%\">"+out.nLike+"</span>"
       			+"</a><a onclick=\"javascript:addDislike("+idPost+")\">"
        	+"<span id = \"dislike\" class=\"glyphicon glyphicon-thumbs-down\" style=\"font-size:1.5em;padding-right:10%\">"+out.nDislike+"</span></a></div>");
		}
	});
}

function load(){
	$("input.onload").each(function(){
		$(this).trigger("click");
	});
};

var intervalUp;

function startUpdate(){
	update();
	intervalUp = setInterval(update,20000);
}

function stopUpdate(){
	//alert("fine");
	clearInterval(intervalUp);
}

	function update(){
		//alert("ok");
		 var gruppo = $("#nomeGruppo").text();
		  var canale = $("#nomeCanale").text();
		
		$.ajax({
			type: "POST",
			url: "updateReaction",
			datatype: "json",
			data: JSON.stringify({"nomeGruppo": gruppo, "nomeCanale" : canale}),
			success: function(data){
				var out = JSON.parse(data);
				//alert(out);
				out.forEach(function(item, index){ 
						if (item.like == '1'){
							
							$("#reaction"+item.postId).replaceWith("<div id = reaction"+item.postId+" style=\"display:inline\" class=\"row\">"
								    + "<a onclick=\"javascript:removeLike('" +item.postId+ "')\">"
					        		+"<span id=\"like\" class=\"glyphicon glyphicon-thumbs-up\" style=\"font-size:1.5em;padding-left:10%;padding-right:20%;color:#36c906\">"+item.nLike+"</span>"
					       			+"</a><a onclick=\"javascript:addDislike('" +item.postId+ "')\">"
					        	+"<span id = \"dislike\" class=\"glyphicon glyphicon-thumbs-down\" style=\"font-size:1.5em;padding-right:10%\">"+item.nDislike+"</span></a></div>");
						}
						
						else if (item.dislike == '1'){
							
							$("#reaction"+item.postId).replaceWith("<div id = reaction"+item.postId+" style=\"display:inline\" class=\"row\">"
								    + "<a onclick=\"javascript:addLike('" +item.postId+ "')\">"
					        		+"<span id=\"like\" class=\"glyphicon glyphicon-thumbs-up\" style=\"font-size:1.5em;padding-left:10%;padding-right:20%\">"+item.nLike+"</span>"
					       			+"</a><a onclick=\"javascript:removeDislike('" +item.postId+ "')\">"
					        	+"<span id = \"dislike\" class=\"glyphicon glyphicon-thumbs-down\" style=\"font-size:1.5em;padding-right:10%;color:#ed0707\">"+item.nDislike+"</span></a></div>");
						}
						
						else{
							
							$("#reaction"+item.postId).replaceWith("<div id = reaction"+item.postId+" style=\"display:inline\" class=\"row\">"
								    + "<a onclick=\"javascript:addLike('" +item.postId+ "')\">"
					        		+"<span id=\"like\" class=\"glyphicon glyphicon-thumbs-up\" style=\"font-size:1.5em;padding-left:10%;padding-right:20%\">"+item.nLike+"</span>"
					       			+"</a><a onclick=\"javascript:addDislike('" +item.postId+ "')\">"
					        	+"<span id = \"dislike\" class=\"glyphicon glyphicon-thumbs-down\" style=\"font-size:1.5em;padding-right:10%\">"+item.nDislike+"</span></a></div>");
						}
						
					});
			}
		});
	}


</script>
<style>
.post{
	background-color: #E8E8E8;
	color: #555;
	border: .1em solid;
	border-color: #C0C0C0;
	border-radius: 10px;
	font-family: Tahoma, Geneva, Arial, sans-serif;
	font-size: 1.1em;
	padding: 10px;
	margin: 20px;
	cursor: default;
	position: relative;
  	left:  calc(40vw - 5%);
    width: calc(40vw - 10%);
}
.post-header{
  padding-left: 10px;
	cursor: default;
  display: inline;
}
.post-header-left{
  float:left;
}
.post-header a{
  font-family: Tahoma, Geneva, Arial, sans-serif;
	font-size: 1.1em;
  color:#555;
}
.post-header-right{
  float: right;
}
.post-body{
  padding-top: 10px;
}
.post-footer{
    margin-bottom: -30px;
}
.contenuto{
  font-size: 1.2em;
}
.post-hr{
	border-color: #D2D2D2;
	margin-top:4px; 
	margin-bottom:5px; 
}
@media screen and (max-width : 767px) {
	#barraCanali, #chatGruppo{
		display:none;
		float:none !important;
		width:100% !important;
	}
	#writeMex{
		width: 100% !important;
		bottom: 10px;
	}
	#switchPanel{
		display:block !important;
	}
	.post{
		left:10%;
		width:70%;
	}
}

#switchPanel{
	position:fixed;
	top:60px;
	left:10px;
	display:none;
}
</style>
<script>
var page = 2;
function shiftLeft(){
	if(page===2){
		$("#homePost").hide();
		$("#barraCanali").show();
		--page;
	}else if(page===3){
		$("#chatGruppo").hide();
		$("#homePost").show();
		--page;
	}
}
function shiftRight(){
	if(page===1){
		$("#barraCanali").hide();
		$("#homePost").show();
		++page;
	}else if(page===2){
		$("#homePost").hide();
		$("#chatGruppo").show();
		++page;
	}
}

function convertDatePost(post,data){	
	$("p#"+post+" small").text(convertDate(data,true));
}
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
			<div id="switchPanel">
					<span class="glyphicon glyphicon-chevron-left" style="font-size:2em;" onclick="javascript:shiftLeft()"></span>
					<span class="glyphicon glyphicon-chevron-right" style="font-size:2em;left:500%;" onclick="javascript:shiftRight()"></span>
				</div>
				
			<div id="homePost" style="margin-top:60px; text-align: center;">
				
				<h1 id="nomeGruppo">${gruppo.nome}</h1>
				<h1 id="nomeCanale" style="display:none">${gruppo.canale.nome}</h1>
				
				<c:if test="${gruppo.nome != null}">
					<script type="text/javascript">
					startUpdate();
				</script>
				</c:if>
				<c:if test="${gruppo.nome == null}">
					<script type="text/javascript">
					stopUpdate();
				</script>
				</c:if>
				
				<c:if test = "${admin == true }">
					<h5 onclick="seiSicuroGruppo()">Elimina gruppo</h5>
					<h5><a href = utentiInAttesa?group=${gruppo.nome}&channel=${gruppo.canale.nome}>Visualizza utenti in attesa</a></h5>
					<h5><a href = gestioneAdmin?group=${gruppo.nome}&channel=${gruppo.canale.nome}>Gestisci admin</a></h5>
					<h5><a href = gestioneMembri?group=${gruppo.nome}&channel=${gruppo.canale.nome}>Gestisci membri</a></h5>
				</c:if>
					<h3 onclick = "javascript:update()">prova</h3>
				<c:choose>
					<c:when test="${iscritto == true }">
						<h4>
							<a href=gestisciGruppo?channel=${gruppo.canale.nome}&group=${gruppo.nome}&esito=cancellazione>Cancellati dal gruppo</a>
						</h4>

					</c:when>
					<c:when test="${iscritto == false }">
						<h4 onclick = "javascript:inviaNotificaRichiesta()">
							Richiedi iscrizione al gruppo  
						</h4>
					</c:when>
				</c:choose>

				<form action = "javascript:creaPost()">
					<input id="contenuto" autocomplete="off" type="text" name="contenuto" >
					<input type = "submit" value = "Pubblica">
				</form>
	
				<c:forEach var = "post" items = "${gruppo.post}">
				<div id = post_${post.id}> 
				  <div class="row">
				  <div class="post">
				    <div class="post-header">
				    <div class="post-header-left">
				        <a href = "utente?to=${post.creatore.email}">${post.creatore.username}</a>
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
				   <!--    <hr style="margin:-0.5px; margin-bottom:7px; border-color:black"> -->
				      <hr class="post-hr">
				      <p class="contenuto">${post.contenuto}</p>
				    </div>
				    <div class="post-footer">
				      <p class="date" id="${post.id}"><small><script>convertDatePost('${post.id}','${post.dataCreazione}');</script></small></p>
				      <hr class="post-hr">
				      
				      <div id = reaction${post.id} style="display:inline" class="row">
				        <a onclick="javascript:addLike(${post.id})">
				        	<span id="like" class="glyphicon glyphicon-thumbs-up" style="font-size:1.5em;padding-left:10%;padding-right:20%">-</span>
				        </a>
				        <a onclick="javascript:addDislike(${post.id})">
				        	<span id = "dislike" class="glyphicon glyphicon-thumbs-down" style="font-size:1.5em;padding-right:10%">-</span>
				        </a>
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
			</div>
		</div>
	</div>	
</body>
</html>