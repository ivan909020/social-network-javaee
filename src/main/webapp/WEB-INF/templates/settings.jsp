<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Settings</h2>
<form action="settings/update" method="post">
	<div class="form-group row">
		<label class="col-lg-3 form-control-label">Information</label>
		<div class="col-lg-9">
			<textarea class="form-control" name="information">${AUTHENTICATED_USER.getInformation()}</textarea>
		</div>
	</div>
	<div class="form-group row">
		<label class="col-lg-3 form-control-label">Password</label>
		<div class="col-lg-9">
			<input class="form-control form-control-sm" type="password" name="password">
		</div>
	</div>
	<div class="float-right">
		<input type="submit" class="btn btn-primary btn-sm" value="Save Changes">
	</div>
</form>
