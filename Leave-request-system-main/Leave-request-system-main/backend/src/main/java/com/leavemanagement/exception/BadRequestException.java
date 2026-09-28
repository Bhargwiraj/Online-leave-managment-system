package com.leavemanagement.exception;

/**
 * Custom exception for bad request scenarios.
 * Example: Invalid input, duplicate email, etc.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
