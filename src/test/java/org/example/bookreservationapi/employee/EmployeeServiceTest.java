package org.example.bookreservationapi.employee;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.repository.EmployeeRepository;
import org.example.bookreservationapi.employee.service.EmployeeService;
import org.example.bookreservationapi.treament.entity.TreatmentDTO;
import org.example.bookreservationapi.treament.service.TreatmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
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

    @Test
    void testFindById_Success() {
        EmployeeEntity testEmployee = new EmployeeEntity(1L, "john_doe", "password123", "John Doe", "Manager");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));

        EmployeeEntity result = employeeService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("john_doe", result.getUsername());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> employeeService.findById(999L));
        verify(employeeRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAllEmployees() {
        EmployeeEntity employee1 = new EmployeeEntity(1L, "john_doe", "password123", "John Doe", "Manager");
        EmployeeEntity employee2 = new EmployeeEntity(2L, "jane_doe", "password456", "Jane Doe", "Staff");
        List<EmployeeEntity> employees = List.of(employee1, employee2);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeEntity> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testSaveEmployee() {
        EmployeeEntity testEmployee = new EmployeeEntity(1L, "john_doe", "password123", "John Doe", "Manager");
        when(employeeRepository.save(testEmployee)).thenReturn(testEmployee);

        EmployeeEntity result = employeeService.saveEmployee(testEmployee);

        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(employeeRepository, times(1)).save(testEmployee);
    }

    @Test
    void testDeleteEmployee_Success() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).existsById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.existsById(999L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> employeeService.deleteEmployee(999L));
        verify(employeeRepository, times(1)).existsById(999L);
    }

    @Test
    void testFindByUsername() {
        EmployeeEntity testEmployee = new EmployeeEntity(1L, "john_doe", "password123", "John Doe", "Manager");
        when(employeeRepository.findByUsername("john_doe")).thenReturn(Optional.of(testEmployee));

        Optional<EmployeeEntity> result = employeeService.findByUsername("john_doe");

        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getUsername());
        verify(employeeRepository, times(1)).findByUsername("john_doe");
    }
}