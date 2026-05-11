package org.example.bookreservationapi.treament.service;


import org.example.bookreservationapi.treament.entity.Treatment;
import org.example.bookreservationapi.treament.repository.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {
    private TreatmentRepository treatmentRepository;

    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }
    public Optional<Treatment> getTreatmentById(Long id) {
        return treatmentRepository.findById(id);
    }

    public Treatment createNewTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public Optional<Treatment> updateTreatmentById(Long id, Treatment treatment) {
        return treatmentRepository.findById(id).map(existing -> {
            existing.setDurationMinutes(treatment.getDurationMinutes());
            existing.setPrice(treatment.getPrice());
            existing.setTitle(treatment.getTitle());
            return treatmentRepository.save(existing);
        });
    }


    public boolean deleteTreatmentById(Long id) {
        treatmentRepository.deleteById(id);
        if (treatmentRepository.findById(id).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAllTreatments() {
        treatmentRepository.deleteAll();
        if (treatmentRepository.findAll().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


}
