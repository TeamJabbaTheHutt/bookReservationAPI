package org.example.bookreservationapi.treament.controller;


import org.apache.coyote.Response;
import org.example.bookreservationapi.treament.entity.Treatment;
import org.example.bookreservationapi.treament.service.TreatmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/treatment")
public class TreatmentController {



    private TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping()
    public ResponseEntity<List<Treatment>> getAllTreatments() {
        return ResponseEntity.ok(treatmentService.getAllTreatments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treatment> getTreatmentById(@PathVariable Long id) {
        Optional<Treatment> treatment = treatmentService.getTreatmentById(id);

        if (!treatment.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(treatment.get());
        }
    }

    @PostMapping()
    public ResponseEntity<Treatment> createTreatment(@RequestBody Treatment treatment) {
        return ResponseEntity.ok(treatmentService.createNewTreatment(treatment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treatment> updateTreatment(@PathVariable Long id ,@RequestBody Treatment treatment) {
        Optional<Treatment> updated = treatmentService.updateTreatmentById(id, treatment);

        if (!updated.isPresent()) {
            return ResponseEntity.internalServerError().build();
        } else {
            return ResponseEntity.ok(updated.get());
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAllTreatments() {
        boolean deleted = treatmentService.deleteAllTreatments();

        if (deleted) {
            return ResponseEntity.ok("Deleted All Treatments");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete treatments");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        boolean deleted = treatmentService.deleteTreatmentById(id);
        if (deleted) {
            return ResponseEntity.ok("Deleted Treatment with id: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to deleted treatment with id: " + id);
        }
    }
}
