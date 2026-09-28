package com.leavemanagement.controller;

import com.leavemanagement.dto.ApiResponse;
import com.leavemanagement.dto.DashboardStats;
import com.leavemanagement.dto.LeaveRequestDto;
import com.leavemanagement.dto.LeaveResponse;
import com.leavemanagement.dto.UserResponse;
import com.leavemanagement.service.LeaveService;
import com.leavemanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private UserService userService;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse> applyLeave(@Valid @RequestBody LeaveRequestDto requestDto, Authentication authentication) {
        LeaveResponse response = leaveService.applyLeave(authentication.getName(), requestDto);
        return ResponseEntity.ok(ApiResponse.success("Leave request submitted successfully", response));
    }

    @GetMapping("/my-leaves")
    public ResponseEntity<ApiResponse> getMyLeaves(Authentication authentication) {
        List<LeaveResponse> leaves = leaveService.getMyLeaves(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("Leaves retrieved successfully", leaves));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse> cancelLeave(@PathVariable Long id, Authentication authentication) {
        LeaveResponse response = leaveService.cancelLeave(authentication.getName(), id);
        return ResponseEntity.ok(ApiResponse.success("Leave request cancelled successfully", response));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> getDashboardStats(Authentication authentication) {
        DashboardStats stats = leaveService.getUserDashboardStats(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("Dashboard stats retrieved successfully", stats));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getUserProfile(Authentication authentication) {
        UserResponse profile = userService.getUserProfile(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("Profile retrieved successfully", profile));
    }
}
