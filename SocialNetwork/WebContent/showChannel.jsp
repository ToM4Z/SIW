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
<link rel="stylesheet" href="css/buttonFile.css">
</head>
<script>

function showChannel(channel){
	$.ajax({
		type: "GET",
		url: "canale?channel="+channel,
		success: function(data){
			var json = JSON.parse(data);
			$("#channelModalTitle").text(json.canale.nome);
			$("#channelInfoImage").attr("src","images/channels/"+json.canale.nome+".jpg?"+new Date().getTime());
			$("#channelInfo").text("").append(
					"<table style=\"border:none\">"
					+"<tr><td><h4><strong>Nome:              </strong></h4></td><td><h4>"+json.canale.nome+"</h4></td></tr>"
					+"<tr><td><h4><strong>"
						+ (json.canale.admin.username == "${sessionScope.user.username}" ? 
							"<a id=\"editChannelDescriptionButton\" onclick=\"javascript:editChannelDescription()\" style=\"float:left\">"
							+"<span class=\"glyphicon glyphicon-pencil\"></span></a>" : "")
									+"Descrizione:       </strong></h4></td><td><h4 id=\"ChannelDescription\">"+json.canale.descrizione+"</h4>"
									+ (json.canale.admin.username == "${sessionScope.user.username}" ? 
										"<form action=\"javascript:sendChannelDescription()\" id=\"descriptionChannelForm\" style=\"display:none\">"
											+"<textarea id=\"descriptionChannelTextarea\" required autocomplete=\"off\" class=\"form-control\" rows=\"5\"></textarea>"
											+"<button type=\"submit\">Invia</button></form>":"")
									+"</td></tr>"
					+"<tr><td><h4><strong>Admin:             </strong></h4></td><td><h4>"+json.canale.admin.username+"</h4></td></tr>"
					+"<tr><td><h4><strong>Data creazione:   </strong></h4></td><td><h4>"+json.canale.data_creazione+"</h4></td></tr>"	
					+"<tr><td>"+ (json.iscritto && json.canale.admin.username != "${sessionScope.user.username}" ? 
									"<button id=\"unsubscribeChannelButton\" onclick=\"window.location.href='iscrizioneCanale?channel="+json.canale.nome+"&iscritto=true'\">Disiscriviti</button>"
									: ( json.blacklist || json.canale.admin.username == "${sessionScope.user.username}" ? "" : 
										"<button id=\"subscribeChannelButton\" onclick=\"window.location.href='iscrizioneCanale?channel="+json.canale.nome+"&iscritto=false'\">Iscriviti</button>"
								)) + (json.canale.admin.username == "${sessionScope.user.username}" ? 
									"<button id=\"deleteChannelButton\" onclick=\"deleteChannel('"+json.canale.nome+"')\">Cancella Canale</button>"
									: "" ) 
							+"</td><td>"+ (json.iscritto ? "<button id=\"showCreateGroupButton\" onclick=\"showCreateGroup()\">Crea Gruppo</button>"
									+"<form id=\"createGroupForm\" action=\"javascript:createGroup('"+json.canale.nome+"')\" style=\"display:none\">"
									+"<input type=\"text\" id=\"nomeGruppo\" placeHolder=\"Nome\">"
									+"<input type=\"submit\" value=\"Crea\"></form>"
							 		: "")+"</td></tr>"
					+"<tr><td>"+ (json.canale.admin.username == "${sessionScope.user.username}" ? "<form action=\"gestioneBlacklist\" method=\"get\">"
											+"<input type=\"text\" name=\"channel\" value=\""+json.canale.nome+"\" style=\"display:none\"> <button id=\"gestioneBlackListButton\" type=\"submit\">BlackList</button></form>"
								:"")+"</td></tr>"
					+"</table><div id=\"MessageErrorCreateGroupModal\" style=\"display: none\" class=\"alert alert-danger\" role=\"alert\"></div>");
			if(json.canale.admin.username != "${sessionScope.user.username}")
				$("#fileChannelImage").hide();
			$("#channelModal").modal("show");
		}
	});
}
function createGroup(channel){
	$.ajax({
		type: "post",
		url: "creaGruppo",
		datatype: "json",
		data: JSON.stringify({nomeCanale: channel, nomeGruppo: $("#nomeGruppo").val()}),
		success: function(data){
			if(data == "AlreadyExists"){
				$("#MessageErrorCreateGroupModal").text("Il gruppo "+$("#nomeGruppo").val()+" è già esistente!").slideDown().show();
			}else
				window.location.href = "gruppo?group="+$("#nomeGruppo").val()+"&channel="+channel;
		}
	});
}

