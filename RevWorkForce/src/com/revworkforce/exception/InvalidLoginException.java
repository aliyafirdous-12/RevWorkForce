package com.revworkforce.exception;

public class InvalidLoginException extends RevWorkforceException {

    public InvalidLoginException() {
        super("Invalid username or password.");
    }
}
