package com.leavemanagement.controller;

import com.leavemanagement.dto.ApiResponse;
import com.leavemanagement.dto.DashboardStats;
import com.leavemanagement.dto.LeaveResponse;
import com.leavemanagement.dto.RemarkRequest;
import com.leavemanagement.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/leaves")
    public ResponseEntity<ApiResponse> getAllLeaves() {
        List<LeaveResponse> leaves = leaveService.getAllLeaves();
        return ResponseEntity.ok(ApiResponse.success("All leave requests retrieved successfully", leaves));
    }

    @GetMapping("/leaves/pending")
    public ResponseEntity<ApiResponse> getPendingLeaves() {
        List<LeaveResponse> leaves = leaveService.getPendingLeaves();
        return ResponseEntity.ok(ApiResponse.success("Pending leave requests retrieved successfully", leaves));
    }

    @PutMapping("/leaves/{id}/approve")
    public ResponseEntity<ApiResponse> approveLeave(@PathVariable Long id, @RequestBody(required = false) RemarkRequest remark) {
        LeaveResponse response = leaveService.approveLeave(id, remark);
        return ResponseEntity.ok(ApiResponse.success("Leave request approved successfully", response));
    }

    @PutMapping("/leaves/{id}/reject")
    public ResponseEntity<ApiResponse> rejectLeave(@PathVariable Long id, @RequestBody(required = false) RemarkRequest remark) {
        LeaveResponse response = leaveService.rejectLeave(id, remark);
        return ResponseEntity.ok(ApiResponse.success("Leave request rejected successfully", response));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> getAdminDashboardStats() {
        DashboardStats stats = leaveService.getAdminDashboardStats();
        return ResponseEntity.ok(ApiResponse.success("Admin dashboard stats retrieved successfully", stats));
    }
}
