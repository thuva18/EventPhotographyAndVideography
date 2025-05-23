package com.eventmanagement.event_photography.service;

import com.eventmanagement.event_photography.model.Booking;
import com.eventmanagement.event_photography.util.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueManagerService {
    private final QueueManager queueManager;

    @Autowired
    public QueueManagerService(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    public void addBookingToQueue(Booking booking) {
        queueManager.addBooking(booking);
    }

    public void removeBookingFromQueue(Long bookingId) {
        queueManager.removeBooking(bookingId);
    }

    public Booking processNextBooking() {
        return queueManager.dequeue();
    }

    public List<Booking> getBookingQueue() {
        return Arrays.stream(queueManager.getBookingQueue())
                .filter(booking -> booking != null)
                .collect(Collectors.toList());
    }
}