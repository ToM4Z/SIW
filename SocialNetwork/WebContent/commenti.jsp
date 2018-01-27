<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="user" class="model.Utente" scope="session" />

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

function convertDateComment(comment,date){
	$("#date"+comment).text(convertDate(date,true));
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
	<jsp:include page="LoosyNetBar.jsp" />
	<div class="row">
		<div class="col-bg-6 brd">
			<jsp:include page="barraCanali.jsp" />
		</div>
		<div class="col-bg-6 brd">
			<jsp:include page="chatGruppo.jsp" />
		</div>
		<div class="col-bg-6 brd">

			<br><br>
			<div id = post_${post.id}> 
				  <div class="row">
				  <div class="post" style="text-align:center">
				    <div class="post-header">
				    <div class="post-header-left">
				        <a href = "javascript:showUser('${post.creatore.email}')">${post.creatore.username}</a> >
				        <a href="javascript:showChannel('${post.canale.nome}')">${post.canale.nome}</a>
						/ <a href="gruppo?group=${post.gruppo.nome}&channel=${post.canale.nome}">${post.gruppo.nome}</a>
					
				    </div>
				    <div class="post-header-right">
				    <a onclick = "javascript:inviaNotificaSegnalazione('${post.id}')"><i class="fa fa-exclamation-triangle" style="font-size:20px"></i></a>
				    <a onclick="javascript:shareOnTwitter('${post.id}')"><i class="fa fa-twitter" style="font-size:24px"></i></a>
				    <a onclick="javascript:shareOnFacebook('${post.id}')"><i class="fa fa-facebook-square" style="font-size:22px"></i></a>
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
			       	 <div style="display:inline">	
				      <form action = "javascript:addCommento(${post.id})" style="margin-bottom:0px;">
				        <input id="commento${post.id}" type="text" class="form-control"
											placeholder="Commenta" autocomplete="off"
											name = "commento${post.id}">
				        <button type="submit" class="btn btn-default" style="position:relative;float:right;bottom:33.5px;height:34px"><span class="glyphicon glyphicon-send" style="font-size:1.2em;margin:-2px"></span></button>
				      </form>
				    </div>
				    <ul id = "listaCommenti"  style="text-align:left;margin-left:-42px;margin-bottom:30px;">
						<c:forEach var="commento" items="${commenti}">
							<li style="list-style-type: none;">
								<div id=commento_${commento.id}>
								<hr class="post-hr">		
									<h4>${commento.creatore.username}</h4>
									<c:if test = "${commento.creatore.email == user.email || post.creatore.email == user.email || admin == true}">
										<a onclick="javascript:deleteCommento(${commento.id})"><span class="glyphicon glyphicon-trash" style="float:right"></span></a>
				   					</c:if>
									<h4>${commento.contenuto}</h4>
									<small id="date${commento.id}" style="float:right">
										<script>convertDateComment('${commento.id}','${commento.dataCreazione}');</script>
									</small>
								</div>
							</li>
						</c:forEach>
					</ul>
				    </div>
				  </div>
				</div>
				</div>
			</div>	

		</div>
	</div>
</body>
</html>