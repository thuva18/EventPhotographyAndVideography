package com.eventmanagement.event_photography.controller;

import com.eventmanagement.event_photography.model.Booking; import com.eventmanagement.event_photography.model.Event; import com.eventmanagement.event_photography.service.EventService; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/events") public class EventController { @Autowired private EventService eventService;

    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("bookings", eventService.getAllBookings());
        return "event-list";
    }

    @GetMapping("/book")
    public String showBookForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("events", eventService.getAllEvents());
        return "event-book";
    }

    @PostMapping("/book")
    public String bookEvent(@ModelAttribute Booking booking) {
        eventService.saveBooking(booking);
        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Booking booking = eventService.getBookingById(id);
        model.addAttribute("booking", booking != null ? booking : new Booking());
        return "event-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBooking(@PathVariable Long id, @ModelAttribute Booking booking) {
        booking.setId(id);
        eventService.updateBooking(booking);
        return "redirect:/events";
    }

    @PostMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        eventService.deleteBooking(id);
        return "redirect:/events";
    }

    @GetMapping("/admin/queue")
    public String showAdminQueue(Model model) {
        model.addAttribute("queue", eventService.getBookingQueue());
        return "admin-queue";
    }

    @PostMapping("/admin/queue/delete")
    public String deleteFromQueue(@RequestParam Long id) {
        eventService.deleteBooking(id);
        return "redirect:/events/admin/queue";
    }

}