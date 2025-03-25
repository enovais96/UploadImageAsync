package com.bix.upload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RegisterException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public RegisterException(String message) {
        super(message);
    }
}