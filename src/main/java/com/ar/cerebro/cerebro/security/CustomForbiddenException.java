package com.ar.cerebro.cerebro.security;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)

public class CustomForbiddenException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CustomForbiddenException(String message) {
        super(message);
    }

}
