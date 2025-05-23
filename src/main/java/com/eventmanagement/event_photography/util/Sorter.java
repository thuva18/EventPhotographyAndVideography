package com.eventmanagement.event_photography.util;

import com.eventmanagement.event_photography.model.Photographer;
import com.eventmanagement.event_photography.model.Videographer;

import java.util.List;

public class Sorter {

    public static List<Photographer> sortPhotographersByRating(List<Photographer> photographers) {
        int n = photographers.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (photographers.get(j).getRating() < photographers.get(j + 1).getRating()) {
                    // Swap
                    Photographer temp = photographers.get(j);
                    photographers.set(j, photographers.get(j + 1));
                    photographers.set(j + 1, temp);
                }
            }
        }
        return photographers;
    }

    public static List<Videographer> sortVideographersByRating(List<Videographer> videographers) {
        int n = videographers.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (videographers.get(j).getRating() < videographers.get(j + 1).getRating()) {
                    // Swap
                    Videographer temp = videographers.get(j);
                    videographers.set(j, videographers.get(j + 1));
                    videographers.set(j + 1, temp);
                }
            }
        }
        return videographers;
    }
}