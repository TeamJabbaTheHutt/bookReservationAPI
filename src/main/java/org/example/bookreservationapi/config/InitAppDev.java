package org.example.bookreservationapi.config;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.repository.EmployeeRepository;
import org.example.bookreservationapi.treament.entity.Treatment;
import org.example.bookreservationapi.treament.repository.TreatmentRepository;
import org.example.bookreservationapi.workingDay.enity.WorkingDay;
import org.example.bookreservationapi.workingDay.repository.WorkingDayRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("prod")
public class InitAppDev {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(
            TreatmentRepository treatmentRepository,
            EmployeeRepository employeeRepository,
            WorkingDayRepository workingDayRepository) {
        return args -> {

            EmployeeEntity employee1 = new EmployeeEntity();
            employee1.setEmployeeName("mette");
            employee1.setUsername("employee1");
            employee1.setRole("ADMIN");
            employee1.setPassword("pw");

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

            WorkingDay workingDay1 = new WorkingDay();
            workingDay1.setStartDateTime(LocalDateTime.of(2026, 5, 12, 9, 0));
            workingDay1.setEndDateTime(LocalDateTime.of(2026, 5, 12, 17, 0));
            workingDay1.setEmployee(employee1);

            WorkingDay workingDay2 = new WorkingDay();
            workingDay2.setStartDateTime(LocalDateTime.of(2026, 5, 13, 10, 0));
            workingDay2.setEndDateTime(LocalDateTime.of(2026, 5, 13, 18, 0));
            workingDay2.setEmployee(employee1);

            WorkingDay workingDay3 = new WorkingDay();
            workingDay3.setStartDateTime(LocalDateTime.of(2026, 5, 14, 8, 0));
            workingDay3.setEndDateTime(LocalDateTime.of(2026, 5, 14, 16, 0));
            workingDay3.setEmployee(employee1);

            WorkingDay workingDay4 = new WorkingDay();
            workingDay4.setStartDateTime(LocalDateTime.of(2026, 5, 15, 12, 0));
            workingDay4.setEndDateTime(LocalDateTime.of(2026, 5, 15, 20, 0));
            workingDay4.setEmployee(employee1);

            WorkingDay workingDay5 = new WorkingDay();
            workingDay5.setStartDateTime(LocalDateTime.of(2026, 5, 16, 9, 0));
            workingDay5.setEndDateTime(LocalDateTime.of(2026, 5, 16, 17, 0));
            workingDay5.setEmployee(employee1);

            WorkingDay workingDay6 = new WorkingDay();
            workingDay6.setStartDateTime(LocalDateTime.of(2026, 5, 17, 9, 0));
            workingDay6.setEndDateTime(LocalDateTime.of(2026, 5, 17, 15, 0));
            workingDay6.setEmployee(employee1);

            workingDayRepository.saveAll(List.of(workingDay1, workingDay2, workingDay3, workingDay4, workingDay5, workingDay6));


        };
    }
}

