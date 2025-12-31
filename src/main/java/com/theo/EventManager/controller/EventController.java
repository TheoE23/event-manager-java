package com.theo.EventManager.controller;

import com.theo.EventManager.model.Event;
import com.theo.EventManager.model.Role;
import com.theo.EventManager.model.User;
import com.theo.EventManager.repository.CategoryRepository;
import com.theo.EventManager.repository.EventRepository;
import com.theo.EventManager.repository.LocationRepository;
import com.theo.EventManager.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    public EventController(EventRepository eventRepository,
                           UserRepository userRepository, CategoryRepository categoryRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/events/new")
    public String createEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("locations", locationRepository.findAll());
        return "event-form";
    }

    @PostMapping("/events")
    public String createEvent(@ModelAttribute Event event,
                              @AuthenticationPrincipal User user) {

        event.setUser(user);
        eventRepository.save(event);
        return "redirect:/events";
    }

    @GetMapping("/events")
    public String listEvents(
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Page<Event> events = eventRepository.findAll(PageRequest.of(page, 5));
        model.addAttribute("events", events);
        return "events";
    }

    @PostMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id,
                              @AuthenticationPrincipal User currentUser,
                              RedirectAttributes redirectAttributes) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event"));

        boolean isOwner = event.getUser().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "You are not allowed to delete this event."
            );
            return "redirect:/events";
        }

        eventRepository.delete(event);
        redirectAttributes.addFlashAttribute("success", "Event deleted.");
        return "redirect:/events";
    }

    @GetMapping("/events/edit/{id}")
    public String editEventForm(@PathVariable Long id,
                                @AuthenticationPrincipal User user,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event"));

        boolean isOwner = event.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "You are not allowed to edit this event."
            );
            return "redirect:/events";
        }

        model.addAttribute("event", event);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("locations", locationRepository.findAll());

        return "event-edit";
    }

    @PostMapping("/events/update/{id}")
    public String updateEvent(
            @PathVariable Long id,
            @ModelAttribute Event updatedEvent
    ) {
        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event id"));

        existing.setTitle(updatedEvent.getTitle());
        existing.setDescription(updatedEvent.getDescription());
        existing.setCategory(updatedEvent.getCategory());
        existing.setLocation(updatedEvent.getLocation());

        eventRepository.save(existing);

        return "redirect:/events";
    }


}
