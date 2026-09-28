package com.leavemanagement.repository;

import com.leavemanagement.entity.LeaveRequest;
import com.leavemanagement.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for LeaveRequest entity.
 * Contains custom query methods for filtering and counting.
 */
@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Get all leave requests for a specific user (ordered by applied date, newest first)
    List<LeaveRequest> findByUserIdOrderByAppliedDateDesc(Long userId);

    // Get all leave requests with a specific status
    List<LeaveRequest> findByStatusOrderByAppliedDateDesc(LeaveStatus status);

    // Get all leave requests ordered by applied date
    List<LeaveRequest> findAllByOrderByAppliedDateDesc();

    // Count leave requests by status (for admin dashboard)
    long countByStatus(LeaveStatus status);

    // Count leave requests by user and status (for user dashboard)
    long countByUserIdAndStatus(Long userId, LeaveStatus status);

    // Count total leave requests by user
    long countByUserId(Long userId);
}
