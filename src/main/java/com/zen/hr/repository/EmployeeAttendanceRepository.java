package com.zen.hr.repository;

import com.zen.hr.entity.EmployeeAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendance, Long> {
        List<EmployeeAttendance> findByEmployeeId(Long employeeId);
        List<EmployeeAttendance> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);
        List<EmployeeAttendance> findByEmployeeIdAndAttendanceDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
    }



