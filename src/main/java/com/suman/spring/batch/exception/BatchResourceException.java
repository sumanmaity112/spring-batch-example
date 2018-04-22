package com.suman.spring.batch.exception;

public class BatchResourceException extends RuntimeException {

    public BatchResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
