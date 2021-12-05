package com.example.exception;

public class PreconditionFailedException extends RuntimeException{

    public PreconditionFailedException(final String message) {
        super(message);
    }

}
