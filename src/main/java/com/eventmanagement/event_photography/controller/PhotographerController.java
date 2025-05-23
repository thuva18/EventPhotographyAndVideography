package com.eventmanagement.event_photography.controller;

import com.eventmanagement.event_photography.model.Photographer;
import com.eventmanagement.event_photography.service.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/photographers")
public class PhotographerController {
    @Autowired
    private PhotographerService photographerService;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("photographer", new Photographer(0L, "", "", "", 0.0));
        return "photographer-add";
    }

    @PostMapping("/add")
    public String addPhotographer(@ModelAttribute Photographer photographer) {
        photographer.setId((long) (photographerService.getAllPhotographers().size() + 1));
        photographerService.savePhotographer(photographer);
        return "redirect:/photographers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Photographer photographer = photographerService.findPhotographerById(id);
        model.addAttribute("photographer", photographer != null ? photographer : new Photographer(0L, "", "", "", 0.0));
        return "photographer-edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePhotographer(@PathVariable Long id, @ModelAttribute Photographer photographer) {
        photographer.setId(id);
        photographerService.updatePhotographer(photographer);
        return "redirect:/photographers";
    }

    @GetMapping
    public String listPhotographers(Model model) {
        model.addAttribute("photographers", photographerService.getAllPhotographers());
        return "photographer-list";
    }

    @PostMapping("/delete/{id}")
    public String deletePhotographer(@PathVariable Long id) {
        photographerService.deletePhotographer(id);
        return "redirect:/photographers";
    }
}