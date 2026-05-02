package com.coffeehaus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple controller that serves the landing page.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String landingPage() {
        return "home";
    }
}
