package com.leavemanagement.enums;

/**
 * Enum for leave request status.
 * PENDING - Leave request is waiting for admin action.
 * APPROVED - Leave request has been approved by admin.
 * REJECTED - Leave request has been rejected by admin.
 * CANCELLED - Leave request was cancelled by the user.
 */
public enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED
}
