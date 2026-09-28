package com.leavemanagement.service;

import com.leavemanagement.config.JwtUtil;
import com.leavemanagement.dto.*;
import com.leavemanagement.entity.User;
import com.leavemanagement.enums.Role;
import com.leavemanagement.exception.BadRequestException;
import com.leavemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for authentication operations.
 * Handles user registration and login logic.
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Register a new user.
     * - Checks if email already exists
     * - Hashes the password with BCrypt
     * - Saves user to database
     * - Returns a JWT token
     */
    public Map<String, Object> register(RegisterRequest request) {
        // Check if email is already registered
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered!");
        }

        // Create new user entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash password
        user.setRole(Role.USER); // Default role is USER

        // Save to database
        User savedUser = userRepository.save(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getId(), savedUser.getRole().name());

        // Build response
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", savedUser.getId());
        response.put("name", savedUser.getName());
        response.put("email", savedUser.getEmail());
        response.put("role", savedUser.getRole().name());

        return response;
    }

    /**
     * Login an existing user.
     * - Finds user by email
     * - Validates password
     * - Returns a JWT token
     */
    public Map<String, Object> login(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password!"));

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password!");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole().name());

        // Build response
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("role", user.getRole().name());

        return response;
    }
}
