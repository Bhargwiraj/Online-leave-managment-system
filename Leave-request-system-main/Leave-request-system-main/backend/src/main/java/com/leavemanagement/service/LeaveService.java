package com.leavemanagement.service;

import com.leavemanagement.dto.DashboardStats;
import com.leavemanagement.dto.LeaveRequestDto;
import com.leavemanagement.dto.LeaveResponse;
import com.leavemanagement.dto.RemarkRequest;
import com.leavemanagement.entity.LeaveRequest;
import com.leavemanagement.entity.User;
import com.leavemanagement.enums.LeaveStatus;
import com.leavemanagement.enums.LeaveType;
import com.leavemanagement.exception.BadRequestException;
import com.leavemanagement.exception.ResourceNotFoundException;
import com.leavemanagement.repository.LeaveRequestRepository;
import com.leavemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public LeaveResponse applyLeave(String email, LeaveRequestDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(dto.getStartDate());
            endDate = LocalDate.parse(dto.getEndDate());
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date format. Expected yyyy-MM-dd");
        }

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date");
        }
        
        if (startDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot apply for leave in the past");
        }

        LeaveType leaveType;
        try {
            leaveType = LeaveType.valueOf(dto.getLeaveType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid leave type");
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUser(user);
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setReason(dto.getReason());

        LeaveRequest savedRequest = leaveRequestRepository.save(leaveRequest);
        return mapToResponse(savedRequest);
    }

    public List<LeaveResponse> getMyLeaves(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        List<LeaveRequest> requests = leaveRequestRepository.findByUserIdOrderByAppliedDateDesc(user.getId());
        return requests.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public LeaveResponse cancelLeave(String email, Long leaveId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        LeaveRequest request = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found!"));

        if (!request.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You can only cancel your own leave requests");
        }

        if (request.getStatus() != LeaveStatus.PENDING) {
            throw new BadRequestException("Can only cancel PENDING leave requests");
        }

        request.setStatus(LeaveStatus.CANCELLED);
        LeaveRequest updated = leaveRequestRepository.save(request);
        return mapToResponse(updated);
    }

    public List<LeaveResponse> getAllLeaves() {
        return leaveRequestRepository.findAllByOrderByAppliedDateDesc().stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<LeaveResponse> getPendingLeaves() {
        return leaveRequestRepository.findByStatusOrderByAppliedDateDesc(LeaveStatus.PENDING).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    public LeaveResponse approveLeave(Long leaveId, RemarkRequest remark) {
        LeaveRequest request = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found!"));

        if (request.getStatus() != LeaveStatus.PENDING) {
            throw new BadRequestException("Only PENDING requests can be approved");
        }

        request.setStatus(LeaveStatus.APPROVED);
        if (remark != null && remark.getRemark() != null) {
            request.setAdminRemark(remark.getRemark());
        }

        LeaveRequest updated = leaveRequestRepository.save(request);
        return mapToResponse(updated);
    }

    public LeaveResponse rejectLeave(Long leaveId, RemarkRequest remark) {
        LeaveRequest request = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found!"));

        if (request.getStatus() != LeaveStatus.PENDING) {
            throw new BadRequestException("Only PENDING requests can be rejected");
        }

        request.setStatus(LeaveStatus.REJECTED);
        if (remark != null && remark.getRemark() != null) {
            request.setAdminRemark(remark.getRemark());
        }

        LeaveRequest updated = leaveRequestRepository.save(request);
        return mapToResponse(updated);
    }

    public DashboardStats getUserDashboardStats(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Long userId = user.getId();
        long total = leaveRequestRepository.countByUserId(userId);
        long pending = leaveRequestRepository.countByUserIdAndStatus(userId, LeaveStatus.PENDING);
        long approved = leaveRequestRepository.countByUserIdAndStatus(userId, LeaveStatus.APPROVED);
        long rejected = leaveRequestRepository.countByUserIdAndStatus(userId, LeaveStatus.REJECTED);
        
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("pending", pending);
        stats.put("approved", approved);
        stats.put("rejected", rejected);
        
        return DashboardStats.builder().stats(stats).build();
    }

    public DashboardStats getAdminDashboardStats() {
        long total = leaveRequestRepository.count();
        long pending = leaveRequestRepository.countByStatus(LeaveStatus.PENDING);
        long approved = leaveRequestRepository.countByStatus(LeaveStatus.APPROVED);
        long rejected = leaveRequestRepository.countByStatus(LeaveStatus.REJECTED);

        Map<String, Long> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("pending", pending);
        stats.put("approved", approved);
        stats.put("rejected", rejected);

        return DashboardStats.builder().stats(stats).build();
    }

    private LeaveResponse mapToResponse(LeaveRequest request) {
        return LeaveResponse.builder()
                .id(request.getId())
                .userName(request.getUser().getName())
                .userEmail(request.getUser().getEmail())
                .leaveType(request.getLeaveType().name())
                .startDate(request.getStartDate().toString())
                .endDate(request.getEndDate().toString())
                .reason(request.getReason())
                .status(request.getStatus().name())
                .adminRemark(request.getAdminRemark())
                .appliedDate(request.getAppliedDate().toString())
                .build();
    }
}
