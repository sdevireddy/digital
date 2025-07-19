package com.zen.hr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zen.hr.entity.EmployeeLeave;
import com.zen.hr.repository.EmployeeLeaveRepository;

@Service
public class EmployeeLeaveServiceImpl implements EmployeeLeaveService {

    @Autowired
    private EmployeeLeaveRepository leaveRepository;

    @Override
    public EmployeeLeave applyLeave(EmployeeLeave leave) {
        return leaveRepository.save(leave);
    }

    @Override
    public List<EmployeeLeave> getLeavesByEmployee(Long employeeId) {
        return leaveRepository.findByEmployee_Id(employeeId);
    }

    @Override
    @Transactional
    public EmployeeLeave approveLeave(Long leaveId) {
        EmployeeLeave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + leaveId));
        leave.setStatus(EmployeeLeave.Status.APPROVED);
        return leaveRepository.save(leave);
    }

    @Override
    @Transactional
    public EmployeeLeave rejectLeave(Long leaveId) {
        EmployeeLeave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + leaveId));
        leave.setStatus(EmployeeLeave.Status.REJECTED);
        return leaveRepository.save(leave);
    }
}

