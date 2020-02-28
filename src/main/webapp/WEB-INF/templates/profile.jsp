<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row mt-2">
	<div class="col-lg-3 order-lg-1 text-center">
		<img src="static/img/default-image.png" class="img-fluid" alt="avatar">
	</div>
	<div class="col-lg-8 order-lg-2 py-3">
		<h3 class="mb-3">
			<a href="user/${user.getId()}">${user.getUsername()}</a>
		</h3>
		<div class="row">
			<div class="col-md-6">
				<h6>Information</h6>
				<p>${user.getInformation()}</p>
			</div>
			<div class="col-md-6">
				<h6>Statistic</h6>
				<div class="row text-center">
					<div class="col">
						<strong>${userPostsCount}</strong>
						<p><a href="user/posts/${user.getId()}" class="small">Posts</a></p>
					</div>
					<div class="col">
						<strong>${userFollowersCount}</strong>
						<p><a href="user/followers/${user.getId()}" class="small">Followers</a></p>
					</div>
					<div class="col">
						<strong>${userFollowingCount}</strong>
						<p><a href="user/following/${user.getId()}" class="small">Following</a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<c:if test="${user.getFollowers() != null}">
	<div class="mt-3 mb-5">
		<h4>Followers</h4>
		<c:set var="users" value="${user.getFollowers()}" scope="request" />
		<jsp:include page="users.jsp" />
	</div>
</c:if>
<c:if test="${user.getFollowing() != null}">
	<div class="mt-3 mb-5">
		<h4>Following</h4>
		<c:set var="users" value="${user.getFollowing()}" scope="request" />
		<jsp:include page="users.jsp" />
	</div>
</c:if>
