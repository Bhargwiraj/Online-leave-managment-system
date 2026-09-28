package com.leavemanagement.repository;

import com.leavemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 * Spring Data JPA automatically generates the implementation.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by email (used during login)
    Optional<User> findByEmail(String email);

    // Check if email already exists (used during registration)
    Boolean existsByEmail(String email);
}
