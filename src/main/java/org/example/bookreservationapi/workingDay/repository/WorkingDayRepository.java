package org.example.bookreservationapi.workingDay.repository;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.workingDay.enity.WorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, Long> {
    List<WorkingDay> findByEmployee(EmployeeEntity employee);
}
