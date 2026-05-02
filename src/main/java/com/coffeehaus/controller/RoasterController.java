package com.coffeehaus.controller;

import com.coffeehaus.entity.Roaster;
import com.coffeehaus.service.RoasterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles all web requests related to Roaster entities.
 * Maps URLs to JSP views and processes form submissions.
 */
@Controller
@RequestMapping("/roasters")
public class RoasterController {

    private final RoasterService roasterService;

    public RoasterController(RoasterService roasterService) {
        this.roasterService = roasterService;
    }

    /**
     * Displays the full list of roasters.
     * GET /roasters
     */
    @GetMapping
    public String showAllRoasters(Model model) {
        model.addAttribute("roasterList", roasterService.getAllRoasters());
        return "roasters/list";
    }

    /**
     * Shows an empty form for adding a new roaster.
     * GET /roasters/add
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("roaster", new Roaster());
        return "roasters/add";
    }

    /**
     * Processes the add-roaster form submission.
     * POST /roasters/save
     * Redirects back to the list on success, or shows an error.
     */
    @PostMapping("/save")
    public String saveNewRoaster(@ModelAttribute("roaster") Roaster roaster,
                                 RedirectAttributes redirectAttrs) {
        try {
            roasterService.createRoaster(roaster);
            redirectAttrs.addFlashAttribute("successMsg", "Roaster added successfully!");
        } catch (RuntimeException ex) {
            redirectAttrs.addFlashAttribute("errorMsg", ex.getMessage());
            return "redirect:/roasters/add";
        }
        return "redirect:/roasters";
    }

    /**
     * Loads the edit form pre-populated with an existing roaster's data.
     * GET /roasters/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model,
                               RedirectAttributes redirectAttrs) {
        var roasterOpt = roasterService.getRoasterById(id);
        if (roasterOpt.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMsg", "Roaster not found with ID: " + id);
            return "redirect:/roasters";
        }
        model.addAttribute("roaster", roasterOpt.get());
        return "roasters/edit";
    }

    /**
     * Processes the update form submission.
     * POST /roasters/update/{id}
     */
    @PostMapping("/update/{id}")
    public String updateRoaster(@PathVariable("id") Long id,
                                @ModelAttribute("roaster") Roaster roaster,
                                RedirectAttributes redirectAttrs) {
        try {
            roasterService.updateRoaster(id, roaster);
            redirectAttrs.addFlashAttribute("successMsg", "Roaster updated successfully!");
        } catch (RuntimeException ex) {
            redirectAttrs.addFlashAttribute("errorMsg", ex.getMessage());
            return "redirect:/roasters/edit/" + id;
        }
        return "redirect:/roasters";
    }
}
