<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="pager.jsp" />
<c:forEach items="${users}" var="user">
	<div class="card mt-2">
		<div class="card-body">
			<a href="user/${user.getId()}">${user.getUsername()}</a>
		</div>
	</div>
</c:forEach>
<jsp:include page="pager.jsp" />
