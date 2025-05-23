package com.eventmanagement.event_photography.service;

import com.eventmanagement.event_photography.model.AdminUser;
import com.eventmanagement.event_photography.model.CustomerUser;
import com.eventmanagement.event_photography.model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private static final String FILE_PATH = "users.txt";

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Set<Long> userIds = new HashSet<>(); // Track IDs to detect duplicates
        File file = new File(FILE_PATH);

        System.out.println("Attempting to read users from: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());
        System.out.println("File is readable: " + file.canRead());

        if (!file.exists()) {
            System.out.println("users.txt does not exist. Creating new empty file.");
            try {
                file.createNewFile();
                System.out.println("Created users.txt.");
            } catch (IOException e) {
                System.out.println("Error creating users.txt: " + e.getMessage());
                e.printStackTrace();
                return users;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 5) {
                        System.out.println("Skipping invalid line in users.txt: " + line);
                        continue;
                    }
                    Long id = Long.parseLong(parts[0].trim());
                    if (!userIds.add(id)) {
                        System.out.println("Duplicate user ID found in users.txt: " + id);
                        continue; // Skip duplicate IDs
                    }
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    String role = parts[3].trim();
                    String email = parts[4].trim();
                    String name = username; // Default name to username
                    if ("Admin".equalsIgnoreCase(role)) {
                        users.add(new AdminUser(id, name, email, username, password));
                    } else if ("Customer".equalsIgnoreCase(role)) {
                        users.add(new CustomerUser(id, name, email, username, password));
                    } else {
                        System.out.println("Unknown role for user: " + line);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing line in users.txt: " + line + " | Error: " + e.getMessage());
                }
            }
            System.out.println("Users loaded: " + users.size());
        } catch (IOException e) {
            System.out.println("Error reading users.txt: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public User findUserByUsername(String username) {
        return getAllUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username.trim()))
                .findFirst()
                .orElse(null);
    }

    public User findUserById(Long id) {
        return getAllUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void saveUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        writeUsersToFile(users);
    }

    public void updateUser(User updatedUser) {
        List<User> users = getAllUsers();
        users.removeIf(u -> u.getId().equals(updatedUser.getId()));
        users.add(updatedUser);
        writeUsersToFile(users);
    }

    public void deleteUser(Long id) {
        List<User> users = getAllUsers();
        users.removeIf(u -> u.getId().equals(id));
        writeUsersToFile(users);
    }

    private void writeUsersToFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                writer.write(u.getId() + "," + u.getUsername() + "," + u.getPassword() + "," + u.getRole() + "," + u.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to users.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}