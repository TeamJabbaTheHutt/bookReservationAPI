package org.example.bookreservationapi.workingDay.service;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.service.EmployeeService;
import org.example.bookreservationapi.workingDay.enity.WorkingDay;
import org.example.bookreservationapi.workingDay.repository.WorkingDayRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingDayService {

    private WorkingDayRepository workingDayRepository;
    private EmployeeService employeeService;

    public WorkingDayService(WorkingDayRepository workingDayRepository, EmployeeService employeeService) {
        this.workingDayRepository = workingDayRepository;
        this.employeeService = employeeService;
    }

    public List<WorkingDay> getMyWorkingDays(Authentication authentication) {
        EmployeeEntity employee = getCurrentEmployee(authentication);
        return workingDayRepository.findByEmployee(employee);
    }

    public WorkingDay createWorkingDay(WorkingDay workingDay, Authentication authentication) {
        EmployeeEntity employee = getCurrentEmployee(authentication);
        workingDay.setEmployee(employee);
        return workingDayRepository.save(workingDay);
    }

    public WorkingDay updateWorkingDay(Long workingDayId, WorkingDay updatedData, Authentication authentication) {
        EmployeeEntity employee = getCurrentEmployee(authentication);
        WorkingDay existing = workingDayRepository.findById(workingDayId)
                .orElseThrow(() -> new RuntimeException("Working day not found: " + workingDayId));

        if (!existing.getEmployee().getId().equals(employee.getId())) {
            throw new RuntimeException("You can only update your own working days");
        }

        existing.setStartDateTime(updatedData.getStartDateTime());
        existing.setEndDateTime(updatedData.getEndDateTime());
        return workingDayRepository.save(existing);
    }

    public void deleteWorkingDay(Long workingDayId, Authentication authentication) {
        EmployeeEntity employee = getCurrentEmployee(authentication);
        WorkingDay existing = workingDayRepository.findById(workingDayId)
                .orElseThrow(() -> new RuntimeException("Working day not found: " + workingDayId));

        if (!existing.getEmployee().getId().equals(employee.getId())) {
            throw new RuntimeException("You can only delete your own working days");
        }

        workingDayRepository.delete(existing);
    }

    private EmployeeEntity getCurrentEmployee(Authentication authentication) {
        String username = authentication.getName();
        return employeeService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + username));
    }
}