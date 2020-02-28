package ua.ivan909020.app.exception;

public class DatabaseException extends RuntimeException {

	public DatabaseException() {
	}

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
