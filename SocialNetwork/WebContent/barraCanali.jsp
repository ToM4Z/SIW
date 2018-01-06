<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>I tuoi canali</h3>	
	<ul>
		<c:forEach var="canale" items="${canali}">
			<li><a href =canale?to=${canale.nome}>${canale.nome}</a></li>
			<ul>
			<c:forEach var = "gruppo" items = "${canale.gruppi }">
				<li><a href =gruppo?to=${gruppo.nome}&at=${canale.nome}>${gruppo.nome}</a></li>
			</c:forEach>
			</ul>
		</c:forEach>
	</ul>
</body>
</html>