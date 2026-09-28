package com.leavemanagement.entity;

import com.leavemanagement.enums.LeaveStatus;
import com.leavemanagement.enums.LeaveType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * LeaveRequest Entity - Maps to the "leave_requests" table in the database.
 * Stores all leave applications submitted by users.
 */
@Entity
@Table(name = "leave_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {

    // Primary key - auto-generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who applied for this leave (Many leaves belong to One user)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Type of leave: SICK, CASUAL, PERSONAL, EMERGENCY
    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type", nullable = false)
    private LeaveType leaveType;

    // Start date of the leave
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // End date of the leave
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    // Reason for taking leave
    @Column(nullable = false, length = 500)
    private String reason;

    // Current status: PENDING, APPROVED, REJECTED, CANCELLED
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status;

    // Remark added by admin while approving/rejecting
    @Column(name = "admin_remark")
    private String adminRemark;

    // Date when the leave was applied
    @Column(name = "applied_date", nullable = false)
    private LocalDate appliedDate;

    /**
     * Automatically set the applied date and status before saving.
     */
    @PrePersist
    public void prePersist() {
        this.appliedDate = LocalDate.now();
        this.status = LeaveStatus.PENDING;
    }
}
