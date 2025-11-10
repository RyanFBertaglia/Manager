package com.manager.exceptions;

public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }
    public NotFound() {
        super("Not Found");
    }
}
