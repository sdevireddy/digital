package com.zen.hr.service;

import com.zen.hr.entity.EmployeeAttendance;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeAttendanceService {
    EmployeeAttendance markAttendance(EmployeeAttendance attendance);

    List<EmployeeAttendance> getAttendanceByEmployee(Long employeeId);
    List<EmployeeAttendance> getAttendanceByEmployeeAndDate(Long employeeId, LocalDate date);
    List<EmployeeAttendance> getMonthlyAttendance(Long employeeId, int year, int month);
    List<EmployeeAttendance> getQuarterlyAttendance(Long employeeId, int year, int quarter);
    List<EmployeeAttendance> getYearlyAttendance(Long employeeId, int year);
}
