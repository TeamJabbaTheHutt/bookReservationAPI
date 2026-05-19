package org.example.bookreservationapi.treatment;



import org.example.bookreservationapi.treament.entity.Treatment;
import org.example.bookreservationapi.treament.repository.TreatmentRepository;
import org.example.bookreservationapi.treament.service.TreatmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@ActiveProfiles("dev")
class TreatmentServiceTest {

    private TreatmentRepository treatmentRepository;
    private TreatmentService treatmentService;

    @BeforeEach
    void setUp() {
        treatmentRepository = Mockito.mock(TreatmentRepository.class);
        treatmentService = new TreatmentService(treatmentRepository);
    }

    @Test
    void getAllTreatments_returnsList() {
        Treatment treatment = new Treatment();

        when(treatmentRepository.findAll())
                .thenReturn(List.of(treatment));

        List<Treatment> result = treatmentService.getAllTreatments();

        assertEquals(1, result.size());
    }

    @Test
    void getTreatmentById_returnsTreatment() {
        Treatment treatment = new Treatment();

        when(treatmentRepository.findById(1L))
                .thenReturn(Optional.of(treatment));

        Optional<Treatment> result = treatmentService.getTreatmentById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void createNewTreatment_savesTreatment() {
        Treatment treatment = new Treatment();

        when(treatmentRepository.save(treatment))
                .thenReturn(treatment);

        Treatment result = treatmentService.createNewTreatment(treatment);

        assertEquals(treatment, result);
    }

    @Test
    void updateTreatmentById_updatesTreatment() {
        Treatment existing = new Treatment();
        existing.setTitle("Old");

        Treatment updated = new Treatment();
        updated.setTitle("New");

        when(treatmentRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(treatmentRepository.save(existing))
                .thenReturn(existing);

        Optional<Treatment> result =
                treatmentService.updateTreatmentById(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("New", result.get().getTitle());
    }

    @Test
    void deleteTreatmentById_returnsTrue() {
        doNothing().when(treatmentRepository).deleteById(1L);

        when(treatmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        boolean result = treatmentService.deleteTreatmentById(1L);

        assertTrue(result);
    }

    @Test
    void deleteAllTreatments_returnsTrue() {
        doNothing().when(treatmentRepository).deleteAll();

        when(treatmentRepository.findAll())
                .thenReturn(List.of());

        boolean result = treatmentService.deleteAllTreatments();

        assertTrue(result);
    }
}