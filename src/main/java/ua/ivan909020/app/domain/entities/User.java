package ua.ivan909020.app.domain.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class User implements Serializable {

	public static final String AUTHENTICATED_USER = "AUTHENTICATED_USER";

	private Integer id;
	private String username;
	private String password;
	private String information;
	private List<Post> posts;
	private Set<User> followers;
	private Set<User> following;

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

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
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
				&& Objects.equals(posts, other.posts) && Objects.equals(followers, other.followers)
				&& Objects.equals(following, other.following);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, information, posts, followers, following);
	}

	@Override
	public String toString() {
		return "User[id=" + id + ", username=" + username + ", password=" + password + ", information=" + information
				+ "]";
	}

}
