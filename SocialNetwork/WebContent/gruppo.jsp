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
	  var text = $("#post-body"+x+" p").text();
	    FB.ui({
	      method: 'share',
	      display: 'popup',
	      href: 'http://i67.tinypic.com/144bkzr.png',
	      quote: text,
	    }, function(response){});
	  }
	
  
function shareOnTwitter(x){
	  var text = $("#post-body"+x+" p").text();
	  
	  var url = "https://twitter.com/intent/tweet";
	  var via = "userName";
	  window.open(url+"?text="+text,"","width=500,height=300");
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
	
	$.ajax({
		type: "POST",
		url: "inviaNotifica",
		datatype: "json",
		data: JSON.stringify({"nomeGruppo": gruppo,"nomeCanale" : canale, "tipo" : "eliminazioneGruppo", "idPost" : ""}),
		success: function(data){
			

	    	}
	});
	
}

function inviaNotificaRichiesta(){
	
	var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var tipo = "richiestaIscrizione";
	
	$.ajax({
		type: "POST",
		url: "inviaNotifica",
		datatype: "json",
		data: JSON.stringify({"nomeGruppo": gruppo,"nomeCanale" : canale, "tipo" : tipo, "idPost" : ""}),
		success: function(data){
			alert("richiesta inviata");

	    	}
	});
	  
}

function load(){
	$("input.onload").each(function(){
		$(this).trigger("click");
	});
	setTimeout(setYTPlayers(),1);
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
	<c:if test = "${blacklist == true }">
		<c:redirect url = "home"/>
	</c:if>
	
	<jsp:include page="imageModal.jsp" />
	<jsp:include page="createPost.jsp" />
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
			<div id="homePost" style="margin-top:60px; text-align: center;">
			
			<div class="page-header">
				<div style="display:none">
		    		<h4 id="nomeCanale">${gruppo.canale.nome}</h4>
		    		<h4 id="nomeGruppo">${gruppo.nome}</h4>
				</div>
				<div class="row">
				  <div class="post">
				    <div class="post-header">
				    	<div class="post-header-left">
				    		<h4 style="margin-top:0px;">${gruppo.canale.nome} > ${gruppo.nome}</h4>
				    	</div>
				    	<div class="post-header-right">
				    		<c:choose>
				    			<c:when test="${iscritto == true}">
									<a href="javascript:showCreatePost()" style="position:relative;right:70px;top:-2px;">Crea post</a>
							    	<div class="dropdown" style="position:relative; top:-31px; right:-10px;">
									  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" 
									  		data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >
									    <span class="glyphicon glyphicon-cog" style="font-size:20px"></span>
									  </button>
									  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
									  	<ul style="margin-left:-30px;">
						    			<c:if test = "${admin == true }">
						    				<c:if test='${gruppo.nome != "home" }'>
										    <li style="list-style-type: none;"><a href = "javascript:seiSicuroGruppo()" class="dropdown-item">Elimina gruppo</a></li>
											<li style="list-style-type: none;"><a href = utentiInAttesa?group=${gruppo.nome}&channel=${gruppo.canale.nome} class="dropdown-item" style="width:100%">Gestisci iscrizioni</a></li>
											<li style="list-style-type: none;"><a href = gestioneMembri?group=${gruppo.nome}&channel=${gruppo.canale.nome} class="dropdown-item">Gestisci membri</a></li>
											</c:if>
											<li style="list-style-type: none;"><a href = gestioneAdmin?group=${gruppo.nome}&channel=${gruppo.canale.nome} class="dropdown-item">Gestisci admin</a></li>
										</c:if>
										<c:if test="${canaleAdmin == false && gruppo.nome != 'home'}">
											<li style="list-style-type: none;"><a href=gestisciGruppo?channel=${gruppo.canale.nome}&group=${gruppo.nome}&esito=cancellazione class="dropdown-item">Disiscriviti dal gruppo</a></li>
										</c:if>
										</ul>
									  </div>
									</div>
								</c:when>
								<c:when test="${iscritto == false }">
									<a href = "javascript:inviaNotificaRichiesta()">Iscriviti</a>
								</c:when>
							</c:choose>
						</div>
			    	</div>
		    	  </div>
		    	</div>
				
				<c:if test = "${iscritto == true}">
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
				    <a onclick="javascript:shareOnTwitter('${post.id}')"><i class="fa fa-twitter" style="font-size:24px;"></i></a>
				    <a onclick="javascript:shareOnFacebook('${post.id}')"><i class="fa fa-facebook-square" style="font-size:22px;"></i></a>
				      <c:if test = "${post.creatore.email == user.email}">
				      
				      <a onclick="javascript:modificaPost(${post.id})"><span class="glyphicon glyphicon-pencil"></span></a>
				      
				      </c:if>
				      <c:if test = "${post.creatore.email == user.email || admin==true}">
			    
			       <a onclick="javascript:eliminaPosts(${post.id})"><span class="glyphicon glyphicon-trash"></span></a>
				      </c:if>
				    </div>

				    <div class="post-body" id="post-body${post.id}">
				      <hr class="post-hr">
				      <p class="contenuto">${post.contenuto}</p>
				      <img src="images/posts/${post.id}.jpg" alt="" width=100% onError="this.remove();" onclick='showImageModal(this.src);'>
				    <div id="player${post.id}"></div>
				    </div>
				    <div class="post-footer">
				      <p class="date" id="${post.id}"><small><script>convertDatePost('${post.id}','${post.dataCreazione}');</script></small></p>
				      <hr class="post-hr">
				      
				      <div id = reaction${post.id} style="display:inline" class="row">
				        <a onclick="javascript:addLike(${post.id})">
				        	<span id="like" class="glyphicon glyphicon-thumbs-up" style="font-size:1.5em;padding-left:10%;padding-right:20%">${post.numLikes}</span>
				        </a>
				        <a onclick="javascript:addDislike(${post.id})">
				        	<span id = "dislike" class="glyphicon glyphicon-thumbs-down" style="font-size:1.5em;padding-right:10%">${post.numDislikes}</span>
				        </a>
				      </div>
				      
				      <hr class="post-hr">
				        <a href = "commenti?idPost=${post.id}" style="font-size:1em">mostra commenti</a>
				        <div style="display:inline">
				      <form action = "javascript:addCommento(${post.id})">
				        <input id="commento${post.id}" type="text" class="form-control"
											placeholder="Commenta"
											name = "commento${post.id}">
				        <button type="submit" class="btn btn-default" style="position:absolute;right:7;bottom:16px;height:33px"><span class="glyphicon glyphicon-send" style="font-size:1.2em;margin:-2px"></span></button>
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