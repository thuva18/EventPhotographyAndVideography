package com.eventmanagement.event_photography.model;

public class Booking {
    private Long id;
    private Long eventId;
    private Long userId;
    private Long photographerId;
    private Long videographerId;
    private String serviceType;
    private String bookingDate;

    public Booking() {}

    public Booking(Long id, Long eventId, Long userId, Long photographerId, Long videographerId, String serviceType, String bookingDate) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.photographerId = photographerId;
        this.videographerId = videographerId;
        this.serviceType = serviceType;
        this.bookingDate = bookingDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getPhotographerId() { return photographerId; }
    public void setPhotographerId(Long photographerId) { this.photographerId = photographerId; }
    public Long getVideographerId() { return videographerId; }
    public void setVideographerId(Long videographerId) { this.videographerId = videographerId; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
}