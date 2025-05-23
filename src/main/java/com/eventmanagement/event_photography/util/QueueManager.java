package com.eventmanagement.event_photography.util;

import com.eventmanagement.event_photography.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class QueueManager {
    private Booking[] bookingQueue;
    private int front;
    private int rear;
    private int nItems;
    private int maxSize;

    public QueueManager() {
        this.maxSize = 100; // Fixed capacity for circular queue
        this.bookingQueue = new Booking[maxSize];
        this.front = 0;
        this.rear = -1;
        this.nItems = 0;
    }

    public void addBooking(Booking booking) {
        if (isFull())
            throw new IllegalStateException("Queue is full");

        rear = (rear + 1) % maxSize;
        bookingQueue[rear] = booking;
         nItems++;
    }

    public Booking dequeue() {
        if (isEmpty())
            return null;

        Booking booking = bookingQueue[front];
        bookingQueue[front] = null;
        front = (front + 1) % maxSize;
        nItems--;
        return booking;
    }

    public void removeBooking(Long bookingId) {
        if (isEmpty()) {
            return;
        }
        int current = front;
        int indexToRemove = -1;
        for (int i = 0; i < nItems; i++) {
            if (bookingQueue[current] != null && bookingQueue[current].getId().equals(bookingId)) {
                indexToRemove = current;
                break;
            }
            current = (current + 1) % maxSize;
        }
        if (indexToRemove != -1) {
            // Shift elements to maintain circular queue
            while (indexToRemove != rear) {
                int next = (indexToRemove + 1) % maxSize;
                bookingQueue[indexToRemove] = bookingQueue[next];
                indexToRemove = next;
            }
            bookingQueue[rear] = null;
            rear = (rear - 1 + maxSize) % maxSize;
            nItems--;
        }
    }

    public Booking[] getBookingQueue() {
        Booking[] result = new Booking[nItems];
        int current = front;
        for (int i = 0; i < nItems; i++) {
            result[i] = bookingQueue[current];
            current = (current + 1) % maxSize;
        }
        return result;
    }

    public boolean isEmpty() {
        return nItems == 0;
    }

    public boolean isFull() {
        return nItems == maxSize;
    }

    public int size() {
        return nItems;
    }
}
