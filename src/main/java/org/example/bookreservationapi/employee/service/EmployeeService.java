package org.example.bookreservationapi.employee.service;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.repository.EmployeeRepository;
import org.example.bookreservationapi.treament.entity.TreatmentDTO;
import org.example.bookreservationapi.treament.service.TreatmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TreatmentService treatmentService;

    public EmployeeService(EmployeeRepository employeeRepository, TreatmentService treatmentService) {
        this.employeeRepository = employeeRepository;
        this.treatmentService = treatmentService;
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

    public Optional<EmployeeEntity> findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    public List<TreatmentDTO> getTreatmentsForEmployee(Long employeeId) {
        return treatmentService.findByEmployeeId(employeeId);
    }
}