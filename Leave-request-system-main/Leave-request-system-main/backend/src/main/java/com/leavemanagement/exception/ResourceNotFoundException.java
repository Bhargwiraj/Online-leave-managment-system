package com.leavemanagement.exception;

/**
 * Custom exception for when a requested resource is not found.
 * Example: User not found, Leave request not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
