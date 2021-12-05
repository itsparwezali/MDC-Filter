package com.example.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class RestTemplateException extends AbstractRuntimeException {

    private HttpStatus httpStatus;
    private String code;

    public RestTemplateException(String message, HttpStatus httpStatus) {
        super(message, String.valueOf(httpStatus.value()));
        this.httpStatus = httpStatus;
    }

    public RestTemplateException(String message, HttpStatus httpStatus, String code) {
        super(message, String.valueOf(httpStatus.value()));
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public RestTemplateException(String message, String httpCode) {
        super(message, httpCode);
    }

    public RestTemplateException(HttpStatus httpStatus, String code) {
        super(String.valueOf(httpStatus.value()));
        this.httpStatus = httpStatus;
        this.code = code;
    }
}