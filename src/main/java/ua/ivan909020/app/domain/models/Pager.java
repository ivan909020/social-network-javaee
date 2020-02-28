package ua.ivan909020.app.domain.models;

import java.util.Objects;

public class Pager {

	private final int pageNumber;
	private final int pageSize;
	private final int totalPages;
	private final int totalElements;
	private final String contextPath;

	private static final int MINIMUM_PAGE = 1;
	private static final int MINIMUM_PAGE_SIZE = 10;
	private static final int MAXIMUM_PAGE_SIZE = 50;

	public Pager(int pageNumber, int pageSize, int totalPages, int totalElements, String contextPath) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.contextPath = contextPath;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public String getContextPath() {
		return contextPath;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pager other = (Pager) obj;
		return pageNumber == other.pageNumber && pageSize == other.pageSize && totalElements == other.totalElements
				&& totalPages == other.totalPages && Objects.equals(contextPath, other.contextPath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pageNumber, pageSize, totalElements, totalPages, contextPath);
	}

	@Override
	public String toString() {
		return "Pager[pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", totalPages=" + totalPages
				+ ", totalElements=" + totalElements + ", contextPath=" + contextPath + "]";
	}

	public static Pager build(int totalElements, int pageNumber, int pageSize, String contextPath) {
		if (pageSize > MAXIMUM_PAGE_SIZE) {
			pageSize = MAXIMUM_PAGE_SIZE;
		}
		if (pageSize < MINIMUM_PAGE_SIZE) {
			pageSize = MINIMUM_PAGE_SIZE;
		}
		int totalPages = totalElements / pageSize;
		if (totalElements > totalPages * pageSize) {
			totalPages++;
		}
		if (pageNumber > totalPages) {
			pageNumber = totalPages;
		}
		if (pageNumber < MINIMUM_PAGE) {
			pageNumber = MINIMUM_PAGE;
		}
		return new Pager(pageNumber, pageSize, totalPages, totalElements, contextPath);
	}

}
