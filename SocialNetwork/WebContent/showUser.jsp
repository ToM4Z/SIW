<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="model.Utente" scope="session" />

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="js/dateConverter.js"></script>
</head>
<script>

function showUser(utente){
	$.ajax({
		type: "GET",
		url: "utente?to="+utente,
		success: function(data){
			var json = JSON.parse(data);
			$("#userInfoImage").attr("src","images/users/"+json.email+".jpg?"+new Date().getTime());
			$("#userModalTitle").text("").append(json.username);
			$("#userInfo").text("").append(
					"<table style=\"border:none\">"
					+"<tr><td><h4><strong>Nome:              </strong></h4></td><td><h4>"+json.nome+"</h4></td></tr>"
					+"<tr><td><h4><strong>Cognome:           </strong></h4></td><td><h4>"+json.cognome+"</h4></td></tr>"
					+"<tr><td><h4><strong>Email:             </strong></h4></td><td><h4>"+json.email+"</h4></td></tr>"
					+"<tr><td><h4><strong>Data di nascita:   </strong></h4></td><td><h4>"+convertDateUser(json.dataDiNascita)+"</h4></td></tr>"	
					+"<tr><td><h4><strong>Data di iscrizione:</strong></h4></td><td><h4>"+convertDateUser(json.dataIscrizione)+"</h4></td></tr>"
					+"</table>");
			$("#userModal").modal("show");
		}
	});
}
function convertDateUser(data){
	var pezzi = data.split(/ /);
	var mese = getMonth(pezzi[0]);
	var giorno = pezzi[1][0]+""+pezzi[1][1];
	
	return giorno+"/"+mese+"/"+pezzi[2];
}
function sendImage(){
	$.ajax({
		type: "POST",
		url: "loadFile?request=user&user=${sessionScope.user.email}",
		data: new FormData($('#fileUserImage')[0]),
        processData: false,
        contentType: false,
        cache : false,
        success : function(data) {
        	if(data=="error"){
        		$("#MessaggeErrorUserModal").text("").append("Errore: il file è troppo grande!");
        		$("#MessaggeErrorUserModal").slideDown().show();
        	}else{
        		location.reload();
        	}
        },
		error: function(){
			$("#MessaggeErrorUserModal").text("").append("Errore durante il caricamento del file!");
    		$("#MessaggeErrorUserModal").slideDown().show();
		}
	});
}
function removeUserInfo(){
	$("#MessaggeErrorUserModal").slideUp().hide();
}
</script>
<body>
	
<div class="modal fade" id="userModal" role="dialog">
    <div class="modal-dialog">    
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" onclick="removeUserInfo()">&times;</button>
          <h4 id="userModalTitle"class="modal-title" style="text-align:center"></h4>
        </div>
		<div id="modal-body" class="modal-body" style="text-align: center;">
			<div class="row">
				<div class="col-md-6">
					<img id="userInfoImage" src="" width=60% 
							onerror="this.src='images/users/unknown.jpg';"
							style="border-radius:50%; margin-top:10%;">
					<br><br>
					<form id="fileUserImage" method="post" enctype="multipart/form-data" >					
						<span class="btn btn-info btn-file">
						    Cambia Foto <input type="file" name="file" onchange="sendImage()" accept="image/jpeg">
						</span>
					</form>
          		</div>
				<div id="userInfo" class="col-md-6">
				</div>
			</div>
			<div id="MessaggeErrorUserModal" style="display: none"class="alert alert-danger" role="alert">
			</div>
		</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeUserInfo()">Chiudi</button>
        </div>
      </div>
      
   </div>
</div>

</body>
<style>
.btn-file {
    position: relative;
    overflow: hidden;
}
.btn-file input[type=file] {
    position: absolute;
    top: 0;
    right: 0;
    min-width: 100%;
    min-height: 100%;
    font-size: 100px;
    text-align: right;
    filter: alpha(opacity=0);
    opacity: 0;
    outline: none;
    background: white;
    cursor: inherit;
    display: block;
}</style>
</html>