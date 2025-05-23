package com.eventmanagement.event_photography.model;

public abstract class Feedback {
    private Long id;
    private Long userId;
    private Long photographerId;
    private String comment;
    private int rating;

    public Feedback() {
    }

    public Feedback(Long id, Long userId, Long photographerId, String comment, int rating) {
        this.id = id;
        this.userId = userId;
        this.photographerId = photographerId;
        this.comment = comment;
        this.rating = rating;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPhotographerId() { return photographerId; }
    public void setPhotographerId(Long photographerId) { this.photographerId = photographerId; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public abstract String displayFeedback();
}