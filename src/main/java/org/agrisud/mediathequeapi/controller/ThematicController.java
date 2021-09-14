package org.agrisud.mediathequeapi.controller;

import java.util.List;

import org.agrisud.mediathequeapi.model.Thematic;
import org.agrisud.mediathequeapi.service.CategoryService;
import org.agrisud.mediathequeapi.service.ThematicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/thematic")
public class ThematicController {
	@Autowired
	ThematicService thematicService;
	
	@PostMapping
	public int addThematic(@RequestBody Thematic thematic) {
		return thematicService.addThematic(thematic);
	}
	
	@DeleteMapping(path ="/{id}")
	public int deleteThematic(@PathVariable("id") Long id) {
		return thematicService.deleteThematic(id);
	}
	
	@PutMapping
	public int updateThematic(@RequestBody Thematic thematic) {
		return thematicService.updateThematic(thematic);
	}
	@GetMapping
	public List<Thematic> getAllThematic() {
		return thematicService.getAllThematic();
	}
	
	@GetMapping("/{id}")
	public Thematic getThematicById(@PathVariable("id") Long id) {
		return thematicService.getThematicById(id);
	}
}
