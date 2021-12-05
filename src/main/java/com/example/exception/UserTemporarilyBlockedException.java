package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UserTemporarilyBlockedException extends RuntimeException {

    public UserTemporarilyBlockedException(final String message) {
        super(message);
    }

}
