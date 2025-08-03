package com.zen.digital.service;

import java.util.List;

import com.zen.digital.entity.EmployeeLeave;

public interface EmployeeLeaveService {
    EmployeeLeave applyLeave(EmployeeLeave leave);
    List<EmployeeLeave> getLeavesByEmployee(Long employeeId);
    EmployeeLeave approveLeave(Long leaveId);
    EmployeeLeave rejectLeave(Long leaveId);
}
