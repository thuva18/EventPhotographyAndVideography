package com.eventmanagement.event_photography.controller;

import com.eventmanagement.event_photography.model.VerifiedFeedback;
import com.eventmanagement.event_photography.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("feedback", new VerifiedFeedback(0L, 0L, 0L, "", 0, false));
        return "feedback-add";
    }

    @PostMapping("/add")
    public String addFeedback(@ModelAttribute VerifiedFeedback feedback) {
        feedback.setId((long) (feedbackService.getAllFeedbacks().size() + 1));
        feedback.setVerified(true);
        feedbackService.saveFeedback(feedback);
        return "redirect:/feedback";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        VerifiedFeedback feedback = feedbackService.findFeedbackById(id);
        model.addAttribute("feedback", feedback != null ? feedback : new VerifiedFeedback(0L, 0L, 0L, "", 0, false));
        return "feedback-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateFeedback(@PathVariable Long id, @ModelAttribute VerifiedFeedback feedback) {
        feedback.setId(id);
        feedbackService.updateFeedback(feedback);
        return "redirect:/feedback";
    }

    @GetMapping
    public String listFeedback(Model model) {
        try {
            model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
            return "feedback-list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error loading feedback list: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return "redirect:/feedback";
    }
}