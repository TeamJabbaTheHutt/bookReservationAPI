package org.example.bookreservationapi.reservation;

import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.entity.ReservationEntity;
import org.example.bookreservationapi.reservation.repository.ReservationRepository;
import org.example.bookreservationapi.reservation.service.ReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmptyCalendarListTest {

    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {

        reservationRepository = mock(ReservationRepository.class);

        reservationService =
                new ReservationService(reservationRepository);
    }

    @Test
    void shouldReturnEmptyListWhenNoReservationsExist() {
        when(reservationRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<Reservation> result =
                reservationService.getAllReservations();

        assertNotNull(result);

        assertTrue(result.isEmpty());

        verify(reservationRepository).findAll();
    }
}