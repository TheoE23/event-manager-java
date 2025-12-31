package com.theo.EventManager.controller;

import com.theo.EventManager.model.Category;
import com.theo.EventManager.model.Location;
import com.theo.EventManager.repository.CategoryRepository;
import com.theo.EventManager.repository.LocationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    public AdminController(CategoryRepository categoryRepository,
                           LocationRepository locationRepository) {
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public String adminPanel() {
        return "admin";
    }

    @PostMapping("/category")
    public String addCategory(@RequestParam String name) {
        Category category = new Category(name);
        categoryRepository.save(category);
        return "redirect:/admin";
    }

    @PostMapping("/location")
    public String addLocation(@RequestParam String name) {
        Location location = new Location(name);
        locationRepository.save(location);
        return "redirect:/admin";
    }
}