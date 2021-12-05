package com.example.exception;

public class NotacceptedRequestException extends RuntimeException {

	public NotacceptedRequestException(final String message) {
		super(message);
	}

}
