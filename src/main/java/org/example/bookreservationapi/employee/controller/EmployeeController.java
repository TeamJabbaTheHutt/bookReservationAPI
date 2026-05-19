package org.example.bookreservationapi.employee.controller;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.service.EmployeeService;
import org.example.bookreservationapi.treament.entity.TreatmentDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public record EmployeeResponse(String username, List<String> roles) {}

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping
    public List<EmployeeEntity> getAllEmployees() {
        return employeeService.getAllEmployees();
    }


    @GetMapping("/{id}")
    public EmployeeEntity getEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id);
    }


    @GetMapping("/employee")
    public EmployeeResponse getEmployee(Authentication authentication) {
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new EmployeeResponse(username, roles);
    }

    @PostMapping
    public EmployeeEntity createEmployee(
            @RequestBody EmployeeEntity employee) {

        return employeeService.saveEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/{id}/treatments")
    public List<TreatmentDTO> getTreatmentsForEmployee(@PathVariable Long id) {
        return employeeService.getTreatmentsForEmployee(id);
    }


}
