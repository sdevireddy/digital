package com.zen.digital.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zen.digital.entity.Employee;

public interface EmployeeService {
    Page<Employee> getAllEmployees(Pageable pageable);
    Employee getEmployeeById(Long id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
}

