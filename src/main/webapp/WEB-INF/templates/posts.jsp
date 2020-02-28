<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${user != null && !user.getPosts().isEmpty()}">
	<jsp:include page="pager.jsp" />
	<div class="card-columns">
		<c:forEach items="${user.getPosts()}" var="post">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">${post.getTitle()}</h5>
					<p class="card-text">${post.getDescription()}</p>
					<p class="card-text text-right">
						<small class="text-muted">Created: ${post.getCreated()}</small>
					</p>
					<c:if test="${AUTHENTICATED_USER.getId() == post.getUserId()}">
						<div class="card-text text-right">
							<form action="post/delete" method="post">
								<input type="hidden" name="id" value="${post.getId()}">
								<input type="submit" class="btn btn-danger btn-sm" value="Delete">
							</form>
						</div>
					</c:if>
				</div>
			</div>
		</c:forEach>
	</div>
	<jsp:include page="pager.jsp" />
</c:if>
