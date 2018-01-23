<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="canale" class="model.Canale" scope="request" />


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
function showCreateChannelModal(){
	$("#createChannelModal").modal("show");
}
function removeCreateChannelModal(){
	$("#nameChannel").val("");
	$("#descriptionChannel").val("");
	hideAlertsCreateChannelModal();
}
function hideAlertsCreateChannelModal(){
	$("#MessageErrorCreateChannelModal").val("").hide();
	$("#MessaggeSuccessCreateChannelModal").hide();
}

function createChannel(){
	$.ajax({
		type: "POST",
		url: "creaCanale",
		datatype: "json",
		data: JSON.stringify({nome: $("#nameChannel").val(), 
								descrizione: $("#descriptionChannel").val()}),
		success: function(data){
			hideAlertsCreateChannelModal();
			if(data=="success"){
				$("#MessaggeSuccessCreateChannelModal").slideDown().show();
				addChannel($("#nameChannel").val());
			}else if(data=="AlreadyExists")
				$("#MessageErrorCreateChannelModal").text("Il Canale "+$("#nameChannel").val()+" già esiste").slideDown().show();
			else
				$("#MessageErrorCreateChannelModal").text("Impossibile creare il canale").slideDown().show();
		},
		error: function(){
			$("#MessageErrorCreateChannelModal").text("Errore durante la creazione del canale").slideDown().show();
		}
	});
}
</script>
<body>
<div class="modal fade" id="createChannelModal" role="dialog">
    <div class="modal-dialog">    
      <div class="modal-content">
        <div class="modal-header">
 			<h2 class="modal-title" style="text-align:center;"> Crea un nuovo Canale </h2>         
        </div>
		<div class="modal-body" style="text-align: center;">			
			<div id="MessageErrorCreateChannelModal" style="display: none"class="alert alert-danger" role="alert"></div>			
			<div id="MessaggeSuccessCreateChannelModal" style="display: none"class="alert alert-success" role="alert">Canale creato con successo!</div>
			<form id="createChannelForm" action="javascript:createChannel()"></form>
			<table style="border:none" align=center>
				<tr>
					<td><h3>Nome :</h3></td>
					<td><input id="nameChannel" form="createChannelForm" required autocomplete="off" type="text" class="form-control" placeholder="Inserisci il nome del Canale"></td>
				</tr>
				<tr>
					<td><h3>Descrizione :</h3></td>
					<td><textarea id="descriptionChannel" form="createChannelForm" required autocomplete="off" class="form-control" rows="5" placeholder="Inserisci una descrizione"></textarea></td>
				</tr>
			</table><br>
			<button type="submit" class="btn btn-primary" form="createChannelForm">Crea</button>
		</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeCreateChannelModal()">Chiudi</button>
        </div>
      </div>
      
   </div>
</div>
</body>
</html>