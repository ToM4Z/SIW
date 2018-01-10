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

<c:if test="${canale.nome == null}">
	<h1>Crea nuovo canale</h1>
 </c:if>


 <c:if test="${canale.nome != null}">
 	
 	<c:redirect url="canale?to=${canale.nome}"/>
 </c:if>
 

</header>

<section class="moduloRegistrazione" class="row">
<div class="col-lg-3">
	<form method="post" action="creaCanale"> 
		<div class="form-group"><label for="nome">Nome:</label><input name="nome" type="text" class="form-control" /></div> 
		<div class="form-group"><label for="descrizione">Descrizione:</label> <input name="descrizione" type="text" class="form-control" /> </div> 
				
		<div class="form-group">
			<input name="inviaDati" type="submit" value="creaCanale"  class="btn btn-success"/>
		</div>		
	</form>
</div>
</section>


</body>
</html>