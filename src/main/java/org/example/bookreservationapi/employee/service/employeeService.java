package org.example.bookreservationapi.employee.service;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class employeeService {

    private final EmployeeRepository employeeRepository;

    public employeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public EmployeeEntity findById(Long employeeId) {

        return employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));
    }

    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public EmployeeEntity saveEmployee(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }


    public void deleteEmployee(Long employeeId) {

        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found");
        }

        employeeRepository.deleteById(employeeId);
    }
}