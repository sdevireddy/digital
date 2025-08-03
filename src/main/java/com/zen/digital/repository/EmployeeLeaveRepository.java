package com.zen.digital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zen.digital.entity.EmployeeLeave;

@Repository
public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, Long> {
	List<EmployeeLeave> findByEmployee_Id(Long employeeId);
}
