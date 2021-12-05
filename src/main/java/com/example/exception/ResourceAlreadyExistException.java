package com.example.exception;

public class ResourceAlreadyExistException extends RuntimeException {

	public ResourceAlreadyExistException(final String message) {
		super(message);
	}

}
