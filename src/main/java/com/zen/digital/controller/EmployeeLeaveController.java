package com.zen.digital.controller;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zen.digital.dto.ApiResponse;
import com.zen.digital.entity.EmployeeLeave;
import com.zen.digital.entity.EmployeeLeave.Status;
import com.zen.digital.service.EmployeeLeaveService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hr/leaves")
public class EmployeeLeaveController {

    @Autowired
    private EmployeeLeaveService leaveService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeLeave>> applyLeave(
            @RequestBody EmployeeLeave leave,
            HttpServletRequest request) {

    	// 🔍 Validate Employee
    	// 🔍 Validate Employee
    	if (leave.getEmployee() == null || leave.getEmployee().getId() == null) {
    	    return buildErrorResponse(request, 400, "Employee information is missing or invalid.");
    	}

    	// 🔍 Validate Dates
    	if (leave.getFromDate() == null) {
    	    return buildErrorResponse(request, 400, "From date is required.");
    	}
    	if (leave.getToDate() == null) {
    	    return buildErrorResponse(request, 400, "To date is required.");
    	}
    	if (leave.getToDate().isBefore(leave.getFromDate())) {
    	    return buildErrorResponse(request, 400, "To date cannot be earlier than from date.");
    	}

    	// 🔍 Validate Leave Type
    	if (leave.getLeaveType() == null) {
    	    return buildErrorResponse(request, 400, "Leave type must be specified.");
    	}


        EmployeeLeave saved = leaveService.applyLeave(leave);
        return ResponseEntity.ok(ApiResponse.success(saved, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<EmployeeLeave>>> getLeavesByEmployee(
            @PathVariable Long employeeId,
            HttpServletRequest request) {

        if (employeeId == null || employeeId <= 0) {
            return buildErrorResponse(request, 400, "Invalid employee ID.");
        }

        List<EmployeeLeave> leaves = leaveService.getLeavesByEmployee(employeeId);
        return ResponseEntity.ok(ApiResponse.success(leaves, request.getRequestURI()));
    }

    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<ApiResponse<EmployeeLeave>> approveLeave(
            @PathVariable Long leaveId,
            HttpServletRequest request) {

        if (leaveId == null || leaveId <= 0) {
            return buildErrorResponse(request, 400, "Invalid leave ID.");
        }

        EmployeeLeave approved = leaveService.approveLeave(leaveId);
        return ResponseEntity.ok(ApiResponse.success(approved, request.getRequestURI()));
    }

    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<ApiResponse<EmployeeLeave>> rejectLeave(
            @PathVariable Long leaveId,
            HttpServletRequest request) {

        if (leaveId == null || leaveId <= 0) {
            return buildErrorResponse(request, 400, "Invalid leave ID.");
        }

        EmployeeLeave rejected = leaveService.rejectLeave(leaveId);
        return ResponseEntity.ok(ApiResponse.success(rejected, request.getRequestURI()));
    }

    // ✅ Standardized Error Response Method
    private <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(HttpServletRequest request, int status, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now())
                .status(status)
                .path(request.getRequestURI())
                .error(message)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
