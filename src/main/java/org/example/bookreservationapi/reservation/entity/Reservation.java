package org.example.bookreservationapi.reservation.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(name = "employee_id")
    private Long employeeId;
    private Long treatmentId;

    private LocalDateTime startDateTime;

    private String customerName;
    private String customerEmail;

    public Reservation(Long reservationId, Long employeeId, Long treatmentId, LocalDateTime startDateTime, String customerName, String customerEmail) {
        this.reservationId = reservationId;
        this.employeeId = employeeId;
        this.treatmentId = treatmentId;
        this.startDateTime = startDateTime;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
