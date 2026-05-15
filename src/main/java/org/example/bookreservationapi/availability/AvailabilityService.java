package org.example.bookreservationapi.availability;

import org.example.bookreservationapi.reservation.entity.Reservation;
import org.example.bookreservationapi.reservation.service.ReservationService;
import org.example.bookreservationapi.treament.entity.Treatment;
import org.example.bookreservationapi.treament.service.TreatmentService;
import org.example.bookreservationapi.workingDay.enity.WorkingDay;
import org.example.bookreservationapi.workingDay.service.WorkingDayService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilityService {

    private static final int SLOT_INTERVAL_MINUTES = 15;

    private final WorkingDayService workingDayService;
    private final ReservationService reservationService;
    private final TreatmentService treatmentService;

    public AvailabilityService(
            WorkingDayService workingDayService,
            ReservationService reservationService,
            TreatmentService treatmentService) {
        this.workingDayService = workingDayService;
        this.reservationService = reservationService;
        this.treatmentService = treatmentService;
    }

    public List<LocalDateTime> getAvailableSlots(Long employeeId, Long treatmentId, LocalDate date) {

        // Henter behandlingens varighed med getDuration
        Treatment treatment = treatmentService.getTreatmentById(treatmentId)
                .orElseThrow(() -> new RuntimeException("Treatment not found"));
        int treatmentDuration = (int) treatment.getDurationMinutes();

        // Henter arbejdsdag for behandler på den dato
        WorkingDay workingDay = findWorkingDayForDate(employeeId, date);
        if (workingDay == null) {
            return List.of();
        }

        // Henter eksisterende reservationer for behandleren den dag
        List<Reservation> existingReservations = reservationService
                .getReservationsForEmployee(employeeId, date, date);

        // Beregner ledige slots via liste af tidspunkter
        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDateTime slotStart = workingDay.getStartDateTime();
        LocalDateTime workingDayEnd = workingDay.getEndDateTime();

        while (true) {
            LocalDateTime slotEnd = slotStart.plusMinutes(treatmentDuration);

            if (slotEnd.isAfter(workingDayEnd)) {
                break;
            }

            if (!overlapsWithExistingReservations(slotStart, slotEnd, existingReservations)) {
                availableSlots.add(slotStart);
            }

            slotStart = slotStart.plusMinutes(SLOT_INTERVAL_MINUTES);
        }

        return availableSlots;
    }

    private WorkingDay findWorkingDayForDate(Long employeeId, LocalDate date) {
        return workingDayService.getWorkingDaysByEmployeeId(employeeId).stream()
                .filter(wd -> wd.getStartDateTime().toLocalDate().equals(date))
                .findFirst()
                .orElse(null);
    }

    private boolean overlapsWithExistingReservations(
            LocalDateTime slotStart,
            LocalDateTime slotEnd,
            List<Reservation> existingReservations) {

        for (Reservation reservation : existingReservations) {
            LocalDateTime resStart = reservation.getStartDateTime();
            LocalDateTime resEnd = resStart.plusMinutes(getReservationDuration(reservation));

            if (slotStart.isBefore(resEnd) && slotEnd.isAfter(resStart)) {
                return true;
            }
        }
        return false;
    }

    private int getReservationDuration(Reservation reservation) {
        return (int) reservation.getTreatment().getDurationMinutes();
    }
}
