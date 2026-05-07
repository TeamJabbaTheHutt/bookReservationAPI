package org.example.bookreservationapi;

import org.example.bookreservationapi.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

//
//public class EmployeeServiceTest() {
//
//    private EmployeeRepository employeeRepository;
//    private EmployeeService employeeService;
//
//
//    @BeforeEach
//    public void setUp() {
//        employeeRepository = mock(EmployeeRepository.class);
//        employeeService = new employeeService(employeeRepository)
//    }
//
//    @Test
//    void shouldFindEmployeeById() {
//
//        employeeEntity employee = new employeeEntity();
//
//        employee.setId(1L);
//        employee.setEmployeeName("John");
//        employee.setRole("ADMIN");
//
//        when(employeeRepository.findById(1L))
//                .thenReturn(Optional.of(employee));
//
//
//        employeeDTO result = employeeService.findById(1);
//
//
//        assertNotNull(result);
//        assertEquals("John", result.getEmployeeName());
//        assertEquals("ADMIN", result.getRole());
//
//        verify(employeeRepository).findById(1L);
//
//    }
//
//}
