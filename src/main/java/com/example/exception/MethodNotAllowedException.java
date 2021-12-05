package com.example.exception;

public class MethodNotAllowedException extends RuntimeException{

    public MethodNotAllowedException (final String message) {
        super(message);
    }

}
