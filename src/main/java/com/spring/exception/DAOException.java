package com.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DAOException extends RuntimeException {
    public DAOException(String message, Exception e) {
        super(message, e);
    }
}
