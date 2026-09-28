package com.leavemanagement.entity;

import com.leavemanagement.enums.Role;
import jakarta.persistence.*;
import lombok.*;

/**
 * User Entity - Maps to the "users" table in the database.
 * Stores information about employees/students and admins.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // Primary key - auto-generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Full name of the user
    @Column(nullable = false)
    private String name;

    // Email - used for login (must be unique)
    @Column(nullable = false, unique = true)
    private String email;

    // Password - stored as BCrypt hash
    @Column(nullable = false)
    private String password;

    // Role - either USER or ADMIN
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
