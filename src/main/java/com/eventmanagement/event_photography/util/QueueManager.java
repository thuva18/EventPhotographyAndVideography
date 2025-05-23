package com.eventmanagement.event_photography.util;

import com.eventmanagement.event_photography.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class QueueManager {
    private Booking[] bookingQueue;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public QueueManager() {
        this.capacity = 100; // Fixed capacity for circular queue
        this.bookingQueue = new Booking[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void addBooking(Booking booking) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % capacity;
        bookingQueue[rear] = booking;
        size++;
    }

    public Booking dequeue() {
        if (isEmpty()) {
            return null;
        }
        Booking booking = bookingQueue[front];
        bookingQueue[front] = null;
        front = (front + 1) % capacity;
        size--;
        return booking;
    }

    public void removeBooking(Long bookingId) {
        if (isEmpty()) {
            return;
        }
        int current = front;
        int indexToRemove = -1;
        for (int i = 0; i < size; i++) {
            if (bookingQueue[current] != null && bookingQueue[current].getId().equals(bookingId)) {
                indexToRemove = current;
                break;
            }
            current = (current + 1) % capacity;
        }
        if (indexToRemove != -1) {
            // Shift elements to maintain circular queue
            while (indexToRemove != rear) {
                int next = (indexToRemove + 1) % capacity;
                bookingQueue[indexToRemove] = bookingQueue[next];
                indexToRemove = next;
            }
            bookingQueue[rear] = null;
            rear = (rear - 1 + capacity) % capacity;
            size--;
        }
    }

    public Booking[] getBookingQueue() {
        Booking[] result = new Booking[size];
        int current = front;
        for (int i = 0; i < size; i++) {
            result[i] = bookingQueue[current];
            current = (current + 1) % capacity;
        }
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }
}