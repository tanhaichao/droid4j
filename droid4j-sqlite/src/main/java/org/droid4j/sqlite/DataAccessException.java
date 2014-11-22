package org.droid4j.sqlite;

public class DataAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
