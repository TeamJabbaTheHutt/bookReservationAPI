package org.example.bookreservationapi.treament.repository;

import org.example.bookreservationapi.treament.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    List<Treatment> findByEmployees_Id(Long employeeId);
}
