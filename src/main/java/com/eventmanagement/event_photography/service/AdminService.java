package com.eventmanagement.event_photography.service;

import com.eventmanagement.event_photography.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private UserService userService;

    public boolean authenticateAdmin(String username, String password) {
        System.out.println("Attempting to authenticate admin: " + username);

        User user = userService.findUserByUsername(username);
        if (user == null) {
            System.out.println("Admin authentication failed: User not found for username: " + username);
            return false;
        }

        if (!"Admin".equalsIgnoreCase(user.getRole())) {
            System.out.println("Admin authentication failed: User role is " + user.getRole() + ", expected Admin");
            return false;
        }

        boolean authenticated = user.authenticate(password);
        if (authenticated) {
            System.out.println("Admin authentication successful for username: " + username);
        } else {
            System.out.println("Admin authentication failed: Incorrect password for username: " + username);
        }
        return authenticated;
    }
}