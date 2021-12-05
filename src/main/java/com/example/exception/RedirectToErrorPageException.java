package com.example.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RedirectToErrorPageException extends AbstractRuntimeException {

    private String redirectLink;

    public RedirectToErrorPageException(String redirectLink) {
        super(null);
        this.redirectLink = redirectLink;
    }

    public RedirectToErrorPageException(String message, String redirectLink) {
        super(message);
        this.redirectLink = redirectLink;
    }
}
