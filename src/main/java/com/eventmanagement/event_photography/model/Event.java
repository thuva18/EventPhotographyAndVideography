package com.eventmanagement.event_photography.model;

public class Event {
    private Long id;
    private String name;
    private String dateTime;
    private String location;
    private String serviceType;

    public Event() {}

    public Event(Long id, String name, String dateTime, String location, String serviceType) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.location = location;
        this.serviceType = serviceType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
}