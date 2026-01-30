package com.revworkforce.exception;

public class RevWorkforceException extends Exception {

    public RevWorkforceException(String message) {
        super(message);
    }

    public RevWorkforceException(String message, Throwable cause) {
        super(message, cause);
    }
}

