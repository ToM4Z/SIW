
 function setYTPlayers(){
	  $(".post-body").each(function(){
		  var player = $(this).find("div:last-child");
			  
		  var url = getYTURL($(this).find("p.contenuto").text());
		  if(url!="error")
			  $(player).replaceWith('<hr class="post-hr"><iframe type="text/html" width=100% allowfullscreen="1" src="'+url+'" frameborder="0"/>');				  
			  
	  });
  }

function getYTURL(url) {
    var regExp = /.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
    var match = url.match(regExp);

    if (match && match[2].length == 11)
        return "https://www.youtube.com/embed/"+match[2];
    else
        return 'error';
}


function inviaNotificaSegnalazione(idPost){
	
	var tipo = "segnalazione";
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	
	$.ajax({
		type: "POST",
		url: "inviaNotifica",
		datatype: "json",
		data: JSON.stringify({"idPost": idPost, "tipo" : tipo, "nomeGruppo": gruppo, "nomeCanale" : canale}),
		success: function(data){
			alert("notifica segnalazione inviata");

	    	}
	});
	
	
}


function inviaNotificaCommento(idPost){
	
	var tipo = "commento";
	$.ajax({
		type: "POST",
		url: "inviaNotifica",
		datatype: "json",
		data: JSON.stringify({"idPost": idPost, "tipo" : tipo, "nomeGruppo": "", "nomeCanale" : ""}),
		success: function(data){
			alert("notifica commento inviata");

	    	}
	});
	
	  
}

function eseguiModifica(x){	
	$.ajax({
		type: "post",
		url: "modificaPost",
		datatype: "json",
		data: JSON.stringify({"idPost":x, "modifica": $("#modifica").val()}),
		success: function(data){
			if(data == "true"){
				  $("div#post-body"+x).replaceWith("<div id=\"post-body"+x+"\" class=\"post-body\">"
						  +"<hr class=\"post-hr\">"				      
						  +"<p class=\"contenuto\">"+$("#modifica").val()+"</p>"
						  +"<img src=\"images/posts/"+x+".jpg\" alt=\"\" width=100% onError=\"this.remove();\" onclick='showImageModal(this.src);'>"
						  +"<div id=\"player"+x+"\"></div>"				    
						  +"</div>");
				  setYTPlayers();
			}
		}
	});
}

function modificaPost(x){
	$("div#post-body"+x).replaceWith("<div id=\"post-body"+x+"\" class=\"post-body\">"
		+"<hr class=\"post-hr\">"				      
		+"<form action = \"javascript:eseguiModifica("+x+")\">"
		+"<textarea id=\"modifica\" rows=\"5\" style=\"width:100%\" required>"+$("div#post-body"+x+" p.contenuto").text()+"</textarea>"
		+"<input type = \"submit\" value = \"Modifica\"></form></div>");
}

function convertDatePost(post,data){	
	$("p#"+post+" small").text(convertDate(data,true));
}


function addCommento(idPost){
	
	  var gruppo = $("#nomeGruppo").text();
	  var canale = $("#nomeCanale").text();
	  var commento = $("#commento"+idPost).val();
	$.ajax({
		type: "POST",
		url: "commento",
		datatype: "json",
		data: JSON.stringify({"gruppo": gruppo,"canale" : canale, "idPost":idPost, "commento": commento}),
		success: function(data){
			
			var out = JSON.parse(data);
			$("#listaCommenti").append("<li><div id=commento_"+out.idCommento+" style=\"display:none\">"
						+"<h4>"+out.username+"</h4>"
							+"<small onclick = \"javascript:deleteCommento("+out.idCommento+")\">Elimina commento</small>"
						+"<h4>"+commento+"</h4>"
					+"</div>"
				+"</li>");
			$("#commento_"+out.idCommento).slideDown().show();
			$("#commento"+idPost).val("");
		}
	});	 
}

function deleteCommento(idCommento){
	
	if (confirm("Sei sicuro di voler cancellare il post?") == true) {

		  var idPost = $("#idPost").text();
		  var elimina = "true";
	$.ajax({
		type: "POST",
		url: "eliminaCommenti",
		datatype: "json",
		data: JSON.stringify({"idCommento": idCommento, "elimina" : elimina}),
		success: function(data){
			 $("#commento_"+idCommento).remove();

	    	}
		});
	}
	
};

function eliminaPosts(idPost){
	
	if (confirm("Sei sicuro di voler cancellare il post?") == true) {
		$.ajax({
			type: "POST",
			url: "eliminaPost",
			datatype: "json",
			data: JSON.stringify({"idPost" : idPost}),
			success: function(data){
				
				$("#post_"+idPost).remove();
		    	}
			});
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

function addDislike(idPost){
	
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

function removeLike(idPost){
	
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
			},
		error: function(){
			stopUpdate();
		}
		});
	}