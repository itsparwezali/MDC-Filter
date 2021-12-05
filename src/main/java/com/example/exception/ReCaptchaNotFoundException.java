package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ReCaptchaNotFoundException extends RuntimeException {

    public ReCaptchaNotFoundException(String message) {
        super(message);
    }

}
