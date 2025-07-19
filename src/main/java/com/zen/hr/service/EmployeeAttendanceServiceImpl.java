package com.zen.hr.service;

import com.zen.hr.entity.EmployeeAttendance;
import com.zen.hr.repository.EmployeeAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import com.zen.hr.entity.EmployeeAttendance;
import com.zen.hr.repository.EmployeeAttendanceRepository;
import com.zen.hr.service.EmployeeAttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeAttendanceServiceImpl implements EmployeeAttendanceService {

    @Autowired
    private EmployeeAttendanceRepository attendanceRepository;

    @Override
    public EmployeeAttendance markAttendance(EmployeeAttendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<EmployeeAttendance> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeAttendance> getAttendanceByEmployeeAndDate(Long employeeId, LocalDate date) {
        return attendanceRepository.findByEmployeeIdAndAttendanceDate(employeeId, date);
    }

    @Override
    public List<EmployeeAttendance> getMonthlyAttendance(Long employeeId, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return attendanceRepository.findByEmployeeIdAndAttendanceDateBetween(employeeId, start, end);
    }

    @Override
    public List<EmployeeAttendance> getQuarterlyAttendance(Long employeeId, int year, int quarter) {
        int startMonth = (quarter - 1) * 3 + 1;
        LocalDate start = LocalDate.of(year, startMonth, 1);
        LocalDate end = start.plusMonths(2).withDayOfMonth(start.plusMonths(2).lengthOfMonth());
        return attendanceRepository.findByEmployeeIdAndAttendanceDateBetween(employeeId, start, end);
    }

    @Override
    public List<EmployeeAttendance> getYearlyAttendance(Long employeeId, int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        return attendanceRepository.findByEmployeeIdAndAttendanceDateBetween(employeeId, start, end);
    }
}

