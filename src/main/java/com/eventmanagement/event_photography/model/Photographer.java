package com.eventmanagement.event_photography.model;

public class Photographer extends ServiceProvider {
    private String specialty;
    private double rating;

    public Photographer() {
        super();
        this.rating = 0.0;
    }

    public Photographer(Long id, String name, String email, String specialty) {
        super(id, name, email);
        this.specialty = specialty;
        this.rating = 0.0; // Default rating
    }

    public Photographer(Long id, String name, String email, String specialty, double rating) {
        super(id, name, email);
        this.specialty = specialty;
        this.rating = rating;
    }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}