package com.pw.taskmanager.modules.shared.errors;

public class DomainException extends RuntimeException {
    private final int statusCode;

    public DomainException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
