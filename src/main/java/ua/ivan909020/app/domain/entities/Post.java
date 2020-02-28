package ua.ivan909020.app.domain.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Post implements Serializable {

	private Integer id;
	private Integer userId;
	private String title;
	private String description;
	private LocalDateTime created;

	public Post() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Post other = (Post) obj;
		return Objects.equals(id, other.id) && Objects.equals(description, other.description)
				&& Objects.equals(title, other.title) && Objects.equals(userId, other.userId)
				&& Objects.equals(created, other.created);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, title, userId, created);
	}

	@Override
	public String toString() {
		return "Post[id=" + id + ", userId=" + userId + ", title=" + title + ", description=" + description
				+ ", created=" + created + "]";
	}

}
