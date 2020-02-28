package ua.ivan909020.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ua.ivan909020.app.dao.UserDao;
import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.exception.DatabaseException;

public class UserDaoImpl implements UserDao {

	private static final UserDao INSTANCE = new UserDaoImpl();

	private DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

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
		String query = "insert into users(username, password) values (?, ?)";
		try (Connection connection = databaseConnection.openConnection();
				PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
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
			statement.executeUpdate();
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

}
