package com.eventmanagement.event_photography.service;

import com.eventmanagement.event_photography.model.VerifiedFeedback;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    private final List<VerifiedFeedback> feedbacks = new ArrayList<>();

    public List<VerifiedFeedback> getAllFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    public VerifiedFeedback findFeedbackById(Long id) {
        return feedbacks.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void saveFeedback(VerifiedFeedback feedback) {
        feedbacks.add(feedback);
    }

    public void updateFeedback(VerifiedFeedback feedback) {
        Optional<VerifiedFeedback> existing = feedbacks.stream()
                .filter(f -> f.getId().equals(feedback.getId()))
                .findFirst();
        existing.ifPresent(f -> {
            f.setUserId(feedback.getUserId());
            f.setPhotographerId(feedback.getPhotographerId());
            f.setComment(feedback.getComment());
            f.setRating(feedback.getRating());
            f.setVerified(feedback.isVerified());
        });
    }

    public void deleteFeedback(Long id) {
        feedbacks.removeIf(f -> f.getId().equals(id));
    }
}