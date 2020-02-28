<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<a class="navbar-brand">Social network</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
		aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarText">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item">
				<a class="nav-link" href="/">Wall</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="search">Search</a>
			</li>
			<c:if test="${AUTHENTICATED_USER != null}">
				<li class="nav-item">
					<a class="nav-link" href="user/${AUTHENTICATED_USER.getId()}">My Profile</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="settings">Settings</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="post">Add post</a>
				</li>
			</c:if>
		</ul>
		<span class="navbar-text">
			<c:choose>
				<c:when test="${AUTHENTICATED_USER == null}">
					<form action="login">
						<input type="submit" class="btn btn-info btn-sm" value="Log in">
					</form>
				</c:when>
				<c:otherwise>
					<form action="logout" method="post">
						<input type="submit" class="btn btn-warning btn-sm" value="Log out">
					</form>
				</c:otherwise>
			</c:choose>
		</span>
	</div>
</nav>
