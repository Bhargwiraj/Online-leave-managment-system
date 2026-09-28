package com.leavemanagement.service;

import com.leavemanagement.dto.UserResponse;
import com.leavemanagement.entity.User;
import com.leavemanagement.exception.ResourceNotFoundException;
import com.leavemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for user-related operations.
 * Handles fetching user profile information.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get user profile by email.
     * Returns user details without the password.
     */
    public UserResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
