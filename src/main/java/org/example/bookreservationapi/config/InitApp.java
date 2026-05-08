package org.example.bookreservationapi.config;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitApp {

    @Bean
    CommandLineRunner initDatabase(
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
        };
    }
}