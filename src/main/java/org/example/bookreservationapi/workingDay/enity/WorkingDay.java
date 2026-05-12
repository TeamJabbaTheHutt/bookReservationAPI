package org.example.bookreservationapi.workingDay.enity;

import jakarta.persistence.*;
import org.example.bookreservationapi.employee.entity.EmployeeEntity;

import java.time.LocalDateTime;

@Entity
public class WorkingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workingDayId;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    public WorkingDay() {
    }

    public WorkingDay(Long workingDayId, LocalDateTime startDateTime, LocalDateTime endDateTime, EmployeeEntity employee) {
        this.workingDayId = workingDayId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.employee = employee;
    }


    public Long getWorkingDayId() {
        return workingDayId;
    }

    public void setWorkingDayId(Long workingDayId) {
        this.workingDayId = workingDayId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
