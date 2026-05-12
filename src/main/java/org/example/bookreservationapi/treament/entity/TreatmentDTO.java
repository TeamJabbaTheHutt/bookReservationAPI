package org.example.bookreservationapi.treament.entity;

public record TreatmentDTO(
        Long treatmentId,
        String title,
        double durationMinutes
) {
}