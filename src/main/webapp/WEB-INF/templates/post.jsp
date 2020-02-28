<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Add post</h2>
<form action="post/create" method="post">
	<div class="form-group row">
		<label class="col-lg-3 form-control-label">Title</label>
		<div class="col-lg-9">
			<input class="form-control form-control-sm" type="text" name="title">
		</div>
	</div>
	<div class="form-group row">
		<label class="col-lg-3 form-control-label">Description</label>
		<div class="col-lg-9">
			<textarea class="form-control" name="description"></textarea>
		</div>
	</div>
	<div class="float-right">
		<input type="submit" class="btn btn-primary btn-sm" value="Create">
	</div>
</form>
