<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="canale" class="model.Canale" scope="request" />
<jsp:useBean id="gruppo" class="model.Gruppo" scope="request" />
<jsp:useBean id="post" class="model.Post" scope="request" />

<html>
<head lang="it">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="js/dateConverter.js"></script>
<script src="js/post.js"></script>
<link rel="stylesheet" href="css/post.css">
<script src="js/ResizeScreen.js"></script>
<link rel="stylesheet" href="css/ResizeScreen.css">

<script>

window.fbAsyncInit = function() {
    FB.init({
      appId      : '165758527482837',
      cookie     : true,
      xfbml      : true,
      version    : 'v2.11'
    });      
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "https://connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
  
  function shareOnFacebook(x){
	    FB.ui({
	      method: 'share',
	      display: 'popup',
	      href: 'http://i67.tinypic.com/144bkzr.png',
	      quote: x,
	    }, function(response){});
	  }
	
  
function shareOnTwitter(x){
	  
	  var url = "https://twitter.com/intent/tweet";
	  var via = "userName";
	  window.open(url+"?text="+x,"","width=500,height=300");
}


function creaPost(){
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var json = JSON.stringify({"gruppo": gruppo,"canale" : canale, "contenuto": $("#contenuto").val()});
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

function seiSicuroGruppo(){
	
	var gruppo = $("#nomeGruppo").text();
	var canale = $("#nomeCanale").text();
	
	if (confirm("Sei sicuro di voler cancellare il gruppo?") == true) {
	    
		inviaNotificaEliminaGruppo();
		document.location.href = "eliminaGruppo?group="+gruppo+"&channel="+canale;
		
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
			<c:if test="${iscritto == true }">
			<jsp:include page="chatGruppo.jsp" />
			</c:if>
		</div>
		<div class="col-bg-6 brd">
			<div id="switchPanel">
				<span class="glyphicon glyphicon-chevron-left" style="font-size:2em;" onclick="javascript:shiftLeft()"></span>
				<span class="glyphicon glyphicon-chevron-right" style="font-size:2em;left:500%;" onclick="javascript:shiftRight()"></span>
			</div>
			<div id="homePost" style="margin-top:60px; text-align: center;">
			
			<div class="page-header">

				
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
				
				<h2 onclick = "javascript:post()">prova pubblicazione</h2>
				
				<c:if test = "${admin == true }">
					<h5><a onclick="seiSicuroGruppo()">Elimina gruppo</a></h5>
					<h5><a href = utentiInAttesa?group=${gruppo.nome}&channel=${gruppo.canale.nome}>Visualizza utenti in attesa</a></h5>
					<h5><a href = gestioneAdmin?group=${gruppo.nome}&channel=${gruppo.canale.nome}>Gestisci admin</a></h5>
					<h5><a href = gestioneMembri?group=${gruppo.nome}&channel=${gruppo.canale.nome}>Gestisci membri</a></h5>
				</c:if>
					<h3 onclick = "javascript:update()">prova</h3>
				<c:choose>
					<c:when test="${iscritto == true && canaleAdmin == false}">
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
				<c:if test = "${iscritto == true}">
				<c:forEach var = "post" items = "${gruppo.post}">
				<div id = post_${post.id}> 
				  <div class="row">
				  <div class="post">
				    <div class="post-header">
				    <div class="post-header-left">
				        <a href = "javascript:showUser('${post.creatore.email}')">${post.creatore.username}</a>
				    </div>
				    <div class="post-header-right">
				    <a onclick = "javascript:inviaNotificaSegnalazione('${post.id}')"><i class="fa fa-exclamation-triangle" style="font-size:20px"></i></a>
				    <a onclick="javascript:shareOnTwitter('${post.contenuto}')"><i class="fa fa-twitter" style="font-size:24px"></i></a>
				    <a onclick="javascript:shareOnFacebook('${post.contenuto}')"><i class="fa fa-facebook-square" style="font-size:22px"></i></a>
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
				</c:if>
			</div>
		</div>
	</div>	
	</div>
</body>
</html>