function showCreateGroup(){
	$("#showCreateGroupButton").hide();
	$("#createGroupForm").show();
}

function deleteChannel(channel){
	$.ajax({
		type:"post",
		url:"inviaNotifica",
		datatype: "json",
		data: JSON.stringify({"nomeGruppo": "","nomeCanale" : channel, "tipo" : "eliminazioneCanale", "idPost" : ""}),
		success: function(){
			document.location.href = "eliminaCanale?channel=" + channel;
		}
	});
}

function showCreateGroupButton(){
	$("#showCreateGroupButton").hide();
}

function sendChannelDescription(){
	$.ajax({
		type: "POST",
		url: "canale",
		datatype: "json",
		data: JSON.stringify({"nomeCanale":$("#channelModalTitle").text(), "modifica": $("#descriptionChannelTextarea").val()}),
		success: function(data){
			if(data=="success"){
				$("#descriptionChannelForm").hide();
				$("#ChannelDescription").text($("#descriptionChannelTextarea").val());
				$("#ChannelDescription").show();
				$("#editChannelDescriptionButton").show();
			}
		}
	});
}

function editChannelDescription(){
	$("#descriptionChannelTextarea").val($("#ChannelDescription").text());
	$("#ChannelDescription").hide();
	$("#editChannelDescriptionButton").hide();
	$("#descriptionChannelForm").show();
}

function sendChannelImage(){
	$.ajax({
		type: "POST",
		url: "loadFile?request=channel&channel="+$("#channelModalTitle").text(),
		data: new FormData($('#fileChannelImage')[0]),
        processData: false,
        contentType: false,
        cache : false,
        success : function(data) {
        	if(data=="error"){
        		$("#MessaggeErrorChannelModal").text("").append("Errore: il file è troppo grande!");
        		$("#MessaggeErrorChannelModal").slideDown().show();
        	}else{
        		location.reload();
        	}
        },
		error: function(){
			$("#MessaggeErrorChannelModal").text("").append("Errore durante il caricamento del file!");
    		$("#MessaggeErrorChannelModal").slideDown().show();
		}
	});
}
function removeChannelInfo(){
	$("#MessaggeErrorChannelModal").slideUp().hide();
}
/*function onLoadShowChannel(){
    $('[data-toggle="popover"]').popover();
}*/
</script>
<body>
	<!--<form action="javascript:onLoadShowChannel()" style="display:none">
		<input type="submit" class="onload">
	</form>-->
<div class="modal fade" id="channelModal" role="dialog">
    <div class="modal-dialog">    
      <div class="modal-content">
        <div class="modal-header">
          <h2 id="channelModalTitle" class="modal-title" style="text-align:center"></h2>
        </div>
		<div class="modal-body" style="text-align: center;">
			<div class="row">
				<div class="col-md-6">
					<img id="channelInfoImage" src="" width=60% 
							onerror="this.src='images/channels/unknown.jpg';"
							style="border-radius:50%; margin-top:10%;">
					<br><br>
					<form id="fileChannelImage" method="post" enctype="multipart/form-data" >					
						<span class="btn btn-info btn-file">
						    Cambia Foto <input type="file" name="file" onchange="sendChannelImage()" accept="image/jpeg">
						</span>
					</form>
          		</div>
				<div id="channelInfo" class="col-md-6">
				</div>
			</div>
			<div id="MessaggeErrorChannelModal" style="display: none"class="alert alert-danger" role="alert">
			</div>
		</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeChannelInfo()">Chiudi</button>
        </div>
      </div>
      
   </div>
</div>

</body>
</html>