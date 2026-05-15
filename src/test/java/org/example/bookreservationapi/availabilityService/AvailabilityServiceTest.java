package org.example.bookreservationapi.availabilityService;

import org.example.bookreservationapi.availability.AvailabilityService;
import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.service.ReservationService;
import org.example.bookreservationapi.treament.entity.Treatment;
import org.example.bookreservationapi.treament.service.TreatmentService;
import org.example.bookreservationapi.workingDay.enity.WorkingDay;
import org.example.bookreservationapi.workingDay.service.WorkingDayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AvailabilityServiceTest {

    @Mock private WorkingDayService workingDayService;
    @Mock private ReservationService reservationService;
    @Mock private TreatmentService treatmentService;

    @InjectMocks private AvailabilityService availabilityService;

    private static final Long EMPLOYEE_ID = 1L;
    private static final Long TREATMENT_ID = 10L;
    private static final LocalDate DATE = LocalDate.of(2026, 5, 12);

    private Treatment treatment;
    private WorkingDay workingDay;

    @BeforeEach
    void setUp() {
        // Behandling på 45 minutter
        treatment = new Treatment();
        treatment.setTreatmentId(TREATMENT_ID);
        treatment.setTitle("Klipning");
        treatment.setDurationMinutes(45.0);

        // Arbejdsdag fra 09:00 til 17:00
        workingDay = new WorkingDay();
        workingDay.setStartDateTime(LocalDateTime.of(2026, 5, 12, 9, 0));
        workingDay.setEndDateTime(LocalDateTime.of(2026, 5, 12, 17, 0));

        when(treatmentService.getTreatmentById(TREATMENT_ID))
                .thenReturn(Optional.of(treatment));
        when(workingDayService.getWorkingDaysByEmployeeId(EMPLOYEE_ID))
                .thenReturn(List.of(workingDay));
    }

    @Test
    void tomDagGiverManySlots() {
        when(reservationService.getReservationsForEmployee(eq(EMPLOYEE_ID), eq(DATE), eq(DATE)))
                .thenReturn(List.of());

        List<LocalDateTime> slots = availabilityService.getAvailableSlots(EMPLOYEE_ID, TREATMENT_ID, DATE);

        // Arbejdsdag 09:00-17:00, behandling 45 min, interval 15 min
        // Sidste slot der kan starte: 16:15 (16:15+45=17:00 OK)
        // Antal slots fra 09:00 til 16:15 i 15-min intervaller = 30
        assertThat(slots).hasSize(30);
        assertThat(slots.get(0)).isEqualTo(LocalDateTime.of(2026, 5, 12, 9, 0));
        assertThat(slots.get(slots.size() - 1)).isEqualTo(LocalDateTime.of(2026, 5, 12, 16, 15));
    }

    @Test
    void slotsOverlapperIkkeEksisterendeBooking() {
        // Eksisterende reservation 10:00-10:45
        Reservation existing = new Reservation();
        existing.setEmployeeId(EMPLOYEE_ID);
        existing.setTreatment(treatment);
        existing.setStartDateTime(LocalDateTime.of(2026, 5, 12, 10, 0));

        when(reservationService.getReservationsForEmployee(eq(EMPLOYEE_ID), eq(DATE), eq(DATE)))
                .thenReturn(List.of(existing));

        List<LocalDateTime> slots = availabilityService.getAvailableSlots(EMPLOYEE_ID, TREATMENT_ID, DATE);

        // 09:30 + 45min = 10:15 → overlapper med 10:00-10:45 → IKKE i listen
        assertThat(slots).doesNotContain(LocalDateTime.of(2026, 5, 12, 9, 30));
        // 09:15 + 45min = 10:00 → ingen overlap → I listen
        assertThat(slots).contains(LocalDateTime.of(2026, 5, 12, 9, 15));
        // 10:00, 10:30 → overlapper → IKKE i listen
        assertThat(slots).doesNotContain(LocalDateTime.of(2026, 5, 12, 10, 0));
        assertThat(slots).doesNotContain(LocalDateTime.of(2026, 5, 12, 10, 30));
        // 10:45 → ingen overlap → I listen
        assertThat(slots).contains(LocalDateTime.of(2026, 5, 12, 10, 45));
    }

    @Test
    void slotKanIkkeStarteEfterArbejdsdagensSlut() {
        when(reservationService.getReservationsForEmployee(eq(EMPLOYEE_ID), eq(DATE), eq(DATE)))
                .thenReturn(List.of());

        List<LocalDateTime> slots = availabilityService.getAvailableSlots(EMPLOYEE_ID, TREATMENT_ID, DATE);

        // 16:30 + 45min = 17:15 → efter arbejdsdagens slut → IKKE i listen
        assertThat(slots).doesNotContain(LocalDateTime.of(2026, 5, 12, 16, 30));
        // 16:15 + 45min = 17:00 → præcis grænsen → I listen
        assertThat(slots).contains(LocalDateTime.of(2026, 5, 12, 16, 15));
    }

    @Test
    void ingenArbejdsdagGiverTomListe() {
        when(workingDayService.getWorkingDaysByEmployeeId(EMPLOYEE_ID))
                .thenReturn(List.of());

        List<LocalDateTime> slots = availabilityService.getAvailableSlots(EMPLOYEE_ID, TREATMENT_ID, DATE);

        assertThat(slots).isEmpty();
    }
}
