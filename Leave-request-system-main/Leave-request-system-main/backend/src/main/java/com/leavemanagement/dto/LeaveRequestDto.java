package com.leavemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO for submitting a new leave request.
 * Contains the details of the leave being applied for.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDto {

    @NotBlank(message = "Leave type is required")
    private String leaveType;   // SICK, CASUAL, PERSONAL, EMERGENCY

    @NotBlank(message = "Start date is required")
    private String startDate;   // Format: yyyy-MM-dd

    @NotBlank(message = "End date is required")
    private String endDate;     // Format: yyyy-MM-dd

    @NotBlank(message = "Reason is required")
    private String reason;
}
