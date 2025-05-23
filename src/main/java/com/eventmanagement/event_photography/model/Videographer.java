package com.eventmanagement.event_photography.model;

public class Videographer extends ServiceProvider {
    private double rating;

    public Videographer() {
        super();
        this.rating = 0.0;
    }

    public Videographer(Long id, String name, String email) {
        super(id, name, email);
        this.rating = 0.0; // Default rating
    }

    public Videographer(Long id, String name, String email, double rating) {
        super(id, name, email);
        this.rating = rating;
    }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}