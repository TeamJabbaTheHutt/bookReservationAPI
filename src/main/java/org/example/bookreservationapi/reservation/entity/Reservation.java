package org.example.bookreservationapi.reservation.entity;


import jakarta.persistence.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(name = "employee_id")
    private Long employeeId;   // ← skal hedde dette
    private Long treatmentId;

    public Reservation(Long reservationId, Long employeeId, Long treatmentId) {
        this.reservationId = reservationId;
        this.employeeId = employeeId;
        this.treatmentId = treatmentId;
    }

    public Reservation() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }
}
