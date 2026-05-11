package org.example.bookreservationapi.reservation;

import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.repository.ReservationRepository;
import org.example.bookreservationapi.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyCalendarListTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldReturnEmptyListWhenNoReservationsExist() {

        when(reservationRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<Reservation> result =
                reservationService.findAllReservationsEvenIfEmpty();

        assertTrue(result.isEmpty());

        verify(reservationRepository).findAll();
    }
}