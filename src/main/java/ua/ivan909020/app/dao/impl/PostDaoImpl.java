package ua.ivan909020.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ua.ivan909020.app.dao.PostDao;
import ua.ivan909020.app.domain.entities.Post;
import ua.ivan909020.app.exception.DatabaseException;

public class PostDaoImpl implements PostDao {

	private static final PostDao INSTANCE = new PostDaoImpl();

	private DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

	private PostDaoImpl() {
	}

	public static PostDao getInstance() {
		return INSTANCE;
	}

	@Override
	public Post findById(Integer id) {
		String query = "select * from posts where id = ?";
		Post post = null;
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					post = new Post();
					post.setId(result.getInt("id"));
					post.setUserId(result.getInt("user_id"));
					post.setTitle(result.getString("title"));
					post.setDescription(result.getString("description"));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find post by id " + id, e);
		}
		return post;
	}

	@Override
	public Post create(Post post) {
		String query = "insert into posts(user_id, title, description, created) values (?, ?, ?, ?)";
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, post.getUserId());
			statement.setString(2, post.getTitle());
			statement.setString(3, post.getDescription());
			statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
			statement.execute();
			try (ResultSet result = statement.getGeneratedKeys()) {
				result.next();
				post.setId(result.getInt("id"));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to create post", e);
		}
		return post;
	}

	@Override
	public Post update(Post post) {
		String query = "update posts set title = ?, description = ? where id = ?";
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, post.getTitle());
			statement.setString(2, post.getDescription());
			statement.setInt(3, post.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Failed to update post with id " + post.getId(), e);
		}
		return post;
	}

	@Override
	public int countAll() {
		String query = "select count(*) from posts";
		int count = 0;
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					count = result.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find posts count", e);
		}
		return count;
	}

	@Override
	public List<Post> findAll(int offset, int limit) {
		String query = "select * from posts offset ? limit ?";
		List<Post> posts = new ArrayList<>();
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, offset);
			statement.setInt(2, limit);
			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					Post post = new Post();
					post.setId(result.getInt("id"));
					post.setUserId(result.getInt("user_id"));
					post.setTitle(result.getString("title"));
					post.setDescription(result.getString("description"));
					post.setCreated(result.getTimestamp("created").toLocalDateTime());
					posts.add(post);
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find all posts", e);
		}
		return posts;
	}

}
