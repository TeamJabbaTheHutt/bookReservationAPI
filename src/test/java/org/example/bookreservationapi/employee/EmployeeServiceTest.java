package org.example.bookreservationapi.employee;

import org.example.bookreservationapi.employee.repository.EmployeeRepository;
import org.example.bookreservationapi.employee.service.EmployeeService;
import org.example.bookreservationapi.treament.entity.TreatmentDTO;
import org.example.bookreservationapi.treament.service.TreatmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private TreatmentService treatmentService;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {

        employeeRepository = mock(EmployeeRepository.class);
        treatmentService = mock(TreatmentService.class);

        employeeService =
                new EmployeeService(employeeRepository, treatmentService);
    }

    @Test
    void shouldReturnFilteredTreatmentsForEmployee() {

        Long employeeId = 1L;

        TreatmentDTO t1 = new TreatmentDTO(
                1L,
                "Massage",
                60
        );

        TreatmentDTO t2 = new TreatmentDTO(
                2L,
                "Facial",
                45
        );

        when(treatmentService.findByEmployeeId(employeeId))
                .thenReturn(List.of(t1, t2));

        List<TreatmentDTO> result =
                employeeService.getTreatmentsForEmployee(employeeId);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Massage", result.get(0).title());
        assertEquals("Facial", result.get(1).title());

        verify(treatmentService).findByEmployeeId(employeeId);
    }
}