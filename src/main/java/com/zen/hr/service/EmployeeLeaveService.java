package com.zen.hr.service;

import java.util.List;

import com.zen.hr.entity.EmployeeLeave;

public interface EmployeeLeaveService {
    EmployeeLeave applyLeave(EmployeeLeave leave);
    List<EmployeeLeave> getLeavesByEmployee(Long employeeId);
    EmployeeLeave approveLeave(Long leaveId);
    EmployeeLeave rejectLeave(Long leaveId);
}
