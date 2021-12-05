package com.example.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends AbstractRuntimeException {

    private String title;

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String title, String message) {
        super(message);
        this.title = title;
    }

}
