<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${loginError != null}">
	<div class="alert alert-warning" role="alert">
		Invalid username or password.
	</div>
</c:if>
<form action="login" method="post">
	<div class="form-group row">
		<label class="col-lg-3 form-control-label">Username</label>
		<div class="col-lg-9">
			<input class="form-control form-control-sm" name="username">
		</div>
	</div>
	<div class="form-group row">
		<label class="col-lg-3 form-control-label">Password</label>
		<div class="col-lg-9">
			<input class="form-control form-control-sm" type="password" name="password">
		</div>
	</div>
	<div class="float-right">
		<input type="submit" class="btn btn-info btn-sm" value="Log in">
	</div>
</form>
