package com.zen.hr.controller;

import com.zen.hr.entity.EmployeeAttendance;
import com.zen.notify.dto.ApiResponse;
import com.zen.hr.service.EmployeeAttendanceService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class EmployeeAttendanceController {

    @Autowired
    private EmployeeAttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeAttendance>> markAttendance(@RequestBody EmployeeAttendance attendance, HttpServletRequest request) {
        EmployeeAttendance saved = attendanceService.markAttendance(attendance);
        return ResponseEntity.ok(ApiResponse.success(saved, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<EmployeeAttendance>>> getAttendanceByEmployee(
            @PathVariable Long employeeId, HttpServletRequest request) {
        List<EmployeeAttendance> attendances = attendanceService.getAttendanceByEmployee(employeeId);
        return ResponseEntity.ok(ApiResponse.success(attendances, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}/date/{date}")
    public ResponseEntity<ApiResponse<List<EmployeeAttendance>>> getAttendanceByEmployeeAndDate(
            @PathVariable Long employeeId, @PathVariable String date, HttpServletRequest request) {
        LocalDate attendanceDate = LocalDate.parse(date);
        List<EmployeeAttendance> attendances = attendanceService.getAttendanceByEmployeeAndDate(employeeId, attendanceDate);
        return ResponseEntity.ok(ApiResponse.success(attendances, request.getRequestURI()));
    }
}
