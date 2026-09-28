package com.leavemanagement.dto;

import lombok.*;

/**
 * DTO for sending leave request details in API responses.
 * Includes all fields needed by the frontend to display leave info.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveResponse {

    private Long id;
    private String userName;       // Name of the user who applied
    private String userEmail;      // Email of the user
    private String leaveType;      // SICK, CASUAL, PERSONAL, EMERGENCY
    private String startDate;      // yyyy-MM-dd
    private String endDate;        // yyyy-MM-dd
    private String reason;
    private String status;         // PENDING, APPROVED, REJECTED, CANCELLED
    private String adminRemark;
    private String appliedDate;    // yyyy-MM-dd
}
