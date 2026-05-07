package org.example.bookreservationapi.reservation;

import java.util.List;

public class ReservationRepository {

    List<Reservation> findByEmployeeId(Integer employeeId);
}
