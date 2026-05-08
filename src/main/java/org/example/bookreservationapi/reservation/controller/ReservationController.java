package org.example.bookreservationapi.reservation.controller;



import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getMyReservations(Authentication authentication) {
        Long employeeId = Long.parseLong(authentication.getName());
        return reservationService.getAllReservationByEmployeeId(employeeId);
    }
}
