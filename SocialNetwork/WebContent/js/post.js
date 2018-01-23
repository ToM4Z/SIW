
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
	$("div.post-body"+x).replaceWith("<div class=\"post-body"+x+"\">"
		+"<hr style=\"margin:-0.5px; margin-bottom:7px; border-color:black\">"
		+"<form action = \"javascript:eseguiModifica("+x+")\"><input id=\"modifica\" type=\"text\" name=\"modifica\" >"
		+"<input type = \"submit\" value = \"Modifica\"></form></div>");
}

function convertDatePost(post,data){	
	$("p#"+post+" small").text(convertDate(data,true));
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
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","eliminaPost", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  	  
	  $("#post_"+idPost).remove();
	  
	  xhr.send(json);
	}
}


function addLike(idPost, numLike, numDis){
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



var intervalUp;

function startUpdate(){
	update();
	intervalUp = setInterval(update,40000);
}

function stopUpdate(){
	clearInterval(intervalUp);
}

	function update(){
		 var gruppo = $("#nomeGruppo").text();
		  var canale = $("#nomeCanale").text();
		
		$.ajax({
			type: "POST",
			url: "updateReaction",
			datatype: "json",
			data: JSON.stringify({"nomeGruppo": gruppo, "nomeCanale" : canale}),
			success: function(data){
				var out = JSON.parse(data);
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