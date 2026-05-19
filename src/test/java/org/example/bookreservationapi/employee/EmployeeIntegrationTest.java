package org.example.bookreservationapi.employee;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.repository.EmployeeRepository;
import org.example.bookreservationapi.workingDay.repository.WorkingDayRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WorkingDayRepository workingDayRepository;

    @BeforeEach
    void setUp() {

        workingDayRepository.deleteAll();

        employeeRepository.deleteAll();

        EmployeeEntity employee = new EmployeeEntity();

        employee.setUsername("john");
        employee.setPassword("1234");
        employee.setEmployeeName("John Doe");
        employee.setRole("ADMIN");

        employeeRepository.save(employee);
    }

    @Test
    void shouldReturnAllEmployees() throws Exception {

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username")
                        .value("john"))
                .andExpect(jsonPath("$[0].employeeName")
                        .value("John Doe"))
                .andExpect(jsonPath("$[0].role")
                        .value("ADMIN"));
    }
    @Test
    void shouldReturnEmployeeById() throws Exception {

        EmployeeEntity employee =
                employeeRepository.findAll().get(0);

        mockMvc.perform(get("/api/employees/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username")
                        .value("john"))
                .andExpect(jsonPath("$.employeeName")
                        .value("John Doe"))
                .andExpect(jsonPath("$.role")
                        .value("ADMIN"));
    }
    @Test
    void shouldReturnEmptyListWhenNoEmployeesExist() throws Exception {

        workingDayRepository.deleteAll();
        employeeRepository.deleteAll();

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
    
}