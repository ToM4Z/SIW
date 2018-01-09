<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="user" class="model.Utente" scope="session" />

<html>
<head lang="it">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, height=device-height">
<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="js/barraCanali.js"></script>
<link rel="stylesheet" href="css/tree.css">
</head>
<body onload="javascript:getCanali()">
	<div class="panel panel-default"
		style="position:fixed; float: left; width: 25%; height: 100%; margin-top: 40px; margin-bottom: -20px">
		<div class="panel-heading" style="text-align: center"><h3>Canali</h3></div>
		<div class="panel-body" id="listaCanali"></div>
	</div>
</body>
</html>