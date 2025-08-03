package com.zen.digital.service;

import java.time.LocalDate;
import java.util.List;

import com.zen.digital.entity.EmployeeAttendance;

public interface EmployeeAttendanceService {
    EmployeeAttendance markAttendance(EmployeeAttendance attendance);

    List<EmployeeAttendance> getAttendanceByEmployee(Long employeeId);
    List<EmployeeAttendance> getAttendanceByEmployeeAndDate(Long employeeId, LocalDate date);
    List<EmployeeAttendance> getMonthlyAttendance(Long employeeId, int year, int month);
    List<EmployeeAttendance> getQuarterlyAttendance(Long employeeId, int year, int quarter);
    List<EmployeeAttendance> getYearlyAttendance(Long employeeId, int year);
}
