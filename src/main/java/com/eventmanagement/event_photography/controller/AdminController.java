package com.eventmanagement.event_photography.controller;

import com.eventmanagement.event_photography.model.Booking;
import com.eventmanagement.event_photography.service.AdminService;
import com.eventmanagement.event_photography.service.EventService;
import com.eventmanagement.event_photography.service.PhotographerService;
import com.eventmanagement.event_photography.service.QueueManagerService;
import com.eventmanagement.event_photography.service.UserService;
import com.eventmanagement.event_photography.service.VideographerService;
import com.eventmanagement.event_photography.model.Photographer;
import com.eventmanagement.event_photography.model.Videographer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private PhotographerService photographerService;
    @Autowired
    private VideographerService videographerService;
    @Autowired
    private QueueManagerService queueManagerService;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        System.out.println("Showing admin login form. Error: " + (error != null ? "Yes" : "No"));
        if (error != null) {
            model.addAttribute("error", "Invalid username or password, or data retrieval failed.");
        }
        return "admin-login";
    }

    @PostMapping("/login")
    public String loginAdmin(@RequestParam String username, @RequestParam String password) {
        System.out.println("Login attempt for username: " + username + ", password: " + password);
        boolean authenticated = adminService.authenticateAdmin(username, password);
        System.out.println("Authentication result: " + authenticated);
        if (authenticated) {
            System.out.println("Login successful for username: " + username + ", redirecting to dashboard");
            return "redirect:/admin/dashboard";
        } else {
            System.out.println("Login failed for username: " + username + ", redirecting to login with error");
            return "redirect:/admin/login?error";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        System.out.println("Accessing admin dashboard");
        try {
            var users = userService.getAllUsers();
            System.out.println("Retrieved users: " + (users != null ? users.size() : "null"));
            model.addAttribute("users", users != null ? users : new java.util.ArrayList<>());

            var bookings = eventService.getAllBookings();
            System.out.println("Retrieved bookings: " + (bookings != null ? bookings.size() : "null"));
            model.addAttribute("bookings", bookings != null ? bookings : new java.util.ArrayList<>());

            var events = eventService.getAllEvents();
            System.out.println("Retrieved events: " + (events != null ? events.size() : "null"));
            model.addAttribute("events", events != null ? events : new java.util.ArrayList<>());

            var photographers = photographerService.getAllPhotographers();
            System.out.println("Retrieved photographers: " + (photographers != null ? photographers.size() : "null"));
            model.addAttribute("photographers", photographers != null ? photographers : new java.util.ArrayList<>());

            var videographers = videographerService.getAllVideographers();
            System.out.println("Retrieved videographers: " + (videographers != null ? videographers.size() : "null"));
            model.addAttribute("videographers", videographers != null ? videographers : new java.util.ArrayList<>());

            model.addAttribute("newPhotographer", new Photographer());
            model.addAttribute("newVideographer", new Videographer());
            System.out.println("Dashboard data loaded successfully, returning admin-dashboard");
            return "admin-dashboard";
        } catch (Exception e) {
            System.out.println("Error accessing admin dashboard: " + e.getMessage());
            return "redirect:/admin/login?error";
        }
    }

    @PostMapping("/addPhotographer")
    public String addPhotographer(@ModelAttribute Photographer photographer) {
        System.out.println("Adding photographer: " + photographer.getName());
        photographerService.savePhotographer(photographer);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/addVideographer")
    public String addVideographer(@ModelAttribute Videographer videographer) {
        System.out.println("Adding videographer: " + videographer.getName());
        videographerService.saveVideographer(videographer);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/events")
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("bookings", eventService.getAllBookings());
        return "event-list";
    }

    @GetMapping("/queue")
    public String showQueue(Model model) {
        System.out.println("Accessing admin queue visualization");
        System.out.println("Accessing admin events list");
        try {
            var bookings = eventService.getAllBookings();
            System.out.println("Retrieved bookings: " + (bookings != null ? bookings.size() : "null"));
            model.addAttribute("bookings", bookings != null ? bookings : new java.util.ArrayList<>());
            return "admin-event-list";
        } catch (Exception e) {
            System.out.println("Error accessing admin events list: " + e.getMessage());
            return "redirect:/admin/login?error";
        }
    }

    @PostMapping("/queue/process")
    public String processNextBooking() {
        System.out.println("Processing next booking in queue");
        try {
            Booking processedBooking = queueManagerService.processNextBooking();
            if (processedBooking != null) {
                System.out.println("Processed booking ID: " + processedBooking.getId());
                eventService.deleteBooking(processedBooking.getId()); // Remove from bookings
            } else {
                System.out.println("No bookings to process");
            }
            return "redirect:/admin/queue";
        } catch (Exception e) {
            System.out.println("Error processing booking: " + e.getMessage());
            return "redirect:/admin/queue";
        }
    }
}