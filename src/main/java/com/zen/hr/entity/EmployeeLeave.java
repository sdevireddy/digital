package com.zen.hr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_leave")
@Data
public class EmployeeLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum LeaveType {
        CASUAL,      // General day-to-day reasons
        SICK,        // Illness-related leave
        ANNUAL,      // Planned yearly leave/vacation
        MATERNITY,   // Maternity leave (applicable for female employees)
        PATERNITY,   // Paternity leave (applicable for male employees)
        COMP_OFF,    // Compensation off (earned by working extra)
        UNPAID,      // Leave without pay
        EARNED,      // Earned leave based on tenure
        BEREAVEMENT, // Leave for death in the family
        MARRIAGE,    // Marriage leave
        STUDY,       // Educational or study leave
        RELOCATION   // Leave for relocation or moving
    }
    public enum Status {
        PENDING, APPROVED, REJECTED
    }
}

