package io.leopard.droid4j.exception;

public class StatusCodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String statusCode;

	private String message;

	public StatusCodeException(String statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
