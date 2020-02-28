<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<base href="${pageContext.request.contextPath}/">

	<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link href="static/css/bootstrap.css" rel="stylesheet">
	<link href="static/css/bootstrap-grid.css" rel="stylesheet">

	<title>Social network</title>
</head>

<body>
	<jsp:include page="templates/header.jsp" />

	<div class="container">
		<div class="mt-2 mb-5">
			<jsp:include page="${template}" />
		</div>
	</div>

	<jsp:include page="templates/footer.jsp" />

	<script src="static/js/jquery.js"></script>
	<script src="static/js/bootstrap.bundle.js"></script>
	<script src="static/js/bootstrap.js"></script>
</body>

</html>
