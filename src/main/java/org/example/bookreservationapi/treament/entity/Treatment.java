package org.example.bookreservationapi.treament.entity;


import jakarta.persistence.*;

@Entity
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long treatmentId;

    private String title;

    private double durationMinutes;

    private double price;

    public Treatment() {}

    public Treatment(String title, double durationMinutes, double price) {
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.price = price;
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
