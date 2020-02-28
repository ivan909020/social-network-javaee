<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="mt-3">
	<ul class="pagination">
		<li class="page-item disabled">
			<span class="page-link" tabindex="-1">Pages</span>
		</li>
		<c:forEach begin="1" end="${pager.getTotalPages()}" var="page">
			<c:choose>
				<c:when test="${page == pager.getPageNumber()}">
					<li class="page-item active">
						<span class="page-link" tabindex="-1">${page}</span>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item">
						<a class="page-link" href="${pager.getContextPath()}page=${page}&size=${pager.getPageSize()}" 
							tabindex="-1">${page}</a>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>

	<ul class="pagination">
		<li class="page-item disabled">
			<span class="page-link" tabindex="-1">Items per page</span>
		</li>
		<c:forEach items="${fn:split('10,25,50', ',')}" var="size">
			<c:choose>
				<c:when test="${size == pager.getPageSize()}">
					<li class="page-item active">
						<span class="page-link" tabindex="-1">${size}</span>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item">
						<a class="page-link" href="${pager.getContextPath()}page=${pager.getPageNumber()}&size=${size}"
							tabindex="-1">${size}</a>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
</div>
