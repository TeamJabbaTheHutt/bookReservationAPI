package org.example.bookreservationapi.employee.repository;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface employeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
