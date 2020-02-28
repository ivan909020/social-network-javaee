package ua.ivan909020.app.domain.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

	public static final String AUTHENTICATED_USER = "AUTHENTICATED_USER";

	private Integer id;
	private String username;
	private String password;
	private String information;
	private List<Post> posts;

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(id, other.id) && Objects.equals(username, other.username)
				&& Objects.equals(password, other.password) && Objects.equals(information, other.information)
				&& Objects.equals(posts, other.posts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, information, posts);
	}

	@Override
	public String toString() {
		return "User[id=" + id + ", username=" + username + ", password=" + password + ", information=" + information
				+ "]";
	}

}
