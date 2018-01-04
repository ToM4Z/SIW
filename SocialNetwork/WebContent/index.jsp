<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta charset="utf-8">
<title>LoosyNet</title>
</head>
<body>
	<c:if test="${sessionScope.username}">
		<jsp:forward page="home.jsp" />
	</c:if>
	<c:if test="${not sessionScope.username}">
		<jsp:forward page="login.html" />
	</c:if>
</body>
</html>
