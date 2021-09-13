package org.agrisud.mediathequeapi.controller;

import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.agrisud.mediathequeapi.service.ListThematicSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list_thematic_support")
public class ListThematicSupportController {
	
	@Autowired
	ListThematicSupportService listThematicSupportService;
	@PostMapping
	public void addListThematicSupport(@RequestBody ListThematicSupport listThematicSupport) {
		listThematicSupportService.addListThematicSupport(listThematicSupport);
	}
}
