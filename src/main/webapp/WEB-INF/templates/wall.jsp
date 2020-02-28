<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>All posts (${postsCount} pieces)</h2>
<jsp:include page="pager.jsp" />
<div class="card-columns mt-3">
	<c:forEach items="${posts}" var="post">
		<div class="card">
			<div class="card-body">
				<h5 class="card-title">${post.getTitle()}</h5>
				<p class="card-text">${post.getDescription()}</p>
				<p class="card-text">
					<small class="text-muted">Created: ${post.getCreated()}</small>
				</p>
			</div>
			<div class="card-footer">
				Author: <a href="user/${post.getUserId()}" class="card-link">view</a>
			</div>
		</div>
	</c:forEach>
</div>
<jsp:include page="pager.jsp" />
