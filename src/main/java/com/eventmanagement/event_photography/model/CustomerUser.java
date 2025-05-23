package com.eventmanagement.event_photography.model;

public class CustomerUser extends User {
    public CustomerUser(Long id, String name, String email, String username, String password) {
        super(id, name, email, username, password, "Customer");
    }

    @Override
    public boolean authenticate(String password) {
        return this.getPassword().equals(password);
    }
}