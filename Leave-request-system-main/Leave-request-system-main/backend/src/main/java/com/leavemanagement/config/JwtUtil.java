package com.leavemanagement.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for JWT (JSON Web Token) operations.
 * Handles token generation, validation, and parsing.
 */
@Component
public class JwtUtil {

    // Secret key from application.properties
    @Value("${jwt.secret}")
    private String secret;

    // Token expiration time from application.properties
    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Generate a signing key from the secret string.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generate a JWT token for a user.
     * The token contains: email (subject), userId, role, and expiration time.
     */
    public String generateToken(String email, Long userId, String role) {
        return Jwts.builder()
                .subject(email)                                           // Set email as subject
                .claim("userId", userId)                                  // Add userId as custom claim
                .claim("role", role)                                      // Add role as custom claim
                .issuedAt(new Date())                                     // Token issued now
                .expiration(new Date(System.currentTimeMillis() + expiration)) // Set expiry
                .signWith(getSigningKey())                                // Sign with secret key
                .compact();
    }

    /**
     * Extract the email (subject) from a JWT token.
     */
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extract the userId from a JWT token.
     */
    public Long getUserIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    /**
     * Extract the role from a JWT token.
     */
    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }

    /**
     * Validate if a JWT token is valid and not expired.
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Parse and return all claims from a JWT token.
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
