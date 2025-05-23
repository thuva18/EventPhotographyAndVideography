package com.eventmanagement.event_photography.model;

public class AdminUser extends User {
    public AdminUser(Long id, String name, String email, String username, String password) {
        super(id, name, email, username, password, "Admin");
    }

    @Override
    public boolean authenticate(String password) {
        return this.getPassword().equals(password);
    }
}