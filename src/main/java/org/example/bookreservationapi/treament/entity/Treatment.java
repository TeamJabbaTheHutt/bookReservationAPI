package org.example.bookreservationapi.treament.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.reservation.entity.Reservation;

import java.util.List;

@Entity
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long treatmentId;

    private String title;

    private double durationMinutes;

    private double price;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "employee_treatment",
            joinColumns = @JoinColumn(name = "treatment_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<EmployeeEntity> employees;

    @JsonManagedReference
    @OneToMany(mappedBy = "treatment")
    private List<Reservation> reservations;


    public Treatment() {}

    public Treatment(Long treatmentId, String title, double durationMinutes, double price, List<EmployeeEntity> employees, List<Reservation> reservations) {
        this.treatmentId = treatmentId;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.price = price;
        this.employees = employees;
        this.reservations = reservations;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public List<EmployeeEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeEntity> employees) {
        this.employees = employees;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(double durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }
}
