package com.zen.digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zen.digital.entity.EmployeeAttendance;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendance, Long> {
        List<EmployeeAttendance> findByEmployeeId(Long employeeId);
        List<EmployeeAttendance> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);
        List<EmployeeAttendance> findByEmployeeIdAndAttendanceDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
    }



