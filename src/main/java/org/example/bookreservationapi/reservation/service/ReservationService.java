package org.example.bookreservationapi.reservation.service;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.service.EmployeeService;
import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.repository.ReservationRepository;
import org.example.bookreservationapi.treament.entity.Treatment;
import org.example.bookreservationapi.treament.service.TreatmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private EmployeeService employeeService;
    private TreatmentService treatmentService;

    public ReservationService(ReservationRepository reservationRepository, EmployeeService employeeService, TreatmentService treatmentService) {
        this.reservationRepository = reservationRepository;
        this.employeeService = employeeService;
        this.treatmentService = treatmentService;
    }

    public List<Reservation> getReservationsForEmployee(Long employeeId, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return reservationRepository.findByEmployeeId(employeeId);
        }

        // Konverter LocalDate til LocalDateTime så hele dagene inkluderes
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();

        return reservationRepository.findByEmployeeIdAndStartDateTimeBetween(employeeId, start, end);
    }

    public List<Reservation> getAllReservationByEmployeeId(Long employeeId) {
        return reservationRepository.findByEmployeeId(employeeId);
    }

    public Optional<EmployeeEntity> findByUsername(String username) {
        return employeeService.findByUsername(username);
    }

    public List<Reservation> findAllReservationsEvenIfEmpty() {
        return reservationRepository.findAll();
    }

    public Reservation createNewReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation createNewReservation(Long employeeId, Long treatmentId,
                                            LocalDateTime startDateTime,
                                            String customerName, String customerEmail) {
        Treatment treatment = treatmentService.getTreatmentById(treatmentId)
                .orElseThrow(() -> new RuntimeException("Treatment not found"));

        Reservation reservation = new Reservation(employeeId, treatment, startDateTime, customerName, customerEmail);
        return reservationRepository.save(reservation);
    }



}

