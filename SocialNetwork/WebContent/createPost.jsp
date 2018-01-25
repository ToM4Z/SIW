<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
<head lang="it">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<script>
function showCreatePost(){
	$("#createPostModal").modal("show");	
}
function updateFilePost(){
	$("#createPostFile").prev().text($("#createPostFile").val().split('\\').pop());
}
function removeCreatePostModal(){
	$("#createPostFile").val("");
	$("#createPostFile").prev().text("Carica Foto");
	$("#errorCreatePostModal").slideUp().hide();
}
function publishPost(){
	$.ajax({
		type: "POST",
		url: "creaPost",
	  	dataType: 'json',
		data: JSON.stringify({"gruppo": $("#nomeGruppo").text(),"canale" : $("#nomeCanale").text(), 
			"contenuto" : $("#createPostContext").val()}),
        success: function(data){
			if(data != "error")
				if($("#createPostFile").prev().text() != "Carica Foto")
					$.ajax({
						type: "POST",
						url: "loadFile?request=post&post="+data,
						data: new FormData($('#createPostForm')[0]),
				        processData: false,
				        contentType: false,
				        cache : false,
				        success : function(data) {
				        	if(data=="error"){
				        		$("#errorCreatePostModal").slideDown().show();
				        	}else{
				        		location.reload();
				        	}
				        },
						error: function(){
				    		$("#errorCreatePostModal").slideDown().show();
						}
					});	
				else
	        		location.reload();
		},
		error: function(data){
			$("#errorCreatePostModal").slideDown().show();
		}
	});
}
</script>
<body>
<div class="modal fade" id="createPostModal" role="dialog">
    <div class="modal-dialog">    
		<div class="modal-content">
        	<div class="modal-header">
          		<h2 id="createPostModalTitle"class="modal-title" style="text-align:center">Condividi un post</h2>
        	</div>
			<div class="modal-body" style="text-align: center;">
				<form id="createPostForm" action="javascript:publishPost()" enctype="multipart/form-data" >			
					<textarea id="createPostContext" rows="5" placeholder="Scrivi qui il tuo post" style="width:100%"></textarea>
					<button type="button" class="btn btn-info btn-file" onclick='$("#createPostFile").trigger("click")' >Carica Foto</button>
					<input type="file" id="createPostFile" name="file" accept="image/jpeg" onchange="updateFilePost()" style="display:none">
					<button type="submit" class="btn btn-info btn-file">Pubblica</button>
				</form>
				<button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeCreatePostModal()">Chiudi</button>
	       		<div id="errorCreatePostModal" style="display: none"class="alert alert-danger" role="alert">Errore nella pubblicazione del post</div>
			</div>
		</div>      
	</div>
</div>
</body>
</html>