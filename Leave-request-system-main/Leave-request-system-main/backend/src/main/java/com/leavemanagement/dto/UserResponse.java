package com.leavemanagement.dto;

import lombok.*;

/**
 * DTO for sending user details in API responses.
 * Does NOT include the password for security.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String role;
}
