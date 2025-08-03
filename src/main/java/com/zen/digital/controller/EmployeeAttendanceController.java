package com.zen.digital.controller;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import com.zen.digital.dto.ApiResponse;
import com.zen.digital.entity.EmployeeAttendance;
import com.zen.digital.service.EmployeeAttendanceService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr/attendance")
public class EmployeeAttendanceController {

    @Autowired
    private EmployeeAttendanceService attendanceService;

    @PostMapping("/attand")
    public ResponseEntity<ApiResponse<EmployeeAttendance>> markAttendance(
            @RequestBody EmployeeAttendance attendance,
            HttpServletRequest request) {

        if (attendance.getEmployee() == null) {
            return buildErrorResponse(request, 400, "Employee ID is required");
        }
        if (attendance.getAttendanceDate() == null) {
            return buildErrorResponse(request, 400, "Attendance date is required");
        }

        return ok(attendanceService.markAttendance(attendance), request);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<EmployeeAttendance>>> getAttendanceByEmployee(
            @PathVariable Long employeeId,
            HttpServletRequest request) {

        if (employeeId <= 0) {
            return buildErrorResponse(request, 400, "Invalid employee ID");
        }

        return ok(attendanceService.getAttendanceByEmployee(employeeId), request);
    }

    @GetMapping("/employee/{employeeId}/date/{date}")
    public ResponseEntity<ApiResponse<List<EmployeeAttendance>>> getAttendanceByEmployeeAndDate(
            @PathVariable Long employeeId,
            @PathVariable String date,
            HttpServletRequest request) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            return ok(attendanceService.getAttendanceByEmployeeAndDate(employeeId, parsedDate), request);
        } catch (Exception e) {
            return buildErrorResponse(request, 400, "Invalid date format, expected yyyy-MM-dd");
        }
    }

    @GetMapping("/employee/{employeeId}/month/{year}/{month}")
    public ResponseEntity<ApiResponse<List<EmployeeAttendance>>> getMonthlyAttendance(
            @PathVariable Long employeeId,
            @PathVariable int year,
            @PathVariable int month,
            HttpServletRequest request) {

        if (month < 1 || month > 12) {
            return buildErrorResponse(request, 400, "Invalid month, should be between 1-12");
        }

        return ok(attendanceService.getMonthlyAttendance(employeeId, year, month), request);
    }

    @GetMapping("/employee/{employeeId}/quarter/{year}/{quarter}")
    public ResponseEntity<ApiResponse<List<EmployeeAttendance>>> getQuarterlyAttendance(
            @PathVariable Long employeeId,
            @PathVariable int year,
            @PathVariable int quarter,
            HttpServletRequest request) {

        if (quarter < 1 || quarter > 4) {
            return buildErrorResponse(request, 400, "Invalid quarter, should be between 1-4");
        }

        return ok(attendanceService.getQuarterlyAttendance(employeeId, year, quarter), request);
    }

    @GetMapping("/employee/{employeeId}/year/{year}")
    public ResponseEntity<ApiResponse<List<EmployeeAttendance>>> getYearlyAttendance(
            @PathVariable Long employeeId,
            @PathVariable int year,
            HttpServletRequest request) {
        return ok(attendanceService.getYearlyAttendance(employeeId, year), request);
    }

    @GetMapping("/employee/{employeeId}/attendance-summary")
    public ResponseEntity<ApiResponse<List<EmployeeAttendance>>> getAttendanceSummary(
            @PathVariable Long employeeId,
            @RequestParam String type,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer quarter,
            HttpServletRequest request) {

        List<EmployeeAttendance> attendances;

        try {
            switch (type.toLowerCase()) {
                case "day" -> {
                    if (date == null) {
                        return buildErrorResponse(request, 400, "Date parameter is required for daily summary");
                    }
                    attendances = attendanceService.getAttendanceByEmployeeAndDate(employeeId, LocalDate.parse(date));
                }
                case "month" -> {
                    if (year == null || month == null || month < 1 || month > 12) {
                        return buildErrorResponse(request, 400, "Year and valid month are required for monthly summary");
                    }
                    attendances = attendanceService.getMonthlyAttendance(employeeId, year, month);
                }
                case "quarter" -> {
                    if (year == null || quarter == null || quarter < 1 || quarter > 4) {
                        return buildErrorResponse(request, 400, "Year and valid quarter are required for quarterly summary");
                    }
                    attendances = attendanceService.getQuarterlyAttendance(employeeId, year, quarter);
                }
                case "year" -> {
                    if (year == null) {
                        return buildErrorResponse(request, 400, "Year is required for yearly summary");
                    }
                    attendances = attendanceService.getYearlyAttendance(employeeId, year);
                }
                default -> {
                    return buildErrorResponse(request, 400, "Invalid type: " + type);
                }
            }
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Internal error while processing attendance summary: " + e.getMessage());
        }

        return ok(attendances, request);
    }

    // ✅ Utility for success responses
    private <T> ResponseEntity<ApiResponse<T>> ok(T data, HttpServletRequest request) {
        long totalRecords = (data instanceof List<?> list) ? list.size() : 1;
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .timestamp(ZonedDateTime.now())
                        .status(200)
                        .path(request.getRequestURI())
                        .data(data)
                        .totalRecords(totalRecords)
                        .pageSize(0)
                        .currentPage(0)
                        .totalPages(0)
                        .error(null)
                        .build()
        );
    }

    // ✅ Utility for error responses in ApiResponse format
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
