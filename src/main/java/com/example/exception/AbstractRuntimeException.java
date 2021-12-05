package com.example.exception;

abstract class AbstractRuntimeException extends RuntimeException {

	private String statusCode;

	public AbstractRuntimeException(String message) {
		super(message);
	}

	public AbstractRuntimeException(String message, String statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	AbstractRuntimeException(String message, Throwable cause, String statusCode) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	String getStatusCode() {
		return statusCode;
	}

}
