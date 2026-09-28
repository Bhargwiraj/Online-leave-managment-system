package com.leavemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Leave Management System.
 * The @SpringBootApplication annotation enables auto-configuration,
 * component scanning, and configuration.
 */
@SpringBootApplication
public class LeaveManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaveManagementApplication.class, args);
        System.out.println("✅ Leave Management System is running on port 8080!");
    }
}
