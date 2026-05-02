package com.coffeehaus.controller;

import com.coffeehaus.entity.CoffeeBlend;
import com.coffeehaus.entity.Roaster;
import com.coffeehaus.service.CoffeeBlendService;
import com.coffeehaus.service.RoasterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for CoffeeBlend-related pages.
 * Manages listing, creating, editing blends, and the joined view.
 */
@Controller
@RequestMapping("/blends")
public class CoffeeBlendController {

    private final CoffeeBlendService blendService;
    private final RoasterService roasterService;

    public CoffeeBlendController(CoffeeBlendService blendService,
                                 RoasterService roasterService) {
        this.blendService = blendService;
        this.roasterService = roasterService;
    }

    /**
     * Lists every coffee blend in the system.
     * GET /blends
     */
    @GetMapping
    public String showAllBlends(Model model) {
        model.addAttribute("blendList", blendService.getAllBlends());
        return "blends/list";
    }

    /**
     * Presents a form for creating a new blend.
     * Also loads all roasters so the user can pick one from a dropdown.
     * GET /blends/add
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("blend", new CoffeeBlend());
        model.addAttribute("availableRoasters", roasterService.getAllRoasters());
        return "blends/add";
    }

    /**
     * Handles the form submission for a new blend.
     * POST /blends/save
     */
    @PostMapping("/save")
    public String saveNewBlend(@ModelAttribute("blend") CoffeeBlend blend,
                               @RequestParam("roasterId") Long roasterId,
                               RedirectAttributes redirectAttrs) {
        try {
            // Attach the chosen roaster to this blend
            Roaster selectedRoaster = roasterService.getRoasterById(roasterId)
                    .orElseThrow(() -> new RuntimeException("Selected roaster does not exist."));
            blend.setRoaster(selectedRoaster);
            blendService.createBlend(blend);
            redirectAttrs.addFlashAttribute("successMsg", "Coffee blend created!");
        } catch (RuntimeException ex) {
            redirectAttrs.addFlashAttribute("errorMsg", ex.getMessage());
            return "redirect:/blends/add";
        }
        return "redirect:/blends";
    }

    /**
     * Loads the edit form with existing blend data.
     * GET /blends/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model,
                               RedirectAttributes redirectAttrs) {
        var blendOpt = blendService.getBlendById(id);
        if (blendOpt.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMsg", "Blend not found with ID: " + id);
            return "redirect:/blends";
        }
        model.addAttribute("blend", blendOpt.get());
        model.addAttribute("availableRoasters", roasterService.getAllRoasters());
        return "blends/edit";
    }

    /**
     * Processes the update form.
     * POST /blends/update/{id}
     */
    @PostMapping("/update/{id}")
    public String updateBlend(@PathVariable("id") Long id,
                              @ModelAttribute("blend") CoffeeBlend blend,
                              @RequestParam("roasterId") Long roasterId,
                              RedirectAttributes redirectAttrs) {
        try {
            Roaster selectedRoaster = roasterService.getRoasterById(roasterId)
                    .orElseThrow(() -> new RuntimeException("Selected roaster does not exist."));
            blend.setRoaster(selectedRoaster);
            blendService.updateBlend(id, blend);
            redirectAttrs.addFlashAttribute("successMsg", "Blend updated!");
        } catch (RuntimeException ex) {
            redirectAttrs.addFlashAttribute("errorMsg", ex.getMessage());
            return "redirect:/blends/edit/" + id;
        }
        return "redirect:/blends";
    }

    /**
     * Displays the inner-join view — blends paired with their roaster details.
     * GET /blends/joined
     */
    @GetMapping("/joined")
    public String showJoinedView(Model model) {
        model.addAttribute("joinedResults", blendService.getBlendsWithRoasterInfo());
        return "joined";
    }
}
