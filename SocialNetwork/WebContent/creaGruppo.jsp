<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="canale" class="model.Canale" scope="request" />

<html>
<head lang="it">
<title>LoosyNet</title>
<meta charset="utf-8">

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<!--   
<LINK rel="stylesheet" href="css/common.css" type="text/css">
 -->

</head>
<body>
<header>

 <c:if test="${gruppo.nome != null}">
 	<c:redirect url = "gruppo?to=${gruppo.nome}&at=${gruppo.canale.nome}"/>
 </c:if>
 <c:if test="${gruppo.nome == null}">
	<h1>Crea nuovo gruppo</h1>
 </c:if>


</header>

<section class="moduloRegistrazione" class="row">
<div class="col-lg-3">
	<form method="post" action="creaGruppo"> 
		<div class="form-group"><label for="nome">Nome:</label><input name="nome" type="text" class="form-control" /></div>  
				
		<div class="form-group">
			<input name="inviaDati" type="submit" value="creaGruppo"  class="btn btn-success"/>
		</div>		
	</form>
</div>
</section>


</body>
</html>