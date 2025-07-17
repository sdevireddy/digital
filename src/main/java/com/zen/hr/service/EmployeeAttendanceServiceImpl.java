package com.zen.hr.service;

import com.zen.hr.entity.EmployeeAttendance;
import com.zen.hr.repository.EmployeeAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeAttendanceServiceImpl implements EmployeeAttendanceService {

    @Autowired
    private EmployeeAttendanceRepository repository;

    @Override
    public EmployeeAttendance markAttendance(EmployeeAttendance attendance) {
        return repository.save(attendance);
    }

    @Override
    public List<EmployeeAttendance> getAttendanceByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeAttendance> getAttendanceByEmployeeAndDate(Long employeeId, LocalDate date) {
        return repository.findByEmployeeIdAndAttendanceDate(employeeId, date);
    }
}
