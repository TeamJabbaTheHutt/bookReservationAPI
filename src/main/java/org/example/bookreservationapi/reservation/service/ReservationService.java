package org.example.bookreservationapi.reservation.service;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.service.EmployeeService;
import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private EmployeeService employeeService;

    public ReservationService(ReservationRepository reservationRepository, EmployeeService employeeService) {
        this.reservationRepository = reservationRepository;
        this.employeeService = employeeService;
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




}

