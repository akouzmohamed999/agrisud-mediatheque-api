package org.agrisud.mediathequeapi.controller;

import java.util.List;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.model.Event;
import org.agrisud.mediathequeapi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	EventService eventService;

	@Autowired
	EventCloudService eventCloudService;

	@GetMapping
	public List<Event> getEvents() {
		return eventService.getEvents();
	}

	@GetMapping("/files")
	public List<String> getFiles() {
		return eventCloudService.getFolders();
	}

}
