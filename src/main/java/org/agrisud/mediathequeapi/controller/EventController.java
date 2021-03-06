package org.agrisud.mediathequeapi.controller;

import org.agrisud.mediathequeapi.model.Event;
import org.agrisud.mediathequeapi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getEvents();
    }
}
