package org.example.bookreservationapi.reservation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservationByEmployeeId(Long employeeId) {
        return reservationRepository.findByEmployeeId(employeeId);
    }
}
