package com.eventmanagement.event_photography.service;

import com.eventmanagement.event_photography.model.Photographer;
import com.eventmanagement.event_photography.util.Sorter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotographerService {
    private static final String FILE_PATH = "photographers.txt";

    public List<Photographer> getAllPhotographers() {
        List<Photographer> photographers = new ArrayList<>();
        File file = new File(FILE_PATH);

        System.out.println("Attempting to read photographers from: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());
        System.out.println("File is readable: " + file.canRead());

        if (!file.exists()) {
            System.out.println("photographers.txt does not exist. Creating new empty file.");
            try {
                file.createNewFile();
                System.out.println("Created photographers.txt.");
            } catch (IOException e) {
                System.out.println("Error creating photographers.txt: " + e.getMessage());
                e.printStackTrace();
                return photographers;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 5) { // Expecting 5 fields: id, name, email, specialty, rating
                        System.out.println("Skipping invalid line in photographers.txt: " + line);
                        continue;
                    }
                    Long id = Long.parseLong(parts[0].trim());
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    String specialty = parts[3].trim();
                    double rating = Double.parseDouble(parts[4].trim());
                    photographers.add(new Photographer(id, name, email, specialty, rating));
                } catch (Exception e) {
                    System.out.println("Error parsing line in photographers.txt: " + line + " | Error: " + e.getMessage());
                }
            }
            System.out.println("Photographers loaded: " + photographers.size());
        } catch (IOException e) {
            System.out.println("Error reading photographers.txt: " + e.getMessage());
            e.printStackTrace();
        }

        // Sort photographers by rating using the Sorter class
        return Sorter.sortPhotographersByRating(photographers);
    }

    public Photographer findPhotographerById(Long id) {
        return getAllPhotographers().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void savePhotographer(Photographer photographer) {
        List<Photographer> photographers = getAllPhotographers();
        photographers.add(photographer);
        writePhotographersToFile(photographers);
    }

    public void updatePhotographer(Photographer updatedPhotographer) {
        List<Photographer> photographers = getAllPhotographers();
        photographers.removeIf(p -> p.getId().equals(updatedPhotographer.getId()));
        photographers.add(updatedPhotographer);
        writePhotographersToFile(photographers);
    }

    public void deletePhotographer(Long id) {
        List<Photographer> photographers = getAllPhotographers();
        photographers.removeIf(p -> p.getId().equals(id));
        writePhotographersToFile(photographers);
    }

    private void writePhotographersToFile(List<Photographer> photographers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Photographer p : photographers) {
                writer.write(p.getId() + "," + p.getName() + "," + p.getEmail() + "," + p.getSpecialty() + "," + p.getRating());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to photographers.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}