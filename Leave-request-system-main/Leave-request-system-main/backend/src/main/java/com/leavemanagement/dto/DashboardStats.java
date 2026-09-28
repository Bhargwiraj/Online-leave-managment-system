package com.leavemanagement.dto;

import lombok.*;

import java.util.Map;

/**
 * DTO for dashboard statistics.
 * Used by both user and admin dashboards.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStats {

    // Key-value pairs for dashboard counts
    // Example: {"totalLeaves": 10, "approved": 5, "rejected": 2, "pending": 3}
    private Map<String, Long> stats;
}
