package org.example.bookreservationapi.reservation.controller;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/all")
    public List<Reservation> getMyReservations(Authentication authentication) {
        String username = authentication.getName();
         EmployeeEntity employee = reservationService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + username));
        return reservationService.getAllReservationByEmployeeId(employee.getId());
    }

    @GetMapping
    public List<Reservation> getMyReservations(
            Authentication authentication,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        String username = authentication.getName();
        EmployeeEntity employee = reservationService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + username));

        return reservationService.getReservationsForEmployee(employee.getId(), startDate, endDate);
    }

    @GetMapping("/all-reservations")
    public List<Reservation> getAllReservationsEvenIfEmpty() {
        return reservationService.findAllReservationsEvenIfEmpty();
    }

    @GetMapping("/employee/{employeeId}")
    public List<Reservation> getReservationsByEmployeeId(@PathVariable Long employeeId) {
        return reservationService.getAllReservationByEmployeeId(employeeId);
    }

}
