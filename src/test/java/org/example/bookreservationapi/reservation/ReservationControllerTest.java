package org.example.bookreservationapi.reservation;

import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/h2init.sql", executionPhase = BEFORE_TEST_METHOD)
@WebMvcTest(ReservationControllerTest.class)
public class ReservationControllerTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

   @Test
    void getListOfReservationByEmployeeIdShouldReturnList() throws Exception {
       List<Reservation> reservations = reservationService.getAllReservationByEmployeeId(1L);
       assertThat(reservations.size()).isEqualTo(4);

       mockMvc.perform(get("/api/reservations/"+1L))
               .andExpect(status().isOk())
               .andExpect(model().attribute("reservationsByEmployee", reservations));

       verify(reservationService).getAllReservationByEmployeeId(1L);
   }

}
