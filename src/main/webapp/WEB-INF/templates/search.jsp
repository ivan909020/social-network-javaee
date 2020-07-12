<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Search users <c:if test="${usersCount != null}">(${usersCount} pieces)</c:if></h2>
<form action="search">
	<div class="input-group input-group-sm mb-3">
		<input type="text" class="form-control" placeholder="Username"
			name="username" value="${param.username}">
		<div class="input-group-append">
			<input type="submit" class="btn btn-outline-secondary" value="Search">
		</div>
	</div>
</form>

<c:if test="${param.username != null}">
	<jsp:include page="users.jsp" />
</c:if>
