package ua.ivan909020.app.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ua.ivan909020.app.exception.DatabaseException;

final class DatabaseConnection {

	private static final DatabaseConnection INSTANCE = new DatabaseConnection();

	private static final DataSource DATA_SOURCE = createDatasource();

	private DatabaseConnection() {
	}

	private static DataSource createDatasource() {
		DataSource dataSource;
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/social-network");
		} catch (NamingException e) {
			throw new DatabaseException("Failed to create datasource", e);
		}
		return dataSource;
	}

	public static DatabaseConnection getInstance() {
		return INSTANCE;
	}

	public Connection openConnection() {
		Connection connection;
		try {
			connection = DATA_SOURCE.getConnection();
		} catch (SQLException e) {
			throw new DatabaseException("Failed to open connection", e);
		}
		return connection;
	}

}
