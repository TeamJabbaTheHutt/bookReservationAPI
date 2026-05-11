package org.example.bookreservationapi.config;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.repository.EmployeeRepository;
import org.example.bookreservationapi.treament.entity.Treatment;
import org.example.bookreservationapi.treament.repository.TreatmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitApp {

    @Bean
    CommandLineRunner initDatabase(
            TreatmentRepository treatmentRepository,
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            EmployeeEntity employee1 = new EmployeeEntity();
            employee1.setEmployeeName("mette");
            employee1.setUsername("employee1");
            employee1.setRole("ADMIN");
            employee1.setPassword(passwordEncoder.encode("pw"));

            employeeRepository.save(employee1);

            Treatment treatment1 = new Treatment();
            treatment1.setTitle("treatment1");
            treatment1.setDurationMinutes(200.0);
            treatment1.setPrice(300.0);

            Treatment treatment2 = new Treatment();
            treatment2.setTitle("treatment2");
            treatment2.setDurationMinutes(201.0);
            treatment2.setPrice(301.0);

            treatmentRepository.saveAll(List.of(treatment1, treatment2));

        };
    }
}