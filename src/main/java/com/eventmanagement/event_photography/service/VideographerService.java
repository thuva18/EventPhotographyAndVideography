package com.eventmanagement.event_photography.service;

import com.eventmanagement.event_photography.model.Videographer;
import com.eventmanagement.event_photography.util.Sorter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideographerService {
    private static final String FILE_PATH = "videographers.txt";

    public List<Videographer> getAllVideographers() {
        List<Videographer> videographers = new ArrayList<>();
        File file = new File(FILE_PATH);

        System.out.println("Attempting to read videographers from: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());
        System.out.println("File is readable: " + file.canRead());

        if (!file.exists()) {
            System.out.println("videographers.txt does not exist. Creating new empty file.");
            try {
                file.createNewFile();
                System.out.println("Created videographers.txt.");
            } catch (IOException e) {
                System.out.println("Error creating videographers.txt: " + e.getMessage());
                e.printStackTrace();
                return videographers;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 4) { // Expecting 4 fields: id, name, email, rating
                        System.out.println("Skipping invalid line in videographers.txt: " + line);
                        continue;
                    }
                    Long id = Long.parseLong(parts[0].trim());
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    double rating = Double.parseDouble(parts[3].trim());
                    videographers.add(new Videographer(id, name, email, rating));
                } catch (Exception e) {
                    System.out.println("Error parsing line in videographers.txt: " + line + " | Error: " + e.getMessage());
                }
            }
            System.out.println("Videographers loaded: " + videographers.size());
        } catch (IOException e) {
            System.out.println("Error reading videographers.txt: " + e.getMessage());
            e.printStackTrace();
        }

        // Sort videographers by rating using the Sorter class
        return Sorter.sortVideographersByRating(videographers);
    }

    public Videographer findVideographerById(Long id) {
        return getAllVideographers().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void saveVideographer(Videographer videographer) {
        List<Videographer> videographers = getAllVideographers();
        videographers.add(videographer);
        writeVideographersToFile(videographers);
    }

    public void updateVideographer(Videographer updatedVideographer) {
        List<Videographer> videographers = getAllVideographers();
        videographers.removeIf(v -> v.getId().equals(updatedVideographer.getId()));
        videographers.add(updatedVideographer);
        writeVideographersToFile(videographers);
    }

    public void deleteVideographer(Long id) {
        List<Videographer> videographers = getAllVideographers();
        videographers.removeIf(v -> v.getId().equals(id));
        writeVideographersToFile(videographers);
    }

    private void writeVideographersToFile(List<Videographer> videographers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Videographer v : videographers) {
                writer.write(v.getId() + "," + v.getName() + "," + v.getEmail() + "," + v.getRating());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to videographers.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}