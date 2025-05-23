package com.eventmanagement.event_photography.controller;

import com.eventmanagement.event_photography.model.CustomerUser;
import com.eventmanagement.event_photography.model.User;
import com.eventmanagement.event_photography.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new CustomerUser(0L, "", "", "", ""));
        return "user-register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute CustomerUser user) {
        user.setId((long) (userService.getAllUsers().size() + 1));
        userService.saveUser(user);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user-login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.findUserByUsername(username);
        if (user != null && user.authenticate(password) && "Customer".equals(user.getRole())) {
            // Store user in session
            session.setAttribute("user", user);
            // Redirect to user profile page
            return "redirect:/users/profile/" + user.getId();
        } else {
            // Failed login, show error message
            model.addAttribute("error", "Invalid username or password, or user is not a customer.");
            return "user-login";
        }
    }

    @GetMapping("/profile/{id}")
    public String showProfileForm(@PathVariable Long id, Model model, HttpSession session) {
        // Get the logged-in user from the session
        User loggedInUser = (User) session.getAttribute("user");
        System.out.println("Logged-in user: " + (loggedInUser != null ? loggedInUser.getUsername() : "null"));
        System.out.println("Requested profile ID: " + id);

        // Check if the user is logged in and the ID matches
        if (loggedInUser == null || !loggedInUser.getId().equals(id)) {
            System.out.println("Unauthorized access attempt for ID: " + id);
            model.addAttribute("error", "Unauthorized access or user not logged in.");
            return "user-login";
        }

        // Retrieve the user by ID (should be the same as loggedInUser)
        User user = userService.findUserById(id);
        System.out.println("Retrieved user: " + (user != null ? user.getUsername() : "null"));
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "user-login";
        }

        // Add the user to the model
        model.addAttribute("user", user);
        return "user-profile";
    }

    @PostMapping("/profile/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute CustomerUser user, HttpSession session) {
        // Get the logged-in user from the session
        User loggedInUser = (User) session.getAttribute("user");

        // Check if the user is logged in and the ID matches
        if (loggedInUser == null || !loggedInUser.getId().equals(id)) {
            System.out.println("Unauthorized update attempt for ID: " + id);
            return "redirect:/users/login";
        }

        // Set the ID and update the user
        user.setId(id);
        userService.updateUser(user);

        // Update the session with the updated user
        session.setAttribute("user", user);
        return "redirect:/users/login";
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        userService.deleteUser(id);
        // Invalidate session if the deleted742 user is the logged-in user
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser != null && loggedInUser.getId().equals(id)) {
            session.invalidate();
        }
        return "redirect:/users/login";
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}