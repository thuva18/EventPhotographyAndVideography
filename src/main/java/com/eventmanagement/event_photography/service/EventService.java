package com.eventmanagement.event_photography.service;

import com.eventmanagement.event_photography.model.Booking;
import com.eventmanagement.event_photography.model.Event;
import com.eventmanagement.event_photography.util.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class EventService {
    private Map<Long, Event> events = new HashMap<>();
    private Map<Long, Booking> bookings = new HashMap<>();
    private final QueueManager queueManager;
    private long eventIdCounter = 1;
    private long bookingIdCounter = 1;

    @Autowired
    public EventService(QueueManager queueManager) {
        this.queueManager = queueManager;
        events.put(1L, new Event(1L, "Wedding A", "2025-05-10 14:00:00", "Venue A", "BOTH"));
        events.put(2L, new Event(2L, "Corporate Event B", "2025-05-12 09:00:00", "Venue B", "PHOTOGRAPHY"));
        events.put(3L, new Event(3L, "Birthday Party C", "2025-05-15 18:00:00", "Venue C", "VIDEOGRAPHY"));
        events.put(4L, new Event(4L, "Graduation D", "2025-05-20 11:00:00", "Venue D", "BOTH"));
        events.put(5L, new Event(5L, "Conference E", "2025-05-22 08:00:00", "Venue E", "PHOTOGRAPHY"));
        events.put(6L, new Event(6L, "Engagement F", "2025-05-25 16:00:00", "Venue F", "BOTH"));
        events.put(7L, new Event(7L, "Anniversary G", "2025-05-28 19:00:00", "Venue G", "VIDEOGRAPHY"));
        events.put(8L, new Event(8L, "Product Launch H", "2025-06-01 10:00:00", "Venue H", "PHOTOGRAPHY"));
        events.put(9L, new Event(9L, "Charity Event I", "2025-06-05 13:00:00", "Venue I", "BOTH"));
        events.put(10L, new Event(10L, "Family Reunion J", "2025-06-10 15:00:00", "Venue J", "VIDEOGRAPHY"));
        events.put(11L, new Event(11L, "Festival K", "2025-06-15 12:00:00", "Venue K", "PHOTOGRAPHY"));
        events.put(12L, new Event(12L, "Wedding L", "2025-06-20 17:00:00", "Venue L", "BOTH"));


        // Initialize queue with existing bookings
        for (Booking booking : bookings.values()) {
            queueManager.addBooking(booking);
        }
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events.values());
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }

    public Booking getBookingById(Long id) {
        return bookings.get(id);
    }

    public void saveBooking(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(bookingIdCounter++);
        }
        bookings.put(booking.getId(), booking);
        queueManager.addBooking(booking);
    }

    public void updateBooking(Booking booking) {
        if (booking.getId() != null) {
            bookings.put(booking.getId(), booking);
        }
    }

    public void deleteBooking(Long id) {
        Booking booking = bookings.get(id);
        if (booking != null) {
            bookings.remove(id);
            queueManager.removeBooking(id);
        }
    }

    public List<Booking> getBookingQueue() {
        return Arrays.stream(queueManager.getBookingQueue())
                .filter(booking -> booking != null)
                .collect(Collectors.toList());
    }
}