package com.zen.hr.controller;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zen.hr.dto.ApiResponse;
import com.zen.hr.entity.Employee;
import com.zen.hr.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hr/employees")
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
        try {
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
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Failed to fetch employees: " + e.getMessage());
        }
    }

    // ✅ Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            ApiResponse<Employee> response = ApiResponse.<Employee>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(employee)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Employee not found with id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error fetching employee: " + e.getMessage());
        }
    }

    // ✅ Create Employee
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee, HttpServletRequest request) {
        try {
            Employee savedEmployee = employeeService.createEmployee(employee);
            ApiResponse<Employee> response = ApiResponse.<Employee>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(savedEmployee)
                    .build();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return buildErrorResponse(request, 400, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error creating employee: " + e.getMessage());
        }
    }

    // ✅ Update Employee
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee,
            HttpServletRequest request) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            ApiResponse<Employee> response = ApiResponse.<Employee>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(updatedEmployee)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Employee not found with id: " + id);
        } catch (RuntimeException e) {
            return buildErrorResponse(request, 400, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error updating employee: " + e.getMessage());
        }
    }

    // ✅ Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id, HttpServletRequest request) {
        try {
            employeeService.deleteEmployee(id);
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(204)
                    .path(request.getRequestURI())
                    .data(null)
                    .build();
            return ResponseEntity.status(204).body(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Employee not found with id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error deleting employee: " + e.getMessage());
        }
    }

 // 🔧 Common method for error responses
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
