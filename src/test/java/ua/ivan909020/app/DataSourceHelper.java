package ua.ivan909020.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import ua.ivan909020.app.exception.DatabaseException;

final class DataSourceHelper {

	private final static DataSourceHelper INSTANCE = new DataSourceHelper();

	private DataSource datasource;

	private DataSourceHelper() {
		this.datasource = createDataSource();
	}

	private BasicDataSource createDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/social_network_test");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		return dataSource;
	}

	public static DataSourceHelper getInstance() {
		return INSTANCE;
	}

	public void configureDataSource() {
		setContext();
		bindContext();
	}

	public void unconfigureDataSource() {
		unbindContext();
		clearContext();
		clearDatabase();
	}

	private void setContext() {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
		System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
	}

	private void clearContext() {
		System.clearProperty(Context.INITIAL_CONTEXT_FACTORY);
		System.clearProperty(Context.URL_PKG_PREFIXES);
	}

	private void bindContext() {
		try {
			Context context = new InitialContext();
			context.createSubcontext("java:");
			context.createSubcontext("java:comp");
			context.createSubcontext("java:comp/env");
			context.createSubcontext("java:comp/env/jdbc");
			context.bind("java:comp/env/jdbc/social-network", datasource);
		} catch (NamingException e) {
			throw new IllegalStateException("Failed to bind context", e);
		}
	}

	private void unbindContext() {
		try {
			Context context = new InitialContext();
			context.unbind("java:comp/env/jdbc/social-network");
			context.destroySubcontext("java:comp/env/jdbc");
			context.destroySubcontext("java:comp/env");
			context.destroySubcontext("java:comp");
			context.destroySubcontext("java:");
		} catch (NamingException e) {
			throw new IllegalStateException("Failed to unbind context", e);
		}
	}

	private void clearDatabase() {
		for (String table : findAllTables()) {
			deleteTable(table);
		}
		for (String sequence : findAllSequences()) {
			resetSequence(sequence);
		}
	}

	private Set<String> findAllTables() {
		String query = "select table_name from information_schema.tables where table_schema='public'";
		Set<String> tables = new HashSet<>();
		try (Connection connection = datasource.getConnection();
				Statement statement = connection.createStatement()) {
			try (ResultSet result = statement.executeQuery(query)) {
				while (result.next()) {
					tables.add(result.getString(1));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find all tables", e);
		}
		return tables;
	}

	private void deleteTable(String tableName) {
		String query = String.format("truncate table %s cascade", tableName);
		try (Connection connection = datasource.getConnection();
				Statement statement = connection.createStatement()) {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new DatabaseException("Failed to delete table by name " + tableName, e);
		}
	}

	private Set<String> findAllSequences() {
		String query = "select sequence_name from information_schema.sequences where sequence_schema='public'";
		Set<String> sequences = new HashSet<>();
		try (Connection connection = datasource.getConnection();
				Statement statement = connection.createStatement()) {
			try (ResultSet result = statement.executeQuery(query)) {
				while (result.next()) {
					sequences.add(result.getString(1));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Failed to find all sequences", e);
		}
		return sequences;
	}

	private void resetSequence(String sequenceName) {
		String query = String.format("alter sequence %s restart with 1", sequenceName);
		try (Connection connection = datasource.getConnection();
				Statement statement = connection.createStatement()) {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new DatabaseException("Failed to update sequence with name " + sequenceName, e);
		}
	}

}
