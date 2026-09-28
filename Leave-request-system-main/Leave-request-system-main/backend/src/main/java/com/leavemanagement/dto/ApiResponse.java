package com.leavemanagement.dto;

import lombok.*;

/**
 * Generic API response wrapper.
 * Provides a consistent response format for all APIs.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

    private boolean success;   // true for success, false for error
    private String message;    // Description of the result
    private Object data;       // The actual response data (can be any type)

    // --- Convenience factory methods ---

    /** Create a success response with data */
    public static ApiResponse success(String message, Object data) {
        return ApiResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /** Create a success response without data */
    public static ApiResponse success(String message) {
        return ApiResponse.builder()
                .success(true)
                .message(message)
                .build();
    }

    /** Create an error response */
    public static ApiResponse error(String message) {
        return ApiResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}
