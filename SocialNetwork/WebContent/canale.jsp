<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="user" class="model.Utente" scope="session" />
<jsp:useBean id="canale" class="model.Canale" scope="request" />

<html>
<head lang="it">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<script>
	function seisicuro() {

		var canale = $("#nomeCanale").text();

		if (confirm("Sei sicuro di voler cancellare il canale?") == true) {

			document.location.href = "eliminaCanale?channel=" + canale;

		} else {

			//do nothing
		}
	}

	function load() {
		$("input.onload").each(function() {
			$(this).trigger("click");
		});
	};
</script>
</head>
<body onload="javascript:load()" style="overflow-x:hidden">
	<c:if test="${empty user.nome}">
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

			<div id="homePost" style="margin-top: 60px; text-align: center;">
				<h1 id="nomeCanale">${canale.nome}</h1>
				<h3>Descizione: ${canale.descrizione}</h3>
				<c:if test="${sessionScope.user.email == canale.admin.email}">
					<h4>
						<a href=creaGruppo?channel=${canale.nome}>Crea Gruppo</a>
					</h4>
					<h4 onclick="seisicuro()">Elimina canale</h4>
				</c:if>

				<c:if test="${sessionScope.user.email != canale.admin.email}">
					<c:choose>
						<c:when test="${iscritto == true && blacklist == false}">
							<h4>
								<a href=iscrizioneCanale?channel=${canale.nome}&iscritto=true>Cancellati
									dal canale</a>
							</h4>
							<h4>
								<a href=creaGruppo?channel=${canale.nome}>Crea Gruppo</a>
							</h4>
						</c:when>
						<c:when test="${iscritto == false && blacklist == false}">
							<h4>
								<a href=iscrizioneCanale?channel=${canale.nome}&iscritto=false>Iscriviti
									al canale</a>
							</h4>
						</c:when>
					</c:choose>
				</c:if>

				<div id=gruppi>
					<h3>Gruppi:</h3>
					<c:forEach var="gruppo" items="${canale.gruppi}">
						<h4>
							<a href=gruppo?group=${gruppo.nome}&channel=${canale.nome}>${gruppo.nome}</a>
						</h4>
						<small><small>${gruppo.data_creazione}</small></small>

					</c:forEach>
				</div>
			</div>
		</div>
	</div>

</body>
</html>