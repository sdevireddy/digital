package com.zen.hr.controller;

import com.zen.hr.entity.EmployeeLeave;
import com.zen.notify.dto.ApiResponse;
import com.zen.hr.service.EmployeeLeaveService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class EmployeeLeaveController {

    @Autowired
    private EmployeeLeaveService leaveService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeLeave>> applyLeave(@RequestBody EmployeeLeave leave, HttpServletRequest request) {
        EmployeeLeave saved = leaveService.applyLeave(leave);
        return ResponseEntity.ok(ApiResponse.success(saved, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<EmployeeLeave>>> getLeavesByEmployee(
            @PathVariable Long employeeId, HttpServletRequest request) {
        List<EmployeeLeave> leaves = leaveService.getLeavesByEmployee(employeeId);
        return ResponseEntity.ok(ApiResponse.success(leaves, request.getRequestURI()));
    }

    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<ApiResponse<EmployeeLeave>> approveLeave(@PathVariable Long leaveId, HttpServletRequest request) {
        EmployeeLeave approved = leaveService.approveLeave(leaveId);
        return ResponseEntity.ok(ApiResponse.success(approved, request.getRequestURI()));
    }

    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<ApiResponse<EmployeeLeave>> rejectLeave(@PathVariable Long leaveId, HttpServletRequest request) {
        EmployeeLeave rejected = leaveService.rejectLeave(leaveId);
        return ResponseEntity.ok(ApiResponse.success(rejected, request.getRequestURI()));
    }
}

