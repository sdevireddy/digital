package com.zen.hr.controller;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zen.hr.entity.Employee;
import com.zen.notify.dto.ApiResponse;
import com.zen.notify.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // ✅ Get all employees with pagination
    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeService.getAllEmployees(pageable);

        ApiResponse<List<Employee>> response = ApiResponse.<List<Employee>>builder()
                .timestamp(ZonedDateTime.now())
                .status(200)
                .path(request.getRequestURI())
                .data(employeePage.getContent())
                .totalRecords(employeePage.getTotalElements())
                .pageSize(employeePage.getSize())
                .currentPage(employeePage.getNumber())
                .totalPages(employeePage.getTotalPages())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Long id, HttpServletRequest request) {
        Employee employee = employeeService.getEmployeeById(id);
        ApiResponse<Employee> response = ApiResponse.<Employee>builder()
                .timestamp(ZonedDateTime.now())
                .status(200)
                .path(request.getRequestURI())
                .data(employee)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee, HttpServletRequest request) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        ApiResponse<Employee> response = ApiResponse.<Employee>builder()
                .timestamp(ZonedDateTime.now())
                .status(200)
                .path(request.getRequestURI())
                .data(savedEmployee)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee,
            HttpServletRequest request) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        ApiResponse<Employee> response = ApiResponse.<Employee>builder()
                .timestamp(ZonedDateTime.now())
                .status(200)
                .path(request.getRequestURI())
                .data(updatedEmployee)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id, HttpServletRequest request) {
        employeeService.deleteEmployee(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(ZonedDateTime.now())
                .status(204)
                .path(request.getRequestURI())
                .data(null)
                .build();
        return ResponseEntity.status(204).body(response);
    }
}
