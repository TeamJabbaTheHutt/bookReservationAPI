package org.example.bookreservationapi.config;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.repository.EmployeeRepository;
import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.repository.ReservationRepository;
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
@Profile("dev")
public class InitAppDev {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(
            TreatmentRepository treatmentRepository,
            EmployeeRepository employeeRepository,
            WorkingDayRepository workingDayRepository,
            ReservationRepository reservationRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            // ============ BEHANDLERE ============
            EmployeeEntity mette = new EmployeeEntity();
            mette.setEmployeeName("Mette Hansen");
            mette.setUsername("employee1");
            mette.setRole("ADMIN");
            mette.setPassword(passwordEncoder.encode("pw"));

            EmployeeEntity laura = new EmployeeEntity();
            laura.setEmployeeName("Laura Jensen");
            laura.setUsername("employee2");
            laura.setRole("ADMIN");
            laura.setPassword(passwordEncoder.encode("pw"));

            EmployeeEntity niklas = new EmployeeEntity();
            niklas.setEmployeeName("Niklas LOoOoOng Slong");
            niklas.setUsername("employee3");
            niklas.setRole("ADMIN");
            niklas.setPassword(passwordEncoder.encode("pw"));

            employeeRepository.saveAll(List.of(mette, laura, niklas));

            // ============ BEHANDLINGER ============
            Treatment klipning = new Treatment();
            klipning.setTitle("Klipning");
            klipning.setDurationMinutes(30.0);
            klipning.setPrice(350.0);
            klipning.setEmployees(List.of(mette, laura, niklas));  // alle udfører

            Treatment farvning = new Treatment();
            farvning.setTitle("Farvning");
            farvning.setDurationMinutes(90.0);
            farvning.setPrice(800.0);
            farvning.setEmployees(List.of(mette, laura));  // kun Mette og Laura

            Treatment striber = new Treatment();
            striber.setTitle("Striber");
            striber.setDurationMinutes(120.0);
            striber.setPrice(1100.0);
            striber.setEmployees(List.of(mette));  // kun Mette

            Treatment fonering = new Treatment();
            fonering.setTitle("Føning og styling");
            fonering.setDurationMinutes(45.0);
            fonering.setPrice(400.0);
            fonering.setEmployees(List.of(laura, niklas));  // Laura og Niklas

            Treatment skaeg = new Treatment();
            skaeg.setTitle("Skægtrim");
            skaeg.setDurationMinutes(20.0);
            skaeg.setPrice(200.0);
            skaeg.setEmployees(List.of(niklas));  // kun Niklas

            treatmentRepository.saveAll(List.of(klipning, farvning, striber, fonering, skaeg));

            // ============ ARBEJDSDAGE ============
            // Mette - mandag til fredag, 9-17
            createWorkingDays(workingDayRepository, mette,
                    LocalDateTime.of(2026, 5, 11, 9, 0),
                    LocalDateTime.of(2026, 5, 11, 17, 0));
            createWorkingDays(workingDayRepository, mette,
                    LocalDateTime.of(2026, 5, 12, 9, 0),
                    LocalDateTime.of(2026, 5, 12, 17, 0));
            createWorkingDays(workingDayRepository, mette,
                    LocalDateTime.of(2026, 5, 13, 9, 0),
                    LocalDateTime.of(2026, 5, 13, 17, 0));
            createWorkingDays(workingDayRepository, mette,
                    LocalDateTime.of(2026, 5, 14, 9, 0),
                    LocalDateTime.of(2026, 5, 14, 17, 0));
            createWorkingDays(workingDayRepository, mette,
                    LocalDateTime.of(2026, 5, 22, 9, 0),
                    LocalDateTime.of(2026, 5, 22, 17, 0));

            // Laura - mandag til lørdag, 10-18
            createWorkingDays(workingDayRepository, laura,
                    LocalDateTime.of(2026, 5, 11, 10, 0),
                    LocalDateTime.of(2026, 5, 11, 18, 0));
            createWorkingDays(workingDayRepository, laura,
                    LocalDateTime.of(2026, 5, 12, 10, 0),
                    LocalDateTime.of(2026, 5, 12, 18, 0));
            createWorkingDays(workingDayRepository, laura,
                    LocalDateTime.of(2026, 5, 13, 10, 0),
                    LocalDateTime.of(2026, 5, 13, 18, 0));
            createWorkingDays(workingDayRepository, laura,
                    LocalDateTime.of(2026, 5, 14, 10, 0),
                    LocalDateTime.of(2026, 5, 14, 18, 0));
            createWorkingDays(workingDayRepository, laura,
                    LocalDateTime.of(2026, 5, 15, 10, 0),
                    LocalDateTime.of(2026, 5, 15, 18, 0));
            createWorkingDays(workingDayRepository, laura,
                    LocalDateTime.of(2026, 5, 16, 9, 0),
                    LocalDateTime.of(2026, 5, 16, 15, 0));  // lørdag kortere

            // Niklas - tirsdag til lørdag, 8-16
            createWorkingDays(workingDayRepository, niklas,
                    LocalDateTime.of(2026, 5, 12, 8, 0),
                    LocalDateTime.of(2026, 5, 12, 16, 0));
            createWorkingDays(workingDayRepository, niklas,
                    LocalDateTime.of(2026, 5, 13, 8, 0),
                    LocalDateTime.of(2026, 5, 13, 16, 0));
            createWorkingDays(workingDayRepository, niklas,
                    LocalDateTime.of(2026, 5, 14, 8, 0),
                    LocalDateTime.of(2026, 5, 14, 16, 0));
            createWorkingDays(workingDayRepository, niklas,
                    LocalDateTime.of(2026, 5, 15, 8, 0),
                    LocalDateTime.of(2026, 5, 15, 16, 0));
            createWorkingDays(workingDayRepository, niklas,
                    LocalDateTime.of(2026, 5, 16, 9, 0),
                    LocalDateTime.of(2026, 5, 16, 14, 0));

            // ============ EKSISTERENDE RESERVATIONER ============
            Reservation r1 = new Reservation();
            r1.setEmployeeId(mette.getId());
            r1.setTreatment(klipning);
            r1.setStartDateTime(LocalDateTime.of(2026, 5, 12, 10, 0));
            r1.setCustomerName("Anders And");
            r1.setCustomerEmail("anders@duckburg.com");

            Reservation r2 = new Reservation();
            r2.setEmployeeId(mette.getId());
            r2.setTreatment(farvning);
            r2.setStartDateTime(LocalDateTime.of(2026, 5, 12, 13, 0));
            r2.setCustomerName("Mickey Mouse");
            r2.setCustomerEmail("mickey@disney.com");

            Reservation r3 = new Reservation();
            r3.setEmployeeId(laura.getId());
            r3.setTreatment(fonering);
            r3.setStartDateTime(LocalDateTime.of(2026, 5, 12, 11, 0));
            r3.setCustomerName("Donald Duck");
            r3.setCustomerEmail("donald@duckburg.com");

            reservationRepository.saveAll(List.of(r1, r2, r3));

            System.out.println("=== Database initialized (dev) ===");
            System.out.println("Employees: " + employeeRepository.count());
            System.out.println("Treatments: " + treatmentRepository.count());
            System.out.println("Working days: " + workingDayRepository.count());
            System.out.println("Reservations: " + reservationRepository.count());
        };
    }

    private void createWorkingDays(WorkingDayRepository repo, EmployeeEntity employee,
                                   LocalDateTime start, LocalDateTime end) {
        WorkingDay wd = new WorkingDay();
        wd.setStartDateTime(start);
        wd.setEndDateTime(end);
        wd.setEmployee(employee);
        repo.save(wd);
    }
}
