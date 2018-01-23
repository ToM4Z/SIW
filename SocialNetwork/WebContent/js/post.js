
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