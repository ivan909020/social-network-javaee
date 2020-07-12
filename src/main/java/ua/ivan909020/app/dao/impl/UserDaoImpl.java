package ua.ivan909020.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ua.ivan909020.app.dao.UserDao;
import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.exception.DatabaseException;

public class UserDaoImpl implements UserDao {

	private static final UserDao INSTANCE = new UserDaoImpl();

	private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

	private UserDaoImpl() {
	}

	public static UserDao getInstance() {
		return INSTANCE;
	}

	@Override
	public User findById(Integer id) {
		String query = "select * from users where id = ?";
		User user = null;
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					user = new User();
					user.setId(result.getInt("id"));
					user.setUsername(result.getString("username"));
					user.setPassword(result.getString("password"));
					user.setInformation(result.getString("information"));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find user by id " + id, e);
		}
		return user;
	}

	@Override
	public User create(User user) {
		String query = "insert into users(username, password, information) values (?, ?, ?)";
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getInformation());
			statement.execute();
			try (ResultSet result = statement.getGeneratedKeys()) {
				result.next();
				user.setId(result.getInt("id"));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to create user", e);
		}
		return user;
	}

	@Override
	public User update(User user) {
		String query = "update users set password = ?, information = ? where id = ?";
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, user.getPassword());
			statement.setString(2, user.getInformation());
			statement.setInt(3, user.getId());
			int result = statement.executeUpdate();
			if (result == 0) {
				user = null;
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to update user with id " + user.getId(), e);
		}
		return user;
	}

	@Override
	public User findByUsername(String username) {
		String query = "select * from users where username = ?";
		User user = null;
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, username);
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					user = new User();
					user.setId(result.getInt("id"));
					user.setUsername(result.getString("username"));
					user.setPassword(result.getString("password"));
					user.setInformation(result.getString("information"));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find user by username " + username, e);
		}
		return user;
	}

	@Override
	public int countByContainsUsername(String username) {
		String query = "select count(*) from users where username ilike ?";
		int count = 0;
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, "%" + username + "%");
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					count = result.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find users count by contains username " + username, e);
		}
		return count;
	}

	@Override
	public List<User> findContainsUsername(String username, int offset, int limit) {
		String query = "select * from users where username ilike ? offset ? limit ?";
		List<User> users = new ArrayList<>();
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, "%" + username + "%");
			statement.setInt(2, offset);
			statement.setInt(3, limit);
			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					User user = new User();
					user.setId(result.getInt("id"));
					user.setUsername(result.getString("username"));
					user.setPassword(result.getString("password"));
					user.setInformation(result.getString("information"));
					users.add(user);
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find users by contains username " + username, e);
		}
		return users;
	}

	@Override
	public int countFollowersByUserId(Integer id) {
		String query = "select count(*) from users_followers where user_id = ?";
		int count = 0;
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					count = result.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find followers count by user id" + id, e);
		}
		return count;
	}

	@Override
	public Set<User> findFollowersByUserId(Integer id, int offset, int limit) {
		String query = "select * from users_followers where user_id = ? offset ? limit ?";
		Set<User> followers = new HashSet<>();
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setInt(2, offset);
			statement.setInt(3, limit);
			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					int followerId = result.getInt("follower_id");
					User user = findById(followerId);
					followers.add(user);
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find followers by user id " + id, e);
		}
		return followers;
	}

	@Override
	public int countFollowingByUserId(Integer id) {
		String query = "select count(*) from users_followers where follower_id = ?";
		int count = 0;
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					count = result.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find following count by follower id" + id, e);
		}
		return count;
	}

	@Override
	public Set<User> findFollowingByUserId(Integer id, int offset, int limit) {
		String query = "select * from users_followers where follower_id = ? offset ? limit ?";
		Set<User> following = new HashSet<>();
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setInt(2, offset);
			statement.setInt(3, limit);
			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					int userId = result.getInt("user_id");
					User user = findById(userId);
					following.add(user);
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find following by follower id " + id, e);
		}
		return following;
	}

	@Override
	public User findUserFollower(Integer userId, Integer followerId) {
		String query = "select * from users_followers where user_id = ? and follower_id = ?";
		User user = null;
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			statement.setInt(2, followerId);
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					int userFollowerId = result.getInt("follower_id");
					user = findById(userFollowerId);
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException(
					"Failed to find user follower by user id " + userId + " and follower id " + followerId, e);
		}
		return user;
	}

	@Override
	public void createUserFollower(Integer userId, Integer followerId) {
		String query = "insert into users_followers(user_id, follower_id) values (?, ?)";
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			statement.setInt(2, followerId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(
					"Failed create user follower by user id " + userId + " and follower id " + followerId, e);
		}
	}

	@Override
	public void deleteUserFollower(Integer userId, Integer followerId) {
		String query = "delete from users_followers where user_id = ? and follower_id = ?";
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			statement.setInt(2, followerId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(
					"Failed delete user follower by user id " + userId + " and follower id " + followerId, e);
		}
	}

}
