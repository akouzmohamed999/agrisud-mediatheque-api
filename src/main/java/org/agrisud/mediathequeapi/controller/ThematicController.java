package org.agrisud.mediathequeapi.controller;

import java.io.IOException;
import java.util.List;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.model.Thematic;
import org.agrisud.mediathequeapi.service.CategoryService;
import org.agrisud.mediathequeapi.service.ThematicService;
import org.agrisud.mediathequeapi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/thematic")
public class ThematicController {
	@Autowired
	ThematicService thematicService;
	@Autowired
	EventCloudService eventCloudService;
	@Autowired
	Utils util;
	
	@PostMapping
	public int addThematic(@RequestBody Thematic thematic) {
		return thematicService.addThematic(thematic);
	}
	
	@DeleteMapping(path ="/{id}")
	public int deleteThematic(@PathVariable("id") Long id) {
		Thematic thematic  = thematicService.getThematicById(id);
		eventCloudService.deleteFile(thematic.getPathImage());
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
	
	@PostMapping(path = "/files" ,consumes = { MediaType.APPLICATION_JSON_VALUE, "multipart/form-data" })
	@ResponseStatus(HttpStatus.OK)
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("path") String path) throws IOException {
		String generatedKey = util.generateKey(32);
		return eventCloudService.uploadFile(multipartFile,path,generatedKey);
    }
}
