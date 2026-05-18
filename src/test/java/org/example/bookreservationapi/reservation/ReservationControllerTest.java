package org.example.bookreservationapi.reservation;


import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.reservation.controller.ReservationController;
import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.service.ReservationService;
import org.example.bookreservationapi.security.SecurityConfig;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    @Test
    void getAllReservations_shouldReturnList() throws Exception {

        Reservation reservation = new Reservation();
        reservation.setEmployeeId(1L);

        List<Reservation> reservations = List.of(reservation);

        when(reservationService.findAllReservationsEvenIfEmpty())
                .thenReturn(reservations);

        mockMvc.perform(get("/api/reservations/all-reservations"))
                .andExpect(status().isOk());

        verify(reservationService).findAllReservationsEvenIfEmpty();
    }
}